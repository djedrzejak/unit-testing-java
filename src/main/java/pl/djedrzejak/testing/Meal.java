package pl.djedrzejak.testing;

public class Meal {

	private int price;
	private int quantity;
	private String name;

	public Meal() {
		
	}
	
	public Meal(int price) {
		this.price = price;
	}
	
	public Meal(int price, int quantity, String name) {
		this.price = price;
		this.quantity = quantity;
		this.name = name;
	}

	public Meal(int price, String name) {
		this.price = price;
		this.name = name;
	}

	public int getPrice() {
		return price;
	}
	
	public int getDiscountedPrice(int discount) {

		if(discount > price) {
			throw new IllegalArgumentException("Za duża zniżka");
		}
		
		return price - discount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + price;
		result = prime * result + quantity;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meal other = (Meal) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price != other.price)
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Meal [price=" + price + ", quantity=" + quantity + ", name=" + name + "]";
	}

	public int getQuantity() {
		return quantity;
	}
	
	public int sumPrice() {
		return getPrice() * getQuantity();
	}
	
}
