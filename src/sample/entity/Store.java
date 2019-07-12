package sample.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Store {

    private IntegerProperty storeId;
    private StringProperty storeName;
    private StringProperty street;
    private StringProperty postalCode;
    private StringProperty city;
    private StringProperty state;
    private StringProperty country;
    private StringProperty openTime;
    private StringProperty closeTime;
    private IntegerProperty currentNum;
    private IntegerProperty maxNum;

    /**
     * Default constructor.
     */
    public Store() {
        this.storeId = new SimpleIntegerProperty();
        this.storeName = new SimpleStringProperty();
        this.street = new SimpleStringProperty();
        this.postalCode = new SimpleStringProperty();
        this.city = new SimpleStringProperty();
        this.state = new SimpleStringProperty();
        this.country = new SimpleStringProperty();
        this.openTime = new SimpleStringProperty();
        this.closeTime = new SimpleStringProperty();
        this.currentNum = new SimpleIntegerProperty();
        this.maxNum = new SimpleIntegerProperty();
    }

    public int getStoreId() {
        return storeId.get();
    }

    public void setStoreId(int storeId) {
        this.storeId.set(storeId);
    }

    public IntegerProperty storeIdProperty() {
        return storeId;
    }

    public String getStoreName() {
        return storeName.get();
    }

    public void setStoreName(String storeName) {
        this.storeName.set(storeName);
    }

    public StringProperty storeNameProperty() {
        return storeName;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getState() {
        return state.get();
    }

    public void setState(String state) {
        this.state.set(state);
    }

    public StringProperty stateProperty() {
        return state;
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public StringProperty countryProperty() {
        return country;
    }

    public String getOpenTime() {
        return openTime.get();
    }

    public void setOpenTime(String openTime) {
        this.openTime.set(openTime);
    }

    public StringProperty openTimeProperty() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime.get();
    }

    public void setCloseTime(String closeTime) {
        this.closeTime.set(closeTime);
    }

    public StringProperty closeTimeProperty() {
        return closeTime;
    }

    public int getCurrentNum() {
        return currentNum.get();
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum.set(currentNum);
    }

    public IntegerProperty currentNumProperty() {
        return currentNum;
    }

    public int getMaxNum() {
        return maxNum.get();
    }

    public void setMaxNum(int maxNum) {
        this.maxNum.set(maxNum);
    }

    public IntegerProperty maxNumProperty() {
        return maxNum;
    }
}
