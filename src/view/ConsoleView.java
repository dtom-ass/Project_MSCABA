package view;

import java.util.Scanner;

/**
 * Vista de Consola (ConsoleView): Interfaz basada en texto (CLI) 
 * para simular el panel de control del Centro de Acopio y Básculas Agroindustriales.
 */
public class ConsoleView {
    private Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra el encabezado principal del panel de control y el estado actual del sistema.
     */
    public void displayDashboardHeader() {
        System.out.println("\n==================================================================");
        System.out.println("     SIMULADOR DE CENTRO DE ACOPIO Y BÁSCULAS AGROINDUSTRIALES (CLI)");
        System.out.println("==================================================================");
    }

    /**
     * Renderiza la sección de la cola de espera de camiones entrantes.
     */
    public void displayTruckQueue(String queueStatus) {
        System.out.println("\n[1] CAMIONES EN ESPERA (Cola con Prioridad):");
        System.out.println("------------------------------------------------------------------");
        if (queueStatus == null || queueStatus.isEmpty()) {
            System.out.println("   (No hay camiones esperando en la fila)");
        } else {
            System.out.println(queueStatus);
        }
    }

    /**
     * Renderiza la matriz 2D que representa las bahías de almacenamiento de la bodega.
     * 0 = Slot Libre [ ], 1 = Slot Ocupado [X]
     */
    public void displayWarehouseMatrix(int[][] matrix) {
        System.out.println("\n[2] MATRIZ DE ALMACENAMIENTO DE BODEGA (Cuadrícula 2D):");
        System.out.println("------------------------------------------------------------------");
        System.out.println("Leyenda: [ ] = Slot Disponible | [X] = Slot Ocupado");
        System.out.print("      ");
        for (int j = 0; j < matrix[0].length; j++) {
            System.out.print("Col " + j + " ");
        }
        System.out.println();
        
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("Fila " + i + " |");
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 0) {
                    System.out.print("  [ ]  ");
                } else {
                    System.out.print("  [X]  ");
                }
            }
            System.out.println("|");
        }
    }

    /**
     * Muestra el panel de control de la báscula y las opciones del menú para el usuario.
     */
    public int showMenuAndGetOption() {
        System.out.println("\n------------------------------------------------------------------");
        System.out.println("[3] PANEL DE CONTROL DE BÁSCULA Y BODEGA:");
        System.out.println("   1. Registrar un Nuevo Camión (Encolar)");
        System.out.println("   2. Procesar Siguiente Camión en Báscula (Pesar y Asignar Slot)");
        System.out.println("   3. Deshacer Última Operación de Pesaje (Pila LIFO)");
        System.out.println("   4. Ver Historial Procesado y Auditorías (Listas)");
        System.out.println("   5. Salir de la Simulación");
        System.out.print("Seleccione una opción (1-5): ");
        
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. Por favor, ingrese un número entre 1 y 5: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public void displayMessage(String message) {
        System.out.println("\n>>> NOTIFICACIÓN: " + message);
    }

    public Scanner getScanner() {
        return scanner;
    }
}