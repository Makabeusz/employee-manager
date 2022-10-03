package com.sojka.employeemanager.employee.utils;

import com.sojka.employeemanager.employee.domain.Family;
import com.sojka.employeemanager.employee.dto.FamilyDto;

public interface FamilyMapper {

    static Family toFamily(FamilyDto familyDto) {
        return Family.builder()
                .id(familyDto.getId())
                .firstName(familyDto.getFirstName())
                .secondName(familyDto.getSecondName())
                .lastName(familyDto.getLastName())
                .birthDate(familyDto.getBirthDate())
                .kinship(familyDto.getKinship())
                .build();
    }

    static FamilyDto toFamilyDto(Family family) {
        return FamilyDto.builder()
                .id(family.getId())
                .firstName(family.getFirstName())
                .secondName(family.getSecondName())
                .lastName(family.getLastName())
                .birthDate(family.getBirthDate())
                .kinship(family.getKinship())
                .build();
    }
}
