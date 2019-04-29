package network.cardboard.crystallogic;

import argo.jdom.JsonNode;
import argo.jdom.JsonObjectNodeBuilder;

import static argo.jdom.JsonNodeBuilders.aNumberBuilder;
import static argo.jdom.JsonNodeBuilders.aStringBuilder;
import static argo.jdom.JsonNodeBuilders.anObjectBuilder;
import static argo.jdom.JsonNodeFactories.*;

/**
 * Items are useful because as a wise creature said, "you can never
 * have too much rope".  What counts as an item is simply something
 * you can carry, whether it be a weapon, armor, assorted adventuring
 * gear, treasure, food, a potion or salve, and who knows what else.
 */
public class Item
{
    public String name;
    public String description;
    public double weight;
    public MonetaryValue value;

    public Item(String name, String description, int valueInCP, double weight)
    {
        this.name = name;
        this.description = description;
        this.value = MonetaryValue.inCopper(valueInCP);
        this.weight = weight;
    }

    /**
     * Constructor: Item(JsonNode)
     * This constructor takes a JsonNode and parses it.
     * The parsing process is handled by an internal private method, parseJSON(JsonNode).
     * @param data
     */
    public Item(JsonNode data)
    {
        parseJSON(data);
    }

    // λ-expressions tend to nag when they don't end in method-calls.
    public double getWeight()
    {
        return this.weight;
    }

    /**
     * Method: parseJSON(JsonNode)
     * This method takes an object node.
     * That object node is then broken into the necessary parts to create an item.
     * @param data
     */
    private void parseJSON(JsonNode data)
    {
        name = data.getStringValue("name");
        description = data.getStringValue("description");
        value = MonetaryValue.inCopper(Integer.parseInt(data.getNumberValue("value")));
        weight = Double.parseDouble(data.getNumberValue("weight"));
    }

    /**
     * Method: toJSON()
     * This method returns the item as a JsonObjectNodeBuilder,
     * which can be used to then .build() the json into an object from where it's
     * being called, or put into a larger json without building first, so that you
     * can get a larger singular save.
     * @return
     */
    public JsonObjectNodeBuilder toJSON()
    {
        JsonObjectNodeBuilder builder = anObjectBuilder();

        builder.withField("name", aStringBuilder(name))
                .withField("description", aStringBuilder(description))
                .withField("value", aNumberBuilder("" + value))
                .withField("weight", aNumberBuilder("" + weight));
        return builder;
    }
}
