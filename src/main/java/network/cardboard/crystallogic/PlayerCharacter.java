package network.cardboard.crystallogic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import argo.format.JsonFormatter;
import argo.format.PrettyJsonFormatter;
import argo.jdom.JsonArrayNodeBuilder;
import argo.jdom.JsonField;
import argo.jdom.JsonNode;
import argo.jdom.JsonNodeBuilder;
import argo.saj.InvalidSyntaxException;
import network.cardboard.crystallogic.PlayerSkill.GameSkill;
import network.cardboard.crystallogic.AbilityScores.BuildMethod;

import static argo.jdom.JsonNodeBuilders.aNumberBuilder;
import static argo.jdom.JsonNodeBuilders.aStringBuilder;
import static argo.jdom.JsonNodeBuilders.anArrayBuilder;
import static argo.jdom.JsonNodeBuilders.anObjectBuilder;

/**
 * Player Characters (or "PCs") are the very essence of Pathfinder or
 * any other roleplaying game that revovles around character
 * development for the sake of the plot. They revolve around seven
 * attributes: their race, their class/job, and their six traditional
 * ability scores: strength, dexterity, constitution, intelligence,
 * wisdom, and charisma.
 */
public class PlayerCharacter
{
    private String name;
    public AbilityScores abilityScores;
    private HashMap<GameSkill, PlayerSkill> skillSet;

    // General Stats.  May want to compress this later?
    private String alignment;
    private String deity;
    private String gender;
    private String size;
    private String height;
    private String weight;
    private String homeland;
    private String hairColor;
    private String eyeColor;
    private String race;
    private String age;

    private Integer platinumCoins;
    private Integer goldCoins;
    private Integer silverCoins;
    private Integer copperCoins;
    private String otherValuables;

    private Inventory inventory;

    private Integer currHealth;
    private Integer maxHealth;

    public PlayerCharacter(String name)
    {
        this.name = name;
        this.abilityScores = new AbilityScores(10);
        this.skillSet = PlayerSkill.skillList();

        // default values
        this.alignment = "N (default)";
        this.race = "Human (default)";
        this.deity = "Some Popular God(ess) (default)";
        this.height = "1'1\" (default)";
        this.weight = ">1 (default)";
        this.homeland = "Home (default)";
        this.hairColor = "Brown-ish (default)";
        this.eyeColor = "Brown (default)";
        this.gender = "Person (default)";
        this.age = ">1 (default)";
        this.size = "M (default)";
        this.platinumCoins = 0;
        this.goldCoins = 0;
        this.silverCoins = 0;
        this.copperCoins = 0;
        this.otherValuables = "Nothing (default)";
        this.currHealth = 0;
        this.maxHealth = 0;
    }

    public PlayerCharacter(JsonNode json)
    {
        try {
            parseJSON(json);
        } catch (InvalidSyntaxException ise) {
            ise.printStackTrace();
	    }
    }

    public PlayerCharacter(String name, BuildMethod rolls)
    {
        this.name = name;
        this.abilityScores = new AbilityScores(rolls);

        // default values
        this.alignment = "N (default)";
        this.race = "Human (default)";
        this.deity = "Some Popular God(ess) (default)";
        this.height = "1'1\" (default)";
        this.weight = ">1 (default)";
        this.homeland = "Home (default)";
        this.hairColor = "Brown-ish (default)";
        this.eyeColor = "Brown (default)";
        this.gender = "Person (default)";
        this.age = ">1 (default)";
        this.size = "M (default)";
        this.platinumCoins = 0;
        this.goldCoins = 0;
        this.silverCoins = 0;
        this.copperCoins = 0;
        this.otherValuables = "Nothing (default)";
        this.currHealth = 0;
        this.maxHealth = 0;
        this.skillSet = PlayerSkill.skillList();
    }

    public PlayerCharacter(String name, AbilityScores abilityScores)
    {
        this.name = name;
        this.abilityScores = abilityScores;

        //default values
        this.alignment = "N (default)";
        this.race = "Human (default)";
        this.deity = "Some Popular God(ess) (default)";
        this.height = "1'1\" (default)";
        this.weight = ">1 (default)";
        this.homeland = "Home (default)";
        this.hairColor = "Brown-ish (default)";
        this.eyeColor = "Brown (default)";
        this.gender = "Person (default)";
        this.age = ">1 (default)";
        this.size = "M (default)";
        this.platinumCoins = 0;
        this.goldCoins = 0;
        this.silverCoins = 0;
        this.copperCoins = 0;
        this.otherValuables = "Nothing (default)";
        this.currHealth = 0;
        this.maxHealth = 0;
        this.skillSet = PlayerSkill.skillList();
        this.inventory = new Inventory();
    }

