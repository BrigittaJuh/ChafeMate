package Controller;


import Database.DatabaseHandler;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modell.Recipe;


public class ReceptViewController implements Initializable {
    private DatabaseHandler dbHandler;
    private int userid;
    private HomePageController parentController;
    private List<Recipe> allRecipes = new ArrayList<>();

    @FXML
    private TextField RezeptSuche;

    @FXML
    private ImageView imageRezept;

    @FXML
    private Label labelrezept;

    @FXML
    private TilePane recipeTilePane;

    @FXML
    private AnchorPane rezeptBoxt;
    @FXML
    private JFXButton ButtonErstellen;
    @FXML
    private AnchorPane side_anchorPane;

    @FXML
    private JFXComboBox<String> rezeptefiltern;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbHandler = new DatabaseHandler();

        ButtonErstellen.setOnAction(event -> openNewRecipeWindow());

        RezeptSuche.textProperty().addListener((observable, oldValue, newValue) -> {
            filterRecipes(newValue);
        });

    }
    public void loadRecipesFromDatabase() {
        allRecipes.clear();
        List<Recipe> recipes = DatabaseHandler.getInstance().getRecipesWithIngredients(userid);
        System.out.println("Anzahl der heruntergeladenen Rezepte: " + recipes.size());
        allRecipes.addAll(recipes);



        displayRecipes(allRecipes);

    }

    private void filterRecipes(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            displayRecipes(allRecipes);
        } else {
            List<Recipe> filteredRecipes = allRecipes.stream()
                    .filter(recipe -> recipe.getName().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());
            displayRecipes(filteredRecipes);
        }
    }
    private void displayRecipes(List<Recipe> recipes) {
        recipeTilePane.getChildren().clear();
        for (Recipe recipe : recipes) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Controller/ReceptCell.fxml"));
                VBox receptCell = loader.load();

                ReceptCellController cellController = loader.getController();
                cellController.setName(recipe.getName());
                cellController.setImage(recipe.getImagePath());
                cellController.setRecipe(recipe);

                recipeTilePane.getChildren().add(receptCell);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setParentController(HomePageController parentController) {
        this.parentController = parentController;
    }


    public void setUserid(int userid) {
        this.userid = userid;
        System.out.println("ReceptViewController: User ID set to " + this.userid);
        loadRecipesFromDatabase();
        displayRecipes(allRecipes);
    }


    private void openNewRecipeWindow() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/ReceptNew.fxml"));
            Parent root = loader.load();
            ReceptNewController receptNewController = loader.getController();
            receptNewController.setUserid(userid);

            receptNewController.setParentController(this);


            Stage stage = new Stage();
            stage.setTitle("Neues rezept ");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}