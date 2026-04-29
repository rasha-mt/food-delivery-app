package com.mentorship.food_delivery_app.factory;

import com.mentorship.food_delivery_app.customer.entity.Customer;

import java.util.UUID;

public class CustomerFactory {
    public static Customer create() {

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());

        return customer;
    }
}
