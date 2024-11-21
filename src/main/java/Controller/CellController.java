package Controller;

import Database.DatabaseHandler;
import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import modell.Shoppinglist;

import java.io.IOException;

public class CellController extends JFXListCell<Shoppinglist> {

        @FXML
        private ImageView deleteButton;

        @FXML
        private ImageView iconImageView;

        @FXML
        private Label produktLabel;

        @FXML
        private AnchorPane roootAncor;

        @FXML
        private Label stuckLabel;
    private FXMLLoader fxmlLoader;
    private DatabaseHandler databaseHandler;

    public CellController(DatabaseHandler databaseHandler) {
    }


    @FXML
    void initialize(){
            databaseHandler = new DatabaseHandler();

        }

    @Override
    protected void updateItem(Shoppinglist myList, boolean empty) {
        super.updateItem(myList, empty);
        if(empty || myList == null) {
            setItem(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/controller/Cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            deleteButton.setOnMouseClicked(event -> {
                getListView().getItems().remove(myList);
                databaseHandler.deleteList(myList.getId());
                System.out.println("Item gelöst: " + myList.getItem());
            });

            produktLabel.setText(myList.getItem());
            stuckLabel.setText(String.valueOf(myList.getQuantily()));


            setText(null);
            setGraphic(roootAncor);

        }
    }
}

