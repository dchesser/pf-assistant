package FXMLControllers;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import network.cardboard.crystallogic.Inventory;
import network.cardboard.crystallogic.Item;
import network.cardboard.crystallogic.PlayerCharacter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Written by David Hagerty
 * Written on 2019-04-28
 */
public class InventoryController implements Initializable {
    @FXML
    private TableView<Item> inventoryTable;

    private Inventory playerInventory;

    public InventoryController(Inventory inventory) {
        this.playerInventory = inventory;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inventoryTable.setItems(FXCollections.observableArrayList(playerInventory.contents));

        TableColumn<Item, String> nameColumn = new TableColumn<>("Item Name");
        TableColumn<Item, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<Item, Double> weightColumn = new TableColumn<>("Weight");
        TableColumn<Item, Integer> valueColumn = new TableColumn<>("Value (in copper)");

        nameColumn.setCellValueFactory(param -> new ObservableStringValue() {
            @Override
            public String get() {
                return param.getValue().name;
            }

            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                return param.getValue().name;
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
        descriptionColumn.setCellValueFactory(param -> new ObservableStringValue() {
            @Override
            public String get() {
                return param.getValue().description;
            }

            @Override
            public void addListener(ChangeListener<? super String> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super String> listener) {

            }

            @Override
            public String getValue() {
                return param.getValue().description;
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
        weightColumn.setCellValueFactory(param -> new ObservableValue<>() {
            @Override
            public void addListener(ChangeListener<? super Double> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super Double> listener) {

            }

            @Override
            public Double getValue() {
                return param.getValue().weight;
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        });
        valueColumn.setCellValueFactory(param -> param.getValue().value.getValue());

        inventoryTable.getColumns().addAll(nameColumn, descriptionColumn, weightColumn, valueColumn);
    }
}
