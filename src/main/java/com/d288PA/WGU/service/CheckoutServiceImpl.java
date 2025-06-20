package com.d288PA.WGU.service;

import com.d288PA.WGU.dao.CartItemRepository;
import com.d288PA.WGU.dao.CartRepository;
import com.d288PA.WGU.entity.Cart;
import com.d288PA.WGU.entity.CartItem;
import com.d288PA.WGU.entity.Customer;
import com.d288PA.WGU.entity.StatusType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public CheckoutServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {
        Cart cart = purchase.getCart();
        Customer customer = purchase.getCustomer();
        Set<CartItem> cartItems = purchase.getCartItems();

        String orderTrackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(orderTrackingNumber);
        cart.setStatus(StatusType.ordered);
        cartItems.forEach(cartItem -> {
            cart.add(cartItem);
            cartItem.setCart(cart);

        });
            cartRepository.save(cart);

            return new PurchaseResponse(orderTrackingNumber);
    }
}
