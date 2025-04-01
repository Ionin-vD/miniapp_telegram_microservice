package com.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.model.CoursesOfUsers;
import com.api.repository.CoursesOfUsersRepository;

@Service
public class CoursesOfUsersService {
    @Autowired
    private CoursesOfUsersRepository coursesOfUsersRepository;

    public List<CoursesOfUsers> findAllByCourseId(Long course_id) {
        return coursesOfUsersRepository.findByCourseId(course_id);
    }

    public CoursesOfUsers save(CoursesOfUsers coursesOfUsers) {
        return coursesOfUsersRepository.save(coursesOfUsers);
    }

    public Optional<CoursesOfUsers> findOne(Long usedId, Long courseId) {
        return coursesOfUsersRepository.findByCourseIdAndUserId(courseId, usedId);
    }

    public Optional<CoursesOfUsers> findById(Long id) {
        return coursesOfUsersRepository.findById(id);
    }

    public CoursesOfUsers update(CoursesOfUsers coursesOfUsers) {
        return coursesOfUsersRepository.save(coursesOfUsers);
    }

    public void delete(CoursesOfUsers user) {
        coursesOfUsersRepository.delete(user);
    }

    public List<CoursesOfUsers> findAllByUserIdAndIsAuth(Long id) {
        return coursesOfUsersRepository.findAllByUserIdAndAuthInCourse(id, true);
    }
}
