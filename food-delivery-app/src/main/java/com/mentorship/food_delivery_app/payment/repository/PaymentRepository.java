package com.mentorship.food_delivery_app.payment.repository;

import com.mentorship.food_delivery_app.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
