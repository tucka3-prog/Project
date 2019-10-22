package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class Customer {
	private int customerID;
	private String firstName;
	private String lastName;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String phone;
	private String email;
	private String password;
	private String username;
	private String access;
	private Button button;

	public Customer(int customerID, String username, String password) {

	}
	
	public Customer(int customerID, String access) {
		this.customerID = customerID;
		this.access = access;
	}

	public Customer(int customerID, String firstName, String lastName, String address1, String address2, String city,
			String state, String postalCode, String country, String phone, String email, String password,
			String username, String access) {
		this.customerID = customerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.country = country;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.username = username;
		this.access = access;
		this.button = new Button("Edit");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				AdminPanel.editCustomerWindow(customerID);

			}
		});

	}

	public Customer(int customerID, String firstName, String lastName, String phone) {
		this.customerID = customerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.button = new Button("Edit");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				AdminPanel.editCustomerWindow(customerID);

			}
		});
	}

	public Customer() {

	}

	public int getCustomerID() {
		return customerID;
	}

	public String getAccess() {
		return access;
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

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public Button getButton() {
		return button;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAccess(String access) {
		this.access = access;
	}

}