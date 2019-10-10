package application;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Product {
	public int productID;
	public String productName;
	public int year;
	public String productDescription;
	public Double ranking;
	public Double unitPrice;
	public String discType;
	public String productAvailable;
	public int unitsInStock;
	public String sku;
	public String note;
	public int supplierID;
	public Button button;


	public Product(int productID, String productName, int year, String productDescription, Double ranking,
			Double unitPrice, String discType, String productAvailable, int unitsInStock, String sku, String note,
			int supplierID) {
		this.productID = productID;
		this.productName = productName;
		this.year = year;
		this.productDescription = productDescription;
		this.ranking = ranking;
		this.unitPrice = unitPrice;
		this.discType = discType;
		this.productAvailable = productAvailable;
		this.unitsInStock = unitsInStock;
		this.sku = sku;
		this.note = note;
		this.supplierID = supplierID;
		this.button = new Button("Edit");
		button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            
            public void handle(ActionEvent event) {

            	AdminPanel.editMovieWindow(productID);

            }
        });

	}
	
	public Product(int productID, String productName, int year, String productDescription, Double ranking,
			Double unitPrice, String discType, String productAvailable, int unitsInStock) {
		this.productID = productID;
		this.productName = productName;
		this.year = year;
		this.productDescription = productDescription;
		this.ranking = ranking;
		this.unitPrice = unitPrice;
		this.discType = discType;
		this.productAvailable = productAvailable;
		this.unitsInStock = unitsInStock;
		this.button = new Button("More info");
		button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            
            public void handle(ActionEvent event) {

            	UserPanel.orderScene(productID);

            }
        });
	}
	
	public Product () {
		
	}
	
	public Product(String productName) {
		this.productName = productName;
	}
	
	public Product(int productID, String productName, Double unitPrice, Double ranking) {
		this.productID = productID;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.ranking = ranking;
		this.button = new Button("More info");
		button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            
            public void handle(ActionEvent event) {

            	UserPanel.orderScene(productID);

            }
        });
	}
	
	public Product(int productID, String productName, Integer year) {
		this.productID = productID;
		this.productName = productName;
		this.year = year;

	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public double getRanking() {
		return ranking;
	}

	public void setRanking(double ranking) {
		this.ranking = ranking;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getDiscType() {
		return discType;
	}

	public void setDiscType(String discType) {
		this.discType = discType;
	}

	public String getProductAvailable() {
		return productAvailable;
	}

	public void setProductAvailable(String productAvailable) {
		this.productAvailable = productAvailable;
	}

	public int getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(int unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getSuppliedID() {
		return supplierID;
	}

	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}
	
	public void  setButton(Button button) {
		this.button = button;
	}
	
	public Button getButton(){
		return button;
	}

}
