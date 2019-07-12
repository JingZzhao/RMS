package sample;

import Util.DatabaseUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import sample.entity.Person;
import sample.entity.Product;
import sample.entity.Store;

import java.net.URL;
import java.util.ResourceBundle;

public class employeeController implements Initializable {

    //private Person employee;

    @FXML
    private JFXTextField tf_userInfo_employeeId;
    @FXML
    private JFXTextField tf_userInfo_employeeType;
    @FXML
    private JFXTextField tf_userInfo_employeefName;
    @FXML
    private JFXTextField tf_userInfo_employeelName;
    @FXML
    private JFXTextField tf_userInfo_employeeGender;
    @FXML
    private JFXTextField tf_userInfo_employeeStore;
    @FXML
    private JFXTextField tf_store_street;
    @FXML
    private JFXTextField tf_store_city;
    @FXML
    private JFXTextField tf_store_state;
    @FXML
    private JFXTextField tf_store_country;
    @FXML
    private JFXTextField tf_store_postcode;
    @FXML
    private Label lb_open;
    @FXML
    private Label lb_close;

    @FXML
    private TableView tbv_stock;
    @FXML
    private TableColumn tb_column_upcCode;
    @FXML
    private TableColumn tb_column_product;
    @FXML
    private TableColumn tb_column_type;
    @FXML
    private TableColumn tb_column_brand;
    @FXML
    private TableColumn tb_column_price;
    @FXML
    private TableColumn tb_column_amount;
    @FXML
    private TableColumn tb_column_vendor;
    @FXML
    private TableColumn tb_column_lastedSupplyDate;
    @FXML
    private TableColumn tb_column_supplyPrice;

    @FXML
    private TextField tf_add_upc;
    @FXML
    private TextField tf_add_amount;
    @FXML
    private TextField tf_add_price;

    @FXML
    private Label lb_current;
    @FXML
    private Label lb_max;

    @FXML
    private Label lb_welcome;


    private Main myApp;

    public void setApp(Main myApp){
        this.myApp = myApp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setUserInfo(String account){
        Person user = DatabaseUtil.getEmployee(account);

        tf_userInfo_employeeId.setText(String.valueOf(user.getId()));
        tf_userInfo_employeefName.setText(user.getFirstName());
        tf_userInfo_employeelName.setText(user.getLastName());
        tf_userInfo_employeeGender.setText(user.getGender());
        tf_userInfo_employeeType.setText(user.getType());

        setStoreInfo(account);

        lb_welcome.setText(user.getFirstName() +" " + user.getLastName() + ", welcom!");
    }

    public void setStoreInfo(String account){
        Store store = DatabaseUtil.getStore(account);

        tf_userInfo_employeeStore.setText(store.getStoreName());
        tf_store_street.setText(store.getStreet());
        tf_store_city.setText(store.getCity());
        tf_store_state.setText(store.getState());
        tf_store_country.setText(store.getCountry());
        tf_store_postcode.setText(store.getPostalCode());
        lb_current.setText(String.valueOf(store.getCurrentNum()));
        lb_max.setText(String.valueOf(store.getMaxNum()));
        lb_open.setText(store.getOpenTime());
        lb_close.setText(store.getCloseTime());

        getStoreStock(store.getStoreId());
    }

    /**
     * Getting store stock information
     * @param id
     */
    public void getStoreStock(int id){
        tb_column_upcCode.setCellValueFactory(new PropertyValueFactory("upc"));
        tb_column_product.setCellValueFactory(new PropertyValueFactory("product"));
        tb_column_type.setCellValueFactory(new PropertyValueFactory("type"));
        tb_column_brand.setCellValueFactory(new PropertyValueFactory("brand"));
        tb_column_price.setCellValueFactory(new PropertyValueFactory("price"));
        tb_column_amount.setCellValueFactory(new PropertyValueFactory("amount"));
        tb_column_vendor.setCellValueFactory(new PropertyValueFactory("vendor"));
        tb_column_lastedSupplyDate.setCellValueFactory(new PropertyValueFactory("lastedSupplyDate"));
        tb_column_supplyPrice.setCellValueFactory(new PropertyValueFactory("supplyPrice"));

        ObservableList<Product> products = DatabaseUtil.getProduct(id);
        if(products != null){
            tbv_stock.setItems(products);
        }

    }

    /**
     * Add new product
     */
    @FXML
    public void handleAdd(){
        Product product = new Product();
        ObservableList<Product> products = tbv_stock.getItems();

        product.setUpc(tf_add_upc.getText());
        product.setAmount(Integer.parseInt(tf_add_amount.getText()));
        product.setPrice(Double.parseDouble(tf_add_price.getText()));

        lb_current.setText(String.valueOf(Integer.parseInt(lb_current.getText())+ product.getAmount()));
        if(Integer.parseInt(lb_current.getText()) > Integer.parseInt(lb_max.getText())){
            lb_current.setTextFill(Color.RED);
        }else{
            //products.add(product);
            //tbv_stock.setItems(products);

            if(products != null){
                products.forEach((product_old) -> {
                    if(product_old.getUpc().equals(tf_add_upc.getText())){
                        product_old.setAmount(Integer.parseInt(tf_add_amount.getText()) + product_old.getAmount());
                    }
                });
            }

        }

        //Add to database

        tf_add_upc.clear();
        tf_add_amount.clear();
        tf_add_price.clear();
    }

    /**
     *  Log out
     */
    @FXML
    private void handleLogout(){
        myApp.gotoLoginUi();
    }
}
