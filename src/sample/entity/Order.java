package sample.entity;

import javafx.beans.property.*;

import java.sql.Date;

public class Order {

    private IntegerProperty order_id;
    private StringProperty upc_code;
    private StringProperty product_name;
    private StringProperty brand;
    private StringProperty type;
    private IntegerProperty amount;
    private SimpleObjectProperty<Date> order_date;
    private StringProperty store;

    public Order(int order_id, String upc_code, String product_name, String brand, String type, int amount, String order_date, String store) {
        this.order_id = new SimpleIntegerProperty();
        this.upc_code = new SimpleStringProperty();
        this.product_name = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.brand = new SimpleStringProperty();
        this.amount = new SimpleIntegerProperty();
        this.order_date = new SimpleObjectProperty<>();
        this.store = new SimpleStringProperty();
    }

    public Order() {
        this(0,null,null,null,null,0,null,null);
    }

    public int getOrder_id() {
        return order_id.get();
    }

    public void setOrder_id(int order_id) {
        this.order_id.set(order_id);
    }

    public IntegerProperty order_idProperty() {
        return order_id;
    }

    public String getUpc_code() {
        return upc_code.get();
    }

    public void setUpc_code(String upc_code) {
        this.upc_code.set(upc_code);
    }

    public StringProperty upc_codeProperty() {
        return upc_code;
    }

    public String getProduct_name() {
        return product_name.get();
    }

    public void setProduct_name(String product_name) {
        this.product_name.set(product_name);
    }

    public StringProperty product_nameProperty() {
        return product_name;
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

    public String brandType() {
        return brand.get();
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    public StringProperty brandProperty() {
        return brand;
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

    public Object getOrder_date() {
        return order_date.get();
    }

    public void setOrder_date(Date order_date) {
        this.order_date.set(order_date);
    }

    public SimpleObjectProperty<Date> order_dateProperty() {
        return order_date;
    }

    public String getStore() {
        return store.get();
    }

    public void setStore(String store) {
        this.store.set(store);
    }

    public StringProperty storeProperty() {
        return store;
    }
}
