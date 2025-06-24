package com.d288PA.WGU.service;


import com.d288PA.WGU.dao.CartItemRepository;
import com.d288PA.WGU.dao.CartRepository;
import com.d288PA.WGU.dao.CustomerRepository;
import com.d288PA.WGU.dao.ExcursionRepository;
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
    private CartItemRepository cartItemRepository;



    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, ExcursionRepository excursionRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {
        Cart cart = purchase.getCart();

        String trackingNumber = UUID.randomUUID().toString();
        cart.setOrderTrackingNumber(trackingNumber);

        Set<CartItem> cartItems = purchase.getCartItems();
        cartItems.forEach(item -> item.setCart(cart));
        cart.setCartItem(cartItems);

//        Customer customer = purchase.getCustomer();
//        cart.setCustomer(customer);
//        customer.add(cart);


        cart.setStatus(StatusType.ordered);
        cartRepository.save(cart);
        // customerRepository.save(customer);




        return new PurchaseResponse(trackingNumber);
    }
}
