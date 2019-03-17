import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
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

    private MenuItem dieMenuItem(String label, Die die)
    {
	MenuItem temp = new MenuItem(label);
	temp.setOnAction(event -> roll(die));

	return temp;
    }

    public DieRollWindow()
    {
	this.result = new Label();
	this.result.setFont(new Font(32.0));
	this.result.setAlignment(Pos.CENTER);

	MenuBar dieMenu = new MenuBar();
	Menu rollMenu = new Menu("Roll");

	rollMenu.getItems().addAll(dieMenuItem("d4", Die.d4),
				   dieMenuItem("d6", Die.d6),
				   dieMenuItem("d8", Die.d8),
				   dieMenuItem("d10", Die.d10),
				   dieMenuItem("d12", Die.d12),
				   dieMenuItem("d20", Die.d20),
				   dieMenuItem("d%", Die.d100));
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
}
