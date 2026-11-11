package model;

/**
 * Manages 1D vectors for arrival/discharge rates and 2D matrices for docks statistics.
 */
public class CenterStatistics {
    // 1D Vectors: Index 0 = Perecedero, Index 1 = Grano, Index 2 = Insumo
    private final double[] arrivalRates = { 5.0, 3.0, 2.0 };     // camiones/hora
    private final double[] dischargeRates = { 10.0, 15.0, 8.0 }; // toneladas/hora

    // 2D Matrices: 3 Docks (Muelles) x 3 Load Categories
    private final double[][] waitingMinutesPerDockAndType = new double[3][3];
    private final int[][] countPerDockAndType = new int[3][3];

    public int getCategoryIndex(String category) {
        if (category == null) return 1;
        switch (category.toLowerCase()) {
            case "perecedero": return 0;
            case "grano": return 1;
            case "insumo": return 2;
            default: return 1;
        }
    }

    public double getDischargeRate(String category) {
        return dischargeRates[getCategoryIndex(category)];
    }

    /**
     * Calculates estimated discharge time in minutes:
     * T_descarga = (Net Weight / Discharge Rate) * 60
     */
    public double calculateEstimatedDischargeTime(Truck truck) {
        double rate = getDischargeRate(truck.getLoadCategory());
        if (rate <= 0) return 0.0;
        return (truck.getNetWeight() / rate) * 60.0;
    }

    /**
     * Calculates estimated wait time in minutes:
     * T_espera = 8 * (queueSize) + 2 * (priority - 1)[cite: 3]
     */
    public double calculateEstimatedWaitTime(int queueSize, int priority) {
        return (8.0 * queueSize) + (2.0 * Math.max(0, priority - 1));
    }

    public void recordOperation(int dockIndex, String category, double waitTime) {
        int catIdx = getCategoryIndex(category);
        if (dockIndex >= 0 && dockIndex < 3) {
            waitingMinutesPerDockAndType[dockIndex][catIdx] += waitTime;
            countPerDockAndType[dockIndex][catIdx]++;
        }
    }

    public String getMatrixReportString() {
        StringBuilder sb = new StringBuilder();
        sb.append("   Muelles (0, 1, 2) vs Categorías [Perecedero, Grano, Insumo]:\n");
        for (int i = 0; i < 3; i++) {
            sb.append("   Muelle ").append(i + 1).append(" -> ");
            for (int j = 0; j < 3; j++) {
                sb.append("[Atenciones: ").append(countPerDockAndType[i][j])
                  .append(", Min. Espera: ").append(String.format("%.1f", waitingMinutesPerDockAndType[i][j])).append("] ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}