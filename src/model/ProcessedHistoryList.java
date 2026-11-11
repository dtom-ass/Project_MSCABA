package model;

/**
 * Simple Linked List for chronological history of processed trucks.
 */
public class ProcessedHistoryList {
    private static class Node {
        Truck truck;
        Node next;

        Node(Truck truck) {
            this.truck = truck;
            this.next = null;
        }
    }

    private Node head;

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
    }

    public String getHistoryString() {
        if (head == null) {
            return "   (Historial vacío)";
        }
        StringBuilder sb = new StringBuilder();
        Node current = head;
        int index = 1;
        while (current != null) {
            sb.append("   [").append(index++).append("] Placa: ").append(current.truck.getLicensePlate())
              .append(" | Carga: ").append(current.truck.getLoadCategory())
              .append(" | Peso Neto: ").append(String.format("%.2f", current.truck.getNetWeight())).append(" tons\n");
            current = current.next;
        }
        return sb.toString();
    }
}