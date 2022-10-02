package com.sojka.employeemanager.employee.utils;

import com.sojka.employeemanager.employee.domain.Education;
import com.sojka.employeemanager.employee.dto.EducationDto;

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
