package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.prism.paint.Paint;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Login {

	String usertext;
	String userpass;
	VBox loginBox;
	BorderPane loginView;
	Text failedLoginT = new Text();

	public BorderPane login() {

		try {

			loginView = new BorderPane();

			loginBox = new VBox();
			loginBox.setPadding(new Insets(15, 12, 15, 12));
			loginBox.setSpacing(10);
			loginBox.setStyle("-fx-background-color: #C5DFCB;");

			Button login = new Button("Login");
			login.setMinWidth(400);

			Button register = new Button("Register");

			TextField username = new TextField();
			username.setPromptText("username");
			username.setPrefWidth(400);
			username.setMaxWidth(400);
			username.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

			TextField password = new TextField();
			password.setPromptText("password");
			password.setPrefWidth(400);
			password.setMaxWidth(400);
			password.setStyle("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

			register.setOnAction(e -> {
				Main.switchSceneRegister();
			});

			login.setOnAction(e -> {

				ArrayList<String> customer = selectCustomer(passwordM(password), usernameM(username));

				if (customer.get(0).equals("Admin")) {

					int userID = Integer.parseInt(customer.get(3));
					Main.switchSceneAdmin(userID, "", "");

				} else if (customer.get(0).equals("User")) {

					int userID = Integer.parseInt(customer.get(3));
					Main.switchSceneUser(userID, 0, "", "");

				} else

					failedLoginT.setText("Username or password incorrect, please check details...");

			});

			HBox welcomeMsg = new HBox();

			Text welcomeTxt = new Text("Welcome to the Moviex shop! , Please login....");
			welcomeTxt.setFont(Font.font("Boulder", FontWeight.BOLD, 24));
			welcomeTxt.setFill(Color.web("500111"));

			failedLoginT.setFont(Font.font("Boulder", FontWeight.BOLD, 16));
			failedLoginT.setFill(Color.web("DE1818"));

			welcomeMsg.getChildren().add(welcomeTxt);

			loginBox.getChildren().addAll(username, password, failedLoginT, login, register);
			loginBox.setMargin(username, new Insets(0, 0, 0, 85));
			loginBox.setMargin(password, new Insets(0, 0, 0, 85));
			loginBox.setMargin(login, new Insets(0, 0, 0, 85));
			loginBox.setMargin(register, new Insets(0, 0, 0, 250));
			loginBox.setMargin(failedLoginT, new Insets(0, 0, 0, 85));

			loginView.setCenter(loginBox);
			loginView.setTop(welcomeMsg);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginView;

	}

	public String usernameM(TextField text) {
		String username;
		username = text.getText();
		System.out.println(username);
		return username;
	}

	public String passwordM(TextField text) {
		String password;
		password = text.getText();
		System.out.println(password);
		return password;
	}

	private Connection connect() {
		String url = "jdbc:sqlite:D:\\programavimas\\SQLite\\parduotuve.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public ArrayList<String> selectCustomer(String password, String username) {

		String sql = "SELECT CustomerID, Username, Password, Access from Customer WHERE Password = ? and Username = ?";
		ArrayList<String> userDetails = new ArrayList<>();

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, password);
			pstmt.setString(2, username);
			ResultSet rs = pstmt.executeQuery();
			int count = 0;
			String access = null;
			String customerID = null;

			while (rs.next()) {
				access = rs.getString("Access");
				customerID = rs.getString("CustomerID");
				count = count + 1;
			}		
			if (count == 1 && access.equals("Admin")) {
				userDetails.add("Admin");
				userDetails.add(username);
				userDetails.add(password);
				userDetails.add(customerID);

				
			} else if (count == 1 && access.equals("User")) {
				userDetails.add("User");
				userDetails.add(username);
				userDetails.add(password);
				userDetails.add(customerID);

			} else  {
				userDetails.add("Fail");
			}
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return userDetails;
	}


}
