package com.mentorship.food_delivery_app.payment.model;

import com.mentorship.food_delivery_app.order.model.Order;
import com.mentorship.food_delivery_app.payment.enums.PaymentMethod;
import com.mentorship.food_delivery_app.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private long id ;
    @Column(name = "amount")
    private double amount ;
    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod method ;
    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status ;
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;


}