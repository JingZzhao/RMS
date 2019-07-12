package sample.entity;

import javafx.beans.property.*;

public class Product {
    private StringProperty upc;
    private StringProperty product;
    private StringProperty type;
    private StringProperty brand;
    private DoubleProperty price;
    private IntegerProperty amount;
    private StringProperty vendor;
    private StringProperty lastedSupplyDate;
    private IntegerProperty lastedSupplyAmount;
    private DoubleProperty supplyPrice;

    public Product(String upc, String product, String type, String brand, double price, int amount, String vendor, String lastedSupplyDate, int lastedSupplyAmount, double supplyPrice) {
        this.upc = new SimpleStringProperty(upc);
        this.product = new SimpleStringProperty(product);
        this.type = new SimpleStringProperty(type);
        this.brand = new SimpleStringProperty(brand);
        this.price = new SimpleDoubleProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
        this.vendor = new SimpleStringProperty(vendor);
        this.lastedSupplyDate = new SimpleStringProperty(lastedSupplyDate);
        this.lastedSupplyAmount = new SimpleIntegerProperty(lastedSupplyAmount);
        this.supplyPrice = new SimpleDoubleProperty(supplyPrice);
    }

    public Product() {
        this(null,null,null,null,0.0,0,null,null,0, 0.0);
    }

    public String getUpc() {
        return upc.get();
    }

    public void setUpc(String upc) {
        this.upc.set(upc);
    }

    public StringProperty upcProperty() {
        return upc;
    }

    public String getProduct() {
        return product.get();
    }

    public void setProduct(String product) {
        this.product.set(product);
    }

    public StringProperty productProperty() {
        return product;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getBrand() {
        return brand.get();
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public StringProperty brandProperty() {
        return brand;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public int getAmount() {
        return amount.get();
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public String setVendor() {
        return vendor.get();
    }

    public void setVendor(String vendor) {
        this.vendor.set(vendor);
    }

    public StringProperty vendorProperty() {
        return vendor;
    }

    public String getLastedSupplyDate() {
        return lastedSupplyDate.get();
    }

    public void setLastedSupplyDate(String lastedSupplyDate) {
        this.lastedSupplyDate.set(lastedSupplyDate);
    }

    public StringProperty lastedSupplyDateProperty() {
        return lastedSupplyDate;
    }

    public int getLastedSupplyAmount() {
        return lastedSupplyAmount.get();
    }

    public void setLastedSupplyAmount(int lastedSupplyAmount) {
        this.lastedSupplyAmount.set(lastedSupplyAmount);
    }

    public IntegerProperty lastedSupplyAmountProperty() {
        return lastedSupplyAmount;
    }

    public double getSupplyPrice() {
        return supplyPrice.get();
    }

    public void setSupplyPrice(double supplyPrice) {
        this.supplyPrice.set(supplyPrice);
    }

    public DoubleProperty supplyPriceProperty() {
        return supplyPrice;
    }

}
