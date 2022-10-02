package com.sojka.employeemanager.infrastructure.education.controller;

import com.sojka.employeemanager.infrastructure.education.domain.service.EducationService;
import com.sojka.employeemanager.infrastructure.education.dto.EducationDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/education/{employee-id}")
@AllArgsConstructor
public class EducationController {

    private EducationService service;

    @GetMapping
    public ResponseEntity<List<EducationDto>> getAllDegrees(@PathVariable("employee-id") String id) {
        return ResponseEntity.ok(service.getEmployeeDegrees(id));
    }

    @GetMapping("/recent")
    public ResponseEntity<EducationDto> getMostRecentDegree(@PathVariable("employee-id") String id) {
        return ResponseEntity.ok(service.getEmployeeMostRecentDegree(id));
    }

}
