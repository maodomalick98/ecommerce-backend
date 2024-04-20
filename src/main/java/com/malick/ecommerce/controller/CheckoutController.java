package com.malick.ecommerce.controller;


import com.malick.ecommerce.dto.Purchase;
import com.malick.ecommerce.dto.PurchaseResponse;
import com.malick.ecommerce.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
