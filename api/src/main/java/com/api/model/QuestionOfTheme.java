package com.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "questions_of_themes")
public class QuestionOfTheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    private ThemeOfCourse theme;

    public QuestionOfTheme() {
    }

    public QuestionOfTheme(String title, ThemeOfCourse theme) {
        this.title = title;
        this.theme = theme;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ThemeOfCourse getTheme() {
        return theme;
    }

    public void setTheme(ThemeOfCourse theme) {
        this.theme = theme;
    }

}
