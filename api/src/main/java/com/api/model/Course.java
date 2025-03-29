package com.api.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CoursesOfUsers> users;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<FeedbackOfCourse> feedbacks;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<ThemeOfCourse> themes;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Schedule> schedule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CoursesOfUsers> getUsers() {
        return users;
    }

    public void setUsers(List<CoursesOfUsers> users) {
        this.users = users;
    }

    public List<FeedbackOfCourse> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackOfCourse> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<ThemeOfCourse> getThemes() {
        return themes;
    }

    public void setThemes(List<ThemeOfCourse> themes) {
        this.themes = themes;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

}
