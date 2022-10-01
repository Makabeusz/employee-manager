package com.sojka.employeemanager.infrastructure.education.domain;

import com.sojka.employeemanager.infrastructure.DomainObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Education implements DomainObject {

    private String id;
    private String degree;
    private String schoolName;
    private String address;
    private String startDate;
    private String finishDate;

    @Override
    public String getObjectId() {
        return this.id;
    }
}
