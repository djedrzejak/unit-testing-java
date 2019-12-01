package pl.djedrzejak.testing;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import pl.djedrzejak.testing.order.Order;

class MealTest {

	@Test
	void shouldReturnDiscountedPrice() {
		//given
		Meal meal = new Meal(35);
		
		//when
		int discountedPrice = meal.getDiscountedPrice(7);
		
		//then
		//assertEquals(28, discountedPrice);
		assertThat(discountedPrice, is(equalTo(28)));
	}
	
	@Test
	void referencesToTheSameObjectsShouldBeEqual() {
		//given
		Meal meal1 = new Meal(10);
		Meal meal2 = meal1;
		
		//then
		//assertSame(meal1, meal2);
		assertThat(meal1, sameInstance(meal2));
	}
	
	@Test
	void referencesToDifferentObjectsShouldNotBeEqual() {
		//given
		Meal meal1 = new Meal(10);
		Meal meal2 = new Meal(10);
		
		//then
		//assertNotSame(meal1, meal2);
		assertThat(meal1, not(sameInstance(meal2)));
	}
	
	@Test
	void twoMealsShouldBeEqualWhenPriceAndNameAreTheSame() {
		//given
		Meal meal1 = new Meal(10, "Pizza");
		Meal meal2 = new Meal(10, "Pizza");
		
		//then
		//assertEquals(meal1, meal2);
		assertThat(meal1, equalTo(meal2));
	}
	
	@Test
	void exceptionShouldBeThrownIsDiscountIsHigherThatThePrice() {
		// given
		Meal meal = new Meal(8, "Zupa");
		
		//then
		assertThrows(IllegalArgumentException.class, () -> meal.getDiscountedPrice(meal.getPrice()+1));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {5,10,15,18})
	void mealPricesShouldBeLowerThan20(int price) {
		MatcherAssert.assertThat(price, Matchers.lessThan(20));
	}

	@ParameterizedTest
	@MethodSource("createMealsWithNameAndPrice")
	void burgersShouldHaveCorrectNameAndPrice(String name, int price) {
		assertThat(name, containsString("burger"));
	}
	
	private static Stream<Arguments> createMealsWithNameAndPrice() {
		return Stream.of(
				Arguments.of("Hamburger", 10),
				Arguments.of("Cheeseburger", 12)
		);
	}

	@ParameterizedTest
	@MethodSource("createCakeNames")
	void cakeNamesShouldEndWithCake(String name) {
		assertThat(name, notNullValue());
		assertThat(name, endsWith("cake"));
	}
	
	private static Stream<String> createCakeNames() {
		List<String> cakeNames = Arrays.asList("Cheesecake", "Fruitcake", "Cupcake");
		return cakeNames.stream();
	}
	
	@TestFactory
	Collection<DynamicTest> dynamicTestCollection() {
		return Arrays.asList(
					dynamicTest("Dynamic test 1", () -> assertThat(5, lessThan(6))),
					dynamicTest("Dynamic test 2", () -> assertEquals(4, 2*2))
				);
	}
	
	@Tag("fries")
	@TestFactory
	Collection<DynamicTest> calculateMealPrices() {
		Order order = new Order();
		order.addMealToOrder(new Meal(10, 2, "Hamburger"));
		order.addMealToOrder(new Meal(7, 4, "Fries"));
		order.addMealToOrder(new Meal(22, 3, "Pizza"));
		Collection<DynamicTest> dynamicTests = new ArrayList<>();
		
		for(int i=0; i<order.getMeals().size(); i++) {
			int price = order.getMeals().get(i).getPrice();
			int quantity= order.getMeals().get(i).getQuantity();
			
			Executable executable = () -> {
				assertThat(calculatePrice(price, quantity), lessThan(70));
			};
			
			String name = "Test name : " + i;
			DynamicTest dynamicTest = DynamicTest.dynamicTest(name, executable);
			dynamicTests.add(dynamicTest);
		}
		
		return dynamicTests;
	}
	
	@Test
	void testMealSumPrice() {
		//given
		Meal meal = mock(Meal.class);
		
		given(meal.getPrice()).willReturn(15);
		given(meal.getQuantity()).willReturn(3);
		given(meal.sumPrice()).willCallRealMethod();
		
		//when
		int result = meal.sumPrice();
		
		//then
		assertThat(result, equalTo(45));
	}
	
	@Test
	void testMealSumPriceWithSpy() {
		//given
		Meal meal = new Meal(14, 4, "Burrito");
		Meal mealSpy = spy(meal);
		
		given(mealSpy.getPrice()).willReturn(15);
		given(mealSpy.getQuantity()).willReturn(3);
		
		//when
		int result = mealSpy.sumPrice();
		
		//then
		then(mealSpy).should().getPrice();
		then(mealSpy).should().getQuantity();
		assertThat(result, equalTo(45));
	}
	
	private int calculatePrice(int price, int quantity) {
		return price * quantity;
	}
}
