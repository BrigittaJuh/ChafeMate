package Controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ZubereitungCelll extends JFXListCell<AnchorPane> {
    @FXML
    private ImageView delete;

    @FXML
    private Label labelZahlen;

    @FXML
    private TextArea textArea;

    @FXML
    private AnchorPane zubereitung;

    private FXMLLoader fxmlLoader;

    public ZubereitungCelll() {
        fxmlLoader = new FXMLLoader(getClass().getResource("/controller/zubereitung.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {

        delete.setOnMouseClicked(event -> deleteStep());
    }

    private void deleteStep() {

        getListView().getItems().remove(getItem());
    }

    public void setStepNumber(int number) {
        labelZahlen.setText(String.valueOf(number));
    }

    public String getStepDescription() {
        return textArea.getText();
    }

    @Override
    protected void updateItem(AnchorPane item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(null);
            setGraphic(zubereitung);
            int index = getIndex() + 1;
            setStepNumber(index);
        }
    }
    public AnchorPane getRoot() {
        return zubereitung;
    }
}
