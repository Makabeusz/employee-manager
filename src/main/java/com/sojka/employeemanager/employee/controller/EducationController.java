package com.sojka.employeemanager.employee.controller;

import com.sojka.employeemanager.employee.domain.Education;
import com.sojka.employeemanager.employee.domain.service.EducationService;
import com.sojka.employeemanager.employee.dto.EducationDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees/{employee-id}/education")
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

    @PostMapping
    public ResponseEntity<EducationDto> addNewDegree(@PathVariable("employee-id") String id, @RequestBody EducationDto educationDto) {
        return new ResponseEntity<>(service.addEmployeeDegree(educationDto), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDegree(@PathVariable("employee-id") String id, @RequestBody EducationDto educationDto) {
        service.deleteEmployeeDegree(educationDto);
        String message = "Employee with id " + id + " removed the degree: " + educationDto.toString();
        return ResponseEntity.ok(message);
    }

}
