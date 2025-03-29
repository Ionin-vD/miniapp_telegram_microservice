package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.CoursesOfUsers;

public interface CoursesOfUsersRepository extends JpaRepository<CoursesOfUsers, Long> {
}
