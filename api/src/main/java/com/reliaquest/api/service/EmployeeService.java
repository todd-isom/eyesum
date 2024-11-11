package com.reliaquest.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reliaquest.api.model.CreatedEmployee;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.Employees;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class EmployeeService {
    private final HttpEncodingAutoConfiguration httpEncodingAutoConfiguration;
    @Value("${app.serverapi.url}")
    private String serverUrl;

    private final RestTemplate restTemplate;


    public EmployeeService(RestTemplate restTemplate, HttpEncodingAutoConfiguration httpEncodingAutoConfiguration) {
        this.restTemplate = restTemplate;
        this.httpEncodingAutoConfiguration = httpEncodingAutoConfiguration;
    }

    public List<Employee> getAllEmployees() {
        ResponseEntity<Employees> getEmployeeResponse = restTemplate.getForEntity(serverUrl, Employees.class);
        return Objects.requireNonNull(getEmployeeResponse.getBody()).getEmployees();
    }

    public List<Employee> getEmployeesByNameSearch(String searchString) {
        if (searchString == null || searchString.isEmpty()) {
            return null;
        }

        List<Employee> matchingEmployees = new ArrayList<>();
        for (Employee employee : getAllEmployees()) {
            if (employee.getName().toLowerCase().contains(searchString.toLowerCase())) {
                matchingEmployees.add(employee);
            }
        }
        if (matchingEmployees.isEmpty()) {
            log.info("No matching employees found for String: " + searchString);
        }
        return matchingEmployees;
    }

    public Employee getEmployeeById(String id) {
        try {
            UUID uuid = UUID.fromString(id);

            for (Employee employee : getAllEmployees()) {
                if (employee.getId().equals(uuid)) {
                    return employee;
                }
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            log.error(illegalArgumentException.getMessage());
        }
        return null;
    }


    public Integer getHighestSalaryOfEmployees() {
        Integer highestSalary = 0;
        for (Employee employee : getAllEmployees()) {
            if (employee.getSalary() > highestSalary) {
                highestSalary = employee.getSalary();
            }
        }
        return highestSalary;
    }

    public List<String> getTop10Employees() {
        List<String> topEmployeeNames = new ArrayList<>();

        List<Employee> allEmployees = getAllEmployees();

        // return an empty list if fewer than 10 employees total exist.
        // what to do wasn't specified in the specs
        if (allEmployees.size() < 10) {
            return topEmployeeNames;
        }

        allEmployees.sort(new SalaryComparator());

        for (Employee employee : allEmployees.subList(0, 10)) {
            topEmployeeNames.add(employee.getName());
            log.info("Top 10 employee name: " + employee.getName() + " salary: " + employee.getSalary());
        }
        return topEmployeeNames;
    }

    static class SalaryComparator implements Comparator<Employee> {
        @Override
        public int compare(Employee employee1, Employee employee2) {
            // the minus sign below is to make the sort in descending order.
            return -(employee1.getSalary().compareTo(employee2.getSalary()));
        }
    }

    public Employee createEmployee(Object input) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>((String) input, headers);

        ResponseEntity<String> createEmployeeResponse = restTemplate.postForEntity(serverUrl, request, String.class);
        ObjectMapper objectMapper = new ObjectMapper();

        CreatedEmployee employee;
        try {
            employee = objectMapper.readValue(createEmployeeResponse.getBody(), CreatedEmployee.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing json into Employee", e);
            throw new RuntimeException(e);
        }
        if (employee.getStatus().equals("Successfully processed request.")) {
            return employee.getEmployee();
        }

        return null;

    }

    public String deleteEmployee(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity =
                new HttpEntity<>("{\n" +
                        "  \"name\": \"" + name + "\"\n" +
                        "}", headers);

        restTemplate.exchange(serverUrl, HttpMethod.DELETE, entity, String.class);
        return name;

    }
}