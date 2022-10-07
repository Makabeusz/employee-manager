package com.sojka.employeemanager.employee.controller;

import com.sojka.employeemanager.employee.domain.service.FamilyService;
import com.sojka.employeemanager.employee.dto.FamilyDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employee/{employee-id}/family")
@AllArgsConstructor
public class FamilyController {

    private FamilyService service;

    @GetMapping
    public ResponseEntity<List<FamilyDto>> getAllFamily(@PathVariable("employee-id") String id) {
        return ResponseEntity.ok(service.getAllFamily(id));
    }

    @GetMapping("/children")
    public ResponseEntity<List<FamilyDto>> getAllChildren(@PathVariable("employee-id") String id) {
        return ResponseEntity.ok(service.getAllChildren(id));
    }

    @PostMapping
    public ResponseEntity<FamilyDto> addFamilyMember(@PathVariable("employee-id") String id, @RequestBody FamilyDto familyMember) {
        return ResponseEntity.ok(service.addFamilyMember(familyMember));
    }

    @GetMapping("/underage-children")
    public ResponseEntity<List<FamilyDto>> getAllUnderageChildren(@PathVariable("employee-id") String id, @RequestParam(defaultValue = "today") String date) {
        if (date.equals("today")) date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return ResponseEntity.ok(service.getAllUnderageChildren(id, date));
    }

}
