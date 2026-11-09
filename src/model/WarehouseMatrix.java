package model;

/**
 * Matriz de la Bodega (WarehouseMatrix): Gestiona el espacio físico 
 * de almacenamiento del centro de acopio utilizando una matriz bidimensional (2D).
 * 0 = Slot Libre (Disponible), 1 = Slot Ocupado (Almacenado).
 */
public class WarehouseMatrix {
    private int rows;
    private int cols;
    private int[][] grid;

    public WarehouseMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new int[rows][cols];
        initializeWarehouse();
    }

    /**
     * Inicializa todos los slots de la bodega como libres (0).
     */
    public void initializeWarehouse() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = 0; // 0 significa disponible
            }
        }
    }

    /**
     * Busca el primer slot disponible en la matriz y lo marca como ocupado (1).
     * Retorna verdadero si se asignó con éxito, o falso si la bodega está llena.
     */
    public boolean assignNextAvailableSlot() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0) {
                    grid[i][j] = 1; // 1 significa ocupado
                    return true;
                }
            }
        }
        return false; // Bodega llena
    }

    /**
     * Libera un slot específico de la bodega (ej. cuando se despacha el producto).
     */
    public void freeSlot(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            grid[row][col] = 0;
        }
    }

    /**
     * Retorna la matriz actual para ser renderizada en la vista.
     */
    public int[][] getGrid() {
        return grid;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}