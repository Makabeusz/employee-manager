package com.sojka.employeemanager.infrastructure.employee.dto;

import com.sojka.employeemanager.infrastructure.employee.domain.Employee;

import java.util.List;

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

    default Employee newEmployee() {
        return Employee.builder()
                .id("4")
                .firstName("Antoine")
                .secondName(null)
                .lastName("Rosaille")
                .birthDate("1995-01-12")
                .personalId("95011286532")
                .build();
    }

    default List<Employee> newEmployees() {
        return List.of(john(), hank(), luize());
    }

    default Employee john() {
        return Employee.builder()
                .id("5")
                .firstName("John")
                .secondName("George")
                .lastName("Wick")
                .birthDate("1995-06-25")
                .personalId("95062506875")
                .build();
    }

    default Employee hank() {
        return Employee.builder()
                .id("6")
                .firstName("Hank")
                .secondName(null)
                .lastName("Moore")
                .birthDate("1985-01-18")
                .personalId("85012465783")
                .build();
    }

    default Employee luize() {
        return Employee.builder()
                .id("7")
                .firstName("Luize")
                .secondName(null)
                .lastName("Jacquline")
                .birthDate("1988-08-16")
                .personalId("88061278314")
                .build();
    }

}