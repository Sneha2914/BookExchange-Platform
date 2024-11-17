package com.example.book_exhange_platform.controller;

import com.example.book_exhange_platform.dto.UserResponseDTO;
import com.example.book_exhange_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        try {
            UserResponseDTO userResponseDTO = userService.registerUser(username, email, password);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestParam String email, @RequestParam String password) {

        if (email == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        UserResponseDTO userResponseDTO = userService.validateUser(email, password);

        if (userResponseDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String newPassword, @RequestParam String oldPassword) {

        if (email == null || oldPassword == null || newPassword == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        boolean passwordUpdated = userService.resetPassword(email, newPassword, oldPassword);

        if (passwordUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body("Password got updated");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password could not be updated");
        }
    }



    // @PutMapping("/{userId}/profile")
    // public ResponseEntity<?> updateUserProfile(
    //         @RequestParam Long userId, @RequestParam String username) {

    //     if (username == null) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required.");
    //     }

    //     return userService.updateUserProfile(userId, username)
    //             .map(updatedUser -> ResponseEntity.ok("User profile updated successfully."))
    //             .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found."));
    // }
}
