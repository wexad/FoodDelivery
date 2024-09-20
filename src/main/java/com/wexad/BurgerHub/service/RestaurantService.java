package com.wexad.BurgerHub.service;

import com.wexad.BurgerHub.dto.RestaurantDTO;
import com.wexad.BurgerHub.handler.exceptions.RestaurantNotFoundException;
import com.wexad.BurgerHub.model.Address;
import com.wexad.BurgerHub.model.Restaurant;
import com.wexad.BurgerHub.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wexad.BurgerHub.mapper.RestaurantMapper.RESTAURANT_MAPPER;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AddressService addressService;

    public RestaurantService(RestaurantRepository restaurantRepository, AddressService addressService) {
        this.restaurantRepository = restaurantRepository;
        this.addressService = addressService;
    }

    public void save(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = RESTAURANT_MAPPER.toEntity(restaurantDTO);
        Address address = restaurant.getAddress();
        if (address != null) {
            addressService.save(address);
        }
        restaurantRepository.save(restaurant);
    }

    public List<RestaurantDTO> findAll() {
        return RESTAURANT_MAPPER.toDto(restaurantRepository.findAll());
    }

    public RestaurantDTO find(Long id) {
        return RESTAURANT_MAPPER.toDto(restaurantRepository.findById(id).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found")));
    }

    public void delete(Long id) {
        Restaurant existingRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with id: " + id));
        restaurantRepository.updateDeletedBy(existingRestaurant.getId());
    }

    public void update(Long id, RestaurantDTO restaurantDTO) {
        Restaurant existingRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with id: " + id));
        existingRestaurant.setName(restaurantDTO.name());
        existingRestaurant.setContactNumber(restaurantDTO.contactNumber());
        Address updatedAddress = RESTAURANT_MAPPER.toEntity(restaurantDTO.addressDTO());
        existingRestaurant.setAddress(updatedAddress);
        restaurantRepository.save(existingRestaurant);
    }
}
