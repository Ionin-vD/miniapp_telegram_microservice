package com.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.CourseDto;
import com.api.model.Course;
import com.api.model.User;
import com.api.service.CourseService;
import com.api.service.UserService;

@RestController
@RequestMapping("/api/mini_app")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;

    @PostMapping("/delete_course")
    public ResponseEntity<?> deleteCourse(@RequestBody CourseDto request) {
        try {
            if (request.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }
            Optional<Course> courseOptional = courseService.findById(request.getId());
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();

                courseService.delete(course);

                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.ok("course null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при удалении курса: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @GetMapping("/get_all_courses")
    public ResponseEntity<?> getAllCourse() {
        try {
            List<Course> courses = courseService.findAll();
            if (!courses.isEmpty()) {
                List<CourseDto> userDtos = courses.stream()
                        .map(course -> new CourseDto(
                                course.getId(),
                                course.getAdmin().getId(),
                                course.getAdmin().getFio(),
                                course.getTitle()))
                        .toList();

                return ResponseEntity.ok(userDtos);
            } else {
                return ResponseEntity.ok("courses null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получении всех курсов: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @PostMapping("/add_course")
    public ResponseEntity<?> addCourse(@RequestBody CourseDto request) {
        try {
            if (request.getId() == null || request.getTitle() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }

            Optional<User> userOptional = userService.findById(request.getId());
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
            }

            User admin = userOptional.get();

            Optional<Course> existingCourse = courseService.findOne(request.getAdminId(), request.getTitle());
            if (existingCourse.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Курс уже существует");
            }

            Course savedCourse = courseService.save(new Course(admin, request.getTitle()));

            CourseDto res = new CourseDto(savedCourse.getId(), savedCourse.getAdmin().getId(), savedCourse.getTitle());

            return ResponseEntity.ok(res);
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении курса: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }

    @PostMapping("/update_title_course")
    public ResponseEntity<?> updateTitleCourse(@RequestBody CourseDto request) {
        try {
            if (request.getTitle() == null || request.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }
            Optional<Course> courseOptional = courseService.findById(request.getId());
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();
                if (request.getTitle() != null) {
                    course.setTitle(request.getTitle());
                }

                courseService.updateCourse(course);

                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.ok("users null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при обновления названия курса: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

}
