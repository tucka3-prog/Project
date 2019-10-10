package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminPanelMethods {

	Filters filter = new Filters();

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

	public void addMovie(String productName, int year, String productDescription, Double ranking, Double unitPrice,
			String discType, String productAvailable, int unitsInStock, String sku, String note, int supplierID) {
		String sql = "INSERT INTO Products(ProductName,Year,ProductDescription,Ranking,UnitPrice,DiscType,ProductAvailable,UnitsInStock,SKU,Note,SupplierID) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, productName);
			pstmt.setInt(2, year);
			pstmt.setString(3, productDescription);
			pstmt.setDouble(4, ranking);
			pstmt.setDouble(5, unitPrice);
			pstmt.setString(6, discType);
			pstmt.setString(7, productAvailable);
			pstmt.setInt(8, unitsInStock);
			pstmt.setString(9, sku);
			pstmt.setString(10, note);
			pstmt.setInt(11, supplierID);
			pstmt.executeUpdate();
			System.out.println("Product added successfully");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public String editMovie(String productName, int year, String productDescription, Double ranking, Double unitPrice,
			String discType, String productAvailable, int unitsInStock, String sku, String note, int supplierID,
			int productID) {
		String sql = "UPDATE Products SET ProductName = ?, Year = ?, ProductDescription = ?, Ranking = ?,"
				+ " UnitPrice = ?, DiscType = ?, ProductAvailable = ?,"
				+ " UnitsInStock = ?, SKU = ?, Note = ?, SupplierID = ? WHERE ProductID = ?";
		String message = null;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, productName);
			pstmt.setInt(2, year);
			pstmt.setString(3, productDescription);
			pstmt.setDouble(4, ranking);
			pstmt.setDouble(5, unitPrice);
			pstmt.setString(6, discType);
			pstmt.setString(7, productAvailable);
			pstmt.setInt(8, unitsInStock);
			pstmt.setString(9, sku);
			pstmt.setString(10, note);
			pstmt.setInt(11, supplierID);
			pstmt.setInt(12, productID);
			pstmt.executeUpdate();
			message = "Product updated successfully";

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return message;
	}

	public void addSupplier(String companyName, String contactFName, String contactLName, String contactTitle,
			String address1, String address2, String city, String state, String postalCode, String country,
			String phone, String email, String url) {
		String sql = "INSERT INTO Suppliers(CompanyName, ContactFName, ContactLName, ContactTitle, Address1, Address2, City, "
				+ "State, PostalCode, Country, Phone, Email, URL) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, companyName);
			pstmt.setString(2, contactFName);
			pstmt.setString(3, contactLName);
			pstmt.setString(4, contactTitle);
			pstmt.setString(5, address1);
			pstmt.setString(6, address2);
			pstmt.setString(7, city);
			pstmt.setString(8, state);
			pstmt.setString(9, postalCode);
			pstmt.setString(10, country);
			pstmt.setString(11, phone);
			pstmt.setString(12, email);
			pstmt.setString(13, url);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public String editSupplier(String companyName, String contactFName, String contactLName, String contactTitle,
			String address1, String address2, String city, String state, String postalCode, String country,
			String phone, String email, String url, int supplierID) {
		String sql = "UPDATE Suppliers SET CompanyName = ?, ContactFName = ?, ContactLName = ?, ContactTitle = ?,"
				+ " Address1 = ?, Address2 = ?, City = ?,"
				+ " State = ?, PostalCode = ?, Country = ?, Phone = ?, Email = ?, URL = ? WHERE SupplierID = ?";
		String message = null;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, companyName);
			pstmt.setString(2, contactFName);
			pstmt.setString(3, contactLName);
			pstmt.setString(4, contactTitle);
			pstmt.setString(5, address1);
			pstmt.setString(6, address2);
			pstmt.setString(7, city);
			pstmt.setString(8, state);
			pstmt.setString(9, postalCode);
			pstmt.setString(10, country);
			pstmt.setString(11, phone);
			pstmt.setString(12, email);
			pstmt.setString(13, url);
			pstmt.setInt(14, supplierID);
			pstmt.executeUpdate();
			message = "Supplier updated successfully";

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return message;
	}

	public void editCustomer(String firstName, String lastName, String address1, String address2, String city,
			String state, String postalCode, String country, String phone, String email, String password,
			String username, String access, int customerID) {
		String sql = "UPDATE Customer SET FirstName = ?, LastName = ?, Address1 = ?, Address2 = ?, City = ?, State = ?, PostalCode = ?, Country = ?, Phone = ?, Email = ? , Password = ? , Username = ? , Access = ? WHERE CustomerID = ?";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, address1);
			pstmt.setString(4, address2);
			pstmt.setString(5, city);
			pstmt.setString(6, state);
			pstmt.setString(7, postalCode);
			pstmt.setString(8, country);
			pstmt.setString(9, phone);
			pstmt.setString(10, email);
			pstmt.setString(11, password);
			pstmt.setString(12, username);
			pstmt.setString(13, access);
			pstmt.setInt(14, customerID);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public ObservableList<Product> getAllProducts() {

		String sql = "SELECT ProductID, ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock "
				+ " FROM Products";

		ObservableList<Product> productList = FXCollections.observableArrayList();

		Product product;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				product = new Product(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getInt("Year"),
						rs.getString("ProductDescription"), rs.getDouble("Ranking"), rs.getDouble("UnitPrice"),
						rs.getString("DiscType"), rs.getString("ProductAvailable"), rs.getInt("UnitsInStock"));

				productList.add(product);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return productList;
	}

	public ObservableList<Product> getAllProductsAdmin() {

		String sql = "SELECT ProductID, ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock, SKU, Note, SupplierID "
				+ " FROM Products";

		ObservableList<Product> productList = FXCollections.observableArrayList();

		Product product;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				product = new Product(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getInt("Year"),
						rs.getString("ProductDescription"), rs.getDouble("Ranking"), rs.getDouble("UnitPrice"),
						rs.getString("DiscType"), rs.getString("ProductAvailable"), rs.getInt("UnitsInStock"),
						rs.getString("SKU"), rs.getString("Note"), rs.getInt("SupplierID"));

				productList.add(product);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return productList;
	}

	public ObservableList<Supplier> getAllSuppliers() {

		String sql = "SELECT SupplierID, CompanyName, Phone " + " FROM Suppliers";

		ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
		Supplier supplier;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				supplier = new Supplier(rs.getInt("SupplierID"), rs.getString("CompanyName"), rs.getString("Phone"));

				supplierList.add(supplier);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return supplierList;
	}

	public ObservableList<Customer> getAllCustomers() {

		String sql = "SELECT CustomerID, FirstName, LastName, Phone " + " FROM Customer";

		ObservableList<Customer> customerList = FXCollections.observableArrayList();
		Customer customer;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				customer = new Customer(rs.getInt("CustomerID"), rs.getString("FirstName"), rs.getString("LastName"),
						rs.getString("Phone"));

				customerList.add(customer);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return customerList;
	}

	public ObservableList<Orders> getAllOrders() {

		String sql = "SELECT OrderID, CustomerID, OrderDate" + " FROM Orders";

		ObservableList<Orders> orderList = FXCollections.observableArrayList();
		Orders order;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				order = new Orders(rs.getInt("OrderID"), rs.getInt("CustomerID"), rs.getString("OrderDate"));

				orderList.add(order);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return orderList;
	}

	public ObservableList<OrderDetails> getAllOrderDetails(int orderID) {

		String sql = "SELECT OrderID, ProductID, Price, Quantity, Total, ShipDate FROM OrderDetails";

		ObservableList<OrderDetails> orderList = FXCollections.observableArrayList();
		OrderDetails orderDetails;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				orderDetails = new OrderDetails(rs.getInt("OrderID"), rs.getInt("ProductID"), rs.getDouble("Price"),
						rs.getInt("Quantity"), rs.getDouble("Total"), rs.getString("ShipDate"));

				orderList.add(orderDetails);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return orderList;
	}

	public ObservableList<OrderDetails> getOrderDetailsByID(int orderID) {

		String sql = "SELECT Products.ProductName, OrderDetails.OrderID, OrderDetails.ProductID, OrderDetails.Price,\r\n" + 
				"OrderDetails.Quantity, OrderDetails.Total, OrderDetails.ShipDate FROM\r\n" + 
				"Products JOIN OrderDetails on Products.ProductID = OrderDetails.ProductID WHERE OrderID = ?";

		ObservableList<OrderDetails> orderList = FXCollections.observableArrayList();
		OrderDetails orderDetails;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				orderDetails = new OrderDetails(rs.getString("ProductName"), rs.getInt("OrderID"), rs.getInt("ProductID"), rs.getDouble("Price"),
						rs.getInt("Quantity"), rs.getDouble("Total"), rs.getString("ShipDate"));

				orderList.add(orderDetails);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return orderList;
	}

	public ObservableList<Product> getProductName(int orderID) {

		String sql = "SELECT ProductName FROM Products INNER JOIN OrderDetails ON Products.ProductID = OrderDetails.ProductID WHERE OrderID = ?";

		ObservableList<Product> productList = FXCollections.observableArrayList();
		Product product;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, orderID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				product = new Product(rs.getString("ProductName"));

				productList.add(product);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return productList;
	}

	public TableView<Product> productTable(ObservableList<Product> products) {

		TableColumn<Product, String> productID = new TableColumn<>("ID");
		productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
		productID.setMaxWidth(50);
		productID.setMinWidth(50);

		TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		nameColumn.setMinWidth(250);
		nameColumn.setMaxWidth(250);

		TableColumn<Product, String> yearColumn = new TableColumn<>("Year");
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
		yearColumn.setMaxWidth(60);
		yearColumn.setMinWidth(60);

		TableColumn<Product, String> edit = new TableColumn<>("Edit");
		edit.setCellValueFactory(new PropertyValueFactory<>("button"));

		TableView<Product> table = new TableView<Product>();
		table.setItems(products);
		table.getColumns().addAll(productID, nameColumn, yearColumn, edit);

		return table;

	}

	public TableView<Supplier> supplierTable(ObservableList<Supplier> suppliers) {

		TableColumn<Supplier, String> supplierID = new TableColumn<>("ID");
		supplierID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
		supplierID.setMaxWidth(50);
		supplierID.setMinWidth(50);

		TableColumn<Supplier, String> companyName = new TableColumn<>("Company Name");
		companyName.setMinWidth(195);
		companyName.setMaxWidth(195);
		companyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));

		TableColumn<Supplier, String> phone = new TableColumn<>("Phone number");
		phone.setMinWidth(145);
		phone.setMaxWidth(145);
		phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

		TableColumn<Supplier, String> button = new TableColumn<>("Edit");
		button.setCellValueFactory(new PropertyValueFactory<>("button"));

		TableView<Supplier> table = new TableView<Supplier>();
		table.setItems(suppliers);
		table.getColumns().addAll(supplierID, companyName, phone, button);

		return table;

	}

	public TableView<Customer> customerTable(ObservableList<Customer> customers) {

		TableColumn<Customer, String> customerID = new TableColumn<>("ID");
		customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
		customerID.setMaxWidth(50);
		customerID.setMinWidth(50);

		TableColumn<Customer, String> firstName = new TableColumn<>("First Name");
		firstName.setMaxWidth(112);
		firstName.setMinWidth(112);
		firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

		TableColumn<Customer, String> lastName = new TableColumn<>("Last Name");
		lastName.setMaxWidth(112);
		lastName.setMinWidth(112);
		lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

		TableColumn<Customer, String> phone = new TableColumn<>("Phone Number");
		phone.setMinWidth(100);
		phone.setMaxWidth(100);
		phone.setCellValueFactory(new PropertyValueFactory<>("phone"));

		TableColumn<Customer, String> button = new TableColumn<>("Edit");
		button.setCellValueFactory(new PropertyValueFactory<>("button"));

		TableView<Customer> table = new TableView<Customer>();
		table.setItems(customers);
		table.getColumns().addAll(customerID, firstName, lastName, phone, button);

		return table;

	}

	public TableView<Orders> ordersTable(ObservableList<Orders> orders) {

		TableColumn<Orders, String> orderID = new TableColumn<>("Order ID");
		orderID.setCellValueFactory(new PropertyValueFactory<>("OrderID"));

		TableColumn<Orders, String> customerID = new TableColumn<>("Customer ID");
		customerID.setMaxWidth(100);
		customerID.setMaxWidth(100);
		customerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));

		TableColumn<Orders, String> orderDate = new TableColumn<>("Order Date");
		orderDate.setMaxWidth(100);
		orderDate.setMinWidth(100);
		orderDate.setCellValueFactory(new PropertyValueFactory<>("OrderDate"));

		TableColumn<Orders, String> button = new TableColumn<>("Order information");
		button.setCellValueFactory(new PropertyValueFactory<>("button"));

		TableView<Orders> table = new TableView<Orders>();
		table.setItems(orders);

		table.getColumns().addAll(orderID, customerID, orderDate, button);

		return table;

	}

	public TableView<OrderDetails> orderDetailsTable(ObservableList<OrderDetails> orders) {
		
		TableColumn<OrderDetails, String> productName = new TableColumn<>("Product Name");
		productName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
		productName.setMaxWidth(200);
		productName.setMinWidth(200);

		TableColumn<OrderDetails, String> orderID = new TableColumn<>("Order ID");
		orderID.setCellValueFactory(new PropertyValueFactory<>("OrderID"));

		TableColumn<OrderDetails, String> price = new TableColumn<>("Price");
		price.setMaxWidth(80);
		price.setMinWidth(80);
		price.setCellValueFactory(new PropertyValueFactory<>("Price"));

		TableColumn<OrderDetails, String> quantity = new TableColumn<>("Quantity");
		quantity.setMaxWidth(80);
		quantity.setMinWidth(80);
		quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

		TableColumn<OrderDetails, String> total = new TableColumn<>("Sub Total");
		total.setMaxWidth(100);
		total.setMinWidth(100);
		total.setCellValueFactory(new PropertyValueFactory<>("Total"));

		TableColumn<OrderDetails, String> shipDate = new TableColumn<>("Ship Date");
		shipDate.setCellValueFactory(new PropertyValueFactory<>("ShipDate"));

		TableView<OrderDetails> table = new TableView<OrderDetails>();

		table.setItems(orders);
		table.setMinWidth(660);

		table.getColumns().addAll(productName, orderID, price, quantity, total, shipDate);

		return table;

	}


	public void isInt(TextField text) {
		try {
			int number = Integer.parseInt(text.getText());
		} catch (NumberFormatException e) {
			System.out.println("Format incorrect");
		}
	}

	public void isDouble(TextField text) {
		try {
			Double number = Double.parseDouble(text.getText());
		} catch (NumberFormatException e) {
			System.out.println("Format incorrect");
		}
	}

	public String getChoiceString(ChoiceBox<String> choiceValue) {
		String choice = choiceValue.getValue();
		return choice;
	}

	public Integer getChoiceInt(ChoiceBox<Integer> choiceValue) {
		Integer choice = choiceValue.getValue();
		return choice;
	}

}
