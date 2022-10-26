package com.sojka.employeemanager;

import com.sojka.employeemanager.employee.dto.SampleEmployeeDto;
import org.hamcrest.Matchers;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public interface ResultMatcherHelper extends SampleEmployeeDto{

    default ResultMatcher duplicateEmployeeMessage() {
        return content().string(Matchers.containsString("Such employee already exists"));
    }

    default ResultMatcher containsNewEmployee() {
        return content().string(Matchers.containsString("{\"firstName\":\"Antoine\",\"secondName\":null,\"lastName\":\"Rosaille\",\"birthDate\":\"1995-01-12\",\"personalId\":\"95011286532\"}"));
    }

    default ResultMatcher answerContains(String text) {
        return content().string(Matchers.containsString(text));
    }


}
