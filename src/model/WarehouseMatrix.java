package model;

/**
 * Represents the warehouse storage matrix (2D grid) for slots/docks.
 */
public class WarehouseMatrix {
    private int[][] grid; // 0 = Available, 1 = Occupied

    public WarehouseMatrix(int rows, int cols) {
        this.grid = new int[rows][cols];
    }

    public boolean assignNextAvailableSlot() {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] == 0) {
                    grid[r][c] = 1; // Occupied
                    return true;
                }
            }
        }
        return false; // Warehouse full
    }

    /**
     * Frees the last occupied slot in the matrix (used for Undo).
     */
    public boolean freeLastSlot() {
        // Search backwards from the last cell to find the most recent occupied slot
        for (int r = grid.length - 1; r >= 0; r--) {
            for (int c = grid[r].length - 1; c >= 0; c--) {
                if (grid[r][c] == 1) {
                    grid[r][c] = 0; // Make available again
                    return true;
                }
            }
        }
        return false;
    }

    public int[][] getGrid() {
        return grid;
    }
}