package model;

/**
 * Lista Circular de Inspectores (InspectorCircularList): Gestiona una lista 
 * circular enlazada para rotar de manera equitativa a los inspectores de calidad 
 * del centro de acopio cada vez que se procesa un camión en la báscula.
 */
public class InspectorCircularList {

    /**
     * Nodo interno que representa a un inspector de calidad dentro de la lista circular.
     */
    private static class InspectorNode {
        String name;
        InspectorNode next;

        public InspectorNode(String name) {
            this.name = name;
            this.next = null;
        }
    }

    private InspectorNode tail; // Apunta al último nodo, cuyo .next apunta al inicio (head)
    private int size;

    public InspectorCircularList() {
        this.tail = null;
        this.size = 0;
    }

    /**
     * Agrega un nuevo inspector de calidad a la lista circular.
     */
    public void addInspector(String name) {
        InspectorNode newNode = new InspectorNode(name);
        if (tail == null) {
            tail = newNode;
            tail.next = tail; // Se apunta a sí mismo para formar el círculo
        } else {
            newNode.next = tail.next; // El nuevo nodo apunta al inicio actual
            tail.next = newNode;     // El final anterior apunta al nuevo nodo
            tail.next = newNode;
            tail = newNode;          // El nuevo nodo se convierte en el final (tail)
        }
        size++;
    }

    /**
     * Obtiene el nombre del inspector de calidad actualmente asignado.
     */
    public String getCurrentInspectorName() {
        if (tail == null) {
            return "Ninguno";
        }
        return tail.next.name; // El inicio (head) es el elemento siguiente al tail
    }

    /**
     * Rota al siguiente inspector de calidad en la lista circular.
     */
    public void rotateToNext() {
        if (tail != null) {
            tail = tail.next; // Mover el tail al siguiente cambia automáticamente el inicio (head)
        }
    }

    /**
     * Retorna una representación en texto de todos los inspectores en la lista circular.
     */
    public String getInspectorsString() {
        if (tail == null) {
            return "   (No hay inspectores registrados)";
        }
        StringBuilder sb = new StringBuilder();
        InspectorNode current = tail.next; // Empezamos por el inicio (head)
        int index = 1;
        do {
            sb.append("   [Inspector ").append(index++).append("] ")
              .append(current.name);
            if (current == tail.next) {
                sb.append(" (TURNO ACTUAL)");
            }
            sb.append("\n");
            current = current.next;
        } while (current != tail.next);
        
        return sb.toString().trim();
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return tail == null;
    }
}