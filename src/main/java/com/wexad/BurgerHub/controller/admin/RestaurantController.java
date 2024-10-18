package com.wexad.BurgerHub.controller.admin;

import com.wexad.BurgerHub.dto.RestaurantDTO;
import com.wexad.BurgerHub.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/restaurant")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Restaurant Controller", description = "Manage restaurants in the system by the admin")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(summary = "Create a new restaurant", description = "Allows the admin to create a new restaurant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/")
    public ResponseEntity<String> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        restaurantService.save(restaurantDTO);
        return new ResponseEntity<>("Restaurant created successfully", HttpStatus.CREATED);
    }

    @Operation(summary = "Get all restaurants", description = "Retrieve a list of all restaurants.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurants retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No restaurants found")
    })
    @GetMapping("/")
    public List<RestaurantDTO> getAll() {
        return restaurantService.findAll();
    }

    @Operation(summary = "Get a restaurant by ID", description = "Retrieve the details of a specific restaurant by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> get(@PathVariable Long id) {
        try {
            restaurantService.find(id);
            return ResponseEntity.ok(restaurantService.find(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Delete a restaurant by ID", description = "Allows the admin to delete a restaurant by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            restaurantService.delete(id);
            return ResponseEntity.ok("Restaurant deleted successfully");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
        }
    }
    @Operation(summary = "Repair a restaurant by ID", description = "Allows the admin to repair a restaurant by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant repaired successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @DeleteMapping("/restore/{id}")
    public ResponseEntity<String> repair(@PathVariable Long id) {
        try {
            restaurantService.restore(id);
            return ResponseEntity.ok("Restaurant deleted successfully");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant not found");
        }
    }

    @Operation(summary = "Update a restaurant by ID", description = "Allows the admin to update the details of a restaurant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated successfully"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
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
