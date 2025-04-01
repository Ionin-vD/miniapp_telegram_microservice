package com.api.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.CourseDto;
import com.api.dto.CoursesOfUsersDto;
import com.api.dto.ThemeOfCourseDto;
import com.api.model.Course;
import com.api.model.CoursesOfUsers;
import com.api.model.ThemeOfCourse;
import com.api.model.User;
import com.api.service.CourseService;
import com.api.service.CoursesOfUsersService;
import com.api.service.UserService;

@RestController
@RequestMapping("/api/mini_app")
public class CoursesOfUsersController {
    @Autowired
    private CoursesOfUsersService coursesOfUsersService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;

    @PostMapping("/get_courses_where_user_auth")
    public ResponseEntity<?> getCoursesWhereUserAuth(@RequestBody CoursesOfUsersDto request) {
        try {
            if (request.getUserId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body is null");
            }

            List<CoursesOfUsers> list = coursesOfUsersService.findAllByUserIdAndIsAuth(request.getUserId());
            if (list.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("courses is null");
            }

            List<Map<String, Object>> result = list.stream()
                    .map(cu -> {
                        Course course = cu.getCourse();
                        User admin = course.getAdmin();

                        return Map.of(
                                "id", cu.getId(),
                                "user_id", cu.getUser().getId(),
                                "course", Map.of(
                                        "id", course.getId(),
                                        "title", course.getTitle(),
                                        "admin", Map.of(
                                                "id", admin.getId(),
                                                "fio", admin.getFio())));
                    })
                    .toList();

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            System.err
                    .println("Ошибка при получении всех курсов в которых пользователь авторизован: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }

    @PostMapping("/delete_user_in_course")
    public ResponseEntity<?> deleteUserInCourse(@RequestBody CoursesOfUsersDto request) {
        try {
            if (request.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }
            Optional<CoursesOfUsers> courseOfUsersOptional = coursesOfUsersService.findById(request.getId());
            if (courseOfUsersOptional.isPresent()) {
                CoursesOfUsers user = courseOfUsersOptional.get();

                coursesOfUsersService.delete(user);

                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.ok("user null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при юзера с курса: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @PostMapping("/check_auth_user_in_course")
    public ResponseEntity<?> checkAuthUserInCourse(@RequestBody CoursesOfUsersDto request) {
        try {
            if (request.getCourseId() == null || request.getUserId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }

            Optional<User> userOptional = userService.findById(request.getUserId());
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
            }

            Optional<Course> courseOptional = courseService.findById(request.getCourseId());
            if (courseOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Курс не найден");
            }

            Optional<CoursesOfUsers> existingOpt = coursesOfUsersService.findOne(request.getUserId(),
                    request.getCourseId());
            if (existingOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Запись не найдена");
            }
            CoursesOfUsers entity = existingOpt.get();

            CoursesOfUsersDto response = new CoursesOfUsersDto(
                    entity.isAuthInCourse());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Ошибка при изменение аутентификации: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }

    @PostMapping("/change_auth_user_in_course")
    public ResponseEntity<?> changeAuthUserInCourse(@RequestBody CoursesOfUsersDto request) {
        try {
            if (request.getCourseId() == null || request.getUserId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }

            Optional<User> userOptional = userService.findById(request.getUserId());
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
            }

            Optional<Course> courseOptional = courseService.findById(request.getCourseId());
            if (courseOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Курс не найден");
            }

            Optional<CoursesOfUsers> existingOpt = coursesOfUsersService.findOne(request.getUserId(),
                    request.getCourseId());
            if (existingOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Запись не найдена");
            }
            CoursesOfUsers entity = existingOpt.get();
            entity.setAuthInCourse(!entity.isAuthInCourse());
            CoursesOfUsers saved = coursesOfUsersService.save(entity);

            CoursesOfUsersDto response = new CoursesOfUsersDto(
                    saved.getId(),
                    saved.getUser().getId(),
                    saved.getCourse().getId(),
                    saved.isAuthInCourse());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Ошибка при изменение аутентификации: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }

    @PostMapping("/get_all_users_in_course")
    public ResponseEntity<?> getAllUserInCourses(@RequestBody CourseDto request) {
        try {
            Long course_id = request.getId();
            List<CoursesOfUsers> themes = coursesOfUsersService.findAllByCourseId(course_id);
            if (!themes.isEmpty()) {
                List<CoursesOfUsersDto> dtoList = themes.stream()
                        .map(t -> new CoursesOfUsersDto(t.getId(),
                                t.getUser().getId(),
                                t.getCourse().getId(),
                                t.isAuthInCourse(),
                                t.getUser().getFio(),
                                t.getUser().isAdmin(),
                                t.getUser().isAuth()))
                        .toList();
                return ResponseEntity.ok(dtoList);
            } else {
                return ResponseEntity.ok("users null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получении всех пользователей по курсу: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @PostMapping("/add_user_in_course")
    public ResponseEntity<?> addUserInCourse(@RequestBody CoursesOfUsersDto request) {
        try {
            if (request.getUserId() == null || request.getCourseId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }

            Optional<User> userOptional = userService.findById(request.getUserId());
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
            }

            Optional<Course> courseOptional = courseService.findById(request.getCourseId());
            if (courseOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Курс не найден");
            }

            Optional<CoursesOfUsers> existing = coursesOfUsersService.findOne(request.getUserId(),
                    request.getCourseId());
            if (existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Пользователь уже добавлен в курс");
            }

            CoursesOfUsers entity = new CoursesOfUsers(userOptional.get(), courseOptional.get());
            CoursesOfUsers saved = coursesOfUsersService.save(entity);

            CoursesOfUsersDto response = new CoursesOfUsersDto(
                    saved.getId(),
                    saved.getUser().getId(),
                    saved.getCourse().getId(),
                    saved.isAuthInCourse());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении пользователя в курс: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }

}
