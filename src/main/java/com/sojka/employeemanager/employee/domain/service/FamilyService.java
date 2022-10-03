package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.employee.dto.FamilyDto;

import java.util.List;

public interface FamilyService {
    List<FamilyDto> getAllFamilyMembers(String id);
}
