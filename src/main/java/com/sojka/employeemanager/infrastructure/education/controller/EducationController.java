package com.sojka.employeemanager.infrastructure.education.controller;

import com.sojka.employeemanager.infrastructure.education.domain.service.EducationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/education")
@AllArgsConstructor
public class EducationController {

    private EducationService service;

//    @GetMapping
//    public ResponseEntity<List<EducationDto>> getAllDegrees() {
//        return ResponseEntity.of(service.getAllDegrees());
//    }

}
