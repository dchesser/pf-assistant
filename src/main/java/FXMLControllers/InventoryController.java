package FXMLControllers;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import network.cardboard.crystallogic.Inventory;
import network.cardboard.crystallogic.Item;
import network.cardboard.crystallogic.PlayerCharacter;

/**
 * Written by David Hagerty
 * Written on 2019-04-28
 */
public class InventoryController {
    @FXML
    private TableView<Item> inventoryTable;

    private Inventory playerInventory;

    public InventoryController()
    {

    }

    public InventoryController(Inventory inventory)
    {
        // This runs automatically without any option to feed it objects.
        // Therefore, please don't try to use it for setting up data that we don't have
        // time to feed it.
    }

    /**
     * Method: setInventory(Inventory)
     * This method sets the display options and data contents of the inventory display.
     * This method should be run if you want it to display any sorts of information,
     * as the constructor is useless for those functions.
     * @param inventory
     */
    public void setInventory(Inventory inventory)
    {
        this.playerInventory = inventory;
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
        // I had to force the ObservableValue<>() to be ObservableValue<Double>()
        // It didn't compile otherwise. -wbullock
        weightColumn.setCellValueFactory(param -> new ObservableValue<Double>() {
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

        inventoryTable.getColumns().addAll(nameColumn, weightColumn, valueColumn, descriptionColumn);


    }
}
