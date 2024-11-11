package com.reliaquest.api.controller;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class EmployeeController implements IEmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<List> getAllEmployees() {
        return ResponseEntity.ok().body(employeeService.getAllEmployees());
    }

    @Override
    public ResponseEntity<List> getEmployeesByNameSearch(String searchString)  {
        List<Employee> employees = employeeService.getEmployeesByNameSearch(searchString);
        if (employees.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(employees);
        }
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(employee);
        }
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        Integer highestSalary = employeeService.getHighestSalaryOfEmployees();
        return ResponseEntity.ok().body(highestSalary);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        List<String> employees = employeeService.getTop10Employees();
        if (employees.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(employees);
        }
    }

    @Override
    public ResponseEntity createEmployee(Object employeeInput) {
        Employee employee = employeeService.createEmployee(employeeInput);
        return ResponseEntity.ok().body(employee);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        String deletedEmployeeName = employeeService.deleteEmployee(id);
        return ResponseEntity.ok().body(deletedEmployeeName);
    }
}
