package com.malick.ecommerce.service;

import com.malick.ecommerce.dto.PaymentData;
import com.malick.ecommerce.dto.Purchase;
import com.malick.ecommerce.dto.PurchaseResponse;
import com.malick.ecommerce.entity.Customer;
import com.malick.ecommerce.entity.Order;
import com.malick.ecommerce.entity.OrderItem;
import com.malick.ecommerce.repository.CustomerRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               @Value("${stripe.key.secret}") String stripeSecretKey) {
        this.customerRepository = customerRepository;
        Stripe.apiKey = stripeSecretKey;
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
        String email = customer.getEmail();
        Customer existingCustomer = this.customerRepository.findByEmail(email);
        if (existingCustomer != null) {
            customer = existingCustomer;
        }
        customer.add(order);
        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingUuid);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentData paymentData) throws StripeException {
        List<String> paymentsMethodTypes = new ArrayList<>();
        paymentsMethodTypes.add("card");
        Map<String, Object> paymentIntentParams = new HashMap<>();
        paymentIntentParams.put("amount", paymentData.amount());
        paymentIntentParams.put("currency", paymentData.currency());
        paymentIntentParams.put("payment_method_types", paymentsMethodTypes);
        paymentIntentParams.put("receipt_email", paymentData.receiptEmail());
        paymentIntentParams.put("description", "Ecommerce purchase");
        return PaymentIntent.create(paymentIntentParams);
    }

    private String generateTrackingNumber() {
        return UUID.randomUUID().toString();
    }

}
