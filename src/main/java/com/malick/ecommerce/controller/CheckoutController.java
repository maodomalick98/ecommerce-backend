package com.malick.ecommerce.controller;


import com.malick.ecommerce.dto.PaymentData;
import com.malick.ecommerce.dto.Purchase;
import com.malick.ecommerce.dto.PurchaseResponse;
import com.malick.ecommerce.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse saveOrder(@RequestBody Purchase purchase) {
        return checkoutService.validateOrder(purchase);
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> PaymentIntent(@RequestBody PaymentData paymentData) throws StripeException {
        PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentData);
        return new ResponseEntity<>(paymentIntent.toJson(), HttpStatus.OK);
    }
}
