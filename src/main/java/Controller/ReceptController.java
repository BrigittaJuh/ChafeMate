package Controller;

import Database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import modell.Recipe;

public class ReceptController {
    @FXML
    private ImageView delete;

    @FXML
    private Label recipeNameLabel;

    @FXML
    private TextArea descriptionLabel;


    @FXML
    private TextArea ingredientsLabel;

    @FXML
    private ImageView recipeImageView;
    private Recipe currentRecipe;

    @FXML
    private void initialize() {
        delete.setPickOnBounds(true);
        delete.setOnMouseClicked(event -> {
            System.out.println("Delete icon clicked");
            if (currentRecipe != null) {
                deleteRecipeFromDatabase(currentRecipe.getId());
            }
        });
    }
    public void showRecipeDetails(Recipe recipe) {
        this.currentRecipe = recipe;
        recipeNameLabel.setText(recipe.getName());


        String[] ingredients = recipe.getIngredients().split(",");
        StringBuilder formattedIngredients = new StringBuilder();
        for (int i = 0; i < ingredients.length; i++) {
            formattedIngredients.append("• ").append(ingredients[i].trim()).append("\n");
        }

        ingredientsLabel.setText(recipe.getIngredients());
        ingredientsLabel.setEditable(false);
        ingredientsLabel.setFocusTraversable(false);
        descriptionLabel.setText(recipe.getDescription());
        descriptionLabel.setFocusTraversable(false);

        descriptionLabel.setText(recipe.getDescription());

        if (recipe.getImagePath() != null && !recipe.getImagePath().isEmpty()) {
            recipeImageView.setImage(new Image(recipe.getImagePath()));
        }
    }

    private void deleteRecipeFromDatabase(int recipeId) {
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Rezept löschen");
        alert.setHeaderText("Möchtest du das Rezept wirklich löschen?");
        alert.setContentText("Dieser Vorgang wird das Rezept und seine Zutaten endgültig löschen.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            dbHandler.deleteRecipe(recipeId);
            System.out.println("Rezept gelöscht:" + recipeId);


            delete.getScene().getWindow().hide();
        }
    }
    @FXML
    private void handleDeleteClick() {
        System.out.println("Delete icon clicked");

        if (currentRecipe != null) {
            deleteRecipeFromDatabase(currentRecipe.getId());
        }
    }

}
