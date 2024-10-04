package com.wexad.BurgerHub.controller.admin;

import com.wexad.BurgerHub.dto.CompoundDTO;
import com.wexad.BurgerHub.dto.ProductDTO;
import com.wexad.BurgerHub.handler.exceptions.ProductNotFoundException;
import com.wexad.BurgerHub.model.*;
import com.wexad.BurgerHub.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.wexad.BurgerHub.mapper.CompoundMapper.COMPOUND_MAPPER;

@Slf4j
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/product")
@Tag(name = "Admin Product Controller", description = "Manage products in the system by the admin")

public class ProductController {

    private final CategoryService categoryService;
    private final StorageService storageService;
    private static final String PRODUCTS_URL = "product";
    private final CompoundService compoundService;
    private final RestaurantService restaurantService;
    private final ProductService productService;

    public ProductController(CategoryService categoryService, StorageService storageService, CompoundService compoundService, RestaurantService restaurantService, ProductService productService) {
        this.categoryService = categoryService;
        this.storageService = storageService;
        this.compoundService = compoundService;
        this.restaurantService = restaurantService;
        this.productService = productService;
    }


    @Operation(summary = "Create a new product", description = "Allows the admin to create a new product by providing details and uploading an image.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "500", description = "Error creating product")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam Double weight,
            @RequestParam String size,
            @RequestParam Long calories,
            @RequestParam Long fat,
            @RequestParam Long protein,
            @RequestParam Long categoryId,
            @RequestParam Long restaurantId,
            @RequestPart("image") MultipartFile imageFile) {
        try {
            String imagePath = storageService.uploadFile(imageFile, PRODUCTS_URL);
            CompoundDTO compoundDTO = new CompoundDTO(weight, calories, fat, protein);
            Compound compound = compoundService.save(COMPOUND_MAPPER.toEntity(compoundDTO));
            Category category = categoryService.findById(categoryId);
            Restaurant restaurant = restaurantService.findById(restaurantId);
            Product product = new Product(name, description, price, compound, category, new Image(imagePath), restaurant);
            productService.saveProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully.");
        } catch (Exception e) {
            log.error("Error creating product: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating product.");
        }
    }


    @Operation(summary = "Get product details", description = "Retrieve details of a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Operation(summary = "Delete a product", description = "Allows the admin to delete a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Error deleting product")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully.");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }


    @Operation(summary = "Update a product", description = "Allows the admin to update a product's details by its ID. Optionally, a new image can be uploaded.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Error updating product")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProduct(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam Double weight,
            @RequestParam String size,
            @RequestParam Long calories,
            @RequestParam Long fat,
            @RequestParam Long protein,
            @RequestParam Long categoryId,
            @RequestParam Long restaurantId,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            Product existingProduct = productService.findById(id);
            existingProduct.setName(name);
            existingProduct.setDescription(description);
            existingProduct.setPrice(price);
            CompoundDTO compoundDTO = new CompoundDTO(weight,  calories, fat, protein);
            Compound updatedCompound = compoundService.save(COMPOUND_MAPPER.toEntity(compoundDTO));
            existingProduct.setCompound(updatedCompound);
            Category category = categoryService.findById(categoryId);
            existingProduct.setCategory(category);
            Restaurant restaurant = restaurantService.findById(restaurantId);
            existingProduct.setRestaurant(restaurant);
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = storageService.uploadFile(imageFile, PRODUCTS_URL);
                existingProduct.setImage(new Image(imagePath));
            }
            productService.saveProduct(existingProduct);
            return ResponseEntity.ok("Product updated successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        } catch (Exception e) {
            log.error("Error updating product: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product.");
        }
    }


}

