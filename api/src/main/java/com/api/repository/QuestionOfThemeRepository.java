package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.QuestionOfTheme;

public interface QuestionOfThemeRepository extends JpaRepository<QuestionOfTheme, Long> {
}
