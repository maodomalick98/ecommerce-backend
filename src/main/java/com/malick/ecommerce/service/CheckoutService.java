package com.malick.ecommerce.service;

import com.malick.ecommerce.dto.PaymentData;
import com.malick.ecommerce.dto.Purchase;
import com.malick.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {

    PurchaseResponse validateOrder(Purchase purchase);
    PaymentIntent createPaymentIntent(PaymentData paymentData) throws StripeException;
}
