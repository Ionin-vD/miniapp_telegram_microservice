package com.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.model.Course;
import com.api.model.User;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c WHERE c.admin.id = :adminId AND c.title = :title")
    Optional<Course> findByAdminIdAndTitle(@Param("adminId") Long adminId, @Param("title") String title);

    Optional<User> findByAdminId(Long adminId);
}
