package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.FeedbackOfCourse;

public interface FeedbackOfCourseRepository extends JpaRepository<FeedbackOfCourse, Long> {
}
