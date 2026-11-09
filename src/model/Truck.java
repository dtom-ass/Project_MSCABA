package model;

/**
 * Representa un camión que llega a la entrada del centro de acopio.
 */
public class Truck {
    private String licensePlate;
    private String productType; // Ejemplo: "Perecedero", "Grano Seco"
    private double tareWeight;   // Peso del camión vacío
    private double grossWeight;  // Peso bruto con carga
    private int priority;        // 1 = Alta (Perecedero), 2 = Normal

    public Truck(String licensePlate, String productType, double tareWeight, double grossWeight, int priority) {
        this.licensePlate = licensePlate;
        this.productType = productType;
        this.tareWeight = tareWeight;
        this.grossWeight = grossWeight;
        this.priority = priority;
    }

    /**
     * Calcula el peso neto del producto agrícola restando el peso bruto menos la tara.
     */
    public double getNetWeight() {
        return this.grossWeight - this.tareWeight;
    }

    // Getters y Setters
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public double getTareWeight() { return tareWeight; }
    public void setTareWeight(double tareWeight) { this.tareWeight = tareWeight; }

    public double getGrossWeight() { return grossWeight; }
    public void setGrossWeight(double grossWeight) { this.grossWeight = grossWeight; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    @Override
    public String toString() {
        return "Truck{" +
                "plate='" + licensePlate + '\'' +
                ", product='" + productType + '\'' +
                ", netWeight=" + getNetWeight() +
                ", priority=" + priority +
                '}';
    }
}