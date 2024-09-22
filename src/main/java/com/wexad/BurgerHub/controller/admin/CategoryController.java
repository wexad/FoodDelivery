package com.wexad.BurgerHub.controller.admin;

import com.wexad.BurgerHub.dto.CategoryDTO;
import com.wexad.BurgerHub.dto.CategoryDataDTO;
import com.wexad.BurgerHub.dto.ImageDTO;
import com.wexad.BurgerHub.service.CategoryService;
import com.wexad.BurgerHub.service.StorageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final StorageService storageService;
    private static final String CATEGORIES_URL = "category";

    public CategoryController(CategoryService categoryService, StorageService storageService) {
        this.categoryService = categoryService;
        this.storageService = storageService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createCategory(@RequestParam("categoryName") String categoryName,
                                                 @RequestParam("description") String description,
                                                 @RequestPart("image") MultipartFile imageFile) {
        try {
            String imagePath = storageService.uploadFile(imageFile, CATEGORIES_URL);
            CategoryDTO categoryDTO = new CategoryDTO(categoryName, description, new ImageDTO(imagePath), false);
            categoryService.saveCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully.");
        } catch (Exception e) {
            log.error("Error creating category: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create category: " + e.getMessage());
        }
    }


    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateCategory(@PathVariable Long id,
                                                 @RequestParam("categoryName") String categoryName,
                                                 @RequestParam("description") String description,
                                                 @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        CategoryDTO categoryDTO = null;
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = storageService.uploadFile(imageFile, CATEGORIES_URL);

                categoryDTO = new CategoryDTO(categoryName, description, new ImageDTO(imagePath), false);
            }
            categoryService.updateCategory(id, categoryDTO);
            return ResponseEntity.ok("Category updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update category.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        try {
            CategoryDTO category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDataDTO>> getCategory() {
        try {
            return ResponseEntity.ok(categoryService.findAll());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete category.");
        }
    }
}

