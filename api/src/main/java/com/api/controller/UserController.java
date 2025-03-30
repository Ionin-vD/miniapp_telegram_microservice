package com.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.dto.ChatIdRequest;
import com.api.dto.CreateUserRequest;
import com.api.dto.UserDto;
import com.api.model.User;
import com.api.service.UserService;

@RestController
@RequestMapping("/api/mini_app")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/check_auth")
    public ResponseEntity<?> checkAuthUser(@RequestBody ChatIdRequest request) {
        try {
            if (request.getChat_id() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }
            Optional<User> userOptional = userService.findByChatId(request.getChat_id());
            if (userOptional.isPresent()) {
                UserDto user = new UserDto(
                        userOptional.get().getId(),
                        userOptional.get().getChatId(),
                        userOptional.get().getFio(),
                        userOptional.get().isAdmin(),
                        userOptional.get().isAuth());

                Map<String, Object> response = new HashMap<>();
                response.put("id", user.getId());
                response.put("fio", user.getFio());
                response.put("isAdmin", user.isAdmin());
                response.put("isAuth", user.isAuth());

                if (user.isAuth()) {
                    return ResponseEntity.ok(response);
                } else {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("user null");
                }

            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("user null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получение всех пользователей: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @GetMapping("/get_all_user")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.findAll();
            if (!users.isEmpty()) {
                List<UserDto> userDtos = users.stream()
                        .map(user -> new UserDto(
                                user.getId(),
                                user.getChatId(),
                                user.getFio(),
                                user.isAdmin(),
                                user.isAuth()))
                        .toList();

                return ResponseEntity.ok(userDtos);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("users null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получение всех пользователей: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @PostMapping("/find_user_by_chat_id")
    public ResponseEntity<?> findUserByChatId(@RequestBody ChatIdRequest request) {
        try {
            if (request.getChat_id() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }
            Optional<User> userOptional = userService.findByChatId(request.getChat_id());
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                Map<String, Object> response = new HashMap<>();
                response.put("id", user.getId());
                response.put("fio", user.getFio());
                response.put("isAdmin", user.isAdmin());
                response.put("isAuth", user.isAuth());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("user null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при поиске пользователя по chat_id: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @PostMapping("/create_user")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        try {
            if (request.getChat_id() == null || request.getFio() == null || request.getFio().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }

            Optional<User> existingUser = userService.findByChatId(request.getChat_id());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь уже существует");
            }

            User user = userService.createUser(new User(request.getChat_id(), request.getFio()));
            if (user != null) {
                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка: пользователь не создан");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при регистрации пользователя: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }

}
