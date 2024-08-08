package com.malick.ecommerce.dto;

public record PaymentData(
        int amount, String currency, String receiptEmail) {
}
