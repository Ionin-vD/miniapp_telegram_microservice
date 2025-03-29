package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
