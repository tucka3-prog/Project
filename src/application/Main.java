package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

	public static void switchSceneAdmin(int customerID, String value, String filterChoice) {

		BorderPane adminScenePanel = adminPanel.editMovie(customerID, value, filterChoice);
		adminScene = new Scene(adminScenePanel, 660, 600);
		window.setScene(adminScene);
	}

	public static void switchSceneUser(int customerID, int categoryID, String value, String seachChoice) {

		BorderPane userScenePanel = userPanel.userScene(customerID, categoryID, value, seachChoice);
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
			VBox registerBox = registerVBox.register();

			registrationScene = new Scene(registerBox, 440, 420);
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
