package com.sojka.employeemanager.employee.dto;

import com.sojka.employeemanager.employee.domain.Employee;

public interface SampleEmployee {

    default Employee firstEmployee() {
        return Employee.builder()
                .id("1")
                .firstName("Patrick")
                .secondName("Wayne")
                .lastName("Swayze")
                .birthDate("1952-08-12")
                .personalId("52081212346")
                .build();
    }

    default Employee secondEmployee() {
        return Employee.builder()
                .id("2")
                .firstName("John")
                .secondName(null)
                .lastName("Wayne")
                .birthDate("1907-05-26")
                .personalId("07052612356")
                .build();
    }

    default Employee thirdEmployee() {
        return Employee.builder()
                .id("3")
                .firstName("Calvin")
                .secondName("Cordozar")
                .lastName("Broadus")
                .birthDate("1971-10-20")
                .personalId("71102012346")
                .build();
    }

}