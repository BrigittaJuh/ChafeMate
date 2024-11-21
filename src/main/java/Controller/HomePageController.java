package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.IOException;

public class HomePageController {
    private int userid;

    @FXML
    private AnchorPane homeAnc;

    @FXML
    StackPane StackPaneHomePage;

    @FXML
    private Button buttonEinkaufen;

    @FXML
    private Button buttonMenu;

    @FXML
    private Button buttonRecept;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_anchorPane;
    @FXML
    private ImageView exit;




    @FXML
    void initialize() {



        exit.setOnMouseClicked(event -> {

            Stage currentStage = (Stage) exit.getScene().getWindow();
            currentStage.close();


            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Controller/login.fxml"));
                Parent root = loader.load();
                Stage loginStage = new Stage();
                loginStage.setScene(new Scene(root));
                loginStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        setPage("/Controller/ReceptView.fxml");

    }

    public void setPage (String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Pane pane = loader.load();


            if (fxml.contains("ReceptView")) {
                ReceptViewController receptController = loader.getController();
                receptController.setUserid(this.userid);  // Felhasználói azonosító beállítása
                receptController.setParentController(this);
            } else if (fxml.contains("EinkaufenView")) {
                EinkaufenController einkaufenController = loader.getController();
                einkaufenController.setUserid(this.userid);
            }

            StackPaneHomePage.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReceptButtonAction() {
        setPage("/Controller/ReceptView.fxml");
    }


    @FXML
    void handleShoppinButtonAction() {
        setPage("/Controller/EinkaufenView.fxml");

    }

    public void setUserid(int userid) {
        this.userid = userid;
        setPage("/Controller/ReceptView.fxml");
    }
    public int getUserid(){
        return this.userid;
    }
}

