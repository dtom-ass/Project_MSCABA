package controller;

import model.*;
import view.ConsoleView;
import java.util.Scanner;

/**
 * Clase Principal (Main): Punto de entrada de la aplicación.
 * Gestiona el modo de operación (Uso limpio o Pruebas robustas con datos precargados)
 * y la integración de las estructuras de datos obligatorias.
 */
public class Main {
    public static void main(String[] args) {
        // Configurar codificación UTF-8 para la consola si es compatible
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
        } catch (Exception e) {
            // Ignorar si la consola no lo soporta
        }

        Scanner scanner = new Scanner(System.in);
        ConsoleView view = new ConsoleView();

        System.out.println("==================================================");
        System.out.println("   CONFIGURACION INICIAL - SISTEMA MSCABA         ");
        System.out.println("==================================================");
        
        // 1. Solicitar nombre de usuario / auditor a cargo
        System.out.print("Ingrese el nombre del usuario / auditor a cargo: ");
        String auditorName = scanner.nextLine().trim();
        if (auditorName.isEmpty()) {
            auditorName = "Auditor General";
        }

        // 2. Seleccionar modo de operación
        System.out.println("\nSeleccione el modo de operación del sistema:");
        System.out.println("  1. Modo Uso (Sistema limpio / vacío - Requiere registro manual)");
        System.out.println("  2. Modo Pruebas (Robusto: Datos ampliados, bodega ocupada y registros)");
        System.out.print("Seleccione una opción (1-2): ");
        
        int modeOption = 1;
        if (scanner.hasNextInt()) {
            modeOption = scanner.nextInt();
        }

        // Estructuras de datos obligatorias
        TruckPriorityQueue truckQueue = new TruckPriorityQueue();
        WarehouseMatrix warehouse = new WarehouseMatrix(3, 3); // Matriz de 3x3
        UndoStack undoStack = new UndoStack();
        ProcessedHistoryList historyList = new ProcessedHistoryList();
        AuditDoubleList auditList = new AuditDoubleList();
        InspectorCircularList inspectorList = new InspectorCircularList();

        // 3. Inicializar según el modo seleccionado
        if (modeOption == 2) {
            // Modo Pruebas Robusto: Cargar inspectores, cola amplia y simular estado avanzado
            inspectorList.addInspector("Ing. Carlos Pérez");
            inspectorList.addInspector("Dra. Sofía Gómez");
            inspectorList.addInspector("Lic. Mario Ramírez");
            inspectorList.addInspector("Ing. Luisa Fernanda");

            // Cola de espera robusta con prioridades mixtas
            truckQueue.enqueueTruck(new Truck("CAR-111", "Tomates (Perecedero)", 3.0, 10.5, 1));
            truckQueue.enqueueTruck(new Truck("BAN-222", "Bananos Exportación", 5.0, 15.0, 1));
            truckQueue.enqueueTruck(new Truck("GRA-333", "Arroz Blanco", 8.0, 20.0, 2));
            truckQueue.enqueueTruck(new Truck("CAF-444", "Café Pergamino", 4.0, 12.5, 2));
            truckQueue.enqueueTruck(new Truck("FRE-555", "Mora Fresca", 2.5, 8.0, 1));

            // Simular camiones ya procesados para poblar matriz de bodega, pila, historial y auditoría
            Truck processed1 = new Truck("PRE-001", "Papa Criolla", 3.5, 11.0, 2);
            Truck processed2 = new Truck("PRE-002", "Leche UHT", 4.0, 13.0, 1);
            Truck processed3 = new Truck("PRE-003", "Aguacate Hass", 3.0, 9.5, 1);

            warehouse.assignNextAvailableSlot();
            undoStack.pushOperation(processed1);
            historyList.addProcessedTruck(processed1);
            auditList.addAuditRecord(processed1);
            inspectorList.rotateToNext();

            warehouse.assignNextAvailableSlot();
            undoStack.pushOperation(processed2);
            historyList.addProcessedTruck(processed2);
            auditList.addAuditRecord(processed2);
            inspectorList.rotateToNext();

            warehouse.assignNextAvailableSlot();
            undoStack.pushOperation(processed3);
            historyList.addProcessedTruck(processed3);
            auditList.addAuditRecord(processed3);
            inspectorList.rotateToNext();

            System.out.println("\n[INFO] Modo Pruebas Robusto activado: Cola con 5 camiones, bodega con 3 slots ocupados y registros precargados.");
        } else {
            // Modo Uso: Limpio, sin inspectores ni camiones predeterminados
            System.out.println("\n[INFO] Modo Uso activado: Sistema limpio. Debe registrar inspectores para operar.");
        }

        System.out.println("\nPresione Enter para ingresar al sistema principal...");
        scanner.nextLine(); // Limpiar buffer
        scanner.nextLine(); // Pausa

        boolean running = true;

