package com.wexad.BurgerHub.controller.order;

import com.wexad.BurgerHub.dto.CategoryDataDTO;
import com.wexad.BurgerHub.dto.MenuDTO;
import com.wexad.BurgerHub.dto.OrderRequestDTO;
import com.wexad.BurgerHub.dto.ProductDTO;
import com.wexad.BurgerHub.service.CategoryService;
import com.wexad.BurgerHub.service.OrderService;
import com.wexad.BurgerHub.service.ProductService;
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
@RequestMapping("/category")
@Tag(name = "Order Controller", description = "Handles product retrieval by category and order processing")
public class OrderController {

    private final ProductService productService;
    private final OrderService orderService;
    private final CategoryService categoryService;

    public OrderController(ProductService productService, OrderService orderService, CategoryService categoryService) {
        this.productService = productService;
        this.orderService = orderService;
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get Products by Category", description = "Fetches a list of products belonging to a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<CategoryDataDTO> categories = categoryService.findAll();
        return ResponseEntity.ok(new MenuDTO(categories, productService.getProductsByCategoryId(categoryId)));
    }

    @Operation(summary = "Place an Order", description = "Enter array of (productId and countSubmits) and cardNumber a new order based on the provided order details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order details provided")
    })
    @PostMapping
    public ResponseEntity<?> order(@RequestBody OrderRequestDTO orders) {
        orderService.save(orders);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @Operation(summary = "Get Product Details", description = "Fetches the details of a specific product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
