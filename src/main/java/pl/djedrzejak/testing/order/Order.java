package pl.djedrzejak.testing.order;

import java.util.ArrayList;
import java.util.List;

import pl.djedrzejak.testing.Meal;

public class Order {

	private OrderStatus orderStatus;
	private List<Meal> meals = new ArrayList<>();
	
	public void addMealToOrder(Meal meal) {
		meals.add(meal);
	}
	
	public void removeMealFromOrder(Meal meal) {
		meals.remove(meal);
	}

	public List<Meal> getMeals() {
		return meals;
	}
	
	public void cancel() {
		meals.clear();
	}

	public void changeOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	@Override
	public String toString() {
		return "Order [meals=" + meals + "]";
	}
	
}
