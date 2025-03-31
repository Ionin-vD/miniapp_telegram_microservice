package com.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.ThemeOfCourse;

public interface ThemeOfCourseRepository extends JpaRepository<ThemeOfCourse, Long> {
    List<ThemeOfCourse> findByCourseId(Long courseId);

    Optional<ThemeOfCourse> findByCourseIdAndTitle(Long courseId, String title);
}