    public PlayerCharacter(String name, AbilityScores abilityScores, String alignment, String race, String deity,
                           String height, String weight, String homeland, String hairColor, String eyeColor,
                           String gender, String age, String size, Integer platinumCoins, Integer goldCoins,
                           Integer silverCoins, Integer copperCoins, String otherValuables, Integer currHealth,
                           Integer maxHealth, HashMap<PlayerSkill.GameSkill, PlayerSkill> skillSet)
    {
        this.name = name;
        this.abilityScores = abilityScores;
        this.alignment = alignment;
        this.race = race;
        this.deity = deity;
        this.height = height;
        this.weight = weight;
        this.homeland = homeland;
        this.hairColor = hairColor;
        this.eyeColor = eyeColor;
        this.gender = gender;
        this.age = age;
        this.size = size;
        this.platinumCoins = platinumCoins;
        this.goldCoins = goldCoins;
        this.silverCoins = silverCoins;
        this.copperCoins = copperCoins;
        this.otherValuables = otherValuables;
        this.currHealth = currHealth;
        this.maxHealth = maxHealth;
        this.skillSet = skillSet;
    }

    public void parseJSON(JsonNode node) throws InvalidSyntaxException
    {
        name = node.getStringValue("name");
	    JsonNode abilities = node.getNode("abilityScores");
        int s= Integer.parseInt(abilities.getNumberValue("STR"));
        int d = Integer.parseInt(abilities.getNumberValue("DEX"));
        int con = Integer.parseInt(abilities.getNumberValue("CON"));
        int i = Integer.parseInt(abilities.getNumberValue("INT"));
        int w = Integer.parseInt(abilities.getNumberValue("WIS"));
        int c = Integer.parseInt(abilities.getNumberValue("CHA"));

        alignment = node.getStringValue("alignment");
        deity = node.getStringValue("deity");
        gender = node.getStringValue("gender");
        size = node.getStringValue("size");
        height = node.getStringValue("height");
        weight = node.getStringValue("weight");
        homeland = node.getStringValue("homeland");
        hairColor = node.getStringValue("hairColor");
        eyeColor = node.getStringValue("eyeColor");
        race = node.getStringValue("race");
        age = node.getStringValue("age");

        platinumCoins = Integer.parseInt(node.getNumberValue("platinumCoins"));
        goldCoins = Integer.parseInt(node.getNumberValue("goldCoins"));
        silverCoins = Integer.parseInt(node.getNumberValue("silverCoins"));
        copperCoins = Integer.parseInt(node.getNumberValue("copperCoins"));
        otherValuables = node.getStringValue("otherValuables");

        currHealth = Integer.parseInt(node.getNumberValue("currentHealth"));
        maxHealth = Integer.parseInt(node.getNumberValue("maxHealth"));

        abilityScores = new AbilityScores(s, d, con, i, w, c);

        // Set the skillSet map...
        if(!node.isNode("skills"))
        {
            skillSet = PlayerSkill.skillList();
        } else {

            skillSet = new HashMap<>();

            // Figure out wtf to do with the saved skillSet
            List<JsonNode> skills = node.getArrayNode("skills");

            GameSkill gameSkill = null;
            PlayerSkill playerSkill;

            boolean isClassSkill;
            int ranks = 0;

            for(JsonNode skill : skills)
            {
                isClassSkill = false;
                ranks = 0;

                List<JsonField> fields = skill.getFieldList();

                for(JsonField field : fields)
                {
                    switch(field.getNameText())
                    {
                        case "name":
                            gameSkill = PlayerSkill.getGameSkill(field.getValue().getText());
                        case "classSkill":
                            isClassSkill = Boolean.getBoolean(field.getValue().getText());
                            break;
                        case "ranks":
                            ranks = Integer.parseInt(field.getValue().getText());
                            break;
                    }
                }
                playerSkill = new PlayerSkill(gameSkill, ranks, isClassSkill);
                skillSet.put(gameSkill, playerSkill);
            }
        }
    }

    public String getName()
    {
	    return this.name;
    }

    // This toString should probably be replaced when we finish figuring out what json library should be used.

