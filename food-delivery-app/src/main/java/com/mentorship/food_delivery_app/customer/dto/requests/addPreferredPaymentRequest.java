package com.mentorship.food_delivery_app.customer.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class addPreferredPaymentRequest{
        @NotNull
        int payment_type_id;
}
