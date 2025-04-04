package com.api.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/get_title_theme_of_id")
    public ResponseEntity<?> getTitleThemeOfId(@RequestBody ThemeOfCourseDto request) {
        try {
            if (request.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }
            Optional<ThemeOfCourse> themes = themeOfCourseService.findById(request.getId());
            if (themes.isPresent()) {
                ThemeOfCourseDto response = new ThemeOfCourseDto(
                        themes.get().getId(),
                        themes.get().getCourse().getId(),
                        themes.get().getTitle());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.ok("themes null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получении всех тем по курсу: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @PostMapping("/delete_theme")
    public ResponseEntity<?> deleteTheme(@RequestBody ThemeOfCourseDto request) {
        try {
            if (request.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }
            Optional<ThemeOfCourse> themeOfCourseOptional = themeOfCourseService.findById(request.getId());
            if (themeOfCourseOptional.isPresent()) {
                ThemeOfCourse theme = themeOfCourseOptional.get();

                themeOfCourseService.delete(theme);

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
    public ResponseEntity<?> getAllThemesInCourses(@RequestBody ThemeOfCourseDto request) {
        try {
            if (request.getCourseId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }
            List<ThemeOfCourse> themes = themeOfCourseService.findByCourseId(request.getCourseId());
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

    @PostMapping("/find_themes_title_by_ids")
    public ResponseEntity<?> findThemesTitleByIds(@RequestBody Map<String, List<Long>> request) {
        List<Long> ids = request.get("ids");
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ids is null");
        }

        List<ThemeOfCourse> themes = themeOfCourseService.findAllByIds(ids);
        Map<Long, String> response = themes.stream()
                .collect(Collectors.toMap(ThemeOfCourse::getId, ThemeOfCourse::getTitle));

        return ResponseEntity.ok(response);
    }

}
