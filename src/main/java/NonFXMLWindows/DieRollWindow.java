package NonFXMLWindows;

import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import network.cardboard.crystallogic.Die;

public class DieRollWindow
{
    private Label result;

    private void roll(Die die)
    {
		String roll = String.valueOf(die.roll());
		this.result.setText(roll);
		System.out.println("Rolled " + roll);
    }

    private MenuItem dieMenuItem(String label, Die die, String combo)
    {
		MenuItem temp = new MenuItem(label);
		temp.setOnAction(event -> roll(die));
		temp.setAccelerator(KeyCombination.keyCombination(combo));

		return temp;
    }

    public DieRollWindow()
    {
		this.result = new Label();
		this.result.setFont(new Font(32.0));
		this.result.setAlignment(Pos.CENTER);

		MenuBar dieMenu = new MenuBar();
		Menu rollMenu = new Menu("Roll");

		rollMenu.getItems().addAll(dieMenuItem("d4", Die.d4, "Ctrl+1"),
					   dieMenuItem("d6", Die.d6, "Ctrl+2"),
					   dieMenuItem("d8", Die.d8, "Ctrl+3"),
					   dieMenuItem("d10", Die.d10, "Ctrl+4"),
					   dieMenuItem("d12", Die.d12, "Ctrl+5"),
					   dieMenuItem("d20", Die.d20, "Ctrl+6"),
					   dieMenuItem("d%", Die.d100, "Ctrl+7"));
		dieMenu.getMenus().addAll(rollMenu);

		VBox root = new VBox();
		root.setAlignment(Pos.TOP_CENTER);
		root.getChildren().addAll(dieMenu, this.result);

		Scene scene = new Scene(root, 250, 200);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Roll Dice");
		stage.show();
    }

	/**
	 * Constructor: DieRollWindow(dieType, quantity, individualModifier, totalModifier)
	 * This constructor is used to make a window that has several different possible modifiers for the rolls
	 * It will always roll the specified type, and specified quantity, but it will roll as often as the user wishes to click.
	 * @param dieType - The type of die that will be rolled (e.g. d20 or d6)
	 * @param quantity - The number of dice that will be rolled (e.g. 10 for 10d6 (10 d6 die being rolled))
	 * @param individualModifier - The modifier that is added to every die rolled (essentially multiplied by the quantity)
	 * @param totalModifier - The modifier added to the end, this will be used more than individual modifier, as most rolls only have one ending addition.
	 */
    public DieRollWindow(Die dieType, int quantity, int individualModifier, int totalModifier)
	{
		this.result = new Label();
		this.result.setFont(new Font(32.0));
		this.result.setAlignment(Pos.CENTER);

		Button reRoll = new Button("Re-roll");

		reRoll.setOnAction(event ->
		{
			int rollResult = dieType.roll();
			this.result.setText("( (" + rollResult + " + " + individualModifier + ") * " + quantity + ") + " + totalModifier + ": " +  (((rollResult + individualModifier) * quantity )+ totalModifier));
		});

		VBox root = new VBox();
		root.setAlignment(Pos.TOP_CENTER);
		root.getChildren().addAll(this.result, reRoll);

		// display first roll

		int rollResult = dieType.roll();

		this.result.setText("( (" + rollResult + " + " + individualModifier + ") * " + quantity + ") + " + totalModifier + ": " +  (((rollResult + individualModifier) * quantity )+ totalModifier));

		Scene scene = new Scene(root, 250, 200);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Roll Dice");
		stage.show();
	}
}
