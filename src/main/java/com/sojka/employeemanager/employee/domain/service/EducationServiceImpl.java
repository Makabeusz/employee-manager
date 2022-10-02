package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.employee.domain.Education;
import com.sojka.employeemanager.employee.domain.repository.EducationRepository;
import com.sojka.employeemanager.employee.dto.EducationDto;
import com.sojka.employeemanager.employee.utils.EducationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EducationServiceImpl implements EducationService {

    private EducationRepository repository;

    @Override
    public List<EducationDto> getEmployeeDegrees(String number) {
        return repository.findAllDegrees(number).stream()
                .map(EducationMapper::toEducationDto)
                .collect(Collectors.toList());
    }

    @Override
    public EducationDto getEmployeeMostRecentDegree(String number) {
        Optional<Education> lastDegree = repository.findMostRecentDegree(number);
        return EducationMapper.toEducationDto(
                lastDegree.orElse(Education.builder().degree("No university degree.").build()));
    }
}
