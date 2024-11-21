package Controller;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ZutatenCell extends JFXListCell<AnchorPane> {
    @FXML
    private ImageView delete;

    @FXML
    private TextField einheit;

    @FXML
    private TextField menge;

    @FXML
    private TextField zutat;

    @FXML
    private AnchorPane rootAnchor;

    private FXMLLoader fxmlLoader;



    @FXML
    public void initialize() {
        delete.setOnMouseClicked(event -> deleteZutat());
    }

    private void deleteZutat() {
        getListView().getItems().remove(getItem());
    }

    @Override
    protected void updateItem(AnchorPane item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(null);
            setGraphic(rootAnchor);
        }
    }
    public String getName() {
        return zutat.getText();
    }

    public String getQuantity() {
        return menge.getText();
    }

    public String getUnit() {
        return einheit.getText();
    }

    // Setter metódusok
    public void setName(String name) {
        zutat.setText(name);
    }

    public void setQuantity(String quantity) {
        menge.setText(quantity);
    }

    public void setUnit(String unit) {
        einheit.setText(unit);
    }
}