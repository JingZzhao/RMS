package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.entity.Person;

import java.io.IOException;

public class Main extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        mainStage.setResizable(false);
        //set window icon
        //...

        gotoLoginUi();
    }

   /* public void gotoMainUi(String userId){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main_ui.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("RetailerApp");
            MainUiController controller = loader.getController();
            controller.setApp(this);
            controller.setMyName(userId);
            Scene scene = new Scene(root, 700, 500);
            //scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }*/

    public void gotoCustomerUi(String account){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customer_ui.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("RetailerApp");
            customerController controller = loader.getController();
            controller.setApp(this);
            controller.setUserInfo(account);
            Scene scene = new Scene(root, 905, 605);
            //scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        }catch(Exception e){
            System.out.println(e.getMessage() + e.toString());
        }
    }

    public boolean showEditDialog(Person customer){
        try{
            //Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("editDialog_ui.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Information");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Set the customer into the controller.
            editDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCustomer(customer);

            //Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public void gotoEmployeeUi(String account){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("employee_ui.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("RetailerApp");
            employeeController controller = loader.getController();
            controller.setApp(this);
            controller.setUserInfo(account);
            Scene scene = new Scene(root, 905, 605);
            //scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        }catch(Exception e){
            System.out.println(e.getMessage() + e.toString());
        }
    }

    /*public void gotoProductUi(String id){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("product_ui.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("RetailerApp");
            ProductUiController controller = loader.getController();
            controller.setApp(this);
            controller.setStoreInfo(id);
            Scene scene = new Scene(root, 700, 460);
            scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        }catch(Exception e){
            System.out.println(e.getMessage() + e.toString());
        }
    }*/

    public void closeWindow(){
        mainStage.close();
    }

    public void hideWindow(){
        mainStage.hide();
    }

    public void showWindow(){
        mainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void gotoLoginUi(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login_ui.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("RetailerApp");
            loginController controller = loader.getController();
            controller.setApp(this);
            Scene scene = new Scene(root, 905, 605);
            //scene.getStylesheets().add(Main.class.getResource("main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
