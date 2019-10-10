package application;

import java.sql.Date;
import java.sql.Time;

import javafx.scene.control.Button;

public class Orders {
	public int orderID;
	public int customerID;
	public Date orderDate;
	public Date shipDate;
	public Time timestamp;
	public String fulfilled;
	public String paid;
	public Date paymentDate;
	public Button button;


	public Orders (int orderID, int customerID, Date orderDate, Date shipDate, Time timestamp,
			String fulfilled, String paid, Date paymentDate) {
		this.orderID = orderID;
		this.customerID = customerID;
		this.orderDate = orderDate;
		this.shipDate = shipDate;
		this.timestamp = timestamp;
		this.fulfilled = fulfilled;
		this.paid = paid;
		this.paymentDate = paymentDate;
		this.button = new Button("Order Details");
		button.setOnAction(e -> {
			AdminPanel.orderDetails(orderID);
		});
	}
	
	public Orders(int orderID, int customerID, Date orderDate) {
		this.orderID = orderID;
		this.customerID = customerID;
		this.orderDate = orderDate;
		this.button = new Button("Order Details");
		button.setOnAction(e -> {
			AdminPanel.orderDetails(orderID);
		});

	}
	
	public int getOrderID() {
		return orderID;
	}
	
	public int getCustomerID() {
		return customerID;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	
	public Date getShipDate() {
		return shipDate;
	}
	public Time getTimestamp() {
		return timestamp;
	}
	public String getFulfilled() {
		return fulfilled;
	}
	public String getPaid() {
		return paid;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public Button getButton() {
		return button;
	}
}