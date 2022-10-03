package com.sojka.employeemanager.employee.utils;

import com.sojka.employeemanager.employee.domain.Family;
import com.sojka.employeemanager.employee.dto.FamilyDto;

public interface FamilyMapper {

    static Family toFamily(FamilyDto employeeDto) {
        return Family.builder()
                .firstName(employeeDto.getFirstName())
                .secondName(employeeDto.getSecondName())
                .lastName(employeeDto.getLastName())
                .birthDate(employeeDto.getBirthDate())
                .kinship(employeeDto.getKinship())
                .build();
    }

    static FamilyDto toFamilyDto(Family family) {
        return FamilyDto.builder()
                .firstName(family.getFirstName())
                .secondName(family.getSecondName())
                .lastName(family.getLastName())
                .birthDate(family.getBirthDate())
                .kinship(family.getKinship())
                .build();
    }
}
