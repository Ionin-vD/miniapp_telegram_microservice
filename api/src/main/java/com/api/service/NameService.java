package com.api.service;

import org.springframework.stereotype.Service;

import com.api.model.NameEntity;

@Service
public class NameService {

    public NameEntity registerUser(NameEntity user) {
        return new NameEntity();
    }

    public String loginUser(String login, String password) {
        return "";
    }
}
