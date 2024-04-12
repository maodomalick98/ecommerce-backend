package com.malick.ecommerce.service;

import com.malick.ecommerce.dto.Purchase;
import com.malick.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse validateOrder(Purchase purchase);
}
