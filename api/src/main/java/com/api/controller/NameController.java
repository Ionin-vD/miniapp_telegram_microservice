package com.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NameController {

    @GetMapping("/save")
    public ResponseEntity<String> saveName() {
        System.out.println("api");
        return ResponseEntity.ok("Имя и фамилия сохранены");
    }
}
