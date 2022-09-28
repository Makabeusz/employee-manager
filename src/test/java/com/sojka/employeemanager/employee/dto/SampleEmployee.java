package com.sojka.employeemanager.employee.dto;

import com.sojka.employeemanager.employee.domain.Employee;

public interface SampleEmployee {

    default Employee firstEmployee() {
        return Employee.builder()
                .id("0")
                .firstName("Patrick")
                .secondName("Wayne")
                .lastName("Swayze")
                .birthDate("12.08.1952")
                .personalId("52081212346")
                .build();
    }

    default Employee secondEmployee() {
        return Employee.builder()
                .id("1")
                .firstName("John")
                .secondName(null)
                .lastName("Wayne")
                .birthDate("26.05.1907")
                .personalId("07052612356")
                .build();
    }

    default Employee thirdEmployee() {
        return Employee.builder()
                .id("2")
                .firstName("Calvin")
                .secondName("Cordozar")
                .lastName("Broadus")
                .birthDate("20.10.1971")
                .personalId("71102012346")
                .build();
    }

}