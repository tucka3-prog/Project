package examples;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ConfirmBox {

    //Create variable
    static int answer;

    public static int display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Test scene2");
        window.setMinWidth(600);
        window.setMinHeight(600);
        Label label = new Label();
        label.setText("Test Message");

        //Create two buttons
        Button action = new Button("Action");
        Button romance = new Button("Romance");
        Button mystery = new Button("Mystery");
        Button horror = new Button("Horror");
        Button fantasy = new Button("Fantasy");
        Button drama = new Button("Drama");
        Button crime = new Button("Crime");
        Button comedy = new Button("Comedy");
        Button adventure = new Button("Adventure");
        Button thriller = new Button("Thriller");
        Button scienceFiction = new Button("Science fiction");
        Button western = new Button("Western");

        //Clicking will set answer and close window
        action.setOnAction(e -> {
            answer = 1;
            window.close();
        });
        romance.setOnAction(e -> {
            answer = 2;
            window.close();
        });
        mystery.setOnAction(e -> {
            answer = 3;
            window.close();
        });
        horror.setOnAction(e -> {
            answer = 4;
            window.close();
        });
        fantasy.setOnAction(e -> {
            answer = 5;
            window.close();
        });
        drama.setOnAction(e -> {
            answer = 6;
            window.close();
        });
        crime.setOnAction(e -> {
            answer = 7;
            window.close();
        });
        comedy.setOnAction(e -> {
            answer = 8;
            window.close();
        });
        adventure.setOnAction(e -> {
            answer = 9;
            window.close();
        });
        thriller.setOnAction(e -> {
            answer = 10;
            window.close();
        });
        scienceFiction.setOnAction(e -> {
            answer = 11;
            window.close();
        });
        western.setOnAction(e -> {
            answer = 12;
            window.close();
        });

        VBox layout = new VBox(10);

        //Add buttons
        layout.getChildren().addAll(label, action, romance, mystery, horror, fantasy, drama, crime, comedy, adventure,
        		thriller, scienceFiction, western);
        layout.setAlignment(Pos.BASELINE_LEFT);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        //Make sure to return answer
        return answer;
    }

}