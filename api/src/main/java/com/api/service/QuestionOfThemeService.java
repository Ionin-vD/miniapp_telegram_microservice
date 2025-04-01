package com.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.model.QuestionOfTheme;
import com.api.repository.QuestionOfThemeRepository;

@Service
public class QuestionOfThemeService {
    @Autowired
    private QuestionOfThemeRepository questionOfThemeRepository;

    public List<QuestionOfTheme> findAllByThemeId(Long themeId) {
        return questionOfThemeRepository.findAllByThemeId(themeId);
    }

    public Optional<QuestionOfTheme> findById(Long id) {
        return questionOfThemeRepository.findById(id);
    }

    public QuestionOfTheme updateQuestion(QuestionOfTheme question) {
        return questionOfThemeRepository.save(question);
    }

    public void delete(QuestionOfTheme question) {
        questionOfThemeRepository.delete(question);
    }

    public Optional<QuestionOfTheme> findOne(Long themeId, String title) {
        return questionOfThemeRepository.findByTitleAndThemeId(title, themeId);
    }

    public QuestionOfTheme save(QuestionOfTheme entity) {
        return questionOfThemeRepository.save(entity);
    }

}
