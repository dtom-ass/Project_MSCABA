package model;

/**
 * Lista Simple de Historial (ProcessedHistoryList): Almacena de manera 
 * lineal y cronológica el registro de todos los camiones que ya han sido 
 * procesados en la báscula y asignados a la bodega.
 */
public class ProcessedHistoryList {

    /**
     * Nodo interno que representa cada elemento dentro de la lista enlazada simple.
     */
    private static class Node {
        Truck truck;
        Node next;

        public Node(Truck truck) {
            this.truck = truck;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public ProcessedHistoryList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Agrega un camión procesado al final de la lista simple.
     */
    public void addProcessedTruck(Truck truck) {
        Node newNode = new Node(truck);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    /**
     * Retorna una cadena formateada con todo el historial de camiones procesados
     * para ser visualizada limpiamente en la vista.
     */
    public String getHistoryString() {
        if (head == null) {
            return "   (No hay camiones procesados en el historial)";
        }
        StringBuilder sb = new StringBuilder();
        Node current = head;
        int index = 1;
        while (current != null) {
            sb.append("   [").append(index++).append("] ")
              .append("Placa: ").append(current.truck.getLicensePlate())
              .append(" | Carga: ").append(current.truck.getProductType())
              .append(" | Peso Neto: ").append(current.truck.getNetWeight()).append(" tons\n");
            current = current.next;
        }
        return sb.toString().trim();
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }
}