package application;

import javafx.scene.control.Button;

public class Orders {
	private int orderID;
	private int customerID;
	private String orderDate;
	private String shipDate;
	private String timestamp;
	private String fulfilled;
	private String paid;
	private String paymentDate;
	private Button button;


	public Orders (int orderID, int customerID, String orderDate, String shipDate, String timestamp,
			String fulfilled, String paid, String paymentDate) {
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
	
	public Orders(int orderID, int customerID, String orderDate) {
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
	public String getOrderDate() {
		return orderDate;
	}
	
	public String getShipDate() {
		return shipDate;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public String getFulfilled() {
		return fulfilled;
	}
	public String getPaid() {
		return paid;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public Button getButton() {
		return button;
	}
}