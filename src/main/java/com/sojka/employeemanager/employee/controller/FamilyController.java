package com.sojka.employeemanager.employee.controller;

import com.sojka.employeemanager.employee.domain.service.FamilyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee/{employee-id}/family")
@AllArgsConstructor
public class FamilyController {

    private FamilyService service;
}
