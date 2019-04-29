package network.cardboard.crystallogic;

import java.util.ArrayList;
import argo.jdom.JsonNode;
import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonObjectNodeBuilder;
import static argo.jdom.JsonNodeBuilders.*;

public class Inventory
{
    // Possibly aim to make private, expose only those methods that
    // are relevant to inventories.
    public ArrayList<Item> contents;

    /**
     * Empty Constructor for new inventories
     */
    public Inventory()
    {
	this.contents = new ArrayList<Item>();
    }

    /**
     * Constructor based on a JSON node.
     * Used to build pre-existing inventories from save file.
     * @param inventoryJSON
     */
    public Inventory(JsonNode inventoryJSON)
    {
        parseJSON(inventoryJSON);
    }

    /**
     * Method: parseJSON(JsonNode)
     * This method takes a JsonArrayNode (the Inventory node) and turns it into the inventory.
     * There is some abstraction up to the Item object to help lessen the code.
     * @param jsonData
     */
    private void parseJSON(JsonNode jsonData)
    {
        jsonData.getArrayNode("inventory").forEach(item -> {
            addItem(new Item(item));
        });
    }

    /**
     * Insert an Item into this inventory.
     * @return true if the Item was added to the Inventory.
     */
    public boolean addItem(Item item)
    {
	return this.contents.add(item);
    }

    /**
     * Remove an Item from this inventory.
     * @return true if the Item was added to the Inventory.
     */
    public boolean removeItem(Item item)
    {
	return this.contents.remove(item);
    }

    /**
     * The amount of items within this Inventory.
     * @return the amount of items in this Inventory.
     */
    public int size()
    {
	return this.contents.size();
    }

    /**
     * The total encumberance of this Inventory's contents.
     * @return the total weight of all the items contained.
     */
    public double totalWeight()
    {
        return this.contents.stream()
            .mapToDouble(i -> i.weight)
            .sum();
    }

    /**
     * Render an Inventory in JSON.
     * @return the JsonNode representing this inventory.
     */
    public JsonArrayNodeBuilder toJSON()
    {
        JsonArrayNodeBuilder builder = anArrayBuilder();

        this.contents.stream()
            .forEach(item -> {
                builder.withElement(item.toJSON());
            });

        return builder;
    }
}
