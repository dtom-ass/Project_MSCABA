package model;

/**
 * Represents a truck arriving at the agro-industrial collection center.
 */
public class Truck {
    private String licensePlate;
    private String loadCategory; // "Perecedero", "Grano", "Insumo"
    private String vehicleType;  // "Sencillo", "Doble Troque", "Tractocamion"
    private double tareWeight;   // Empty weight in tons
    private double grossWeight;  // Gross weight in tons
    private int priority;        // 1 = High, 2 = Normal, 3 = Low

    public Truck(String licensePlate, String loadCategory, String vehicleType, double tareWeight, double grossWeight, int priority) {
        this.licensePlate = licensePlate;
        this.loadCategory = loadCategory;
        this.vehicleType = vehicleType;
        this.tareWeight = tareWeight;
        this.grossWeight = grossWeight;
        this.priority = priority;
    }

    public double getNetWeight() {
        return Math.max(0.0, this.grossWeight - this.tareWeight);
    }

    // Getters
    public String getLicensePlate() { return licensePlate; }
    public String getLoadCategory() { return loadCategory; }
    public String getVehicleType() { return vehicleType; }
    public double getTareWeight() { return tareWeight; }
    public double getGrossWeight() { return grossWeight; }
    public int getPriority() { return priority; }

    @Override
    public String toString() {
        return "Truck [Plate=" + licensePlate + ", Category=" + loadCategory + 
               ", Vehicle=" + vehicleType + ", Net=" + String.format("%.2f", getNetWeight()) + "t, Priority=" + priority + "]";
    }
}