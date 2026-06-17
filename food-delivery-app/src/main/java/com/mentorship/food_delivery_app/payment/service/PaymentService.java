package com.mentorship.food_delivery_app.payment.service;

import com.mentorship.food_delivery_app.common.exceptions.ResourceNotFoundException;
import com.mentorship.food_delivery_app.payment.model.PaymentTypeConfig;
import com.mentorship.food_delivery_app.payment.repository.PaymentTypeConfigRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentTypeConfigRepository paymentTypeConfigRepository;

    public PaymentTypeConfig getPaymentTypeById(Integer PaymentTypeId){
        return paymentTypeConfigRepository.findById(PaymentTypeId).orElseThrow(() ->
                new ResourceNotFoundException("Payment Config Type is not found"));
    }

}
