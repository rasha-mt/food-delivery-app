package com.mentorship.food_delivery_app.customer.service;

import com.mentorship.food_delivery_app.customer.dto.AddressDto;
import com.mentorship.food_delivery_app.customer.dto.requests.AddAddressRequest;
import com.mentorship.food_delivery_app.customer.dto.requests.UpdateAddressRequest;
import com.mentorship.food_delivery_app.customer.model.Address;
import com.mentorship.food_delivery_app.customer.model.Customer;
import com.mentorship.food_delivery_app.customer.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final CustomerService customerService;
    private final AddressRepository addressRepository;

    public AddressDto addAddress(
            AddAddressRequest request
    ) {

        Customer customer = customerService.getCustomer();
        Address address = Address.builder()
                .label(request.getLabel())
                .phoneNumber(request.getPhoneNumber())
                .city(request.getCity())
                .street(request.getStreet())
                .building(request.getBuilding())
                .customer(customer)
                .build();

        Address saved = addressRepository.save(address);

        return AddressDto.map(saved);
    }

    public List<AddressDto> getAddresses() {

        Customer customer = customerService.getCustomer();
        return addressRepository.findByCustomerId(customer.getId())
                .stream()
                .map(AddressDto::map)
                .toList();
    }

    public AddressDto updateAddress(
            UUID addressId,
            UpdateAddressRequest request
    ) {
        Customer customer = customerService.getCustomer();
        Address address = getOwnedAddress(
                customer.getId(),
                addressId
        );

        address.setLabel(request.getLabel());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setCity(request.getCity());
        address.setStreet(request.getStreet());
        address.setBuilding(request.getBuilding());

        return AddressDto.map(address);
    }

    public void deleteAddress(
            UUID addressId
    ) {
        Customer customer = customerService.getCustomer();
        Address address = getOwnedAddress(
                customer.getId(),
                addressId
        );

        addressRepository.delete(address);
    }



    private Address getOwnedAddress(
            UUID customerId,
            UUID addressId
    ) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow();

        if (!address.getCustomer()
                .getId()
                .equals(customerId)) {

            throw new RuntimeException(
                    "Address does not belong to customer"
            );
        }

        return address;
    }

}