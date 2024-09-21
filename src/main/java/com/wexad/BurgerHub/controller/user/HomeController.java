package com.wexad.BurgerHub.controller.user;

import com.wexad.BurgerHub.controller.admin.UserController;
import com.wexad.BurgerHub.dto.*;
import com.wexad.BurgerHub.security.SessionUser;
import com.wexad.BurgerHub.service.AuthUserService;
import com.wexad.BurgerHub.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RestController
public class HomeController {


    private final AuthUserService authUserService;
    private final OrderService orderService;

    public HomeController(AuthUserService authUserService, SessionUser sessionUser, UserController userController, OrderService orderService) {
        this.authUserService = authUserService;
        this.orderService = orderService;
    }

    @GetMapping("/home")
    public UserDataDTO home() {
        return authUserService.getUserData();
    }

    @PostMapping(value = "/home", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveImage(@RequestParam("file") MultipartFile file) {
        authUserService.saveImage(file);
        return ResponseEntity.status(HttpStatus.CREATED).body("Image saved successfully");
    }

    @PutMapping("/home")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordDTO passwordDTO) {
        authUserService.resetPassword(passwordDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Password reset successfully");
    }

    @PutMapping("/home/address")
    public ResponseEntity<?> changeAddress(@RequestBody AddressDTO addressDTO) {
        authUserService.changeAddress(addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Address changed successfully");
    }
    @GetMapping("/orders")
    public Map<LocalDateTime, List<ArchiveDTO>> getOrders() {
        return orderService.getOrders();
    }
}
