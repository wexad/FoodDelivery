package com.wexad.BurgerHub.controller.user;

import com.wexad.BurgerHub.controller.admin.UserController;
import com.wexad.BurgerHub.dto.*;
import com.wexad.BurgerHub.service.AuthUserService;
import com.wexad.BurgerHub.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Home Controller", description = "User management actions such as viewing profile, uploading image, and handling orders.")
public class HomeController {


    private final AuthUserService authUserService;
    private final OrderService orderService;

    public HomeController(AuthUserService authUserService, UserController userController, OrderService orderService) {
        this.authUserService = authUserService;
        this.orderService = orderService;
    }

    @Operation(summary = "Get user data", description = "Retrieve the current user's profile data including personal information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDataDTO.class))),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping("/home")
    public UserDataDTO home() {
        return authUserService.getUserData();
    }

    @Operation(summary = "Upload profile image", description = "Upload a new profile image for the current user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Image uploaded successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid file format or upload error")
    })
    @PostMapping(value = "/home", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveImage(@RequestParam("file") MultipartFile file) {
        authUserService.saveImage(file);
        return ResponseEntity.status(HttpStatus.CREATED).body("Image saved successfully");
    }

    @Operation(summary = "Reset password", description = "Allow the user to reset their password by providing the old and new password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Password reset successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Password validation error")
    })
    @PutMapping("/home")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordDTO passwordDTO) {
        authUserService.resetPassword(passwordDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Password reset successfully");
    }

    @Operation(summary = "Change address", description = "Update the user's address information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address changed successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid address information")
    })
    @PutMapping("/home/address")
    public ResponseEntity<String> changeAddress(@RequestBody AddressDTO addressDTO) {
        authUserService.changeAddress(addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Address changed successfully");
    }

    @Operation(summary = "Get user's past orders", description = "Retrieve the list of past orders made by the user along with order details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ArchiveDTO.class))),
            @ApiResponse(responseCode = "404", description = "No orders found")
    })
    @GetMapping("/orders")
    public Map<LocalDateTime, List<ArchiveDTO>> getOrders() {
        return orderService.getOrders();
    }
}
