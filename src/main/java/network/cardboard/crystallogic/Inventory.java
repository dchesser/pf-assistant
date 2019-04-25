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

    public Inventory()
    {
	this.contents = new ArrayList<Item>();
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
    public int itemsContained()
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
    public JsonNode toJSON()
    {
	JsonArrayNodeBuilder builder = anArrayBuilder();

	this.contents.stream()
	    .forEach(item -> {
		    JsonObjectNodeBuilder jsonItem = anObjectBuilder()
			.withField("name", aStringBuilder(item.name))
			.withField("description", aStringBuilder(item.description))
			.withField("cp_value", aNumberBuilder(item.value.toString()))
			.withField("weight", aNumberBuilder(new Double(item.weight).toString()));

		    builder.withElement(jsonItem);
		});

	return builder.build();
    }
}
