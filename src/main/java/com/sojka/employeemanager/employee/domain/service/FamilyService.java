package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.employee.dto.FamilyDto;

import java.util.List;

public interface FamilyService {
    List<FamilyDto> getAllFamily(String id);

    List<FamilyDto> getAllChildren(String id);

    FamilyDto addFamilyMember(FamilyDto familyMember);

    List<FamilyDto> getAllUnderageChildren(String id, String date);

    void deleteFamilyMember(FamilyDto familyMember);
}
