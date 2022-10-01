package com.sojka.employeemanager.infrastructure.education.domain;

import com.sojka.employeemanager.infrastructure.education.dto.EducationDto;

public interface EducationMapper {

    static EducationDto toEducationDto(Education education) {
        return EducationDto.builder()
                .id(education.getId())
                .degree(education.getDegree())
                .schoolName(education.getSchoolName())
                .address(education.getAddress())
                .startDate(education.getStartDate())
                .finishDate(education.getFinishDate())
                .build();
    }

    static Education toEducation(EducationDto educationDto) {
        return Education.builder()
                .id(educationDto.getId())
                .degree(educationDto.getDegree())
                .schoolName(educationDto.getSchoolName())
                .address(educationDto.getAddress())
                .startDate(educationDto.getStartDate())
                .finishDate(educationDto.getFinishDate())
                .build();
    }


}
