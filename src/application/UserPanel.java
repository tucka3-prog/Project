package application;

import java.text.DecimalFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserPanel {

	UserPanelMethods upMethods = new UserPanelMethods();
	AdminPanelMethods apMethods = new AdminPanelMethods();
	Filters filter = new Filters();
	int customerID;
	Customer customer;
	public static ObservableList<OrderDetails> productList = FXCollections.observableArrayList();

	public BorderPane userScene(int customerID, int categoryID, String value, String searchChoice) {

		this.customer = filter.selectCustomer(customerID);
		this.customerID = customerID;
		ObservableList<Product> productList = FXCollections.observableArrayList();
		String searchOption = null;

		if (searchChoice.equals("searchTop10")) {
			searchOption = filter.filterChoice("searchTop10");
		} else if (searchChoice.equals("searchByName")) {
			searchOption = filter.filterChoice("searchByName");
		}

		if (categoryID == 0 && value.equals("")) {
			productList = apMethods.getAllProducts();
		} else if (categoryID > 0) {
			productList = filter.filterCategory(categoryID);
		} else if (value.length() != 0) {
			productList = filter.searchByStringUser(searchOption, "%" + value + "%");
		}

		TableView<Product> table = upMethods.productTable(productList);

		BorderPane userScenePanel = new BorderPane();
		userScenePanel.setLeft(table);

		Button top10Button = new Button("Top 10");
		top10Button.setPrefWidth(145);
		top10Button.setOnAction(e -> {

			String searchText = "top10";
			Main.switchSceneUser(customerID, 0, searchText, "searchTop10");

		});

		Button clearButton = new Button("Clear Basket");
		clearButton.setPrefWidth(145);
		clearButton.setOnAction(e -> {
			UserPanel.productList.clear();
		});

		Button completeButton = new Button("Confirm Order");
		completeButton.setPrefWidth(145);
		completeButton.setOnAction(e -> {
			upMethods.completeOrder(this.customerID, UserPanel.productList);
			if (UserPanel.productList.size() > 0) {
				orderComplete();
			}
			UserPanel.productList.clear();

		});

		ComboBox<String> categoryChoice = new ComboBox<>();
		categoryChoice.getItems().addAll("Action", "Romance", "Mystery", "Horror", "Fantasy", "Drama", "Crime",
				"Comedy", "Aventure", "Thriller", "Science fiction", "Western", "Show All");
		categoryChoice.setOnAction(e -> {
			switchCategory(categoryChoice.getValue());
		});
		categoryChoice.setValue("Category");

		String size = "View Basket";
		Button cartButton = new Button(size);
		cartButton.setPrefWidth(145);
		cartButton.setOnAction(e -> {
			shoppingCart();
		});

		Label label1 = new Label("You can check our");
		label1.setFont(Font.font("Boulder", FontWeight.BOLD, 16));
		Label label2 = new Label("Sort by category");
		label2.setFont(Font.font("Boulder", FontWeight.BOLD, 16));
		Label label3 = new Label("Product basket");
		label3.setFont(Font.font("Boulder", FontWeight.BOLD, 16));

		VBox choice1 = new VBox();
		VBox choice2 = new VBox();
		VBox choices = new VBox();
		VBox basket = new VBox();

		basket.getChildren().addAll(label3, cartButton, clearButton, completeButton);
		basket.setSpacing(15);

		choice1.getChildren().addAll(label1, top10Button);
		choice2.getChildren().addAll(label2, categoryChoice);
		choices.getChildren().addAll(choice1, choice2);
		choices.setSpacing(10);

		VBox optionChoice = new VBox();
		optionChoice.getChildren().addAll(choices, basket);
		optionChoice.setSpacing(200);
		optionChoice.setPadding(new Insets(20, 0, 0, 5));

		Label searchLabel1 = new Label("Search for product");
		TextField searchText1 = new TextField();

		Button searchButton = new Button("Search");
		searchButton.setOnAction(e -> {

			String searchText = searchText1.getText();
			Main.switchSceneUser(customerID, 0, searchText, "searchByName");

		});

		String customerName = customer.getFirstName();
		String message = "Welcome " + customerName + "...";
		Text welcomeMsg = new Text(message);
		welcomeMsg.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
		Text emptySpace = new Text("\t \t");
		
		Menu fileMenu = new Menu("File");
		MenuItem logoutM = new MenuItem("Logout");
		logoutM.setOnAction(e -> {
			Main.switchSceneLogin();
		});

		MenuItem exitM = new MenuItem("Exit");
		exitM.setOnAction(e -> {
			System.exit(1);
		});

		fileMenu.getItems().addAll(logoutM, exitM);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);

		HBox topBox = new HBox();
		topBox.getChildren().addAll(welcomeMsg, emptySpace, searchLabel1, searchText1, searchButton);
		
		VBox topMenu = new VBox();
		topMenu.getChildren().addAll(menuBar, topBox);
		topMenu.setSpacing(5);


		userScenePanel.setTop(topMenu);
		userScenePanel.setCenter(optionChoice);

		return userScenePanel;

	}

	public static void orderScene(int productID) {

		Stage secondStage = new Stage();
		secondStage.initModality(Modality.APPLICATION_MODAL);
		UserPanel userPanel = new UserPanel();
		AdminPanelMethods apMethods = new AdminPanelMethods();
		Filters select = new Filters();

		Product product = select.selectProduct(productID);
		OrderDetails productDetails = new OrderDetails();

		BorderPane productOrder = new BorderPane();

		Label quantityL = new Label("Please enter quantity: ");
		Label unitPriceL = new Label("Unit Price: ");
		Label productAvailableL = new Label("Is product Available? - ");
		Label unitsInStockL = new Label("Units in Stock: ");
		Label totalL = new Label("Total: ");
		totalL.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		Text message = new Text();
		Text unitPriceT = new Text(String.valueOf(product.getUnitPrice()));
		Text productAvailableT = new Text(String.valueOf(product.getProductAvailable()));
		Text unitsInStockT = new Text(String.valueOf(product.getUnitsInStock()));
		Text totalT = new Text();
		TextField quantityT = new TextField();
		quantityT.setPrefWidth(40.0);

		quantityT.setOnKeyReleased(e -> {
			try {

				double a = Double.parseDouble(unitPriceT.getText());
				double b = Double.parseDouble(quantityT.getText());
				
				DecimalFormat df = new DecimalFormat("#.##");
				String c =  df.format(a*b);

				totalT.setText(c);

			} catch (Exception ee) {
			}

		});

		Button orderB = new Button("Add to Basket");
		orderB.setOnAction(e -> {
			try {
				apMethods.isInt(quantityT);
				message.setText("Quantity must be a number");
				int qty = Integer.parseInt(quantityT.getText());

				if (product.getUnitsInStock() < qty) {
					message.setText("Not enough units in Stock");
				} else if (product.getUnitsInStock() >= qty) {

					productDetails.setQuantity(qty);
					productDetails.setProductName(product.getProductName());
					productDetails.setPrice(product.getUnitPrice());
					productDetails.setTotal();
					productDetails.setProductID(product.getProductID());

					userPanel.addProductToCart(productList, productDetails);
					message.setText("Product added to Basket!");

				}

			} catch (Exception ee) {

			}

		});

		Button goBackB = new Button("Back");
		goBackB.setOnAction(e -> {
			secondStage.hide();
		});

		HBox unitPrice = new HBox();
		HBox productAvailable = new HBox();
		HBox unitsInStock = new HBox();
		HBox quantity = new HBox();
		HBox total = new HBox();
		HBox order = new HBox();

		productAvailable.getChildren().addAll(productAvailableL, productAvailableT);
		unitsInStock.getChildren().addAll(unitsInStockL, unitsInStockT);
		unitPrice.getChildren().addAll(unitPriceL, unitPriceT);
		quantity.getChildren().addAll(quantityL, quantityT);
		total.getChildren().addAll(totalL, totalT);
		order.getChildren().addAll(orderB, goBackB);
		order.setSpacing(10);

		VBox centerBot = new VBox();
		centerBot.getChildren().addAll(productAvailable, unitsInStock, unitPrice, quantity, total, order, message);
		centerBot.setPadding(new Insets(10, 10, 10, 110));
		centerBot.setSpacing(10);

		productOrder.setRight(centerBot);

		Label nameL = new Label("Name: ");
		Label yearL = new Label("Year released: ");
		Label rankingL = new Label("Ranking (IMDB): ");
		Label discTypeL = new Label("Disc type: ");

		Text yearT = new Text(String.valueOf(product.getYear()));
		Text nameT = new Text(String.valueOf(product.getProductName()));
		Text rankingT = new Text(String.valueOf(product.getRanking()));
		Text discTypeT = new Text(String.valueOf(product.getDiscType()));

		HBox name = new HBox();
		HBox year = new HBox();
		HBox ranking = new HBox();
		HBox discType = new HBox();

		name.getChildren().addAll(nameL, nameT);
		year.getChildren().addAll(yearL, yearT);
		ranking.getChildren().addAll(rankingL, rankingT);
		discType.getChildren().addAll(discTypeL, discTypeT);

		VBox leftSide = new VBox();
		leftSide.getChildren().addAll(name, year, ranking, discType);
		leftSide.setPadding(new Insets(10, 10, 10, 10));
		leftSide.setSpacing(10);

		try {
			String productImage = "projectImages/" + productID + ".jpg";
			Image image2 = new Image(productImage, 200, 300, false, false);
			leftSide.getChildren().add(new ImageView(image2));
		} catch (Exception image) {
			String productImage = "projectImages/" + 999 + ".png";
			Image image2 = new Image(productImage, 200, 300, false, false);
			leftSide.getChildren().add(new ImageView(image2));
		}

		productOrder.setLeft(leftSide);

		Label descriptionL = new Label("Movie description");
		TextArea descriptionT = new TextArea(String.valueOf(product.getProductDescription()));
		descriptionT.setEditable(false);
		descriptionT.setWrapText(true);
		descriptionT.setMinWidth(250);
		descriptionT.setMaxWidth(350);
		descriptionT.setMinHeight(250);
		descriptionT.setMaxWidth(350);

		VBox center = new VBox();

		center.getChildren().addAll(descriptionL, descriptionT, centerBot);
		center.setPadding(new Insets(10, 10, 10, 10));

		productOrder.setCenter(center);

		Scene scene = new Scene(productOrder, 600, 530);
		secondStage.setScene(scene);

		secondStage.showAndWait();
	}

	public static void shoppingCart() {

		UserPanelMethods upMethods = new UserPanelMethods();
		BorderPane orderDetailsPanel = new BorderPane();

		TableView<OrderDetails> table = upMethods.orderDetailsTable(productList);

		orderDetailsPanel.setCenter(table);

		Scene orderDetailsScene = new Scene(orderDetailsPanel, 555, 400);

		Stage secondStage = new Stage();
		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setScene(orderDetailsScene);
		secondStage.showAndWait();

	}

	public void addProductToCart(ObservableList<OrderDetails> productList, OrderDetails product) {

		productList.add(product);

	}

	private void switchCategory(String category) {
		switch (category) {
		case "Action":
			Main.switchSceneUser(customerID, 1, "", "searchByName");
			break;
		case "Romance":
			Main.switchSceneUser(customerID, 2, "", "searchByName");
			break;
		case "Mystery":
			Main.switchSceneUser(customerID, 3, "", "searchByName");
			break;
		case "Horror":
			Main.switchSceneUser(customerID, 4, "", "searchByName");
			break;
		case "Fantasy":
			Main.switchSceneUser(customerID, 5, "", "searchByName");
			break;
		case "Drama":
			Main.switchSceneUser(customerID, 6, "", "searchByName");
			break;
		case "Crime":
			Main.switchSceneUser(customerID, 7, "", "searchByName");
			break;
		case "Comedy":
			Main.switchSceneUser(customerID, 8, "", "searchByName");
			break;
		case "Aventure":
			Main.switchSceneUser(customerID, 9, "", "searchByName");
			break;
		case "Thriller":
			Main.switchSceneUser(customerID, 10, "", "searchByName");
			break;
		case "Science fiction":
			Main.switchSceneUser(customerID, 11, "", "searchByName");
			break;
		case "Western":
			Main.switchSceneUser(customerID, 12, "", "searchByName");
			break;
		case "Show All":
			Main.switchSceneUser(customerID, 0, "", "searchByName");
			break;

		}
	}

	public static void orderComplete() {
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(200);
		window.setMinHeight(100);

		Label label = new Label();
		label.setText("Order is Complete, it will be shipped after 7 days");
		label.setFont(Font.font("Boulder", FontWeight.BOLD, 16));

		Button closeButton = new Button("OK!!!!");
		closeButton.setOnAction(e -> window.close());

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
