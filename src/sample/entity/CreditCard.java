package sample.entity;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CreditCard {
    private LongProperty credit_card_number;
    private StringProperty type;
    private StringProperty exp_date;

    public CreditCard(Long credit_card_number, String type, String exp_date) {
        this.credit_card_number = new SimpleLongProperty();
        this.type = new SimpleStringProperty();
        this.exp_date = new SimpleStringProperty();
    }

    public  CreditCard(){
        this(null,null,null);
    }

    public long getCredit_Card_Num() {
        return credit_card_number.get();
    }

    public void setCredit_Card_Num(long credit_card_number) {
        this.credit_card_number.set(credit_card_number);
    }

    public LongProperty credit_card_numberProperty() {
        return credit_card_number;
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

    public String getExp_Date() {
        return exp_date.get();
    }

    public void setExp_Date(String exp_date) {
        this.exp_date.set(exp_date);
    }

    public StringProperty exp_dateProperty() {
        return exp_date;
    }
}
