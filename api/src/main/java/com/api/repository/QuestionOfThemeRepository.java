package com.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.QuestionOfTheme;

public interface QuestionOfThemeRepository extends JpaRepository<QuestionOfTheme, Long> {

    List<QuestionOfTheme> findAllByThemeId(Long themeId);

    Optional<QuestionOfTheme> findByTitleAndThemeId(String title, Long themeId);
}
