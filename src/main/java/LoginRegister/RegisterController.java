package LoginRegister;


import Database.DatabaseHandler;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import modell.User;

public class RegisterController {
    @FXML
    private JFXButton buttonregAnmelden;

    @FXML
    private TextField regBenutzername;

    @FXML
    private TextField regEmail;

    @FXML
    private TextField regFirstname;

    @FXML
    private TextField regLastname;

    @FXML
    private PasswordField regPasswort;

    @FXML
    private StackPane regRoot;

    @FXML
    void initialize(){

        buttonregAnmelden.setOnAction(event -> {
            createUser();

        });

    }
    private void createUser() {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        String name = regFirstname.getText();
        String lastName = regLastname.getText();
        String userName = regBenutzername.getText();
        String password = regPasswort.getText();
        String email = regEmail.getText();


        if (name.isEmpty() || lastName.isEmpty() || userName.isEmpty() || password.isEmpty() || email.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehlende Daten");
            alert.setContentText("Bitte füllen Sie alle Felder aus!");
            alert.showAndWait();
        } else {

            User user = new User(name, lastName, userName, password, email);

            databaseHandler.signUpUser(user);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erfolgreiche Registrierung");
            alert.setHeaderText(null);
            alert.setContentText("Die Registrierung war erfolgreich!");
            alert.showAndWait();

            goToLogin();
        }
    }

    private void goToLogin() {
        try {

            Parent loginRoot = FXMLLoader.load(getClass().getResource("/Controller/login.fxml"));

            Scene loginScene = new Scene(loginRoot);

            Stage currentStage = (Stage) regRoot.getScene().getWindow();
            currentStage.setScene(loginScene);

            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
