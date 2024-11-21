package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modell.Recipe;

import java.io.IOException;

public class ReceptCellController {

    private Recipe recipe;


    @FXML
    private ImageView imageView;

    @FXML
    private Label nameLabel;

    @FXML
    private VBox ReceptBox;

    public void setName(String name) {
        nameLabel.setText(name);
    }

    public void setImage(String imagePath) {
        Image image = new Image(imagePath);
        imageView.setImage(image);
    }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }


    @FXML
    private void handleClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Controller/Recept.fxml"));
            Parent root = loader.load();

            ReceptController receptController = loader.getController();
            receptController.showRecipeDetails(recipe);

            Stage stage = new Stage();
            stage.setTitle(recipe.getName());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
