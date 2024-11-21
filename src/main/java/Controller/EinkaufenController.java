package Controller;
import Database.DatabaseHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import io.github.palexdev.mfxcore.controls.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import modell.Shoppinglist;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class EinkaufenController {
    private int userid;
    private DatabaseHandler databaseHandler;
    @FXML
    private JFXListView<Shoppinglist> listView;
    @FXML
    private AnchorPane rootAnc;
    @FXML
    private TextField produkt;
    @FXML
    private TextField stuck;
    @FXML
    private JFXButton addItem;
    private ObservableList<Shoppinglist> shoppinglists;

    @FXML
    void initialize() {
        databaseHandler = new DatabaseHandler();
        shoppinglists = FXCollections.observableArrayList();

        listView.setItems(shoppinglists);
        listView.setCellFactory(param -> new CellController(databaseHandler));


        addItem.setOnAction(event -> {
            Shoppinglist shoppinglist = new Shoppinglist();
            String shoppingListItem = produkt.getText().trim();
            int shoppingListStuck = Integer.parseInt(stuck.getText().trim());

            if (!shoppingListItem.equals("") && shoppingListStuck > 0) {
                shoppinglist.setItem(shoppingListItem);
                shoppinglist.setQuantily(shoppingListStuck);

                databaseHandler.insertList(shoppinglist, this.userid);
                shoppinglists.add(shoppinglist);

                produkt.setText("");
                stuck.setText("");
                System.out.println("Produkt hinzugefügt");
            } else {
                System.out.println("Fehler beim Hinzufügen");
            }
        });
    }


        public void setUserid ( int userid){
            this.userid = userid;
            System.out.println("User Id is " + this.userid);
            loadShoppingList();
        }
    private void loadShoppingList() {

        shoppinglists.clear();
        shoppinglists.addAll(databaseHandler.getShoppingList(userid));
        listView.setItems(shoppinglists);

    }

        public int getUserid () {
            return this.userid;

        }
    }
