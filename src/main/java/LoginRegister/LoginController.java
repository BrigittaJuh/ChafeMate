package LoginRegister;

import Controller.HomePageController;
import Database.DatabaseHandler;
import animations.Shaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import modell.User;

import java.io.IOException;

public class LoginController {

    private  int userid;


    @FXML
    private JFXButton buttonNeuReg;

    @FXML
    private JFXButton buttoneinloggen;

    @FXML
    private TextField fieldbenutzer;

    @FXML
    private PasswordField fieldpasswort;

    @FXML
    private StackPane loginRoot;

    @FXML
    private JFXCheckBox showpasswort;
    private  DatabaseHandler databaseHandler;


    @FXML
    void initialize() {
        databaseHandler = new DatabaseHandler();

        buttoneinloggen.setOnAction(event -> {
            String loginText = fieldbenutzer.getText().trim();
            String loginPassword = fieldpasswort.getText().trim();

            User user = new User();
            user.setUserName(loginText);
            user.setPassword(loginPassword);

            if (databaseHandler.authenticateUser(user)) {
                System.out.println("Anmeldung erfolgreich!");


                userid = databaseHandler.getUserIdByUsername(loginText);
                System.out.println("User ID : " + userid);
                buttonNeuReg.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Controller/HomePage.fxml"));

                try {
                    loader.load();
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));

                    HomePageController homePageController = loader.getController();
                    homePageController.setUserid(userid);

                    stage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {

                Shaker shaker = new Shaker(fieldbenutzer);
                shaker.shake();
            }
        });

        buttonNeuReg.setOnAction(event -> {
            buttonNeuReg.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Controller/Register.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));


            stage.showAndWait();
        });
    }
}



