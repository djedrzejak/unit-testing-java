package pl.djedrzejak.testing.cart;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import pl.djedrzejak.testing.order.Order;
import pl.djedrzejak.testing.order.OrderStatus;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class CartServiceTest {
	
	@InjectMocks
	private CartService cartService;
	@Mock
	private CartHandler cartHandler;
	@Captor
	ArgumentCaptor<Cart> argumentCaptor;
	
	private Order order;
	private Cart cart;
	
	@BeforeEach
	void init() {
		order = new Order();
		cart = new Cart();
		cart.addOrderToCart(order);
	}
	
	@Test
	void processCartShouldSentToPrepare() {
		//given
		given(cartHandler.canHandleCart(cart)).willReturn(true);
		
		//when
		Cart resultCart = cartService.processCart(cart);
		
		//then
		verify(cartHandler).sendToPrepare(resultCart);
		//to co wyzej ale w innej formie
		then(cartHandler).should().sendToPrepare(resultCart);
		
		assertThat(resultCart.getOrders(), hasSize(1));
		assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
		
		InOrder inOrder = inOrder(cartHandler);
		inOrder.verify(cartHandler).canHandleCart(resultCart);
		inOrder.verify(cartHandler).sendToPrepare(resultCart);
	}

	@Test
	void processCartShouldBeRejected() {
		//given
		given(cartHandler.canHandleCart(cart)).willReturn(false);
		
		//when
		Cart resultCart = cartService.processCart(cart);
		
		//then
		verify(cartHandler, never()).sendToPrepare(resultCart);
		then(cartHandler).should(never()).sendToPrepare(resultCart);
		assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
	}
	
	@Test
	void processCartShouldBeRejectedWithArgumentMatcher() {
		//given
		given(cartHandler.canHandleCart(any())).willReturn(false);
		
		//when
		Cart resultCart = cartService.processCart(cart);
		
		//then
		verify(cartHandler, never()).sendToPrepare(resultCart);
		then(cartHandler).should(never()).sendToPrepare(resultCart);
		assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));
	}
	
	@Test
	void processCartShouldSentToPrepareWithLambdas() {
		//given
		given(cartHandler.canHandleCart(argThat(c -> c.getOrders().size() > 0))).willReturn(true);
		
		//when
		Cart resultCart = cartService.processCart(cart);
		
		//then
		then(cartHandler).should().sendToPrepare(resultCart);	
		assertThat(resultCart.getOrders(), hasSize(1));
		assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));

	}
	
	@Test
	void canHandleCartShouldThrowException() {
		//given
		given(cartHandler.canHandleCart(cart)).willThrow(IllegalStateException.class);
		
		//then
		assertThrows(IllegalStateException.class, () -> cartService.processCart(cart));
	}
	
	@Test
	void processCartShouldSentToPrepareWithArgumentCaptor() {
		//given
		given(cartHandler.canHandleCart(cart)).willReturn(true);
		
		//when
		Cart resultCart = cartService.processCart(cart);
		
		//then
		//verify(cartHandler).sendToPrepare(argumentCaptor.capture());
		then(cartHandler).should().sendToPrepare(argumentCaptor.capture());
		
		assertThat(argumentCaptor.getValue().getOrders().size(), equalTo(1));
		
		assertThat(resultCart.getOrders(), hasSize(1));
		assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
	}
	
	@Test
	void shouldDoNothingWhenProcessCart() {
		//given
		given(cartHandler.canHandleCart(cart)).willReturn(true);
		willDoNothing().given(cartHandler).sendToPrepare(cart);
		
		//when
		Cart resultCart = cartService.processCart(cart);
		
		//then
		then(cartHandler).should().sendToPrepare(resultCart);
		assertThat(resultCart.getOrders(), hasSize(1));
		assertThat(resultCart.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));
	}

	@Test
	void deliveryShoulBeFree() {
		Cart cart2 = new Cart();
		cart2.addOrderToCart(new Order());
		cart2.addOrderToCart(new Order());
		cart2.addOrderToCart(new Order());
		
		given(cartHandler.isDeliveryFree(cart2)).willCallRealMethod();
		
		//when
		boolean isDeliveryFree = cartHandler.isDeliveryFree(cart2);
		
		//then
		assertTrue(isDeliveryFree);
	}
}
