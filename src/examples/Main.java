package examples;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;

import application.Product;
import application.Filters;

public class Main extends Application {

    Stage window;
    Button button;
    CategoryMenu categories = new CategoryMenu();
	Filters select = new Filters();
	ArrayList<Product> products = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) {
    	
        window = primaryStage;
        window.setTitle("Moviex - Online shop");

        BorderPane layout = new BorderPane();
        GridPane productOrderWindow = new GridPane();
        
        VBox leftside = new VBox();
        leftside = categories.addVBox();
        
        HBox top = new HBox();
        top = categories.addHBox();
        
        
        ListView<Product> listView = new ListView<>();
        listView.getItems().addAll(select.filterCategory(1));
        
        Product current = listView.getSelectionModel().getSelectedItem();
        

        
        layout.setLeft(leftside);
        layout.setTop(top);
        layout.setCenter(listView);

        Scene scene = new Scene(layout, 800, 700);
		Scene scene2 = new Scene(productOrderWindow, 800, 700);
        
		
		
        window.setScene(scene);
        window.show();
        

    }
    
    public static void main(String[] args) {
        launch(args);
    }

}