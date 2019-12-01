package pl.djedrzejak.testing.cart;

import pl.djedrzejak.testing.order.OrderStatus;

public class CartService {

	private CartHandler cartHandler;

	public CartService(CartHandler cartHandler) {
		this.cartHandler = cartHandler;
	}
	
	public Cart processCart(Cart cart) {
		OrderStatus status;
		
		if(cartHandler.canHandleCart(cart)) {
			cartHandler.sendToPrepare(cart);
			status = OrderStatus.PREPARING;
		} else {
			status = OrderStatus.REJECTED;
		}
		
		cart.getOrders().forEach(order -> {
			order.changeOrderStatus(status);
		});
		
		return cart;
	}
	
}
