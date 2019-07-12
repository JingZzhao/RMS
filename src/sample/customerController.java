package sample;

import Util.DatabaseUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.util.converter.LongStringConverter;
import sample.entity.CreditCard;
import sample.entity.Order;
import sample.entity.Person;

import java.net.URL;
import java.util.ResourceBundle;

public class customerController implements Initializable {

    //private Person customer;

    //Customer information
    @FXML
    private JFXTextField tf_userInfo_customerId;
    @FXML
    private JFXTextField tf_userInfo_customerfName;
    @FXML
    private JFXTextField tf_userInfo_customerlName;
    @FXML
    private JFXTextField tf_userInfo_customerGender;
    @FXML
    private JFXTextField tf_userInfo_customerPostcode;
    @FXML
    private JFXTextField tf_userInfo_customerType;
    @FXML
    private JFXTextField tf_userInfo_customerStreet;
    @FXML
    private JFXTextField tf_userInfo_customerCity;
    @FXML
    private JFXTextField tf_userInfo_customerState;
    @FXML
    private JFXTextField tf_userInfo_customerCountry;

    @FXML
    private TableView tbv_purchase_creditCard;
    @FXML
    private TableColumn tb_column_cardNum;
    @FXML
    private TableColumn tb_column_cardType;
    @FXML
    private TableColumn tb_column_expDate;

    @FXML
    private TableView tbv_purchase_order;
    @FXML
    private TableColumn tb_column_orderId;
    @FXML
    private TableColumn tb_column_upc;
    @FXML
    private TableColumn tb_column_product;
    @FXML
    private TableColumn tb_column_brand;
    @FXML
    private TableColumn tb_column_amount;
    @FXML
    private TableColumn tb_column_orderDate;
    @FXML
    private TableColumn tb_column_store;
    @FXML
    private TableColumn tb_column_type;

    @FXML
    private TextField tf_add_cardNumber;
    @FXML
    private TextField tf_add_type;
    @FXML
    private TextField tf_add_expiredDate;


    @FXML
    private Label lb_welcome;

    private Main myApp;
    private int customer_credit_id = 0;
    private CreditCard edit_card = new CreditCard();
   // private long cardNum_edit;
    //private String cardType_edit;
    //private String cardExpDate_edit;

