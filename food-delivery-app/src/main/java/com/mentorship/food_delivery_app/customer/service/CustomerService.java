package com.mentorship.food_delivery_app.customer.service;

import com.mentorship.food_delivery_app.common.exceptions.ResourceNotFoundException;
import com.mentorship.food_delivery_app.customer.model.Customer;
import com.mentorship.food_delivery_app.customer.repository.CustomerRepository;
import java.util.UUID;

import com.mentorship.food_delivery_app.payment.model.PaymentIntegrationType;
import com.mentorship.food_delivery_app.payment.model.PaymentTypeConfig;
import com.mentorship.food_delivery_app.payment.repository.PaymentRepository;
import com.mentorship.food_delivery_app.payment.service.PaymentService;
import com.mentorship.food_delivery_app.user.model.User;
import com.mentorship.food_delivery_app.user.repository.UserRepository;
import com.mentorship.food_delivery_app.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PaymentService paymentService;
    private final UserService userService;

    public Customer getCustomer() {

        UUID customerId =(UUID) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer not found"));
    }

    public void  addCustomerPreferredPaymentType(int paymentMethodId) {
        Customer customer = getCustomer();
        PaymentTypeConfig paymentTypeConfig = paymentService.getPaymentTypeById(paymentMethodId);
        customer.setPreferredPaymentMethod(paymentTypeConfig);
        customerRepository.save(customer);
    }

    public void  deactivateAccount(){
        Customer customer= getCustomer();

        User user=userService.getUser(customer.getUser().getId());
        user.setIsEnabled(false);
    }


}
