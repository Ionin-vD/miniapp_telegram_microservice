package com.api.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    @Column(nullable = false)
    private String fio;

    @Column(columnDefinition = "boolean default false")
    private boolean isAdmin = false;
    @Column(columnDefinition = "boolean default false")
    private boolean isAuth = false;
    @Column(columnDefinition = "boolean default false")
    private boolean isDeleted = false;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Course> adminCourses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CoursesOfUsers> courseLinks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FeedbackOfCourse> feedbacks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Schedule> schedule;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Progress> progress;

    public User() {
    }

    public User(Long chat_id, String fio2) {
        this.chatId = chat_id;
        this.fio = fio2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean isAuth) {
        this.isAuth = isAuth;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<Course> getAdminCourses() {
        return adminCourses;
    }

    public void setAdminCourses(List<Course> adminCourses) {
        this.adminCourses = adminCourses;
    }

    public List<CoursesOfUsers> getCourseLinks() {
        return courseLinks;
    }

    public void setCourseLinks(List<CoursesOfUsers> courseLinks) {
        this.courseLinks = courseLinks;
    }

    public List<FeedbackOfCourse> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackOfCourse> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    public List<Progress> getProgress() {
        return progress;
    }

    public void setProgress(List<Progress> progress) {
        this.progress = progress;
    }

}
