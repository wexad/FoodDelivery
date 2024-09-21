package com.wexad.BurgerHub.controller.admin;

import com.wexad.BurgerHub.dto.CompoundDTO;
import com.wexad.BurgerHub.dto.ProductDTO;
import com.wexad.BurgerHub.enums.Size;
import com.wexad.BurgerHub.handler.exceptions.ProductNotFoundException;
import com.wexad.BurgerHub.model.*;
import com.wexad.BurgerHub.service.*;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
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
            CompoundDTO compoundDTO = new CompoundDTO(weight, Size.valueOf(size), calories, fat, protein);
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

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


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

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateProduct(
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
            CompoundDTO compoundDTO = new CompoundDTO(weight, Size.valueOf(size), calories, fat, protein);
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