    /**
     * Method: toString()
     * This method returns the entire character in a JSON formatted string, primarily for saving the character.
     * @return - Returns the JSON formatted string version of the character.
     */
    @Override
    public String toString() {

        // create json object
        JsonNodeBuilder builder = anObjectBuilder()
                .withField("name", aStringBuilder(name))
                .withField("abilityScores", anObjectBuilder()
                        .withField("STR", aNumberBuilder("" + abilityScores.strength.getValue()))
                        .withField("DEX", aNumberBuilder("" + abilityScores.dexterity.getValue()))
                        .withField("CON", aNumberBuilder("" + abilityScores.constitution.getValue()))
                        .withField("INT", aNumberBuilder("" + abilityScores.intelligence.getValue()))
                        .withField("WIS", aNumberBuilder("" + abilityScores.wisdom.getValue()))
                        .withField("CHA", aNumberBuilder("" + abilityScores.wisdom.getValue()))
                )
                .withField("skills", buildSkills())
                .withField("alignment", aStringBuilder(alignment))
                .withField("race", aStringBuilder(race))
                .withField("deity", aStringBuilder(deity))
                .withField("height", aStringBuilder(height))
                .withField("weight", aStringBuilder(weight))
                .withField("homeland", aStringBuilder(homeland))
                .withField("hairColor", aStringBuilder(hairColor))
                .withField("eyeColor", aStringBuilder(eyeColor))
                .withField("gender", aStringBuilder(gender))
                .withField("age", aStringBuilder(age))
                .withField("size", aStringBuilder(size))
                .withField("platinumCoins", aNumberBuilder("" + platinumCoins))
                .withField("goldCoins", aNumberBuilder("" + goldCoins))
                .withField("silverCoins", aNumberBuilder("" + silverCoins))
                .withField("copperCoins", aNumberBuilder("" + copperCoins))
                .withField("otherValuables", aStringBuilder(otherValuables))
                .withField("currentHealth", aNumberBuilder("" + currHealth))
                .withField("maxHealth", aNumberBuilder("" + maxHealth));

        JsonNode json = builder.build();

        JsonFormatter formatter = new PrettyJsonFormatter();

        return formatter.format(json);
    }

    /**
     * Method: buildSkills()
     * This method produces the object builder that will contain all skill information.
     * @return - JsonObjectNodeBuilder that has all of the skills tied to the player
     */
    private JsonArrayNodeBuilder buildSkills()
    {
        JsonArrayNodeBuilder builder = anArrayBuilder();

        for(Map.Entry<PlayerSkill.GameSkill, PlayerSkill> skill : skillSet.entrySet())
        {
            builder.withElement(skill.getValue().buildJson());
        }

        return builder;
    }

    public PlayerSkill getSkill(GameSkill skill)
    {
	    return this.skillSet.get(skill);
    }

    public HashMap<GameSkill, PlayerSkill> getSkillSet() { return skillSet; }

    public int rollForSkill(GameSkill skill)
    {
        PlayerSkill playerSkill = this.getSkill(skill);

        return Die.d20.roll() + getTotalSkillBonus(skill);
    }

    public int getTotalSkillBonus(GameSkill skill)
    {
        int bonuses = 0;
        PlayerSkill playerSkill = this.getSkill(skill);

        if (playerSkill.isBasedOnStrength())
            bonuses += this.abilityScores.strength.getModifier();
        else if (playerSkill.isBasedOnDexterity())
            bonuses += this.abilityScores.dexterity.getModifier();
        else if (playerSkill.isBasedOnConstitution())
            bonuses += this.abilityScores.constitution.getModifier();
        else if (playerSkill.isBasedOnIntelligence())
            bonuses += this.abilityScores.intelligence.getModifier();
        else if (playerSkill.isBasedOnWisdom())
            bonuses += this.abilityScores.wisdom.getModifier();
        else if (playerSkill.isBasedOnCharisma())
            bonuses += this.abilityScores.charisma.getModifier();

        bonuses += playerSkill.getRanks();

        // return sie.d20.roll();
        return bonuses;
    }

    // General Stats getters (they are necessary for now).  May want a more clever way to do it later.

    public String getAlignment() {
        return alignment;
    }

    public String getDeity() {
        return deity;
    }

    public String getGender() {
        return gender;
    }

    public String getSize() {
        return size;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getHomeland() {
        return homeland;
    }

    public String getHairColor() {
        return hairColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public String getRace() {
        return race;
    }

    public String getAge() {
        return age;
    }

    public Integer getPlatinumCoins() {
        return platinumCoins;
    }

    public Integer getGoldCoins() {
        return goldCoins;
    }

    public Integer getSilverCoins() {
        return silverCoins;
    }

    public Integer getCopperCoins() {
        return copperCoins;
    }

    public String getOtherValuables() {
        return otherValuables;
    }

    public Integer getCurrHealth() {
        return currHealth;
    }

    public Integer getMaxHealth() {
        return maxHealth;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
