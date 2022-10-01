package com.sojka.employeemanager.infrastructure.education.dto;

import com.sojka.employeemanager.infrastructure.education.domain.Education;

public interface SampleEducationDegree {

    default Education firstEmployeeBachelorDegree() {
        return Education.builder()
                .id("1")
                .degree("Bachelor")
                .schoolName("University of London")
                .address("Gower Street, London, WC1E 6BT")
                .startDate("2010-10-01")
                .finishDate("2013-07-02")
                .build();
    }

    default Education firstEmployeeMasterDegree() {
        return Education.builder()
                .id("1")
                .degree("Master")
                .schoolName("University of London")
                .address("Gower Street, London, WC1E 6BT")
                .startDate("2013-10-01")
                .finishDate("2015-06-29")
                .build();
    }

    default Education secondEmployeeSecondaryDegree() {
        return Education.builder()
                .id("2")
                .degree("Secondary")
                .schoolName("College of Lake County")
                .address("19351 W Washington St., Grayslake, IL, 60030-1198")
                .startDate("1990-10-01")
                .finishDate("2015-06-29")
                .build();
    }


}