package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	AdminPanelMethods apMethods = new AdminPanelMethods();
	UserPanelMethods upMethods = new UserPanelMethods();

	static AdminPanel adminPanel = new AdminPanel();
	static UserPanel userPanel = new UserPanel();

	Login login = new Login();
	Register registerVBox = new Register();

	static Stage window;
	static Scene loginScene;
	static Scene registrationScene;
	static Scene adminScene;
	static Scene userScene;

	public static void switchSceneAdmin(int customerID) {

		BorderPane adminScenePanel = adminPanel.editMovie(customerID);
		adminScene = new Scene(adminScenePanel, 650, 600);
		window.setScene(adminScene);
	}

	public static void switchSceneUser(int customerID) {

		BorderPane userScenePanel = userPanel.userScene(customerID);
		userScene = new Scene(userScenePanel, 650, 600);
		window.setScene(userScene);

	}

	public static void switchSceneRegister() {
		window.setScene(registrationScene);
	}

	public static void switchSceneLogin() {
		window.setScene(loginScene);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Moviex - Movie shop :)");

		try {

			window = primaryStage;

			BorderPane loginPanel = login.login();
			HBox registerBox = registerVBox.register();

			BorderPane userScenePanel = userPanel.userScene(1);
			userScene = new Scene(userScenePanel, 650, 600);

			BorderPane adminScenePanel = adminPanel.editMovie(1);
			adminScene = new Scene(adminScenePanel, 650, 600);

			registrationScene = new Scene(registerBox, 350, 400);
			loginScene = new Scene(loginPanel, 600, 250);

			window.setScene(loginScene);
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
