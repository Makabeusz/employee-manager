package com.sojka.employeemanager.employee.domain.repository;

import com.sojka.employeemanager.employee.domain.Family;

import java.util.List;

public interface FamilyRepository {

    List<Family> findAllFamilyMembers(String id);

    List<Family> findAllChildren(String id);

    Family save(Family familyMember);

    Family findFamilyMember(Family familyMember);
}
