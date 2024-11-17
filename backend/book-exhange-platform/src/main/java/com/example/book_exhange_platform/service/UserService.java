package com.example.book_exhange_platform.service;

import com.example.book_exhange_platform.dto.UserResponseDTO;
import com.example.book_exhange_platform.model.User;
import com.example.book_exhange_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserResponseDTO registerUser(String username, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        User newUser = new User(username, email, encodedPassword);
        userRepository.save(newUser);
        UserResponseDTO userResponseDTO = new UserResponseDTO(newUser.getId(), newUser.getEmail(), newUser.getUsername());
        return userResponseDTO;
    }

    public UserResponseDTO validateUser(String email, String password) {

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            passwordEncoder.matches(password, user.get().getPassword());
            return new UserResponseDTO(user.get().getId(), user.get().getEmail(), user.get().getUsername());
        }
        return null;
    }

    public boolean resetPassword(String email, String newPassword, String oldPassword) {

        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent() && passwordEncoder.matches(oldPassword, user.get().getPassword())) {

            String encodedPassword = passwordEncoder.encode(newPassword);
            user.get().setPassword(encodedPassword);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

    // public Optional<User> updateUserProfile(Long userId, String username) {
    //     // Find the user by ID
    //     Optional<User> optionalUser = userRepository.findById(userId);

    //     if (optionalUser.isPresent()) {
    //         User user = optionalUser.get();
    //         user.setUsername(username);
    //         userRepository.save(user); // Save updated user
    //     }

    //     return optionalUser;
    // }
}