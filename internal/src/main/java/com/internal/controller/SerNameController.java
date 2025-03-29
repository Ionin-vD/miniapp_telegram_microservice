package com.internal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SerNameController {

    @GetMapping("/save_internal")
    public ResponseEntity<String> saveName() {
        System.out.println("internal");
        return ResponseEntity.ok("Имя и фамилия сохранены");
    }
}
