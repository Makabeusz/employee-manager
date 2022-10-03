package com.sojka.employeemanager.employee.domain.service;

import com.sojka.employeemanager.employee.domain.exceptions.NoChildrenException;
import com.sojka.employeemanager.employee.domain.exceptions.NoFamilyException;
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
        List<FamilyDto> family = repository.findAllFamilyMembers(id).stream()
                .map(FamilyMapper::toFamilyDto)
                .collect(Collectors.toList());
        if (family.isEmpty()) throw new NoFamilyException(id);
        return family;
    }

    @Override
    public List<FamilyDto> getAllChildren(String id) {
        List<FamilyDto> children = repository.findAllChildren(id).stream()
                .map(FamilyMapper::toFamilyDto)
                .collect(Collectors.toList());
        if (children.isEmpty()) throw new NoChildrenException(id);
        return children;
    }
}
