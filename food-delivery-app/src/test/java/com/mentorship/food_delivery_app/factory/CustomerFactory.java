package com.mentorship.food_delivery_app.factory;

import com.mentorship.food_delivery_app.customer.entity.Customer;
import com.mentorship.food_delivery_app.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerFactory {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer create() {

        Customer customer = new Customer();
        return customerRepository.saveAndFlush(customer);
    }
}
