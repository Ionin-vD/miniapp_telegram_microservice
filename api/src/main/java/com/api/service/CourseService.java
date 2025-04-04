package com.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.model.Course;
import com.api.model.User;
import com.api.repository.CourseRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> findOne(Long adminId, String title) {
        return courseRepository.findByAdminIdAndTitle(adminId, title);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    public Optional<User> findByAdminId(Long adminId) {
        return courseRepository.findByAdminId(adminId);
    }

    public void delete(Course course) {
        courseRepository.delete(course);
    }

}
