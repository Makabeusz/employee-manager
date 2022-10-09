package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.employee.domain.Education;
import com.sojka.employeemanager.employee.domain.exceptions.DuplicatedEducationException;
import com.sojka.employeemanager.employee.domain.exceptions.NoEducationException;
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
                lastDegree.orElseThrow(() -> new NoEducationException(number)));
    }

    @Override
    public EducationDto addEmployeeDegree(EducationDto educationDto) {
        Education degree = EducationMapper.toEducation(educationDto);
        if (repository.exists(degree)) throw new DuplicatedEducationException(degree.toString());
        Education saved = repository.save(degree);
        return EducationMapper.toEducationDto(saved);
    }

    @Override
    public void deleteEmployeeDegree(EducationDto educationDto) {
        Education degree = EducationMapper.toEducation(educationDto);
        if (!repository.exists(degree)) throw new NoEducationException(educationDto.toString());
        repository.delete(degree);
    }
}
