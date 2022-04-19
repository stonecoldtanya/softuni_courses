package exam.model.dto.json;

import java.math.BigDecimal;

public class LaptopExportDto {
    private String macAddress;

    private double cpuSpeed;

    private int ram;

    private int storage;

    private String description;

    private BigDecimal price;

    private String shopName;

    private String shopTownName;

    public LaptopExportDto() {
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopTownName() {
        return shopTownName;
    }

    public void setShopTownName(String shopTownName) {
        this.shopTownName = shopTownName;
    }

    @Override
    public String toString() {
        return String.format("Laptop - %s%n", macAddress) +
                String.format("*Cpu speed - %.2f%n", cpuSpeed)+
                String.format("**Ram - %d%n", ram) +
                String.format("***Storage - %d%n", storage) +
                String.format("****Price - %.2f%n", price) +
                String.format("#Shop name - %s%n", shopName) +
                String.format("##Town - %s%n", shopTownName);
    }
}
