package Controller;

import Database.DatabaseHandler;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modell.Ingredient;
import modell.Recipe;
import modell.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ReceptNewController {
    private int userid;

    private DatabaseHandler databaseHandler = new DatabaseHandler();
    private ReceptViewController parentController;
    @FXML
    private TextField RezeptNameField;

    @FXML
    private Button buttonSpeichern;

    @FXML
    private HBox ingredienstHBox;

    @FXML
    private ImageView neueSchritte;

    @FXML
    private ImageView neueZutat;

    @FXML
    private ImageView rezeptBild;

    @FXML
    private AnchorPane side_anchorPane;

    @FXML
    private HBox zubereitungHbox;

    @FXML
    private Label zubereitungSchritte;

    @FXML
    private TextArea zubereitungText;

    @FXML
    private JFXListView<AnchorPane> zubereitungList;

    @FXML
    private JFXListView<AnchorPane> zutatenList;
    private User currentUser;


    @FXML
    public void initialize() {
        databaseHandler = new DatabaseHandler();

        zutatenList.setCellFactory(param -> new ListCell<AnchorPane>() {
            @Override
            protected void updateItem(AnchorPane item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(empty || item == null ? null : item);
            }
        });

        zubereitungList.setCellFactory(param -> new ListCell<AnchorPane>() {
            @Override
            protected void updateItem(AnchorPane item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                setGraphic(empty || item == null ? null : item);
            }
        });

        addZutatCell();
        neueZutat.setOnMouseClicked(event -> addZutatCell());

        addStep();
        neueSchritte.setOnMouseClicked(event -> addStep());

        rezeptBild.setOnMouseClicked(event -> handleImageUpload());
    }


    public void setUserid(int userid) {
        this.userid = userid;
        System.out.println("User ID set to: " + this.userid);
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    private void addZutatCell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/Zutaten.fxml"));
            AnchorPane zutatPane = loader.load();


            ZutatenCell controller = loader.getController();


            controller.setName("");
            controller.setQuantity("");
            controller.setUnit("");

            zutatenList.getItems().add(zutatPane);
            zutatPane.setUserData(controller);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addStep() {
        try {
            ZubereitungCelll cell = new ZubereitungCelll();
            zubereitungList.getItems().add(cell.getRoot());
            cell.getRoot().setUserData(cell);
            cell.setStepNumber(zubereitungList.getItems().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleImageUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                rezeptBild.setImage(image);
                String imagePath = selectedFile.getAbsolutePath();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void handleSaveRecipe() {

        String recipeName = RezeptNameField.getText();

        if (recipeName == null || recipeName.trim().isEmpty()) {
            showAlert("Fehler", "Der Rezeptname darf nicht leer sein.");
            return;
        }


        List<Ingredient> ingredients = zutatenList.getItems().stream()
                .map(anchorPane -> (ZutatenCell) anchorPane.getUserData())
                .filter(cell -> cell != null && !cell.getName().isEmpty() && !cell.getQuantity().isEmpty() && !cell.getUnit().isEmpty())
                .map(cell -> new Ingredient(0, 0, cell.getName(), cell.getQuantity(), cell.getUnit()))
                .collect(Collectors.toList());

        if (ingredients.isEmpty()) {
            showAlert("Fehler", "Mindestens eine Zutat muss angegeben werden, wobei alle Felder ausgefüllt sein müssen.");
            return;
        }


        String description = getZubereitungText();
        if (description == null || description.trim().isEmpty()) {
            showAlert("Fehler", "Mindestens ein Zubereitungsschritt muss angegeben werden.");
            return;
        }
        if (rezeptBild.getImage() == null) {
            showAlert("Fehler", "Bitte laden Sie ein Bild zum Rezept hoch.");
            return;
        }


        String imagePath = rezeptBild.getImage() != null ? rezeptBild.getImage().getUrl() : "";
        Recipe recipe = new Recipe();
        recipe.setUserid(userid);
        recipe.setName(recipeName);
        recipe.setDescription(description);
        recipe.setImagePath(imagePath);

        Stage stage = (Stage) buttonSpeichern.getScene().getWindow();
        stage.close();

        databaseHandler.saveRecipe(recipe, ingredients);
        System.out.println("Rezept und Zutaten wurden in der Datenbank gespeichert.");
        if (parentController != null) {
            parentController.loadRecipesFromDatabase();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private String getZubereitungText() {
        StringBuilder zubereitungSteps = new StringBuilder();
        for (AnchorPane item : zubereitungList.getItems()) {
            ZubereitungCelll cell = (ZubereitungCelll) item.getUserData();
            if (cell != null) {
                zubereitungSteps.append(cell.getStepDescription()).append("\n");
            }
        }
        return zubereitungSteps.toString();
    }
    public void setParentController(ReceptViewController parentController) {
        this.parentController = parentController;
    }

}