    public void setApp(Main myApp){
        this.myApp = myApp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        edit_card = null;
        // Listen for selection changes
        tbv_purchase_creditCard.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> select_creditCard())
        );
    }

    /**
     * Setting user information
     * @param account
     */
    public void setUserInfo(String account){
        Person user = DatabaseUtil.getCustomer(account);

        tf_userInfo_customerId.setText(String.valueOf(user.getId()));
        tf_userInfo_customerfName.setText(user.getFirstName());
        tf_userInfo_customerlName.setText(user.getLastName());
        tf_userInfo_customerGender.setText(user.getGender());
        tf_userInfo_customerStreet.setText(user.getStreet());
        tf_userInfo_customerCity.setText(user.getCity());
        tf_userInfo_customerState.setText(user.getState());
        tf_userInfo_customerCountry.setText(user.getCountry());
        tf_userInfo_customerPostcode.setText(user.getPostalCode());
        tf_userInfo_customerType.setText(user.getType());

        customer_credit_id = user.getId();
        getCreditCard(user.getId());
        getOrder(user.getId());

        lb_welcome.setText(user.getFirstName() +" " + user.getLastName() + ", welcom!");
    }

    /**
     * Getting credit card information
     * @param id
     */
    public void getCreditCard(int id){
        tb_column_cardNum.setCellValueFactory(new PropertyValueFactory("credit_card_number"));
        tb_column_cardType.setCellValueFactory(new PropertyValueFactory("type"));
        tb_column_expDate.setCellValueFactory(new PropertyValueFactory("exp_date"));

        ObservableList<CreditCard> credit_cards = DatabaseUtil.getCreditCard(id);
        if(credit_cards != null){
            tbv_purchase_creditCard.setItems(credit_cards);
        }

    }

    /**
     * Add new Credit card
     */
    @FXML
    public void handleAdd(){
        CreditCard creditCard = new CreditCard();
        ObservableList<CreditCard> credit_cards = tbv_purchase_creditCard.getItems();

        Long cardNum = Long.parseLong(tf_add_cardNumber.getText());
        String cardType = tf_add_type.getText();
        String cardExpDate = tf_add_expiredDate.getText();

        creditCard.setCredit_Card_Num(cardNum);
        creditCard.setType(cardType);
        creditCard.setExp_Date(cardExpDate);
        credit_cards.add(creditCard);

        //Add to database
        DatabaseUtil.addCreditCard(cardNum, cardType, cardExpDate, this.customer_credit_id);

        if(credit_cards != null){
            tbv_purchase_creditCard.setItems(credit_cards);
        }

        tf_add_cardNumber.clear();
        tf_add_type.clear();
        tf_add_expiredDate.clear();
    }

    /**
     * Delete one credit card.
     */
    @FXML
    public void handleDelete(){
        int selectedIndex = tbv_purchase_creditCard.getSelectionModel().getSelectedIndex();
        CreditCard cCard = (CreditCard) tbv_purchase_creditCard.getSelectionModel().getSelectedItem();

        //Delete from database
        DatabaseUtil.deleteCreditCard(cCard.getCredit_Card_Num());
        //Delete from table view
        tbv_purchase_creditCard.getItems().remove(selectedIndex);
    }

    public void select_creditCard() {
        edit_card = (CreditCard) tbv_purchase_creditCard.getSelectionModel().getSelectedItem();
        System.out.println("log:" + edit_card.getCredit_Card_Num() + "," + edit_card.getType() + "," + edit_card.getExp_Date());

        // Set table view editable.
        //tbv_purchase_creditCard.setEditable(true);
        //tb_column_cardNum.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        //tb_column_cardType.setCellFactory(TextFieldTableCell.forTableColumn());
        //tb_column_expDate.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    /**
     * Getting order information
     * @param id
     */
    public void getOrder(int id){
        tb_column_orderId.setCellValueFactory(new PropertyValueFactory("order_id"));
        tb_column_upc.setCellValueFactory(new PropertyValueFactory("upc_code"));
        tb_column_product.setCellValueFactory(new PropertyValueFactory("product_name"));
        tb_column_brand.setCellValueFactory(new PropertyValueFactory("brand"));
        tb_column_amount.setCellValueFactory(new PropertyValueFactory("amount"));
        tb_column_orderDate.setCellValueFactory(new PropertyValueFactory("order_date"));
        tb_column_store.setCellValueFactory(new PropertyValueFactory("store"));
        tb_column_type.setCellValueFactory(new PropertyValueFactory("type"));

        ObservableList<Order> orders = DatabaseUtil.getOrder(id);
        if(orders != null){
            tbv_purchase_order.setItems(orders);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEdit(){
        Person customer = new Person();

        customer.setStreet(tf_userInfo_customerStreet.getText());
        customer.setCity(tf_userInfo_customerCity.getText());
        customer.setState(tf_userInfo_customerState.getText());
        customer.setCountry(tf_userInfo_customerCountry.getText());
        customer.setPostalCode(tf_userInfo_customerPostcode.getText());
        customer.setId(Integer.parseInt(tf_userInfo_customerId.getText()));

       boolean okClicked = myApp.showEditDialog(customer);
       if(okClicked){
           //Update the address information of customer on customer UI
           tf_userInfo_customerStreet.setText(customer.getStreet());
           tf_userInfo_customerCity.setText(customer.getCity());
           tf_userInfo_customerState.setText(customer.getState());
           tf_userInfo_customerCountry.setText(customer.getCountry());
           tf_userInfo_customerPostcode.setText(customer.getPostalCode());
       } else {
           // Show the error message.
           Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
           alert.setAlertType(Alert.AlertType.ERROR);
           alert.setContentText("Editing Failed!");
           alert.show();
       }

    }

    /**
     *  Log out
     */
    @FXML
    private void handleLogout(){
        myApp.gotoLoginUi();
    }

}

