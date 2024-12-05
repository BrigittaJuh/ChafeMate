package LoginRegister;

import Controller.HomePageController;
import Database.DatabaseHandler;
import animations.Shaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.application.Platform;
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

            // Bejelentkezési folyamat egy külön szálon
            new Thread(() -> {
                boolean isAuthenticated = databaseHandler.authenticateUser(user);
                if (isAuthenticated) {
                    Platform.runLater(() -> {
                        System.out.println("Anmeldung erfolgreich!");

                        userid = databaseHandler.getUserIdByUsername(loginText);
                        System.out.println("User ID : " + userid);

                        // Főablak bezárása és új jelenet betöltése
                        Stage currentStage = (Stage) buttoneinloggen.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Controller/HomePage.fxml"));

                        try {
                            Parent root = loader.load();
                            HomePageController homePageController = loader.getController();
                            homePageController.setUserid(userid);

                            currentStage.setScene(new Scene(root));
                            currentStage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    Platform.runLater(() -> {
                        Shaker shaker = new Shaker(fieldbenutzer);
                        shaker.shake();
                    });
                }
            }).start();
        });

        buttonNeuReg.setOnAction(event -> {
            Stage currentStage = (Stage) buttonNeuReg.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Controller/Register.fxml"));

            try {
                Parent root = loader.load();
                currentStage.setScene(new Scene(root));
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}



