package com.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.dto.QuestionOfThemeDto;
import com.api.dto.QuestionOfThemeDtoList;
import com.api.model.QuestionOfTheme;
import com.api.model.ThemeOfCourse;
import com.api.service.QuestionOfThemeService;
import com.api.service.ThemeOfCourseService;

@RestController
@RequestMapping("/api/mini_app")
public class QuestionOfThemeController {
    @Autowired
    private QuestionOfThemeService questionOfThemeService;
    @Autowired
    private ThemeOfCourseService themeOfCourseService;

    @PostMapping("/delete_question")
    public ResponseEntity<?> deleteQuestion(@RequestBody QuestionOfThemeDtoList request) {
        try {
            List<Long> ids = request.getIds();

            if (ids == null || ids.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }

            for (Long id : ids) {
                Optional<QuestionOfTheme> question = questionOfThemeService.findById(id);
                if (question.isPresent()) {
                    questionOfThemeService.delete(question.get());
                }
            }

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            System.err.println("Ошибка при удалении вопросов: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @PostMapping("/update_question_in_theme")
    public ResponseEntity<?> updateQuestionInTheme(@RequestBody QuestionOfThemeDto request) {
        try {
            if (request.getTitle() == null || request.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }
            Optional<QuestionOfTheme> questions = questionOfThemeService.findById(request.getId());
            if (questions.isPresent()) {
                QuestionOfTheme question = questions.get();
                if (request.getTitle() != null) {
                    question.setTitle(request.getTitle());
                }

                questionOfThemeService.updateQuestion(question);

                return ResponseEntity.ok("success");
            } else {
                return ResponseEntity.ok("users null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при обновления названия курса: " +
                    e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @PostMapping("/get_all_question_of_theme")
    public ResponseEntity<?> getAllQuestionsOfThemes(@RequestBody QuestionOfThemeDto request) {
        try {
            if (request.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }
            List<QuestionOfTheme> question = questionOfThemeService.findAllByThemeId(request.getId());
            if (!question.isEmpty()) {
                List<QuestionOfThemeDto> dtoList = question.stream()
                        .map(t -> new QuestionOfThemeDto(
                                t.getId(),
                                t.getTitle(),
                                t.getTheme().getId(),
                                t.getTheme().getTitle()))
                        .toList();
                return ResponseEntity.ok(dtoList);
            } else {
                return ResponseEntity.ok("question null");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получении всех вопросов по теме: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера");
        }
    }

    @PostMapping("/add_question_in_theme")
    public ResponseEntity<?> addQuestionInTheme(@RequestBody QuestionOfThemeDtoList request) {
        try {
            if (request.getTitleList() == null || request.getTitleList().isEmpty() || request.getThemeId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("body null");
            }

            Optional<ThemeOfCourse> themeOptional = themeOfCourseService.findById(request.getThemeId());
            if (themeOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Тема не найдена");
            }

            ThemeOfCourse theme = themeOptional.get();

            List<QuestionOfThemeDto> added = new ArrayList<>();

            for (String title : request.getTitleList()) {
                Optional<QuestionOfTheme> exists = questionOfThemeService.findOne(request.getThemeId(), title);
                if (exists.isPresent()) {
                    continue;
                }

                QuestionOfTheme entity = new QuestionOfTheme(title, theme);
                QuestionOfTheme saved = questionOfThemeService.save(entity);

                added.add(new QuestionOfThemeDto(saved.getId(), saved.getTheme().getId(), saved.getTitle()));
            }

            return ResponseEntity.ok(added);
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении вопросов: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }

}