package com.mentorship.food_delivery_app.customer.dto;

import com.mentorship.food_delivery_app.customer.model.Address;

import java.util.UUID;

public record AddressDto(
        UUID id,
        String label,
        String city,
        String street,
        String building,
        String apartment,
        String phoneNumber

) {
    public static AddressDto map(
            Address address
    ) {

        return new AddressDto(
                address.getId(),
                address.getLabel(),
                address.getCity(),
                address.getStreet(),
                address.getBuilding(),
                address.getApartment(),
                address.getPhoneNumber()

        );
    }
}