package com.reliaquest.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatedEmployee {
    @JsonProperty("data")
    Employee employee;
    @JsonProperty("status")
    String status;

    public CreatedEmployee() {
    }

    public CreatedEmployee(Employee employee, String status) {
        this.employee = employee;
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getStatus() {
        return status;
    }

}


