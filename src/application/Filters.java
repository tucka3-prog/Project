package application;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Filters {

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

	public String filterChoice(String parameter) {

		switch (parameter) {
		case "filterByYearO":
			parameter = "SELECT ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock "
					+ "FROM Products WHERE Year > ?";
			break;
		case "filterByYearU":
			parameter = "SELECT ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock "
					+ "FROM Products WHERE Year < ?";
			break;
		case "filterByPriceU":
			parameter = "SELECT ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock "
					+ "FROM Products WHERE Price < ?";
			break;
		case "filterByPriceO":
			parameter = "SELECT ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock "
					+ "FROM Products WHERE Price > ?";
			break;
		case "filterByRankingO":
			parameter = "SELECT ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock "
					+ "FROM Products WHERE Ranking > ?";
			break;
		case "filterByRankingU":
			parameter = "SELECT ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock "
					+ "FROM Products WHERE Ranking < ?";
			break;
		case "searchByName":
			parameter = "SELECT ProductID, ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock, SKU, Note, SupplierID "
					+ "FROM Products WHERE ProductName Like ?";
			break;
		case "searchByID":
			parameter = "SELECT ProductID, ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock, SKU, Note, SupplierID  "
					+ "FROM Products WHERE ProductID Like ?";
			break;
		case "searchByYear":
			parameter = "SELECT ProductID, ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock, SKU, Note, SupplierID  "
					+ "FROM Products WHERE Year Like ?";
			break;
		case "searchCByName":
			parameter = "Select CustomeriD, FirstName, LastName, Address1, Address2, City, State, PostalCode, Country,"
					+ " Phone, Email, Password, Username, Password, Access from Customer where FirstName Like ?";
			break;
		case "searchCByLastname":
			parameter = "Select CustomeriD, FirstName, LastName, Address1, Address2, City, State, PostalCode, Country,"
					+ " Phone, Email, Password, Username, Password, Access from Customer where LastName Like ?";
			break;
		case "searchCByID":
			parameter = "Select CustomeriD, FirstName, LastName, Address1, Address2, City, State, PostalCode, Country, "
					+ "Phone, Email, Password, Username, Password, Access from Customer where CustomerID Like ?";
			break;
		case "searchOByID":
			parameter = "Select OrderID, CustomerID, OrderDate, ShipDate, Timestamp, Fulfilled, "
					+ "Paid, PaymentDate from Orders where OrderID Like ?";
			break;
		case "searchOByCID":
			parameter = "Select OrderID, CustomerID, OrderDate, ShipDate, Timestamp, Fulfilled, "
					+ "Paid, PaymentDate from Orders where CustomerID Like ?";
			break;
		case "searchOByDate":
			parameter = "Select OrderID, CustomerID, OrderDate, ShipDate, Timestamp, Fulfilled, "
					+ "Paid, PaymentDate from Orders where OrderDate Like ?";
			break;
		case "searchTop10":
			parameter = "SELECT Products.ProductID, ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, "
					+ "ProductAvailable, UnitsInStock from Products JOIN OrderDetails on Products.ProductID = OrderDetails.ProductID GROUP BY ProductName ORDER BY SUM(Quantity) DESC LIMIT 10";
			break;
			

		}
		return parameter;

	}


	public ObservableList<Product> filterCategory(int category) {

		String sql = "SELECT Products.ProductID, ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock "
				+ " FROM Products INNER JOIN ProductsCategory ON Products.ProductID = ProductsCategory.ProductID WHERE ProductsCategory.CategoryID = ?";

		ObservableList<Product> productList = FXCollections.observableArrayList();
		Product product;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, category);
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


	public Product selectProduct(int productID) {

		String sql = "SELECT Products.ProductID, ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock, SKU, Note, SupplierID "
				+ " FROM Products WHERE ProductID = ?";

		Product product = null;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, productID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				product = new Product(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getInt("Year"),
						rs.getString("ProductDescription"), rs.getDouble("Ranking"), rs.getDouble("UnitPrice"),
						rs.getString("DiscType"), rs.getString("ProductAvailable"), rs.getInt("UnitsInStock"),
						rs.getString("SKU"), rs.getString("Note"), rs.getInt("SupplierID"));

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return product;
	}
	
	public Supplier selectSupplier(int supplierID) {

		String sql = "SELECT SupplierID, CompanyName, ContactFName, ContactLName, ContactTitle, Address1, Address2,"
				+ "City, State, PostalCode, Country, Phone, Email, URL FROM Suppliers WHERE SupplierID = ?";

		Supplier supplier = null;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, supplierID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				supplier = new Supplier(rs.getInt("SupplierID"), rs.getString("CompanyName"), rs.getString("ContactFName"),
						rs.getString("ContactLName"), rs.getString("ContactTitle"), rs.getString("Address1"),
						rs.getString("Address2"), rs.getString("City"), rs.getString("State"), rs.getString("PostalCode"), 
						rs.getString("Country"), rs.getString("Phone"), rs.getString("Email"), rs.getString("URL") );

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return supplier;
	}
	
	public Customer selectCustomer(int customerID) {

		String sql = "SELECT CustomerID, FirstName, LastName, Address1, Address2, City, State,"
				+ "PostalCode, Country, Phone, Email, Password, Username, Access FROM Customer WHERE CustomerID = ?";

		Customer customer = null;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, customerID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				customer = new Customer(rs.getInt("CustomerID"), rs.getString("FirstName"), rs.getString("LastName"),
						rs.getString("Address1"), rs.getString("Address2"), rs.getString("City"),
						rs.getString("State"), rs.getString("PostalCode"), rs.getString("Country"), rs.getString("Phone"), 
						rs.getString("Email"), rs.getString("Password"), rs.getString("Username"), rs.getString("Access") );

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return customer;
	}

	public OrderDetails selectProductOrder(int productID, int quantity) {
		String sql = "SELECT ProductID,UnitPrice FROM PRODUCTS WHERE ProductID = ?";

		OrderDetails product = null;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, productID);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				Double total = (quantity * rs.getDouble("UnitPrice"));

				product = new OrderDetails(rs.getInt("ProductID"), rs.getDouble("UnitPrice"), quantity, total);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return product;
	}

	public ObservableList<Product> searchByString(String sql, String value) {

		ObservableList<Product> productList = FXCollections.observableArrayList();
		Product product;
		
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

				pstmt.setString(1, value);
			


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
	
	public ObservableList<Product> searchByStringUser(String sql, String value) {

		ObservableList<Product> productList = FXCollections.observableArrayList();
		Product product;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			if(value.equals("%" + "top10" + "%") || value.equals("%" + "" + "%")) {
		
			}else {
				pstmt.setString(1, value);
			}

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
	
	public ObservableList<Customer> searchByStringC(String sql, String value) {

		ObservableList<Customer> customerList = FXCollections.observableArrayList();
		Customer customer;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, value);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				customer = new Customer(rs.getInt("CustomerID"), rs.getString("FirstName"), rs.getString("LastName"),
						rs.getString("Address1"), rs.getString("Address2"), rs.getString("City"),
						rs.getString("State"), rs.getString("PostalCode"), rs.getString("Country"),
						rs.getString("Phone"), rs.getString("Email"), rs.getString("Password"), rs.getString("Username"), rs.getString("Access"));

				customerList.add(customer);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return customerList;
	}
	
	public ObservableList<Orders> searchByStringO(String sql, String value) {

		ObservableList<Orders> orderList = FXCollections.observableArrayList();
		Orders orders;

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

				
			pstmt.setString(1, value);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				orders = new Orders(rs.getInt("OrderID"), rs.getInt("CustomerID"), rs.getString("OrderDate"),
						rs.getString("ShipDate"), rs.getString("Timestamp"), rs.getString("Fulfilled"),
						rs.getString("Paid"), rs.getString("PaymentDate"));

				orderList.add(orders);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return orderList;
	}
	
	

}