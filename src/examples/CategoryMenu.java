package examples;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

import application.Product;
import application.Filters;

public class CategoryMenu {
	
	Filters select = new Filters();
	ArrayList<Product> products = new ArrayList<>();
	
	public HBox addHBox() {
	    HBox hbox = new HBox();
	    hbox.setPadding(new Insets(15, 12, 15, 12));
	    hbox.setSpacing(10);
	    hbox.setStyle("-fx-background-color: #336699;");

	    Button buttonCurrent = new Button("Current");
	    buttonCurrent.setPrefSize(100, 20);

	    Button buttonProjected = new Button("Projected");
	    buttonProjected.setPrefSize(100, 20);
	    hbox.getChildren().addAll(buttonCurrent, buttonProjected);

	    return hbox;
	}
	
	public VBox addVBox() {
	    VBox vbox = new VBox();
	    vbox.setPadding(new Insets(10));
	    vbox.setSpacing(8);

	    Text title = new Text("Categories");
	    title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	    vbox.getChildren().add(title);

	    Button action = new Button("Action");
	    action.setPrefSize(100, 20);
	    action.setOnAction(e -> {
	    	products = select.filterCategory(1);
	    	System.out.println(products.size());
	    });
	    
	    Button romance = new Button("Romance");
	    romance.setPrefSize(100, 20);
	    romance.setOnAction(e -> {
	    	products = select.filterCategory(2);
	    	System.out.println(products.size());
	    });
	    
	    Button mystery = new Button("Mystery");
	    mystery.setPrefSize(100, 20);
	    Button fantasy = new Button("Fantasy");
	    fantasy.setPrefSize(100, 20);
	    Button drama = new Button("Drama");
	    drama.setPrefSize(100, 20);
	    Button crime = new Button("Crime");
	    crime.setPrefSize(100, 20);
	    Button comedy = new Button("Comedy");
	    comedy.setPrefSize(100, 20);
	    Button adventure = new Button("Adventure");
	    adventure.setPrefSize(100, 20);
	    Button thriller = new Button("Thriller");
	    thriller.setPrefSize(100, 20);
	    Button science = new Button("Science fiction");
	    science.setPrefSize(100, 20);
	    Button western = new Button("Western");
	    western.setPrefSize(100, 20);
	    
	    vbox.getChildren().addAll(action, romance, mystery, fantasy, drama, crime, comedy,
	    		adventure, thriller, science, western);

	    return vbox;
	}

	
}
