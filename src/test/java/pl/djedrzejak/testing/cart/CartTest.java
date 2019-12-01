package pl.djedrzejak.testing.cart;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;

import pl.djedrzejak.testing.cart.Cart;
import pl.djedrzejak.testing.order.Order;
import pl.djedrzejak.testing.Meal;

class CartTest {

	@Test
	void simulateLargeOrder() {
		//given
		Cart cart = new Cart();
		
		//then
		assertTimeout(Duration.ofMillis(10), cart::simulateLargeOrder);
	}

	@Test
	void cartShouldNotBeEmptyAfterAddingOrderToCart() {
		//given
		Order order = new Order();
		Cart cart = new Cart();
		
		//when
		cart.addOrderToCart(order);
		
		//then
		assertThat(cart.getOrders(), anyOf(
				notNullValue(),
				hasSize(1),
				is(not(empty())),
				is(not(emptyCollectionOf(Order.class)))
		));
		
		assertThat(cart.getOrders(), allOf(
				notNullValue(),
				hasSize(1),
				is(not(empty())),
				is(not(emptyCollectionOf(Order.class)))
		));
		
		assertAll(
				() -> assertThat(cart.getOrders(), notNullValue()),
				() -> assertThat(cart.getOrders(), hasSize(1)),
				() -> {
					List<Meal> mealList = cart.getOrders().get(0).getMeals();
					assertThat(mealList, empty());
				}
		);
	}
}
