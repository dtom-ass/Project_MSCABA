package model;

import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Cola con Prioridad de Camiones (TruckPriorityQueue): Gestiona la llegada 
 * de vehículos a la entrada del centro de acopio, organizándolos automáticamente 
 * según su nivel de urgencia o tipo de mercancía (ej. perecederos vs granos secos).
 */
public class TruckPriorityQueue {
    private PriorityQueue<Truck> queue;

    public TruckPriorityQueue() {
        // Configuramos el comparador: un número menor de prioridad significa mayor urgencia (Ej: 1 > 2)
        Comparator<Truck> priorityComparator = Comparator.comparingInt(Truck::getPriority);
        this.queue = new PriorityQueue<>(priorityComparator);
    }

    /**
     * Agrega un nuevo camión a la cola de espera de la entrada.
     */
    public void enqueueTruck(Truck truck) {
        queue.add(truck);
    }

    /**
     * Extrae y retorna el camión que está al frente de la cola (mayor prioridad).
     */
    public Truck processNextTruck() {
        return queue.poll();
    }

    /**
     * Consulta el siguiente camión en la fila sin eliminarlo de la estructura.
     */
    public Truck peekNextTruck() {
        return queue.peek();
    }

    /**
     * Comprueba si la cola de espera se encuentra totalmente vacía.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Retorna una cadena formateada con todos los camiones en espera 
     * para ser visualizada limpiamente en la vista.
     */
    public String getQueueStatusString() {
        if (queue.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        // Usamos una copia temporal para recorrer la cola sin alterar su orden interno
        PriorityQueue<Truck> tempQueue = new PriorityQueue<>(queue);
        int position = 1;
        while (!tempQueue.isEmpty()) {
            Truck truck = tempQueue.poll();
            sb.append("   [Fila ").append(position++).append("] ")
              .append("Placa: ").append(truck.getLicensePlate())
              .append(" | Carga: ").append(truck.getProductType())
              .append(" | Prioridad: ").append(truck.getPriority() == 1 ? "ALTA (Perecedero)" : "Normal")
              .append("\n");
        }
        return sb.toString().trim();
    }
}