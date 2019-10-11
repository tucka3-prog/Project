package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Register {

	Login login = new Login();
	public final int passwordLength = 8;
	public final int inputLength = 1;

	public VBox register() {

		HBox registerField = new HBox();
		VBox registerFieldL = new VBox();
		VBox registerFielR = new VBox();
		HBox buttons = new HBox();
		VBox finalField = new VBox();

		registerField.setPadding(new Insets(15, 12, 15, 12));
		registerField.setSpacing(10);
		registerField.setStyle("-fx-background-color: #C5DFCB;");

		Button goBack = new Button("Go Back");
		goBack.setOnAction(e -> {
			Main.switchSceneLogin();
		});

		Button register = new Button("Register");
		buttons.getChildren().addAll(goBack, register);

		TextField firstNameT = new TextField();
		firstNameT.setMinWidth(200);
		firstNameT.setMaxWidth(200);
		TextField lastNameT = new TextField();
		lastNameT.setMinWidth(200);
		lastNameT.setMaxWidth(200);
		TextField address1T = new TextField();
		address1T.setMinWidth(200);
		address1T.setMaxWidth(200);
		TextField address2T = new TextField();
		address2T.setMinWidth(200);
		address2T.setMaxWidth(200);
		TextField cityT = new TextField();
		cityT.setMinWidth(200);
		cityT.setMaxWidth(200);
		TextField stateT = new TextField();
		stateT.setMinWidth(200);
		stateT.setMaxWidth(200);
		TextField postalCodeT = new TextField();
		postalCodeT.setMinWidth(200);
		postalCodeT.setMaxWidth(200);
		TextField countryT = new TextField();
		countryT.setMinWidth(200);
		countryT.setMaxWidth(200);
		TextField phoneT = new TextField();
		phoneT.setMinWidth(200);
		phoneT.setMaxWidth(200);
		TextField emailT = new TextField();
		emailT.setMinWidth(200);
		emailT.setMaxWidth(200);
		TextField passwordT = new TextField();
		passwordT.setMinWidth(200);
		passwordT.setMaxWidth(200);
		TextField usernameT = new TextField();
		usernameT.setMinWidth(200);
		usernameT.setMaxWidth(200);
		Text messageT = new Text();
		Text inputMessage = new Text("Field marked with * are mandatory");
		inputMessage.setFont(Font.font("Boulder", FontWeight.BOLD, 16));

		Label firstNameL = new Label("First Name *");
		Label lastNameL = new Label("Last Name *");
		Label address1L = new Label("Address line 1");
		Label address2L = new Label("Address line 2");
		Label cityL = new Label("City");
		Label stateL = new Label("State");
		Label postalCodeL = new Label("Postal Code");
		Label countryL = new Label("Country");
		Label phoneL = new Label("Phone Number *");
		Label emailL = new Label("Email address");
		Label passwordL = new Label("Password *");
		Label usernameL = new Label("Username *");

		register.setOnAction(e -> {
			try {
				String firstName = firstNameT.getText();
				String lastName = lastNameT.getText();
				String address1 = address1T.getText();
				String address2 = address2T.getText();
				String city = cityT.getText();
				String state = stateT.getText();
				String postalCode = postalCodeT.getText();
				String country = countryT.getText();
				String phone = phoneT.getText();
				String email = emailT.getText();
				String password = passwordT.getText();
				String username = usernameT.getText();
				String usernameCheck = checkUsername(username);

				if (!isValidPassword(password)) {
					messageT.setText("Password must be 8 characters");
				}

				if (!isValidInput(firstName) || !isValidInput(lastName) || !isValidInput(phone)
						|| !isValidInput(username)) {
					messageT.setText("Check the mandatory fields!");
				}
				
				if (usernameCheck == username) {
					messageT.setText("Username already exists");
				}

				if (isValidPassword(password) && isValidInput(firstName) && isValidInput(lastName)
						&& isValidInput(phone) && isValidInput(username) && usernameCheck != username) {

					registration(firstName, lastName, address1, address2, city, state, postalCode, country, phone,
							email, password, username);

					messageT.setText("Registration successfull");
				}
			} catch (Exception exc) {
				System.out.println("Incorrect input format");
			}
		});

		registerFieldL.getChildren().addAll(firstNameL, firstNameT, lastNameL, lastNameT, address1L, address1T,
				address2L, address2T, cityL, cityT, stateL, stateT, postalCodeL, postalCodeT, countryL, countryT,
				phoneL, phoneT, emailL, emailT, passwordL, passwordT, usernameL, usernameT);

		registerFielR.getChildren().addAll(postalCodeL, postalCodeT, countryL, countryT, phoneL, phoneT, emailL, emailT,
				passwordL, passwordT, usernameL, usernameT, buttons, messageT);

		registerField.getChildren().addAll(registerFieldL, registerFielR);
		
		finalField.getChildren().addAll(inputMessage, registerField);

		return finalField;

	}

	private Connection connect() {
		// SQLite connection string
		String url = "jdbc:sqlite:D:\\programavimas\\SQLite\\parduotuve.db";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public void registration(String firstName, String lastName, String address1, String address2, String city,
			String state, String postalcode, String country, String phone, String email, String password,
			String username) {
		String sql = "INSERT INTO Customer(FirstName,LastName,Address1,Address2,City,State,PostalCode,Country,Phone,Email,Password,Username) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, address1);
			pstmt.setString(4, address2);
			pstmt.setString(5, city);
			pstmt.setString(6, state);
			pstmt.setString(7, postalcode);
			pstmt.setString(8, country);
			pstmt.setString(9, phone);
			pstmt.setString(10, email);
			pstmt.setString(11, password);
			pstmt.setString(12, username);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public String checkUsername(String username) {

		String sql = "SELECT Username FROM Customer WHERE Username = ?";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, username);

			ResultSet rs = pstmt.executeQuery();
			int count = 0;

			while (rs.next()) {
				count = count + 1;
			}
			if (count == 1) {
				return username;
			} else if (count != 1) {
				username = "available";
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return username;
	}

	public boolean isValidPassword(String password) {

		Boolean validation = null;

		if (password.length() < passwordLength)
			validation = false;
		else if (password.length() >= passwordLength)
			validation = true;

		return validation;

	}

	public boolean isValidInput(String input) {

		Boolean validation = null;

		if (input.length() < inputLength)
			validation = false;
		else if (input.length() >= inputLength)
			validation = true;

		return validation;

	}
}
