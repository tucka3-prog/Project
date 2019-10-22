package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class UserPanelMethods {

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

	public TableView<Product> productTable(ObservableList<Product> products) {

		TableColumn<Product, String> productName = new TableColumn<>("Name");
		productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		productName.setMinWidth(210);
		productName.setMaxWidth(210);

		TableColumn<Product, String> unitPrice = new TableColumn<>("Price ($)");
		unitPrice.setMaxWidth(59);
		unitPrice.setMinWidth(59);
		unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

		TableColumn<Product, String> ranking = new TableColumn<>("Ranking");
		ranking.setMaxWidth(59);
		ranking.setMinWidth(59);
		ranking.setCellValueFactory(new PropertyValueFactory<>("ranking"));

		TableColumn<Product, String> edit = new TableColumn<>("Edit");
		edit.setCellValueFactory(new PropertyValueFactory<>("button"));

		TableView<Product> table = new TableView<Product>();
		table.setItems(products);
		table.getColumns().addAll(productName, unitPrice, ranking, edit);

		return table;

	}

	public ObservableList<Product> getTop10() {

		String sql = "SELECT ProductID, ProductName, Year, ProductDescription, Ranking, UnitPrice, DiscType, ProductAvailable, UnitsInStock"
				+ " from Products as c JOIN OrderDetails as ct on c.ProductID = ct.ProductID GROUP BY ProductName ORDER BY SUM(Quantity) DESC LIMIT 10";

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

	public TableView<OrderDetails> orderDetailsTable(ObservableList<OrderDetails> orders) {

		TableColumn<OrderDetails, String> productName = new TableColumn<>("Product Name");
		productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		productName.setMinWidth(250);

		TableColumn<OrderDetails, String> price = new TableColumn<>("Price");
		price.setMaxWidth(100);
		price.setMinWidth(100);
		price.setCellValueFactory(new PropertyValueFactory<>("Price"));

		TableColumn<OrderDetails, String> quantity = new TableColumn<>("Quantity");
		quantity.setMaxWidth(100);
		quantity.setMinWidth(100);
		quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

		TableColumn<OrderDetails, String> total = new TableColumn<>("Sub Total");
		total.setMaxWidth(100);
		total.setMinWidth(100);
		total.setCellValueFactory(new PropertyValueFactory<>("Total"));

		TableView<OrderDetails> table = new TableView<OrderDetails>();

		table.setItems(orders);

		table.getColumns().addAll(productName, price, quantity, total);

		return table;

	}

	public TableView<Product> orderDetailsProcuctName(ObservableList<Product> product) {

		TableColumn<Product, String> productName = new TableColumn<>("Product Name");
		productName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));

		TableView<Product> table = new TableView<Product>();

		table.setItems(product);

		table.getColumns().add(productName);

		return table;

	}

	public void completeOrder(int customerID, ObservableList<OrderDetails> productList) {
		String sqlNewOrder = "INSERT INTO Orders(CustomerID, OrderDate, ShipDate, Timestamp) VALUES(?,?,?,?)";

		String sqlAddItem = "INSERT INTO OrderDetails(OrderID, ProductID, Price, Quantity, Total, ShipDate)"
				+ "VALUES(?,?,?,?,?,?)";
		String updateQuanty = "UPDATE Products SET UnitsInStock = UnitsInStock - ? WHERE ProductID = ?";
		String updateStock = "Update Products set ProductAvailable = 'No' where UnitsInStock = 0";

		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement pstmt1 = null, pstmt2 = null, pstmt3 = null, pstmt4 = null;

		try {
			conn = this.connect();
			if (conn == null)
				return;

			conn.setAutoCommit(false);

			pstmt1 = conn.prepareStatement(sqlNewOrder, Statement.RETURN_GENERATED_KEYS);

			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
			String dateStamp = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
			String shipDate = addWeek(dateStamp);

			pstmt1.setInt(1, customerID);
			pstmt1.setString(2, dateStamp);
			pstmt1.setString(3, shipDate);
			pstmt1.setString(4, timeStamp);

			int rowAffected = pstmt1.executeUpdate();

			rs = pstmt1.getGeneratedKeys();
			int orderID = 0;
			if (rs.next()) {
				orderID = rs.getInt(1);
			}

			if (rowAffected != 1) {
				conn.rollback();
			}

			for (int a = 0; a < productList.size(); a++) {

				pstmt2 = conn.prepareStatement(sqlAddItem);
				pstmt2.setInt(1, orderID);
				pstmt2.setInt(2, productList.get(a).getProductID());
				pstmt2.setDouble(3, productList.get(a).getPrice());
				pstmt2.setInt(4, productList.get(a).getQuantity());
				pstmt2.setDouble(5, productList.get(a).getTotal());
				pstmt2.setString(6, shipDate);		
				pstmt2.executeUpdate();	
				
				pstmt3 = conn.prepareStatement(updateQuanty);
				pstmt3.setInt(1, productList.get(a).getQuantity());
				pstmt3.setInt(2, productList.get(a).getProductID());
				pstmt3.executeUpdate();		
				
			}
			
			pstmt4 = conn.prepareStatement(updateStock);
			pstmt4.executeUpdate();		
			
			if (productList.size() > 0) {
				conn.commit();
			}

		} catch (SQLException e1) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException e2) {
				System.out.println(e2.getMessage());
			}
			System.out.println(e1.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt1 != null) {
					pstmt1.close();
				}
				if (pstmt2 != null) {
					pstmt2.close();
				}
				if (pstmt3 != null) {
					pstmt2.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e3) {
				System.out.println(e3.getMessage());
			}
		}
	}

	public String addWeek(String date) {
		String test = LocalDate.parse(date).plusDays(7).toString();
		return test;
	}

}
