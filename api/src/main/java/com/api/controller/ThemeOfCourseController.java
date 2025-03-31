package com.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.CourseIdRequest;
import com.api.dto.ThemeOfCourseDto;
import com.api.model.Course;
import com.api.model.ThemeOfCourse;
import com.api.service.CourseService;
import com.api.service.ThemeOfCourseService;

@RestController
@RequestMapping("/api/mini_app")
public class ThemeOfCourseController {
    @Autowired
    private ThemeOfCourseService themeOfCourseService;
    @Autowired
    private CourseService courseService;

    @PostMapping("/update_theme_in_course")
    public ResponseEntity<?> updateThemeInCourse(@RequestBody ThemeOfCourseDto request) {
        try {
            if (request.getTitle() == null || request.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }
            Optional<ThemeOfCourse> themeOfCourseOptional = themeOfCourseService.findById(request.getId());
            if (themeOfCourseOptional.isPresent()) {
                ThemeOfCourse theme = themeOfCourseOptional.get();
                if (request.getTitle() != null) {
                    theme.setTitle(request.getTitle());
                }

                themeOfCourseService.updateTheme(theme);

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

    @PostMapping("/get_all_themes_in_course")
    public ResponseEntity<?> getAllThemesInCourses(@RequestBody CourseIdRequest request) {
        try {
            Long course_id = request.getCourse_id();
            List<ThemeOfCourse> themes = themeOfCourseService.findByCourseId(course_id);
            if (!themes.isEmpty()) {
                List<ThemeOfCourseDto> dtoList = themes.stream()
                        .map(t -> new ThemeOfCourseDto(
                                t.getId(),
                                t.getTitle(),
                                t.getCourse().getId(),
                                t.getCourse().getTitle()))
                        .toList();
                return ResponseEntity.ok(dtoList);
            } else {
                return ResponseEntity.ok("themes null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получении всех тем по курсу: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @PostMapping("/add_theme_in_course")
    public ResponseEntity<?> addThemeInCourse(@RequestBody ThemeOfCourseDto request) {
        try {
            if (request.getTitle() == null || request.getCourseId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }

            Optional<Course> courseOptional = courseService.findById(request.getCourseId());
            if (courseOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Курс не найден");
            }

            Optional<ThemeOfCourse> existing = themeOfCourseService.findOne(request.getCourseId(),
                    request.getTitle());
            if (existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Тема уже добавлен в курс");
            }

            ThemeOfCourse entity = new ThemeOfCourse(request.getTitle(), courseOptional.get());
            ThemeOfCourse saved = themeOfCourseService.save(entity);

            ThemeOfCourseDto response = new ThemeOfCourseDto(
                    saved.getId(),
                    saved.getCourse().getId(),
                    saved.getTitle());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении пользователя в курс: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }
}