        // 4. Bucle principal del menú interactivo
        while (running) {
            view.displayDashboardHeader();
            
            System.out.println("[AUDITOR A CARGO]: " + auditorName);
            
            // Mostrar estado actual de la cola de camiones
            view.displayTruckQueue(truckQueue.getQueueStatusString());
            
            // Mostrar matriz de la bodega
            view.displayWarehouseMatrix(warehouse.getGrid());

            // Mostrar el inspector de turno actual
            System.out.println("\n[INFO] Inspector de Calidad en Turno: " + inspectorList.getCurrentInspectorName());

            // Menú extendido
            System.out.println("--------------------------------------------------");
            System.out.println("[3] PANEL DE CONTROL DE BASCULA Y BODEGA:");
            System.out.println("   1. Registrar un Nuevo Camión (Encolar)");
            System.out.println("   2. Procesar Siguiente Camión en Báscula (Pesar y Asignar)");
            System.out.println("   3. Deshacer Última Operación de Pesaje (Pila LIFO)");
            System.out.println("   4. Registrar Nuevo Inspector (Lista Circular)");
            System.out.println("   5. Ver Historiales y Auditorías (Listas)");
            System.out.println("   6. Salir de la Simulación");
            System.out.print("Seleccione una opción (1-6): ");

            int option = -1;
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
            } else {
                scanner.next(); // Limpiar entrada inválida
            }

            switch (option) {
                case 1: // Registrar un nuevo camión
                    System.out.println("\n--- REGISTRAR NUEVO CAMION ---");
                    System.out.print("Ingrese la placa del camión: ");
                    String plate = scanner.next();
                    
                    System.out.print("Ingrese el tipo de carga (ej. Cafe, Papa, Fresa): ");
                    String product = scanner.next();
                    
                    System.out.print("Ingrese el peso Tara (vacio en toneladas): ");
                    double tare = scanner.nextDouble();
                    
                    System.out.print("Ingrese el peso Bruto (con carga en toneladas): ");
                    double gross = scanner.nextDouble();
                    
                    System.out.print("Ingrese la prioridad (1 = Alta/Perecedero, 2 = Normal): ");
                    int priority = scanner.nextInt();

                    Truck newTruck = new Truck(plate, product, tare, gross, priority);
                    truckQueue.enqueueTruck(newTruck);
                    view.displayMessage("¡Camión con placa " + plate + " agregado exitosamente por " + auditorName + "!");
                    break;

                case 2: // Procesar siguiente camión en báscula
                    if (inspectorList.isEmpty()) {
                        view.displayMessage("¡Error! No hay inspectores de calidad registrados en la Lista Circular. Registre al menos uno en la opción 4.");
                        break;
                    }
                    Truck nextTruck = truckQueue.processNextTruck();
                    if (nextTruck == null) {
                        view.displayMessage("No hay camiones en la cola de espera.");
                    } else {
                        boolean assigned = warehouse.assignNextAvailableSlot();
                        if (!assigned) {
                            view.displayMessage("¡Advertencia! La bodega está completamente llena. No se pudo asignar slot.");
                            truckQueue.enqueueTruck(nextTruck);
                        } else {
                            undoStack.pushOperation(nextTruck);
                            historyList.addProcessedTruck(nextTruck);
                            auditList.addAuditRecord(nextTruck);
                            inspectorList.rotateToNext();

                            view.displayMessage("Camión " + nextTruck.getLicensePlate() + 
                                " pesado por " + auditorName + ". Inspector responsable: " + inspectorList.getCurrentInspectorName() + 
                                ". Peso Neto: " + nextTruck.getNetWeight() + " tons.");
                        }
                    }
                    break;

                case 3: // Deshacer última operación (Pila LIFO)
                    if (undoStack.isEmpty()) {
                        view.displayMessage("No hay operaciones recientes para deshacer.");
                    } else {
                        Truck undoneTruck = undoStack.popOperation();
                        view.displayMessage("Operación deshecha con éxito para el camión: " + undoneTruck.getLicensePlate());
                    }
                    break;

                case 4: // Registrar inspector en la Lista Circular
                    System.out.println("\n--- REGISTRAR INSPECTOR DE CALIDAD ---");
                    System.out.print("Ingrese el nombre completo del inspector: ");
                    scanner.nextLine(); // Limpiar buffer
                    String inspectorName = scanner.nextLine().trim();
                    if (!inspectorName.isEmpty()) {
                        inspectorList.addInspector(inspectorName);
                        view.displayMessage("Inspector " + inspectorName + " agregado exitosamente a la Lista Circular.");
                    } else {
                        view.displayMessage("El nombre no puede estar vacío.");
                    }
                    break;

                case 5: // Ver historiales y auditorías (Listas)
                    System.out.println("\n==================================================");
                    System.out.println("          HISTORIALES Y REGISTROS DEL SISTEMA     ");
                    System.out.println("==================================================");
                    System.out.println("Auditor Responsable: " + auditorName);
                    
                    System.out.println("\n-> HISTORIAL DE CAMIONES PROCESADOS (Lista Simple):");
                    System.out.println(historyList.getHistoryString());
                    
                    System.out.println("\n-> AUDITORÍA DE TIQUETES (Lista Doble - Hacia Adelante):");
                    System.out.println(auditList.getForwardAuditString());

                    System.out.println("\n-> ROTACIÓN DE INSPECTORES (Lista Circular):");
                    System.out.println(inspectorList.getInspectorsString());
                    
                    System.out.println("\nPresione Enter para volver al menú principal...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;

                case 6: // Salir
                    running = false;
                    System.out.println("\nSesión finalizada para el auditor: " + auditorName + ". Saliendo...");
                    break;

                default:
                    view.displayMessage("Opción no válida. Intente de nuevo.");
            }
        }
        scanner.close();
    }
}