package com.mentorship.food_delivery_app.customer.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class UpdateAddressRequest {
    @NonNull
    String label;
    @NonNull
    String city;
    @NonNull
    String street;
    @NonNull
    String building;
    @NonNull
    String apartment;
    @NonNull
    String phoneNumber;

    String note;
}
