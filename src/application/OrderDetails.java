package application;

public class OrderDetails {
	int orderID;
	int productID;
	Double price;
	int quantity;
	Double total;
	String shipDate;
	int orderDetailID;
	String productName;
	
	public OrderDetails (int orderID, int productID, Double price, int quantity,
			Double total, String shipDate) {
		this.orderID = orderID;
		this.productID = productID;
		this.price = price;
		this.quantity = quantity;
		this.total = this.price * this.quantity;
		this.shipDate = shipDate;
	}
	
	public OrderDetails (String productName, int orderID, int productID, Double price, int quantity,
			Double total, String shipDate) {
		this.productName = productName;
		this.orderID = orderID;
		this.productID = productID;
		this.price = price;
		this.quantity = quantity;
		this.total = this.price * this.quantity;
		this.shipDate = shipDate;
	}
	
	public OrderDetails (int orderID, int productID, Double price, int quantity,
			Double total, String shipDate, String productName) {
		this.orderID = orderID;
		this.productID = productID;
		this.price = price;
		this.quantity = quantity;
		this.total = this.price * this.quantity;
		this.shipDate = shipDate;
		this.productName = productName;
	}
	
	public OrderDetails (int productID, Double price, int quantity,
			Double total) {
		this.productID = productID;
		this.price = price;
		this.quantity = quantity;
		this.total = this.price * this.quantity;

	}
	
	public OrderDetails() {
	}
	
	public OrderDetails(String productName) {
		this.productName = productName;
	}

	public int getOrderDetailID() {
		return orderDetailID;
	}
	
	public void setOrderDetailID(int orderDetailID) {
		this.orderDetailID = orderDetailID;
	}
	
	public int getOrderID() {
		return orderID;
	}
	
	public void setProductID(int productID) {
		this.productID = productID;
	}
	
	public int getProductID() {
		return productID;
	}
	public Double getPrice() {
		return price;
	}
	public int getQuantity() {
		return quantity;
	}
	public Double getTotal() {
		return total;
	}
	
	public String getShipDate() {
		return shipDate;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public void setTotal() {
		this.total = this.price * this.quantity;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
}
