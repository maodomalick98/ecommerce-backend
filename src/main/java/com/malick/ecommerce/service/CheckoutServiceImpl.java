package com.malick.ecommerce.service;

import com.malick.ecommerce.dto.Purchase;
import com.malick.ecommerce.dto.PurchaseResponse;
import com.malick.ecommerce.entity.Customer;
import com.malick.ecommerce.entity.Order;
import com.malick.ecommerce.entity.OrderItem;
import com.malick.ecommerce.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse validateOrder(Purchase purchase) {
        Order order = purchase.order();

        String orderTrackingUuid = this.generateTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingUuid);

        Set<OrderItem> orderItem = purchase.orderItems();
        orderItem.forEach(order::add);

        order.setBillingAddress(purchase.billingAddress());
        order.setShippindAddress(purchase.shippingAddress());

        Customer customer = purchase.customer();
        customer.add(order);
        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingUuid);
    }

    private String generateTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
