package com.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.CoursesOfUsers;

public interface CoursesOfUsersRepository extends JpaRepository<CoursesOfUsers, Long> {

    List<CoursesOfUsers> findByCourseId(Long course_id);

    Optional<CoursesOfUsers> findByCourseIdAndUserId(Long usedId, Long courseId);

    List<CoursesOfUsers> findAllByUserIdAndAuthInCourse(Long userId, boolean authInCourse);
}
