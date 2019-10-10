package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminPanel {

	AdminPanelMethods apMethods = new AdminPanelMethods();
	Filters filter = new Filters();
	int customerID;

	public void addMovie() {

		BorderPane adminPanel = new BorderPane();

		Menu fileMenu = new Menu("File");

		MenuItem logoutM = new MenuItem("Logout");
		logoutM.setOnAction(e -> {

		});

		MenuItem exitM = new MenuItem("Exit");
		exitM.setOnAction(e -> {
			System.exit(1);
		});

		fileMenu.getItems().addAll(logoutM, exitM);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);

		TextField productNameT = new TextField();
		TextField yearT = new TextField();
		TextField productDescriptionT = new TextField();
		TextField rankingT = new TextField();
		TextField unitPriceT = new TextField();

		ChoiceBox<String> discTypeT = new ChoiceBox<>();
		discTypeT.getItems().addAll("DVD", "Blu-ray", "CD");
		discTypeT.setValue("DVD");

		ChoiceBox<String> productAvailableT = new ChoiceBox<>();
		productAvailableT.getItems().addAll("Yes", "No");
		productAvailableT.setValue("Yes");

		TextField unitsInStockT = new TextField();
		TextField skuT = new TextField();
		TextField noteT = new TextField();

		ChoiceBox<Integer> supplierIDT = new ChoiceBox<>();
		supplierIDT.getItems().addAll(1, 2, 3, 4, 5, 6, 7);
		supplierIDT.setValue(1);

		Text messageT = new Text();

		Label productNameL = new Label("Product Name");
		Label yearL = new Label("Release year");
		Label productDescriptionL = new Label("Product Description");
		Label rankingL = new Label("IMDB Ranking");
		Label unitPriceL = new Label("Unit Price");
		Label discTypeL = new Label("Disc type");
		Label productAvailableL = new Label("Is product available");
		Label unitsInStockL = new Label("Units in stock");
		Label skuL = new Label("SKU");
		Label noteL = new Label("Additional notes");
		Label supplierIDL = new Label("Choose a supplier");
		Label addProduct = new Label("Add movie");

		Button submit = new Button("Submit");
		submit.setOnAction(e -> {
			try {
				String productName = productNameT.getText();

				apMethods.isInt(yearT);
				messageT.setText("Year should be a number");
				Integer year = Integer.parseInt(yearT.getText());

				String productDescription = productDescriptionT.getText();

				apMethods.isDouble(rankingT);
				messageT.setText("Ranking format should be xx.xx");
				Double ranking = Double.parseDouble(rankingT.getText());

				apMethods.isDouble(unitPriceT);
				messageT.setText("Price format should be xx.xx");
				Double unitPrice = Double.parseDouble(unitPriceT.getText());

				String discType = apMethods.getChoiceString(discTypeT);
				String productAvailable = apMethods.getChoiceString(productAvailableT);

				apMethods.isInt(unitsInStockT);
				messageT.setText("Units in Stock should be a number");
				Integer unitsInStock = Integer.parseInt(unitsInStockT.getText());

				String sku = skuT.getText();
				String note = noteT.getText();

				Integer supplierID = apMethods.getChoiceInt(supplierIDT);

				apMethods.addMovie(productName, year, productDescription, ranking, unitPrice, discType,
						productAvailable, unitsInStock, sku, note, supplierID);

				messageT.setText("Added successfully");

			} catch (Exception exc) {
				System.out.println("Input Format error");
			}
		});

		HBox center = new HBox();
		VBox centerLeft = new VBox();
		VBox centerRight = new VBox();

		HBox buttons = new HBox();
		buttons.getChildren().addAll(submit);

		centerLeft.getChildren().addAll(productNameL, productNameT, yearL, yearT, productDescriptionL,
				productDescriptionT, rankingL, rankingT, unitPriceL, unitPriceT, discTypeL, discTypeT);

		centerRight.getChildren().addAll(productAvailableL, productAvailableT, unitsInStockL, unitsInStockT, skuL, skuT,
				noteL, noteT, supplierIDL, supplierIDT, addProduct, buttons, messageT);

		center.getChildren().addAll(centerLeft, centerRight);

		adminPanel.setCenter(center);
		adminPanel.setTop(menuBar);

		Scene adminPanelScene = new Scene(adminPanel, 450, 450);

		Stage secondStage = new Stage();
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setScene(adminPanelScene);
		secondStage.showAndWait();

	}

	public BorderPane editMovie(int customerID, String value, String filterChoice) {

		BorderPane adminPanel = new BorderPane();
		ObservableList<Product> productList = FXCollections.observableArrayList();
		
		if (value.equals("") || value.length() == 0) {
			productList = apMethods.getAllProductsAdmin();
			
		} else if (value.length() >= 0) {
			String searchOption = filter.filterChoice(filterChoice);
			productList = filter.searchByString(searchOption, "%" + value + "%");
		}

		TableView<Product> table = apMethods.productTable(productList);

		Menu fileMenu = new Menu("File");

		MenuItem logoutM = new MenuItem("Logout");
		logoutM.setOnAction(e -> {

		});

		MenuItem exitM = new MenuItem("Exit");
		exitM.setOnAction(e -> {
			System.exit(1);
		});

		fileMenu.getItems().addAll(logoutM, exitM);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);

		Button option1 = new Button("Add or edit a Movie");
		option1.setOnAction(e -> editMovie(customerID, "", ""));
		option1.setMinWidth(200);
		option1.setMaxWidth(200);

		Button option2 = new Button("Add or edit Suppliers");
		option2.setOnAction(e -> editSupplier());
		option2.setMinWidth(200);
		option2.setMaxWidth(200);

		Button option3 = new Button("Edit Customer information");
		option3.setOnAction(e -> customerInformation());
		option3.setMinWidth(200);
		option3.setMaxWidth(200);

		Button option4 = new Button("Information by Order ID");
		option4.setOnAction(e -> orderInformationID());
		option4.setMinWidth(200);
		option4.setMaxWidth(200);

		VBox leftSide = new VBox();
		leftSide.getChildren().addAll(option1, option2, option3, option4);

		ComboBox<String> searchOption = new ComboBox<>();
		searchOption.getItems().addAll("Search by Name", "Search by Year", "Search by Product ID");
		searchOption.setValue("Search by Name");

		HBox searchBox1 = new HBox();
		TextField searchText1 = new TextField();
		

		Button button1 = new Button("Search");
		button1.setOnAction(e -> {

			String searchText = searchText1.getText();
			searchChoice(searchText, searchOption.getValue());

		});

		Button button4 = new Button("Add a new Movie");
		button4.setOnAction(e -> {
			addMovie();
		});

		searchBox1.getChildren().addAll(searchOption, searchText1, button1);

		VBox center = new VBox();

		center.getChildren().addAll(searchBox1, table, button4);

		adminPanel.setLeft(leftSide);
		adminPanel.setCenter(center);
		adminPanel.setTop(menuBar);

		return adminPanel;

	}


	public static void editMovieWindow(int productID) {

		try {

			Filters filter = new Filters();
			Product product = filter.selectProduct(productID);

			BorderPane editWindowPanel = new BorderPane();
			editWindowPanel.setPadding(new Insets(25, 50, 50, 25));

			Scene editWindow;

			HBox center = new HBox();
			center.setSpacing(50);

			VBox leftSide = new VBox();
			leftSide.setSpacing(10);

			VBox rightSide = new VBox();
			rightSide.setSpacing(10);

			HBox buttons = new HBox();

			Label label1 = new Label("Product ID");
			Label label2 = new Label("Product Name");
			Label label3 = new Label("Release Year");
			Label label4 = new Label("Product Description");
			Label label5 = new Label("Ranking");
			Label label6 = new Label("Unit Price");
			Label label7 = new Label("Disc type");
			Label label8 = new Label("Is product available?");
			Label label9 = new Label("Units in stock");
			Label label10 = new Label("SKU");
			Label label11 = new Label("Note");
			Label label12 = new Label("SupplierID");

			TextField productIDT = new TextField(String.valueOf(product.getProductID()));
			productIDT.setEditable(false);
			TextField productNameT = new TextField(product.getProductName());
			TextField yearT = new TextField(String.valueOf(product.getYear()));
			TextArea productDescriptionT = new TextArea(product.getProductDescription());
			productDescriptionT.setWrapText(true);
			productDescriptionT.setMaxWidth(200);
			TextField rankingT = new TextField(String.valueOf(product.getRanking()));
			TextField unitPriceT = new TextField(String.valueOf(product.getUnitPrice()));

			ChoiceBox<String> discTypeT = new ChoiceBox<>();
			discTypeT.getItems().addAll("DVD", "Blu-ray", "CD");
			discTypeT.setValue(product.getDiscType());

			ChoiceBox<String> productAvailableT = new ChoiceBox<>();
			productAvailableT.getItems().addAll("Yes", "No");
			productAvailableT.setValue(product.getProductAvailable());

			TextField unitsInStockT = new TextField(String.valueOf(product.getUnitsInStock()));
			TextField skuT = new TextField(product.getSku());
			TextField noteT = new TextField(product.getNote());

			ChoiceBox<Integer> supplierIDT = new ChoiceBox<>();
			supplierIDT.getItems().addAll(1, 2, 3, 4, 5, 6, 7);
			supplierIDT.setValue(product.getSuppliedID());

			Text messageT = new Text("");

			Button save = new Button("Save");

			save.setOnAction(e -> {
				try {

					AdminPanelMethods apMethods = new AdminPanelMethods();

					String productName = productNameT.getText();

					apMethods.isInt(yearT);
					messageT.setText("Year format should be: XXXX ");
					int year = Integer.parseInt(yearT.getText());

					String productDescription = productDescriptionT.getText();

					apMethods.isDouble(rankingT);
					messageT.setText("Ranking format should be: X.XX");
					Double ranking = Double.parseDouble(rankingT.getText());

					apMethods.isDouble(unitPriceT);
					messageT.setText("Price format should be: XX.XX");
					Double unitPrice = Double.parseDouble(unitPriceT.getText());

					String discType = apMethods.getChoiceString(discTypeT);
					String productAvailable = apMethods.getChoiceString(productAvailableT);

					apMethods.isInt(unitsInStockT);
					messageT.setText("Units in stock must be a number");
					int unitsInStock = Integer.parseInt(unitsInStockT.getText());

					String sku = skuT.getText();
					String note = noteT.getText();
					int supplierID = apMethods.getChoiceInt(supplierIDT);

					apMethods.editMovie(productName, year, productDescription, ranking, unitPrice, discType,
							productAvailable, unitsInStock, sku, note, supplierID, productID);

					messageT.setText("Saved successfully");

				} catch (Exception exc) {
					System.out.println("Input Format error");
				}
			});

			buttons.getChildren().addAll(save);

			leftSide.getChildren().addAll(label1, productIDT, label2, productNameT, label3, yearT, label4,
					productDescriptionT, label5, rankingT, label6, unitPriceT);

			rightSide.getChildren().addAll(label7, discTypeT, label8, productAvailableT, label9, unitsInStockT, label10,
					skuT, label11, noteT, label12, supplierIDT, buttons, messageT);

			center.getChildren().addAll(leftSide, rightSide);

			editWindowPanel.setCenter(center);

			editWindow = new Scene(editWindowPanel, 500, 600);

			Stage secondStage = new Stage();
			secondStage.initModality(Modality.APPLICATION_MODAL);
			secondStage.setScene(editWindow);
			secondStage.showAndWait();

		} catch (Exception e) {
			System.out.println("Unknown error occured");
		}
	}

	public void addNewSupplier() {

		BorderPane adminPanel = new BorderPane();

		TextField companyNameT = new TextField();
		TextField contactFNameT = new TextField();
		TextField contactLNameT = new TextField();

		ChoiceBox<String> contactTitleT = new ChoiceBox<>();
		contactTitleT.getItems().addAll("Mr", "Mrs", "Miss", "Ms");
		contactTitleT.setValue("Mr");

		TextField address1T = new TextField();
		TextField address2T = new TextField();
		TextField cityT = new TextField();
		TextField stateT = new TextField();
		TextField postalCodeT = new TextField();
		TextField countryT = new TextField();
		TextField phoneT = new TextField();
		TextField emailT = new TextField();
		TextField urlT = new TextField();

		Label companyNameL = new Label("Company Name");
		Label contactFNameL = new Label("Contact first name");
		Label contactLNameL = new Label("Contact last name");
		Label contactTitleL = new Label("Contact Title");
		Label address1L = new Label("Address line 1");
		Label address2L = new Label("Address line 2");
		Label cityL = new Label("City");
		Label stateL = new Label("State");
		Label postalCodeL = new Label("Postal Code");
		Label countryL = new Label("Country");
		Label phoneL = new Label("Phone");
		Label emailL = new Label("E-mail adress");
		Label urlL = new Label("URL address");
		Text messageT = new Text();

		Button submit = new Button("Submit");
		submit.setOnAction(e -> {

			String companyName = companyNameT.getText();
			String contactFName = contactFNameT.getText();
			String contactLName = contactLNameT.getText();
			String contactTitle = apMethods.getChoiceString(contactTitleT);
			String address1 = address1T.getText();
			String address2 = address2T.getText();
			String city = cityT.getText();
			String state = stateT.getText();
			String postalCode = postalCodeT.getText();
			String country = countryT.getText();
			String phone = phoneT.getText();
			String email = emailT.getText();
			String url = urlT.getText();

			apMethods.addSupplier(companyName, contactFName, contactLName, contactTitle, address1, address2, city,
					state, postalCode, country, phone, email, url);

			messageT.setText("Added successfully");
		});

		HBox center = new HBox();
		VBox centerLeft = new VBox();
		VBox centerRight = new VBox();

		HBox buttons = new HBox();
		buttons.getChildren().addAll(submit);

		centerLeft.getChildren().addAll(companyNameL, companyNameT, contactFNameL, contactFNameT, contactLNameL,
				contactLNameT, contactTitleL, contactTitleT, address1L, address1T, address2L, address2T, cityL, cityT);

		centerRight.getChildren().addAll(stateL, stateT, postalCodeL, postalCodeT, countryL, countryT, phoneL, phoneT,
				emailL, emailT, urlL, urlT, buttons, messageT);

		center.getChildren().addAll(centerLeft, centerRight);

		adminPanel.setCenter(center);

		Scene adminPanelScene = new Scene(adminPanel, 450, 450);

		Stage secondStage = new Stage();
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setScene(adminPanelScene);
		secondStage.showAndWait();

	}

	public void editSupplier() {

		BorderPane adminPanel = new BorderPane();

		Menu fileMenu = new Menu("File");

		MenuItem logoutM = new MenuItem("Logout");
		logoutM.setOnAction(e -> {

		});

		MenuItem exitM = new MenuItem("Exit");
		exitM.setOnAction(e -> {
			System.exit(1);
		});

		fileMenu.getItems().addAll(logoutM, exitM);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);

		ObservableList<Supplier> filteredList = apMethods.getAllSuppliers();
		TableView<Supplier> table = apMethods.supplierTable(filteredList);

		Button option1 = new Button("Add or edit a Movie");
		option1.setOnAction(e -> Main.switchSceneAdmin(this.customerID, "", ""));
		option1.setMinWidth(200);
		option1.setMaxWidth(200);

		Button option2 = new Button("Add or edit Suppliers");
		option2.setOnAction(e -> editSupplier());
		option2.setMinWidth(200);
		option2.setMaxWidth(200);

		Button option3 = new Button("Edit Customer information");
		option3.setOnAction(e -> customerInformation());
		option3.setMinWidth(200);
		option3.setMaxWidth(200);

		Button option4 = new Button("Information by Order ID");
		option4.setOnAction(e -> orderInformationID());
		option4.setMinWidth(200);
		option4.setMaxWidth(200);

		Button addSupplier = new Button("Add a new Supplier");
		addSupplier.setOnAction(e -> {
			addNewSupplier();
		});

		VBox leftSide = new VBox();
		leftSide.getChildren().addAll(option1, option2, option3, option4);

		VBox center = new VBox();

		center.getChildren().addAll(table, addSupplier);

		adminPanel.setLeft(leftSide);
		adminPanel.setCenter(center);
		adminPanel.setTop(menuBar);

		Scene adminPanelScene = new Scene(adminPanel, 660, 600);

		Main.window.setScene(adminPanelScene);

	}

	public static void editSupplierWindow(int supplierID) {

		try {

			Filters filter = new Filters();
			Supplier supplier = filter.selectSupplier(supplierID);

			BorderPane editWindowPanel = new BorderPane();
			editWindowPanel.setPadding(new Insets(25, 50, 50, 25));

			Scene editWindow;

			HBox center = new HBox();
			center.setSpacing(50);

			VBox leftSide = new VBox();
			leftSide.setSpacing(10);

			VBox rightSide = new VBox();
			rightSide.setSpacing(10);

			HBox buttons = new HBox();

			Label label1 = new Label("SupplierID");
			Label label2 = new Label("Company Name");
			Label label3 = new Label("Contact first name");
			Label label4 = new Label("Contact last name");
			Label label5 = new Label("Contact Title");
			Label label6 = new Label("Address line 1");
			Label label7 = new Label("Address line 2");
			Label label8 = new Label("City");
			Label label9 = new Label("State");
			Label label10 = new Label("Postal Code");
			Label label11 = new Label("Country");
			Label label12 = new Label("Phone");
			Label label13 = new Label("E-mail Address");
			Label label14 = new Label("URL Address");

			TextField supplierIDT = new TextField(String.valueOf(supplier.getSupplierID()));
			supplierIDT.setEditable(false);
			TextField companyNameT = new TextField(supplier.getCompanyName());
			TextField contactFNameT = new TextField(supplier.getContactFName());
			TextField contactLNameT = new TextField(supplier.getContactLName());

			ChoiceBox<String> contactTitleT = new ChoiceBox<>();
			contactTitleT.getItems().addAll("Mr", "Mrs", "Miss", "Ms");
			contactTitleT.setValue(supplier.getContactTitle());

			TextField address1T = new TextField(supplier.getAddress1());
			TextField address2T = new TextField(supplier.getAddress2());
			TextField cityT = new TextField(supplier.getCity());
			TextField stateT = new TextField(supplier.getState());
			TextField postalCodeT = new TextField(supplier.getPostalCode());
			TextField countryT = new TextField(supplier.getCountry());
			TextField phoneT = new TextField(supplier.getPhone());
			TextField emailT = new TextField(supplier.getEmail());
			TextField urlT = new TextField(supplier.getUrl());

			Text messageT = new Text();

			Button save = new Button("Save");

			save.setOnAction(e -> {

				AdminPanelMethods apMethods = new AdminPanelMethods();

				String companyName = companyNameT.getText();
				String contactFName = contactFNameT.getText();
				String contactLName = contactLNameT.getText();
				String contactTitle = apMethods.getChoiceString(contactTitleT);
				String address1 = address1T.getText();
				String address2 = address2T.getText();
				String city = cityT.getText();
				String state = stateT.getText();
				String postalCode = postalCodeT.getText();
				String country = countryT.getText();
				String phone = phoneT.getText();
				String email = emailT.getText();
				String url = urlT.getText();

				apMethods.editSupplier(companyName, contactFName, contactLName, contactTitle, address1, address2, city,
						state, postalCode, country, phone, email, url, supplierID);

				messageT.setText("Saved successfully");

			});

			buttons.getChildren().addAll(save);

			leftSide.getChildren().addAll(label1, supplierIDT, label2, companyNameT, label3, contactFNameT, label4,
					contactLNameT, label5, contactTitleT, label6, address1T, label7, address2T);

			rightSide.getChildren().addAll(label8, cityT, label9, stateT, label10, postalCodeT, label11, countryT,
					label12, phoneT, label13, emailT, label14, urlT, buttons, messageT);

			center.getChildren().addAll(leftSide, rightSide);

			editWindowPanel.setCenter(center);

			editWindow = new Scene(editWindowPanel, 500, 600);

			Stage secondStage = new Stage();
			secondStage.initModality(Modality.APPLICATION_MODAL);
			secondStage.setScene(editWindow);
			secondStage.showAndWait();

		} catch (Exception e) {
			System.out.println("Input format error");
		}
	}

	public void customerInformation() {

		Menu fileMenu = new Menu("File");

		MenuItem logoutM = new MenuItem("Logout");
		logoutM.setOnAction(e -> {

		});

		MenuItem exitM = new MenuItem("Exit");
		exitM.setOnAction(e -> {
			System.exit(1);
		});

		fileMenu.getItems().addAll(logoutM, exitM);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);

		BorderPane adminPanel = new BorderPane();

		ObservableList<Customer> customerListC = apMethods.getAllCustomers();
		TableView<Customer> table = apMethods.customerTable(customerListC);

		Button option1 = new Button("Add or edit a Movie");
		option1.setOnAction(e -> Main.switchSceneAdmin(this.customerID, "", ""));
		option1.setMinWidth(200);
		option1.setMaxWidth(200);

		Button option2 = new Button("Add or edit Suppliers");
		option2.setOnAction(e -> editSupplier());
		option2.setMinWidth(200);
		option2.setMaxWidth(200);

		Button option3 = new Button("Edit Customer information");
		option3.setOnAction(e -> customerInformation());
		option3.setMinWidth(200);
		option3.setMaxWidth(200);

		Button option4 = new Button("Information by Order ID");
		option4.setOnAction(e -> orderInformationID());
		option4.setMinWidth(200);
		option4.setMaxWidth(200);

		VBox leftSide = new VBox();
		leftSide.getChildren().addAll(option1, option2, option3, option4);

		ComboBox<String> searchOptionB = new ComboBox<>();
		searchOptionB.getItems().addAll("Search by name", "Search by lastname", "Search by Customer ID");
		searchOptionB.setValue("Choose search option...");

		HBox searchBox1 = new HBox();
		TextField searchText1 = new TextField();

		Button button1 = new Button("Search");
		button1.setOnAction(e -> {

			String searchText = searchText1.getText();
			searchChoice(searchText, searchOptionB.getValue());

		});

		searchBox1.getChildren().addAll(searchOptionB, searchText1, button1);

		VBox center = new VBox();

		center.getChildren().addAll(searchBox1, table);

		adminPanel.setLeft(leftSide);
		adminPanel.setCenter(center);
		adminPanel.setTop(menuBar);

		Scene adminPanelScene = new Scene(adminPanel, 660, 600);

		Main.window.setScene(adminPanelScene);

	}

	public void customerInformationSearch(String value, String filterChoice) {

		BorderPane adminPanel = new BorderPane();

		Menu fileMenu = new Menu("File");

		MenuItem logoutM = new MenuItem("Logout");
		logoutM.setOnAction(e -> {

		});

		MenuItem exitM = new MenuItem("Exit");
		exitM.setOnAction(e -> {
			System.exit(1);
		});

		fileMenu.getItems().addAll(logoutM, exitM);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);

		String searchOption = filter.filterChoice(filterChoice);
		ObservableList<Customer> customerList = filter.searchByStringC(searchOption, "%" + value + "%");
		TableView<Customer> table = apMethods.customerTable(customerList);

		Button option1 = new Button("Add or edit a Movie");
		option1.setOnAction(e -> Main.switchSceneAdmin(this.customerID, "", ""));
		option1.setMinWidth(200);
		option1.setMaxWidth(200);

		Button option2 = new Button("Add or edit Suppliers");
		option2.setOnAction(e -> editSupplier());
		option2.setMinWidth(200);
		option2.setMaxWidth(200);

		Button option3 = new Button("Edit Customer information");
		option3.setOnAction(e -> customerInformation());
		option3.setMinWidth(200);
		option3.setMaxWidth(200);

		Button option4 = new Button("Information by Order ID");
		option4.setOnAction(e -> orderInformationID());
		option4.setMinWidth(200);
		option4.setMaxWidth(200);

		VBox leftSide = new VBox();
		leftSide.getChildren().addAll(option1, option2, option3, option4);

		ComboBox<String> searchOptionB = new ComboBox<>();
		searchOptionB.getItems().addAll("Search by name", "Search by lastname", "Search by Customer ID");
		searchOptionB.setValue("Choose search option...");

		HBox searchBox1 = new HBox();
		TextField searchText1 = new TextField();

		Button button1 = new Button("Search");
		button1.setOnAction(e -> {

			String searchText = searchText1.getText();
			searchChoice(searchText, searchOptionB.getValue());

		});

		searchBox1.getChildren().addAll(searchOptionB, searchText1, button1);

		VBox center = new VBox();

		center.getChildren().addAll(searchBox1, table);

		adminPanel.setLeft(leftSide);
		adminPanel.setCenter(center);
		adminPanel.setTop(menuBar);

		Scene adminPanelScene = new Scene(adminPanel, 660, 600);

		Main.window.setScene(adminPanelScene);

	}

	public static void editCustomerWindow(int customerID) {
		try {

			Filters filter = new Filters();
			Customer customer = filter.selectCustomer(customerID);

			BorderPane editWindowPanel = new BorderPane();
			editWindowPanel.setPadding(new Insets(25, 50, 50, 25));

			Scene editWindow;

			HBox center = new HBox();
			center.setSpacing(50);

			VBox leftSide = new VBox();
			leftSide.setSpacing(10);

			VBox rightSide = new VBox();
			rightSide.setSpacing(10);

			HBox buttons = new HBox();

			Label label1 = new Label("Customer ID");
			Label label2 = new Label("Customer Name");
			Label label3 = new Label("Customer Lastname");
			Label label4 = new Label("Address line 1");
			Label label5 = new Label("Address line 2");
			Label label6 = new Label("City");
			Label label7 = new Label("State");
			Label label8 = new Label("Postal Code");
			Label label9 = new Label("Country");
			Label label10 = new Label("Phone");
			Label label11 = new Label("E-mail Address");
			Label label12 = new Label("Password");
			Label label13 = new Label("Username");
			Label label14 = new Label("Access");

			TextField customerIDT = new TextField(String.valueOf(customer.getCustomerID()));
			customerIDT.setEditable(false);
			TextField firstNameT = new TextField(customer.getFirstName());
			TextField lastNameT = new TextField(customer.getLastName());
			TextField address1T = new TextField(customer.getAddress1());
			TextField address2T = new TextField(customer.getAddress2());
			TextField cityT = new TextField(customer.getCity());
			TextField stateT = new TextField(customer.getState());
			TextField postalCodeT = new TextField(customer.getPostalCode());
			TextField countryT = new TextField(customer.getCountry());
			TextField phoneT = new TextField(customer.getPhone());
			TextField emailT = new TextField(customer.getEmail());
			TextField passwordT = new TextField(customer.getPassword());
			TextField usernameT = new TextField(customer.getUsername());

			ChoiceBox<String> accessT = new ChoiceBox<>();
			accessT.getItems().addAll("User", "Admin");
			accessT.setValue(customer.getAccess());

			System.out.println(customer.getAccess());

			Text messageT = new Text();

			Button save = new Button("Save");

			save.setOnAction(e -> {

				AdminPanelMethods apMethods = new AdminPanelMethods();

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
				String access = apMethods.getChoiceString(accessT);

				apMethods.editCustomer(firstName, lastName, address1, address2, city, state, postalCode, country, phone,
						email, password, username, access, customerID);

				messageT.setText("Saved successfully");

			});

			buttons.getChildren().addAll(save);

			leftSide.getChildren().addAll(label1, customerIDT, label2, firstNameT, label3, lastNameT, label4, address1T,
					label5, address2T, label6, cityT, label7, stateT);

			rightSide.getChildren().addAll(label8, postalCodeT, label9, countryT, label10, phoneT, label11, emailT,
					label12, passwordT, label13, usernameT, label14, accessT, buttons, messageT);

			center.getChildren().addAll(leftSide, rightSide);

			editWindowPanel.setCenter(center);

			editWindow = new Scene(editWindowPanel, 500, 600);

			Stage secondStage = new Stage();
			secondStage.initModality(Modality.APPLICATION_MODAL);
			secondStage.setScene(editWindow);
			secondStage.showAndWait();

		} catch (Exception e) {
			System.out.println("Input format error");
		}
	}

	public void orderInformationID() {

		BorderPane adminPanel = new BorderPane();

		ObservableList<Orders> orderList = apMethods.getAllOrders();
		TableView<Orders> table = apMethods.ordersTable(orderList);

		Button option1 = new Button("Add or edit a Movie");
		option1.setOnAction(e -> Main.switchSceneAdmin(this.customerID, "", ""));
		option1.setMinWidth(200);
		option1.setMaxWidth(200);

		Button option2 = new Button("Add or edit Suppliers");
		option2.setOnAction(e -> editSupplier());
		option2.setMinWidth(200);
		option2.setMaxWidth(200);

		Button option3 = new Button("Edit Customer information");
		option3.setOnAction(e -> customerInformation());
		option3.setMinWidth(200);
		option3.setMaxWidth(200);

		Button option4 = new Button("Information by Order ID");
		option4.setOnAction(e -> orderInformationID());
		option4.setMinWidth(200);
		option4.setMaxWidth(200);

		ComboBox<String> searchOptionB = new ComboBox<>();
		searchOptionB.getItems().addAll("Search by Order ID", "Search by customer ID", "Search by Order Date");
		searchOptionB.setValue("Choose search option...");

		HBox searchBox1 = new HBox();
		TextField searchText1 = new TextField();

		Button button1 = new Button("Search");
		button1.setOnAction(e -> {

			String searchText = searchText1.getText();
			searchChoice(searchText, searchOptionB.getValue());

		});

		searchBox1.getChildren().addAll(searchOptionB, searchText1, button1);

		Menu fileMenu = new Menu("File");
		MenuItem logoutM = new MenuItem("Logout");
		logoutM.setOnAction(e -> {

		});

		MenuItem exitM = new MenuItem("Exit");
		logoutM.setOnAction(e -> {
			System.exit(1);
		});

		fileMenu.getItems().addAll(logoutM, exitM);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);

		VBox center = new VBox();
		center.getChildren().addAll(searchBox1, table);

		VBox leftSide = new VBox();
		leftSide.getChildren().addAll(option1, option2, option3, option4);

		adminPanel.setLeft(leftSide);
		adminPanel.setCenter(center);
		adminPanel.setTop(menuBar);

		Scene adminPanelScene = new Scene(adminPanel, 660, 600);

		Main.window.setScene(adminPanelScene);

	}

	public void orderInformationSearch(String value, String filterChoice) {

		BorderPane adminPanel = new BorderPane();

		Menu fileMenu = new Menu("File");
		MenuItem logoutM = new MenuItem("Logout");
		logoutM.setOnAction(e -> {

		});

		MenuItem exitM = new MenuItem("Exit");
		logoutM.setOnAction(e -> {
			System.exit(1);
		});

		fileMenu.getItems().addAll(logoutM, exitM);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);

		String searchOption = filter.filterChoice(filterChoice);
		ObservableList<Orders> orderList = filter.searchByStringO(searchOption, "%" + value + "%");
		TableView<Orders> table = apMethods.ordersTable(orderList);

		Button option1 = new Button("Add or edit a Movie");
		option1.setOnAction(e -> Main.switchSceneAdmin(this.customerID, "", ""));
		option1.setMinWidth(200);
		option1.setMaxWidth(200);

		Button option2 = new Button("Add or edit Suppliers");
		option2.setOnAction(e -> editSupplier());
		option2.setMinWidth(200);
		option2.setMaxWidth(200);

		Button option3 = new Button("Edit Customer information");
		option3.setOnAction(e -> customerInformation());
		option3.setMinWidth(200);
		option3.setMaxWidth(200);

		Button option4 = new Button("Information by Order ID");
		option4.setOnAction(e -> orderInformationID());
		option4.setMinWidth(200);
		option4.setMaxWidth(200);

		ComboBox<String> searchOptionB = new ComboBox<>();
		searchOptionB.getItems().addAll("Search by Order ID", "Search by customer ID", "Search by Order Date");
		searchOptionB.setValue("Choose search option...");

		HBox searchBox1 = new HBox();
		TextField searchText1 = new TextField();

		Button button1 = new Button("Search");
		button1.setOnAction(e -> {

			String searchText = searchText1.getText();
			searchChoice(searchText, searchOptionB.getValue());

		});

		searchBox1.getChildren().addAll(searchOptionB, searchText1, button1);

		VBox leftSide = new VBox();
		leftSide.getChildren().addAll(option1, option2, option3, option4);

		VBox center = new VBox();
		center.getChildren().addAll(searchBox1, table);

		adminPanel.setLeft(leftSide);
		adminPanel.setCenter(center);
		adminPanel.setTop(menuBar);

		Scene adminPanelScene = new Scene(adminPanel, 660, 600);

		Main.window.setScene(adminPanelScene);

	}

	public static void orderDetails(int orderID) {

		AdminPanelMethods apMethods = new AdminPanelMethods();
		BorderPane orderDetailsPanel = new BorderPane();
		AdminPanel ap = new AdminPanel();

		ObservableList<OrderDetails> orderDetails = apMethods.getOrderDetailsByID(orderID);
		ObservableList<Product> productName = apMethods.getProductName(orderID);

		TableView<OrderDetails> table = apMethods.orderDetailsTable(orderDetails);
		TableView<Product> table1 = apMethods.orderDetailsProcuctName(productName);

		HBox tables = new HBox();
		VBox center = new VBox();

		tables.getChildren().addAll(table1, table);

		center.getChildren().addAll(tables);
		orderDetailsPanel.setCenter(center);

		Scene orderDetailsScene = new Scene(orderDetailsPanel, 650, 400);

		Stage secondStage = new Stage();
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setScene(orderDetailsScene);
		secondStage.showAndWait();

	}

	private void searchChoice(String searchText, String searchOption) {
		switch (searchOption) {
		case "Search by Name":
			Main.switchSceneAdmin(customerID, searchText, "searchByName");
			break;
		case "Search by Year":
			Main.switchSceneAdmin(customerID, searchText, "searchByYear");
			break;
		case "Search by Product ID":
			Main.switchSceneAdmin(customerID, searchText, "searchByID");
			break;
		case "Search by name":
			customerInformationSearch(searchText, "searchCByName");
			break;
		case "Search by lastname":
			customerInformationSearch(searchText, "searchCByLastname");
			break;
		case "Search by Customer ID":
			customerInformationSearch(searchText, "searchCByID");
			break;
		case "Search by Order ID":
			orderInformationSearch(searchText, "searchOByID");
			break;
		case "Search by customer ID":
			orderInformationSearch(searchText, "searchOByCID");
			break;
		case "Search by Order Date":
			orderInformationSearch(searchText, "searchOByDate");
			break;
		}

	}

}
