package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;
import model.*;
import view.DashboardView;
import java.util.List;
import java.util.Optional;

public class Main extends Application {

    private TruckPriorityQueue truckQueue = new TruckPriorityQueue();
    private WarehouseMatrix warehouse = new WarehouseMatrix(3, 3);
    private UndoStack undoStack = new UndoStack();
    private ProcessedHistoryList historyList = new ProcessedHistoryList();
    private AuditDoubleList auditList = new AuditDoubleList();
    private InspectorCircularList inspectorList = new InspectorCircularList();
    private VehicleCatalogList vehicleCatalog = new VehicleCatalogList();
    private CenterStatistics stats = new CenterStatistics();

    private int dockCounter = 0;
    private String auditorName = "Auditor General";
    private boolean isTestingMode = true;

    private DashboardView view;

    @Override
    public void start(Stage primaryStage) {
        if (!showInitialConfigurationDialogs()) return;

        primaryStage.setTitle("MSCABA — Centro de Recolección Agroindustrial");

        vehicleCatalog.addVehicleType("Sencillo", 3.5);
        vehicleCatalog.addVehicleType("Doble Troque", 7.0);
        vehicleCatalog.addVehicleType("Tractocamion", 12.0);

        if (isTestingMode) {
            loadTestingData();
        }

        view = new DashboardView(auditorName);
        initControllerActions();
        refreshUI();

        Scene scene = new Scene(view.getRoot(), 1100, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        if (isTestingMode) {
            view.setNotification("🚀 Modo Pruebas: Múltiples camiones en cola y slots ocupados cargados.");
        } else {
            view.setNotification("✨ Modo Uso activado: Sistema limpio listo para operar.");
        }
    }

    private boolean showInitialConfigurationDialogs() {
        TextInputDialog auditorDialog = new TextInputDialog("Auditor General");
        auditorDialog.setTitle("Project MSCABA - Configuración");
        auditorDialog.setHeaderText("Configuración de Sesión del Auditor");
        auditorDialog.setContentText("Nombre completo del auditor:");
        Optional<String> resultName = auditorDialog.showAndWait();
        if (resultName.isPresent() && !resultName.get().trim().isEmpty()) {
            auditorName = resultName.get().trim();
        } else {
            return false;
        }

        List<String> modes = List.of("1. Modo Uso (Limpio)", "2. Modo Pruebas (Robusto)");
        ChoiceDialog<String> modeDialog = new ChoiceDialog<>(modes.get(1), modes);
        modeDialog.setTitle("Project MSCABA - Modo");
        modeDialog.setHeaderText("Seleccione perfil de inicialización");
        Optional<String> resultMode = modeDialog.showAndWait();
        if (resultMode.isPresent()) {
            isTestingMode = resultMode.get().startsWith("2");
            return true;
        }
        return false;
    }

    private void loadTestingData() {
        // Inspectores iniciales en la lista circular
        inspectorList.addInspector("Ing. Carlos Pérez");
        inspectorList.addInspector("Dra. Sofía Gómez");
        inspectorList.addInspector("Lic. Mario Ramírez");

        // --- 1. MÁS CAMIONES EN LA COLA DE ESPERA (Prioritarios y Normales) ---
        truckQueue.enqueueTruck(new Truck("FRE-101", "Perecedero", "Sencillo", 3.5, 10.0, 1));
        truckQueue.enqueueTruck(new Truck("MIL-202", "Perecedero", "Doble Troque", 7.0, 18.0, 1));
        truckQueue.enqueueTruck(new Truck("GRA-303", "Grano", "Tractocamion", 12.0, 38.0, 2));
        truckQueue.enqueueTruck(new Truck("INS-404", "Insumo", "Sencillo", 3.5, 9.5, 3));
        truckQueue.enqueueTruck(new Truck("CAFE-505", "Grano", "Doble Troque", 7.0, 24.0, 2));

        // --- 2. OCUPAR SLOTS EN LA BODEGA PARA PRUEBAS (Matriz 2D) ---
        // Simulamos el procesamiento previo de algunos camiones para que la bodega tenga celdas ocupadas y libres
        Truck dummy1 = new Truck("PRE-001", "Perecedero", "Sencillo", 3.5, 11.0, 1);
        Truck dummy2 = new Truck("GRA-002", "Grano", "Tractocamion", 12.0, 42.0, 2);

        warehouse.assignNextAvailableSlot(); // Ocupa Fila 0, Col 0
        warehouse.assignNextAvailableSlot(); // Ocupa Fila 0, Col 1

        undoStack.pushOperation(dummy1);
        historyList.addProcessedTruck(dummy1);
        auditList.addAuditRecord(dummy1);

        undoStack.pushOperation(dummy2);
        historyList.addProcessedTruck(dummy2);
        auditList.addAuditRecord(dummy2);
    }

    private void initControllerActions() {
        view.getBtnEnqueue().setOnAction(e -> {
            try {
                String plate = view.getTfPlate().getText().trim();
                String cat = view.getCbCategory().getValue();
                String vType = view.getCbVehicle().getValue();
                double tare = Double.parseDouble(view.getTfTare().getText());
                double cargoWeight = Double.parseDouble(view.getTfCargoWeight().getText());
                int priority = view.getCbPriority().getValue();

                if (plate.isEmpty()) {
                    view.setNotification("⚠️ Error: La placa es obligatoria.");
                    return;
                }

                double gross = tare + cargoWeight;

                Truck t = new Truck(plate, cat, vType, tare, gross, priority);
                truckQueue.enqueueTruck(t);
                view.setNotification("✅ ¡Camión " + plate + " encolado correctamente! (Carga: " + cargoWeight + "t)");
                view.getTfPlate().clear(); 
                view.getTfCargoWeight().clear();
                refreshUI();
            } catch (NumberFormatException ex) {
                view.setNotification("⚠️ Error: Ingrese un valor numérico válido para el peso de la carga.");
            }
        });

        view.getBtnProcess().setOnAction(e -> {
            if (inspectorList.isEmpty()) {
                view.setNotification("⚠️ Error: Registre al menos un inspector en la Lista Circular.");
                return;
            }
            Truck next = truckQueue.processNextTruck();
            if (next == null) {
                view.setNotification("ℹ️ No hay camiones en la cola de espera.");
            } else {
                boolean assigned = warehouse.assignNextAvailableSlot();
                if (!assigned) {
                    view.setNotification("⚠️ Advertencia: Bodega llena. Camión devuelto a la cola.");
                    truckQueue.enqueueTruck(next);
                } else {
                    int dock = dockCounter % 3;
                    dockCounter++;
                    double wait = stats.calculateEstimatedWaitTime(truckQueue.size(), next.getPriority());
                    stats.recordOperation(dock, next.getLoadCategory(), wait);

                    undoStack.pushOperation(next);
                    historyList.addProcessedTruck(next);
                    auditList.addAuditRecord(next);
                    inspectorList.rotateToNext();

                    view.setNotification("✨ Camión " + next.getLicensePlate() + " atendido en Muelle " + (dock + 1) + 
                        " | Insp: " + inspectorList.getCurrentInspectorName());
                    refreshUI();
                }
            }
        });

        view.getBtnUndo().setOnAction(e -> {
            if (undoStack.isEmpty()) {
                view.setNotification("ℹ️ No hay operaciones recientes para deshacer.");
            } else {
                Truck undone = undoStack.popOperation();
                warehouse.freeLastSlot();
                truckQueue.enqueueTruck(undone);
                if (dockCounter > 0) dockCounter--;

                view.setNotification("🔄 Operación deshecha para: " + undone.getLicensePlate());
                refreshUI();
            }
        });

        view.getBtnAddInspector().setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Gestión de Inspectores");
            dialog.setHeaderText("Registro en Lista Circular");
            dialog.setContentText("Nombre completo del inspector:");
            dialog.showAndWait().ifPresent(name -> {
                if (!name.trim().isEmpty()) {
                    inspectorList.addInspector(name.trim());
                    view.setNotification("✅ Inspector " + name.trim() + " agregado con éxito.");
                    view.setInspectorText(inspectorList.getCurrentInspectorName());
                }
            });
        });

        view.getBtnRefreshReports().setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("--- HISTORIAL DE PROCESADOS (Lista Simple) ---\n").append(historyList.getHistoryString()).append("\n");
            sb.append("--- AUDITORÍA DE TIQUETES (Lista Doble) ---\n").append(auditList.getForwardAuditString()).append("\n");
            sb.append("--- MATRIZ ESTADÍSTICA DE MUELLES (Matriz 2D) ---\n").append(stats.getMatrixReportString()).append("\n");
            sb.append("--- CATÁLOGO DE VEHÍCULOS (Lista Doble) ---\n").append(vehicleCatalog.getCatalogForwardString());
            view.setReportsText(sb.toString());
            view.setNotification("📋 Reportes y estadísticas actualizados.");
        });
    }

    private void refreshUI() {
        view.setQueueStatusText(truckQueue.getQueueStatusString());
        view.refreshWarehouseGrid(warehouse.getGrid());
        view.setInspectorText(inspectorList.getCurrentInspectorName());
    }

    public static void main(String[] args) {
        launch(args);
    }
}