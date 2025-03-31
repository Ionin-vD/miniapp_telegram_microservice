package com.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.model.User;
import com.api.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByChatId(Long chatId) {
        return userRepository.findByChatId(chatId);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAllByIdNot(1L);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long adminId) {
        return userRepository.findById(adminId);
    }
}
