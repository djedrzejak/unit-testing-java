package pl.djedrzejak.testing.order;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.djedrzejak.testing.Meal;
import pl.djedrzejak.testing.order.Order;

class OrderTest {

	private Order order;
	
	@BeforeEach
	void initializeOrder() {
		System.out.println("Jakiś fajny komunikat");
		order = new Order();
	}
	
	@AfterEach
	void cleanUp() {
		System.out.println("After each");
		order.cancel();
	}
	
	@Test
	void testAssertArrayEquals() {
		//given
		int[] ints1 = {1,2,3};
		int[] ints2 = {1,2,3};
		
		//then
		assertArrayEquals(ints1, ints2);
	}

	@Test
	void mealListShouldBeEmptyAfterCreationOfOrder() {
		
		//then
		assertThat(order.getMeals(), empty());
		assertThat(order.getMeals().size(), equalTo(0));
		assertThat(order.getMeals(), hasSize(0));
		assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
	}
	
	@Test
	void addingMealToOrderShouldIncreaseOrderSize() {
		//given
		Meal meal = new Meal(15, "Burger");
		
		//when
		order.addMealToOrder(meal);
		
		//then
		assertThat(order.getMeals(), hasSize(1));
		assertThat(order.getMeals(), contains(meal));
		assertThat(order.getMeals(), hasItem(meal));
		assertThat(order.getMeals().get(0).getPrice(), equalTo(15));
	}
	
	@Test
	void removingMealFromOrderShouldDecreaseOrderSize() {
		//given
		Meal meal = new Meal(15, "Burger");
		
		//when
		order.addMealToOrder(meal);
		order.removeMealFromOrder(meal);
		
		//then
		assertThat(order.getMeals(), hasSize(0));
		assertThat(order.getMeals(), not(contains(meal)));
	}
	
	@Test
	void mealsShouldBeInCorrectOrderAfterAddingThemToOrder() {
		//given
		Meal meal1 = new Meal(15, "Burger");
		Meal meal2 = new Meal(25, "Pizza");
		
		//when
		order.addMealToOrder(meal1);
		order.addMealToOrder(meal2);
		
		//then
		assertThat(order.getMeals(), contains(meal1, meal2)); //sprawdza kolejnosc
		assertThat(order.getMeals(), containsInAnyOrder(meal2, meal1));
	}
	
	@Test
	void testIfTwoMealListsAreTheSame() {
		//given
		Meal meal1 = new Meal(15, "Burger");
		Meal meal2 = new Meal(25, "Pizza");

		List<Meal> meals1 = Arrays.asList(meal1, meal2);
		List<Meal> meals2 = Arrays.asList(meal1, meal2);
		
		//then
		assertThat(meals1, is(meals2));
	}
	
}
