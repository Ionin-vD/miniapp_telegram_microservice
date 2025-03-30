package com.api.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.api.model.User;
import com.api.repository.UserRepository;

@Component
public class InitData {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        Long predefinedChatId = 1327132620L;
        // Long predefinedChatId = 1003822400L;
        if (!userRepository.existsByChatId(predefinedChatId)) {
            User user = new User();
            user.setChatId(predefinedChatId);
            user.setFio("SUPER USER");
            user.setAdmin(true);
            user.setAuth(true);
            userRepository.save(user);
        }
    }
}
