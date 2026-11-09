package model;

import java.util.Stack;

/**
 * Pila de Deshacer (UndoStack): Gestiona una estructura LIFO (Last In, First Out)
 * para almacenar las últimas operaciones de pesaje registradas. Permite revertir
 * (deshacer) la última acción realizada por el operador en caso de error.
 */
public class UndoStack {
    private Stack<Truck> stack;

    public UndoStack() {
        this.stack = new Stack<>();
    }

    /**
     * Agrega (apila) un camión procesado recientemente a la pila de control.
     */
    public void pushOperation(Truck truck) {
        stack.push(truck);
    }

    /**
     * Extrae y retorna el último camión procesado, eliminándolo de la pila (Deshacer).
     */
    public Truck popOperation() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.pop();
    }

    /**
     * Consulta el último camión procesado sin retirarlo de la pila.
     */
    public Truck peekLastOperation() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }

    /**
     * Comprueba si la pila de operaciones está vacía.
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * Retorna el número de operaciones almacenadas en la pila.
     */
    public int size() {
        return stack.size();
    }
}