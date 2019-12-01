package pl.djedrzejak.testing.order;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import pl.djedrzejak.testing.order.OrderStatus;

class OrderStatusTest {

	
	@ParameterizedTest
	@EnumSource(OrderStatus.class)
	void allOrderStatusShouldBeShortenThan15Chars(OrderStatus orderStatus) {
		assertThat(orderStatus.toString().length(), lessThan(15));
	}

}
