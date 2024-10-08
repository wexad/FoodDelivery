package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.model.Address;
import com.wexad.BurgerHub.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void save(Address address) {
        addressRepository.save(address);
    }
}
