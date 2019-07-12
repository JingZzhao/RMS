package sample;

import Util.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.entity.Person;


/**
 * Dialog to edit details of a customer.
 */

public class editDialogController {

    @FXML
    private TextField streetField;
    @FXML
    private TextField state_provinceField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField postalCodeField;


    private Stage dialogStage;
    private Person customer;
    private boolean okClicked = false;
    private int customerId = -1;

    /**
     *  Initializes the controller class. This method is automatically called
     *  after the fxml has been loaded.
     */
    @FXML
    private  void initialize(){

    }

    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the customer to be edited in the dialog.
     * @param customer
     */
    public void setCustomer(Person customer) {
        this.customer = customer;
        customerId = customer.getId();

        streetField.setText(customer.getStreet());
        cityField.setText(customer.getCity());
        state_provinceField.setText(customer.getState());
        countryField.setText(customer.getCountry());
        postalCodeField.setText(customer.getPostalCode());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * @return
     */
    public boolean isOkClicked(){
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk(){
        if(isInputValid()){
            String updateStreet = streetField.getText();
            String updateCity = cityField.getText();
            String updateState = state_provinceField.getText();
            String updatepCode = postalCodeField.getText();
            String updateCountry = countryField.getText();

            customer.setStreet(updateStreet);
            customer.setCity(updateCity);
            customer.setState(updateState);
            customer.setPostalCode(updatepCode);
            customer.setCountry(updateCountry);

            DatabaseUtil.updateAddress(updateStreet, updateCity, updateState, updateCountry,updatepCode, this.customerId);

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel(){
        okClicked = true;
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * @return true if the input is valid
     */
    private boolean isInputValid(){
        String errorMessage = "";

        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "No valid street!\n";
        }
        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }
        if (state_provinceField.getText() == null || state_provinceField.getText().length() == 0) {
            errorMessage += "No valid state/province!\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Input is valid!");
            alert.show();
            return false;
        }

    }

}
