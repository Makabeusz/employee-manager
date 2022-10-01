package com.sojka.employeemanager;

import com.sojka.employeemanager.infrastructure.employee.dto.SampleEmployeeDto;
import org.hamcrest.Matchers;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public interface ResultMatcherHelper extends SampleEmployeeDto{

    default ResultMatcher duplicateEmployeeMessage() {
        return content().string(Matchers.containsString("Such employee already exists"));
    }

    default ResultMatcher containsNewEmployee() {
        return content().string(Matchers.containsString("{\"firstName\":\"Antoine\",\"secondName\":null,\"lastName\":\"Rosaille\",\"birthDate\":\"1995-01-12\",\"personalId\":\"95011286532\"}"));
    }

    default ResultMatcher conflictStatus() {
        return status().is(HttpStatus.CONFLICT.value());
    }

    default ResultMatcher createdStatus() {
        return status().is(HttpStatus.CREATED.value());
    }

    default ResultMatcher notFoundStatus() {
        return status().is(HttpStatus.NOT_FOUND.value());
    }

    default ResultMatcher badRequestStatus() {
        return status().is(HttpStatus.BAD_REQUEST.value());
    }
}
