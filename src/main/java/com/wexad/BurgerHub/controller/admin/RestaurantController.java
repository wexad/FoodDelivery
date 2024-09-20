package com.wexad.BurgerHub.controller.admin;

import com.wexad.BurgerHub.dto.RestaurantDTO;
import com.wexad.BurgerHub.service.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/restaurant")
@PreAuthorize("hasRole('ADMIN')")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/")
    public ResponseEntity<String> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        restaurantService.save(restaurantDTO);
        return new ResponseEntity<>("Restaurant created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/")
    public List<RestaurantDTO> getAll() {
        return restaurantService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            restaurantService.find(id);
            return ResponseEntity.ok(restaurantService.find(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            restaurantService.delete(id);
            return ResponseEntity.ok("Restaurant deleted successfully");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRestaurant(@RequestBody RestaurantDTO restaurantDTO, @PathVariable Long id) {
        try {
            restaurantService.update(id, restaurantDTO);  // Delegate the update logic to the service layer
            return ResponseEntity.ok("Restaurant updated successfully");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
        }
    }

}
