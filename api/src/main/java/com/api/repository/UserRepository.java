package com.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByChatId(Long chatId);

    List<User> findAllByIdNot(Long id);

    boolean existsByChatId(Long predefinedChatId);
}
