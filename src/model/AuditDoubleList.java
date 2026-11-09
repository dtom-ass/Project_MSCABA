package model;

/**
 * Lista Doble de Auditoría (AuditDoubleList): Almacena los registros detallados 
 * de los pesajes realizados en la báscula utilizando una estructura doblemente enlazada,
 * lo que permite navegar hacia adelante y hacia atrás entre los tiquetes emitidos.
 */
public class AuditDoubleList {

    /**
     * Nodo interno que representa cada tiquete de auditoría, conteniendo 
     * referencias al nodo siguiente y al nodo anterior.
     */
    private static class DoubleNode {
        Truck truck;
        DoubleNode next;
        DoubleNode prev;

        public DoubleNode(Truck truck) {
            this.truck = truck;
            this.next = null;
            this.prev = null;
        }
    }

    private DoubleNode head;
    private DoubleNode tail;
    private int size;

    public AuditDoubleList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Agrega un nuevo registro de camión auditado al final de la lista doble.
     */
    public void addAuditRecord(Truck truck) {
        DoubleNode newNode = new DoubleNode(truck);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * Retorna una representación en texto para recorrer la lista hacia adelante.
     */
    public String getForwardAuditString() {
        if (head == null) {
            return "   (No hay registros de auditoría disponibles)";
        }
        StringBuilder sb = new StringBuilder();
        DoubleNode current = head;
        int index = 1;
        while (current != null) {
            sb.append("   [Auditoría Adelante ").append(index++).append("] ")
              .append("Placa: ").append(current.truck.getLicensePlate())
              .append(" | Bruto: ").append(current.truck.getGrossWeight())
              .append(" | Tara: ").append(current.truck.getTareWeight())
              .append(" | Neto: ").append(current.truck.getNetWeight()).append("\n");
            current = current.next;
        }
        return sb.toString().trim();
    }

    /**
     * Retorna una representación en texto para recorrer la lista hacia atrás (desde el último hasta el primero).
     */
    public String getBackwardAuditString() {
        if (tail == null) {
            return "   (No hay registros de auditoría disponibles)";
        }
        StringBuilder sb = new StringBuilder();
        DoubleNode current = tail;
        int index = 1;
        while (current != null) {
            sb.append("   [Auditoría Atrás ").append(index++).append("] ")
              .append("Placa: ").append(current.truck.getLicensePlate())
              .append(" | Neto: ").append(current.truck.getNetWeight()).append("\n");
            current = current.prev;
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