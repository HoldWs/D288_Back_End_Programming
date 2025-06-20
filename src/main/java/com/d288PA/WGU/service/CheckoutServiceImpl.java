package com.d288PA.WGU.service;

import com.d288PA.WGU.dao.CartItemRepository;
import com.d288PA.WGU.dao.CartRepository;
import com.d288PA.WGU.dao.CustomerRepository;
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

    private CartRepository cartRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CartRepository cartRepository, CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {
        Cart cart = purchase.getCart();
        Set<CartItem> cartItems = purchase.getCartItems();

        String trackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(trackingNumber);
        cart.setStatus(StatusType.ordered);
        cartItems.forEach(cartItem -> {
            cart.add(cartItem);
            cartItem.setCart(cart);

        });
            Customer customer = purchase.getCustomer();
            customer.add(cart);
            cartRepository.save(cart);
            customerRepository.save(customer);


            return new PurchaseResponse(trackingNumber);
    }
}
