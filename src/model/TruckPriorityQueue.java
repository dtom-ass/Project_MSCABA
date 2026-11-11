package model;

import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Priority Queue for incoming trucks waiting at the collection center.
 * Prioritizes perishable goods over standard grain/supplies.
 */
public class TruckPriorityQueue {
    private PriorityQueue<Truck> queue;

    public TruckPriorityQueue() {
        // Comparator: First by priority (ascending: 1 is highest), then FIFO implicit order
        this.queue = new PriorityQueue<>(Comparator.comparingInt(Truck::getPriority));
    }

    public void enqueueTruck(Truck truck) {
        queue.offer(truck);
    }

    public Truck processNextTruck() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Returns the number of trucks waiting in the queue.
     */
    public int size() {
        return queue.size();
    }

    public String getQueueStatusString() {
        if (queue.isEmpty()) {
            return "   (No hay camiones esperando en la fila)";
        }
        StringBuilder sb = new StringBuilder();
        int index = 1;
        // Create a temporary sorted array to display in priority order
        Truck[] array = queue.toArray(new Truck[0]);
        java.util.Arrays.sort(array, Comparator.comparingInt(Truck::getPriority));
        
        for (Truck t : array) {
            sb.append("   [Fila ").append(index++).append("] Placa: ").append(t.getLicensePlate())
              .append(" | Carga: ").append(t.getLoadCategory())
              .append(" | Prioridad: ").append(t.getPriority() == 1 ? "ALTA (Perecedero)" : "Normal").append("\n");
        }
        return sb.toString();
    }
}