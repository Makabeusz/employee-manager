package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.employee.domain.Family;
import com.sojka.employeemanager.employee.domain.repository.FamilyRepository;
import com.sojka.employeemanager.employee.dto.FamilyDto;
import com.sojka.employeemanager.employee.utils.FamilyMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FamilyServiceImpl implements FamilyService {

    private FamilyRepository repository;

    @Override
    public List<FamilyDto> getAllFamily(String id) {
        return repository.findAllFamilyMembers(id).stream()
                .map(FamilyMapper::toFamilyDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyDto> getAllChildren(String id) {
        return repository.findAllChildren(id).stream()
                .map(FamilyMapper::toFamilyDto)
                .collect(Collectors.toList());
    }

    @Override
    public FamilyDto addFamilyMember(FamilyDto familyMember) {
        Family family = FamilyMapper.toFamily(familyMember);
        return FamilyMapper.toFamilyDto(
                repository.save(family));//TODO: integration test

    }
}
