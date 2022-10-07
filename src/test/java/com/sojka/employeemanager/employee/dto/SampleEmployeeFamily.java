package com.sojka.employeemanager.employee.dto;

import com.sojka.employeemanager.employee.domain.Family;

public interface SampleEmployeeFamily {

    default Family firstEmployeeWife() {
        return Family.builder()
                .id("1")
                .firstName("Jane")
                .secondName(null)
                .lastName("Swayze")
                .birthDate("1953-06-17")
                .kinship("spouse")
                .build();
    }

    default Family firstEmployeeChild() {
        return Family.builder()
                .id("1")
                .firstName("John")
                .secondName("Joshua")
                .lastName("Swayze")
                .birthDate("1980-06-12")
                .kinship("child")
                .build();
    }

    default Family secondEmployeeWife() {
        return Family.builder()
                .id("2")
                .firstName("Mary")
                .secondName("Jane")
                .lastName("Wayne")
                .birthDate("1921-12-25")
                .kinship("spouse")
                .build();
    }

    default Family newSecondEmployeeChild() {
        return Family.builder()
                .id("2")
                .firstName("Julia")
                .secondName(null)
                .lastName("Wayne")
                .birthDate("1946-08-01")
                .kinship("child")
                .build();
    }

}
