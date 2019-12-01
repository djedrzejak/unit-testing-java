package pl.djedrzejak.testing.cart;

import java.util.ArrayList;
import java.util.List;

import pl.djedrzejak.testing.Meal;
import pl.djedrzejak.testing.order.Order;

public class Cart {

	private List<Order> orders = new ArrayList<Order>();
	
	public void addOrderToCart(Order order) {
		orders.add(order);
	}
	
	public void clearCart() {
		orders.clear();
	}
	
	public void simulateLargeOrder() {
		for(int i=0; i<1000; i++) {
			Meal meal = new Meal(i%10, "Hamburger nr " + i+1);
			Order order = new Order();
			order.addMealToOrder(meal);
			addOrderToCart(order);
		}
		System.out.println("Cart size: " + orders.size());
		clearCart();
	}

	public List<Order> getOrders() {
		return orders;
	}
	
}
