package pl.djedrzejak.testing.account;

public class Account {

	private boolean active;
	private Address defaultDeliveryAddress;
	
	public Account() {
		this.active = false;
	}
	
	public Account(Address defaultDeliveryAddress) {
		this();
		this.defaultDeliveryAddress = defaultDeliveryAddress;
		if(defaultDeliveryAddress != null) {
			activate();
		}
	}



	public void activate() {
		active = true;
	}

	public boolean isActive() {
		return active;
	}

	public Address getDefaultDeliveryAddress() {
		return defaultDeliveryAddress;
	}

	public void setDefaultDeliveryAddress(Address defaultDeliveryAddress) {
		this.defaultDeliveryAddress = defaultDeliveryAddress;
	}
	
}
