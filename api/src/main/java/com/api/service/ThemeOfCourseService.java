package com.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.model.ThemeOfCourse;
import com.api.repository.ThemeOfCourseRepository;

@Service
public class ThemeOfCourseService {
    @Autowired
    private ThemeOfCourseRepository themeOfCourseRepository;

    public List<ThemeOfCourse> findAll() {
        return themeOfCourseRepository.findAll();
    }

    public List<ThemeOfCourse> findByCourseId(Long courseId) {
        return themeOfCourseRepository.findByCourseId(courseId);
    }

    public Optional<ThemeOfCourse> findOne(Long courseId, String title) {
        return themeOfCourseRepository.findByCourseIdAndTitle(courseId, title);
    }

    public ThemeOfCourse save(ThemeOfCourse entity) {
        return themeOfCourseRepository.save(entity);
    }

    public Optional<ThemeOfCourse> findById(Long id) {
        return themeOfCourseRepository.findById(id);
    }

    public ThemeOfCourse updateTheme(ThemeOfCourse theme) {
        return themeOfCourseRepository.save(theme);
    }

    public void delete(ThemeOfCourse theme) {
        themeOfCourseRepository.delete(theme);
    }

    public List<ThemeOfCourse> findAllByIds(List<Long> ids) {
        return themeOfCourseRepository.findAllById(ids);
    }

}
