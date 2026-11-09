package controller;

import model.*;
import view.ConsoleView;

/**
 * Clase Principal (Main): Punto de entrada de la aplicación. 
 * Actúa como el controlador que coordina el menú interactivo, la vista de consola 
 * y todas las estructuras de datos del modelo.
 */
public class Main {
    public static void main(String[] args) {
        // 1. Inicializar la Vista y el Modelo
        ConsoleView view = new ConsoleView();
        
        // Estructuras de datos obligatorias
        TruckPriorityQueue truckQueue = new TruckPriorityQueue();
        WarehouseMatrix warehouse = new WarehouseMatrix(3, 3); // Matriz de 3 filas x 3 columnas
        UndoStack undoStack = new UndoStack();
        ProcessedHistoryList historyList = new ProcessedHistoryList();
        AuditDoubleList auditList = new AuditDoubleList();
        InspectorCircularList inspectorList = new InspectorCircularList();

        // 2. Registrar inspectores iniciales en la Lista Circular
        inspectorList.addInspector("Ing. Carlos Pérez");
        inspectorList.addInspector("Dra. Sofía Gómez");
        inspectorList.addInspector("Lic. Mario Ramírez");

        // 3. Cargar algunos camiones de prueba iniciales en la Cola con Prioridad
        truckQueue.enqueueTruck(new Truck("ABC-123", "Fresas (Perecedero)", 4.5, 12.0, 1)); // Prioridad Alta (1)
        truckQueue.enqueueTruck(new Truck("XYZ-789", "Maíz Seco", 6.0, 18.5, 2));          // Prioridad Normal (2)
        truckQueue.enqueueTruck(new Truck("MIL-456", "Leche Fresca", 5.0, 14.0, 1));       // Prioridad Alta (1)

        boolean running = true;

        // 4. Bucle principal del menú interactivo
        while (running) {
            view.displayDashboardHeader();
            
            // Mostrar estado actual de la cola de camiones
            view.displayTruckQueue(truckQueue.getQueueStatusString());
            
            // Mostrar matriz de la bodega
            view.displayWarehouseMatrix(warehouse.getGrid());

            // Mostrar el inspector de turno actual
            System.out.println("\n[INFO] Inspector de Calidad en Turno: " + inspectorList.getCurrentInspectorName());

            // Obtener opción del usuario
            int option = view.showMenuAndGetOption();

            switch (option) {
                case 1: // Registrar un nuevo camión
                    System.out.println("\n--- REGISTRAR NUEVO CAMIÓN ---");
                    System.out.print("Ingrese la placa del camión: ");
                    String plate = view.getScanner().next();
                    
                    System.out.print("Ingrese el tipo de carga (ej. Cafe, Papa, Fresa): ");
                    String product = view.getScanner().next();
                    
                    System.out.print("Ingrese el peso Tara (vacío en toneladas): ");
                    double tare = view.getScanner().nextDouble();
                    
                    System.out.print("Ingrese el peso Bruto (con carga en toneladas): ");
                    double gross = view.getScanner().nextDouble();
                    
                    System.out.print("Ingrese la prioridad (1 = Alta/Perecedero, 2 = Normal): ");
                    int priority = view.getScanner().nextInt();

                    Truck newTruck = new Truck(plate, product, tare, gross, priority);
                    truckQueue.enqueueTruck(newTruck);
                    view.displayMessage("¡Camión con placa " + plate + " agregado exitosamente a la cola de espera!");
                    break;

                case 2: // Procesar siguiente camión en báscula
                    Truck nextTruck = truckQueue.processNextTruck();
                    if (nextTruck == null) {
                        view.displayMessage("No hay camiones en la cola de espera.");
                    } else {
                        // Intentar asignar slot en la matriz de la bodega
                        boolean assigned = warehouse.assignNextAvailableSlot();
                        if (!assigned) {
                            view.displayMessage("¡Advertencia! La bodega está completamente llena. No se pudo asignar slot.");
                            // Devolvemos el camión a la cola si no hay campo (opcional)
                            truckQueue.enqueueTruck(nextTruck);
                        } else {
                            // Registrar en las demás estructuras
                            undoStack.pushOperation(nextTruck);
                            historyList.addProcessedTruck(nextTruck);
                            auditList.addAuditRecord(nextTruck);
                            
                            // Rotar inspector en la lista circular
                            inspectorList.rotateToNext();

                            view.displayMessage("Camión " + nextTruck.getLicensePlate() + 
                                " pesando exitosamente. Peso Neto: " + nextTruck.getNetWeight() + 
                                " tons. Slot asignado en bodega y auditado.");
                        }
                    }
                    break;

                case 3: // Deshacer última operación (Pila LIFO)
                    if (undoStack.isEmpty()) {
                        view.displayMessage("No hay operaciones recientes para deshacer.");
                    } else {
                        Truck undoneTruck = undoStack.popOperation();
                        view.displayMessage("Operación deshecha con éxito para el camión: " + undoneTruck.getLicensePlate());
                        // Nota didáctica: Aquí se podría liberar el último slot de la matriz si se desea simular el "Undo" físico
                    }
                    break;

                case 4: // Ver historiales y auditorías (Listas)
                    System.out.println("\n==================================================");
                    System.out.println("          HISTORIALES Y REGISTROS DEL SISTEMA     ");
                    System.out.println("==================================================");
                    System.out.println("\n-> HISTORIAL DE CAMIONES PROCESADOS (Lista Simple):");
                    System.out.println(historyList.getHistoryString());
                    
                    System.out.println("\n-> AUDITORÍA DE TIQUETES (Lista Doble - Hacia Adelante):");
                    System.out.println(auditList.getForwardAuditString());

                    System.out.println("\n-> ROTACIÓN DE INSPECTORES (Lista Circular):");
                    System.out.println(inspectorList.getInspectorsString());
                    
                    System.out.println("\nPresione Enter para volver al menú principal...");
                    view.getScanner().nextLine(); // Limpiar buffer
                    view.getScanner().nextLine(); // Pausa
                    break;

                case 5: // Salir
                    running = false;
                    System.out.println("\n¡Gracias por usar el Simulador del Centro de Acopio! Saliendo...");
                    break;

                default:
                    view.displayMessage("Opción no válida. Intente de nuevo.");
            }
        }
    }
}