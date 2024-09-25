package com.wexad.BurgerHub.controller.order;

import com.wexad.BurgerHub.dto.*;
import com.wexad.BurgerHub.service.CategoryService;
import com.wexad.BurgerHub.service.OrderItemService;
import com.wexad.BurgerHub.service.OrderService;
import com.wexad.BurgerHub.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Tag(name = "Order Controller", description = "Handles product retrieval by category and order processing")
public class OrderController {

    private final ProductService productService;
    private final OrderService orderService;
    private final CategoryService categoryService;
    private final OrderItemService orderItemService;

    public OrderController(ProductService productService, OrderService orderService, CategoryService categoryService, OrderItemService orderItemService) {
        this.productService = productService;
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.orderItemService = orderItemService;
    }

    @Operation(summary = "Get Products by Category", description = "Fetches a list of products belonging to a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ProductDataDTO>> getProductsByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategoryId(categoryId));
    }

    @Operation(summary = "Place an Order", description = "Enter array of (productId and countSubmits) and cardNumber a new order based on the provided order details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order details provided")
    })
    @PostMapping
    public ResponseEntity<LoginDTO> order(@RequestBody CardDTO cardDTO) {
        orderService.save(cardDTO);
        List<CategoryDataDTO> categories = categoryService.findAll();
        List<ProductDataDTO> products = productService.getProducts();
        List<OrderItemDTO> orderItems = orderItemService.getOrderItems();
        return ResponseEntity.ok(new LoginDTO(null, categories, products, orderItems));
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

    @Operation(summary = "Add product to the order", description = "Adds a product to the current order by providing the product ID. Returns the updated list of order items.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product added successfully",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content)
    })
    @PostMapping("/addOrderItem/")
    public List<OrderItemDTO> addProduct(@RequestParam("productId") Long productId) {
        orderItemService.addProduct(productId);
        return orderItemService.getOrderItems();
    }
    @PostMapping("/minusOrderItem/")
    public List<OrderItemDTO> minusProduct(@RequestParam("productId") Long productId) {
        orderItemService.minusProduct(productId);
        return orderItemService.getOrderItems();
    }
}
