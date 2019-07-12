package sample;

import Util.DatabaseUtil;
import Util.FileUtil;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class loginController implements Initializable {
    @FXML
    public ToggleGroup identity;

    private Main myApp;

    @FXML
    private ProgressBar prgs_login;

    @FXML
    private JFXCheckBox rememberInfo;

    @FXML
    private JFXButton btn_start;

    @FXML
    private JFXTextField tf_user;

    @FXML
    private JFXPasswordField tf_password;

    @FXML
    private JFXRadioButton rb_customer;

    @FXML
    private JFXRadioButton rb_employee;

    JFXDialog dialog = new JFXDialog();

    private Thread thread;

    public void setApp( Main myApp){
        this.myApp = myApp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

        rememberInfo.setSelected(true);

        RequiredFieldValidator validator_user = new RequiredFieldValidator();
        validator_user.setMessage("Please input user name");
        tf_user.getValidators().add(validator_user);
        tf_user.focusedProperty().addListener((o, oldVal,newVal)->{
            if(!newVal) tf_user.validate();
        });

        RequiredFieldValidator validator_password = new RequiredFieldValidator();
        validator_password.setMessage("Please input password");
        tf_user.getValidators().add(validator_password);
        tf_user.focusedProperty().addListener((o, oldVal,newVal)->{
            if(!newVal) tf_user.validate();
        });

        rb_customer.setSelected(false);
        prgs_login.setVisible(false);
        String str = FileUtil.getUserAndPass();
        Pattern p = Pattern.compile("[#]+");
        String[] result = p.split(str);
        if(result.length >= 1){
            tf_user.setText(result[0]);
        }
        if(result.length >= 2){
            tf_password.setText(result[1]);
        }

    }

    /**
     * Login button pressed event
     */
    @FXML
    public void onStart(){
        System.out.println("ok");
        prgs_login.setVisible(true);

        //Create login thread
        myProgress myProgress = new myProgress(prgs_login);
        thread = new Thread(myProgress);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();

        if(rememberInfo.isSelected()){
            FileUtil.setUserAndPass(tf_user.getText(), tf_password.getText());
        }else{
            FileUtil.setUserAndPass(tf_user.getText(), "");
        }

        //Login UI control components are invisible
        setDisable(true);
    }

    /**
     * On login--control component---login UI components are invisible
     */
    public void setDisable(boolean bool){
        btn_start.setDisable(bool);
        tf_user.setDisable(bool);
        tf_password.setDisable(bool);
        rememberInfo.setDisable(bool);
    }

    /**
     * Check and login
     */
    private void doCheckUser(){
        if(identity.getSelectedToggle() == rb_customer){
            if(DatabaseUtil.checkCustomer(tf_user.getText().trim(), tf_password.getText())){
                myApp.gotoCustomerUi(tf_user.getText());
            }else{
                setDisable(false);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed!");
                alert.show();
            }
        }else if(identity.getSelectedToggle() == rb_employee){
            if(DatabaseUtil.checkEmployee(tf_user.getText().trim(), tf_password.getText())){
                myApp.gotoEmployeeUi(tf_user.getText());
            }else{
                setDisable(false);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed!");
                alert.show();
            }
        }
        else{
            setDisable(false);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please Choose User Type!");
            alert.show();
        }

    }

    /**
     * Forgot password
     */
    @FXML
    public void forgotPass(){
        myApp.hideWindow();
        Stage myStage = new Stage();
        myStage.setResizable(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("forgotPass.fxml"));
        Parent root = null;
        try{
            root = loader.load();
        }catch(IOException e){
            e.printStackTrace();
        }
        //ForgotPass con = loader.getController();

    }

    /**
     * Login--press login button---start new thread to check user type
     */
    class myProgress implements Runnable{

        private  ProgressBar prgs_login;

        myProgress(ProgressBar prgs_login){
            this.prgs_login = prgs_login;
        }

        @Override
        public void run(){
            try{

                for(int i = 0; i <= 100; i++){
                    prgs_login.setProgress(i);
                }
                sleep(100);
                // update JavaFX main thread
                Platform.runLater(loginController.this::doCheckUser);

            }catch (Exception ignored){

            }
        }
    }

}
