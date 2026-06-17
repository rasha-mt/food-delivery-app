package com.mentorship.food_delivery_app.payment.repository;

import com.mentorship.food_delivery_app.payment.model.Payment;
import com.mentorship.food_delivery_app.payment.model.PaymentTypeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeConfigRepository extends JpaRepository<PaymentTypeConfig,Integer> {
}
