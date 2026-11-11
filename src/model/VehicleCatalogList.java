package model;

/**
 * Doubly Linked List for vehicle types catalog with default tare weights.
 */
public class VehicleCatalogList {
    private static class Node {
        String vehicleType;
        double defaultTareWeight; // Peso vacío estándar (Tara) en toneladas
        Node prev;
        Node next;

        Node(String vehicleType, double defaultTareWeight) {
            this.vehicleType = vehicleType;
            this.defaultTareWeight = defaultTareWeight;
        }
    }

    private Node head;
    private Node tail;

    public void addVehicleType(String type, double tareWeight) {
        Node newNode = new Node(type, tareWeight);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public double getTareWeightForType(String type) {
        Node current = head;
        while (current != null) {
            if (current.vehicleType.equalsIgnoreCase(type)) {
                return current.defaultTareWeight;
            }
            current = current.next;
        }
        return 3.5;
    }

    public String getCatalogForwardString() {
        StringBuilder sb = new StringBuilder();
        Node current = head;
        int index = 1;
        while (current != null) {
            sb.append("   [").append(index++).append("] Tipo: ").append(current.vehicleType)
              .append(" | Tara Estándar: ").append(current.defaultTareWeight).append(" tons\n");
            current = current.next;
        }
        return sb.length() > 0 ? sb.toString() : "   (Catálogo vacío)";
    }
}