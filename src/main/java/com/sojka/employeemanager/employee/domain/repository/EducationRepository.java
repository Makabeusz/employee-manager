package com.sojka.employeemanager.employee.domain.repository;

import com.sojka.employeemanager.employee.domain.Education;

import java.util.List;
import java.util.Optional;

public interface EducationRepository {

    List<Education> findAllDegrees(String id);

    Optional<Education> findMostRecentDegree(String id);
}
