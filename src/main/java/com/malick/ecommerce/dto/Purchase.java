package com.malick.ecommerce.dto;

import com.malick.ecommerce.entity.Customer;
import com.malick.ecommerce.entity.Address;
import com.malick.ecommerce.entity.Order;
import com.malick.ecommerce.entity.OrderItem;

import java.util.Set;

public record Purchase(
        Customer customer,
        Address shippingAddress,
        Address billingAddress,
        Order order,
        Set<OrderItem> orderItems
) {
}
