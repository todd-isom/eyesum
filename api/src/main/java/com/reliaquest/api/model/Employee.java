package com.reliaquest.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

    @Getter
    @JsonProperty("id")
    private UUID id;
    @Getter
    @JsonProperty("employee_name")
    @JsonAlias("name")
    private String name;
    @Getter
    @JsonProperty("employee_salary")
    @JsonAlias("salary")
    private Integer salary;
    @JsonProperty("employee_age")
    @JsonAlias("age")
    private Integer age;
    @JsonProperty("employee_title")
    @JsonAlias("title")
    private String title;
    @Getter
    @JsonProperty("employee_email")
    @JsonAlias("email")
    private String email;

    public Employee() {
    }

    public Employee(UUID id, String name, Integer salary, Integer age, String title, String email) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.title = title;
        this.email = email;
    }
}
