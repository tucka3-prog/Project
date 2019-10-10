package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CompleteOrder {

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
	
    public void completeOrder(String customerID, ArrayList<OrderDetails> productList) {
        String sqlNewOrder = "INSERT INTO Orders(CustomerID) VALUES(?)";
        
        String sqlAddItem = "INSERT INTO OrderDetails(OrderID, ProductID, Price, Quantity, Total, ShipDate)"
                + "VALUES(?,?,?,?,?,?)";
 
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null, pstmt2 = null, pstmt3 = null;
        
        try {
            conn = this.connect();
            if(conn == null)
                return;
            
            conn.setAutoCommit(false);
            
            pstmt1 = conn.prepareStatement(sqlNewOrder,
                    Statement.RETURN_GENERATED_KEYS);
 
            pstmt1.setString(1, customerID);
            int rowAffected = pstmt1.executeUpdate();
 
            rs = pstmt1.getGeneratedKeys();
            int orderID = 0;
            if (rs.next()) {
                orderID = rs.getInt(1);
            }
 
            if (rowAffected != 1) {
                conn.rollback();
            }
            
            for (int a = 0; a<productList.size(); a++) {
            	
            Date date = new Date(199009);
            	
            pstmt2 = conn.prepareStatement(sqlAddItem);
            pstmt2.setInt(1, orderID);
            pstmt2.setInt(2, productList.get(a).productID);
            pstmt2.setDouble(3, productList.get(a).price);
            pstmt2.setInt(4, productList.get(a).quantity);
            pstmt2.setDouble(5, productList.get(a).total);
            pstmt2.setDate(6, date);
            // 
            pstmt2.executeUpdate();
            }
            
            conn.commit();
            System.out.println("Order completed successfully");
            
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
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e3) {
                System.out.println(e3.getMessage());
            }
        }
    }
}   
