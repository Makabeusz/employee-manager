package com.sojka.employeemanager.employee.domain.repository;

import com.sojka.employeemanager.employee.domain.Family;

import java.util.List;
import java.util.Optional;

public interface FamilyRepository {

    List<Family> findAllFamilyMembers(String id);

    List<Family> findAllChildren(String id);

    Family save(Family familyMember);

    Optional<Family> findFamilyMember(Family familyMember);

    boolean exists(Family familyMember);

    List<Family> findAllUnderageChildren(String id, String date);

    void deleteFamilyMember(Family familyMember);
}
