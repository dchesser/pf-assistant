package network.cardboard.crystallogic;

import argo.jdom.JsonNode;
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

    // λ-expressions tend to nag when they don't end in method-calls.
    public double getWeight()
    {
	return this.weight;
    }

    public JsonNode toJSON()
    {
	return object(field("name", string(this.name)),
		      field("description", string(this.description)),
		      field("cp_value", number(this.value.toCopper())),
		      field("weight", number(new Double(this.weight).toString())));
    }
}
