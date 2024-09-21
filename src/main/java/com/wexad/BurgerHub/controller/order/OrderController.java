package com.wexad.BurgerHub.controller.order;

import com.wexad.BurgerHub.dto.OrderRequestDTO;
import com.wexad.BurgerHub.service.OrderService;
import com.wexad.BurgerHub.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class OrderController {

    private final ProductService productService;
    private final OrderService orderService;

    public OrderController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getProducts(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategoryId(categoryId));
    }

    @GetMapping
    public ResponseEntity<?> getProductsByCategoryId() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @PostMapping
    public ResponseEntity<?> order(@RequestBody OrderRequestDTO orders) {
        orderService.save(orders);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }
}
