package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Supplier {
	private int supplierID;
	private String companyName;
	private String contactFName;
	private String contactLName;
	private String contactTitle;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String phone;
	private String email;
	private String url;
	private Button button;
	
	public Supplier(int supplierID, String companyName, String contactFName, String contactLName, String contactTitle, 
			String address1, String address2, String city, String state, String postalCode, String country,
			String phone, String email, String url) {
		
		this.supplierID = supplierID;
		this.companyName = companyName;
		this.contactFName = contactFName;
		this.contactLName = contactLName;
		this.contactTitle =contactTitle;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
		this.phone = phone;
		this.email = email;
		this.url = url;

		
	}

	public Supplier(int supplierID, String companyName, String phone) {
		this.supplierID = supplierID;
		this.companyName = companyName;
		this.phone = phone;
		this.button = new Button("Edit");
		
		button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            
            public void handle(ActionEvent event) {

            	AdminPanel.editSupplierWindow(supplierID);

            }
        });
	}
	
	public Supplier () {
	}
	
	public int getSupplierID() {
		return supplierID;
	}
	
	public String getCompanyName() {
		return companyName;
	}


	public String getContactFName() {
		return contactFName;
	}

	public String getContactLName() {
		return contactLName;
	}
	
	public String getContactTitle() {
		return contactTitle;
	}
	
	public String getAddress1() {
		return address1;
	}
	
	public String getAddress2() {
		return address2;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getState() {
		return state;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	public String getCountry() {
		return country;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getUrl() {
		return url;
	}
	

	
	public Button getButton() {
		return button;
	}
	
	public void setButton(Button button) {
		this.button = button;
	}
	public void setPhone (String phone) {
		this.phone = phone;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}
}
