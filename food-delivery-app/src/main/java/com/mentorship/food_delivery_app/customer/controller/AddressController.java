package com.mentorship.food_delivery_app.customer.controller;

import com.mentorship.food_delivery_app.customer.dto.AddressDto;
import com.mentorship.food_delivery_app.customer.dto.requests.AddAddressRequest;
import com.mentorship.food_delivery_app.customer.dto.requests.UpdateAddressRequest;
import com.mentorship.food_delivery_app.customer.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public AddressDto addAddress(
            @PathVariable Long customerId,
            @RequestBody AddAddressRequest request
    ) {
        return addressService.addAddress(request);
    }

    @GetMapping
    public List<AddressDto> getAddresses(
            @PathVariable Long customerId
    ) {
        return addressService.getAddresses();
    }

    @PutMapping("/{addressId}")
    public AddressDto updateAddress(
            @PathVariable UUID addressId,
            @RequestBody UpdateAddressRequest request
    ) {
        return addressService.updateAddress(
                addressId,
                request
        );
    }

    @DeleteMapping("/{addressId}")
    public void deleteAddress(
            @PathVariable UUID addressId
    ) {
        addressService.deleteAddress(addressId);
    }

}
