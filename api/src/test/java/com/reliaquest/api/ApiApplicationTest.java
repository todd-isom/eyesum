package com.reliaquest.api;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ApiApplicationTest {

    @Autowired
    private EmployeeService employeeService;

    @Mock
    EmployeeService employeeServiceMocked;

    Employee employee1 = new Employee(UUID.fromString("d005f39a-beb8-4390-afec-fd54e91d94ee"),
            "Jill Jenkins",139082,48,"Financial Advisor", "jillj@company.com");

    Employee employee2 = new Employee(UUID.fromString("5255f1a5-f9f7-4be5-829a-134bde088d17"),
            "Bill Bob",89750,24,"Documentation Engineer", "billBob@company.com");

    Employee employee3 = new Employee(UUID.fromString("a99bc7ff-dca1-44c5-9627-6ddb6e071249"),
            "Keva Bergnaum",259802,63,"Senior Farming Coordinatorr", "veribet@company.com");

    Employee employee4 = new Employee(UUID.fromString("e926d29d-3817-4a0f-8696-c8d2a374a1c3"),
            "Mrs. Freddy Wisozk",252918,29,"Mining Liaison", "ronstring@company.com");

    Employee employee5 = new Employee(UUID.fromString("e9dd5cc6-9186-44b3-99a3-94bfde057a6d"),
            "Sharyl Thiel",487899,18,"Forward Education Planner", "xovictoriaox@company.com");

    Employee employee6 = new Employee(UUID.fromString("795b7f94-1a50-4cd4-b50f-75184fb42f1b"),
            "Elizbeth Deckow",481627,28,"Hospitality Facilitator", "overhold@company.com");

    Employee employee7 = new Employee(UUID.fromString("9ead3a24-e01b-42f1-badd-6bb07d705e12"),
            "Glenn Parisian",272058,43,"Administration Developer", "bitchin_blair@company.com");

    Employee employee8 = new Employee(UUID.fromString("f908bc8c-26db-45be-8a06-63d7aa80a6f5"),
            "Sandy Ullrich",224055,65,"District Executive", "bamity@company.com");

    Employee employee9 =  new Employee(UUID.fromString("43d5c25f-7f99-47bc-b5e5-fb2a78d06bcf"),
            "Sammy O'Hara",77160,48,"Banking Agent", "tori_the_taurus@company.com");

    Employee employee10 = new Employee(UUID.fromString("8c034cd7-7d5f-4bdf-904f-a743db26e2c9"),
            "Dr. Candyce Lemke",300031,18,"District Designer", "fixsan@company.com");

    Employee employee11 = new Employee(UUID.fromString("6c38efb9-3215-4e58-9fb3-ba96593f83aa"),
            "Buddy Hudson",68678,29,"Global Marketing Producer", "bamity@company.com");

    Employee employee12 = new Employee(UUID.fromString("87ef9515-bd7f-4e0e-9923-034ffd58fd1a"),
            "Doreatha Moen",456202,34,"Marketing Architect", "flowdesk@company.com");


    @Test
    void getAllEmployeesTest() {

        List<Employee> employees = employeeService.getAllEmployees();

        assert employees.size() == 50;

    }

    @Test
    void getEmployeeByNameSearchTest() {
        when(employeeServiceMocked.getAllEmployees()).thenReturn(getTestEmployeeData());
        when(employeeServiceMocked.getEmployeesByNameSearch(anyString())).thenCallRealMethod();

        // test when a null searchstring is entered
        assert employeeService.getEmployeesByNameSearch("") == null;

        // test that it finds Jill
        List<Employee> jilltest = employeeServiceMocked.getEmployeesByNameSearch("Jill");
        assert jilltest.size() == 1;
        Employee jill = jilltest.get(0);

        assert jill.getName().equals("Jill Jenkins");
        assert jill.getSalary() == 139082;
        assert jill.getEmail().equals("jillj@company.com");

        // find employee's  who's name contains "ill"
       List<Employee> employeesWithIinName = employeeServiceMocked.getEmployeesByNameSearch("ill");
       assert employeesWithIinName.size() == 2;
        Employee firstMatch = employeesWithIinName.get(0);

        assert firstMatch.getName().equals("Jill Jenkins");
        assert firstMatch.getSalary() == 139082;
        assert firstMatch.getEmail().equals("jillj@company.com");

       Employee secondMatch = employeesWithIinName.get(1);
       assert secondMatch.getName().equals("Bill Bob");

       // test when no employee's are found matching the search string
        List<Employee> resultsWithNoMatches = employeeService.getEmployeesByNameSearch("duh");
        assert resultsWithNoMatches.isEmpty();

    }

    @Test
    void getEmployeeByIdTest() {
        when(employeeServiceMocked.getAllEmployees()).thenReturn(getTestEmployeeData());
        when(employeeServiceMocked.getEmployeeById(anyString())).thenCallRealMethod();

        Employee employee = employeeServiceMocked.getEmployeeById("d005f39a-beb8-4390-afec-fd54e91d94ee");
        assert employee.getName().equals("Jill Jenkins");
        assert employee.getSalary() == 139082;
        assert employee.getEmail().equals("jillj@company.com");

        employee = employeeServiceMocked.getEmployeeById("5255f1a5-f9f7-4be5-829a-134bde088d17");
        assert employee.getName().equals("Bill Bob");

        assert employeeService.getEmployeeById(UUID.randomUUID().toString()) == null;
    }

    @Test
    void getHighestSalaryTest() {
        when(employeeServiceMocked.getAllEmployees()).thenReturn(getTestEmployeeData());
        when(employeeServiceMocked.getHighestSalaryOfEmployees()).thenCallRealMethod();

        Integer highestSalary = employeeServiceMocked.getHighestSalaryOfEmployees();
        assert highestSalary == 487899;

    }

    @Test
    void getTop10EmployeesTest() {
     when(employeeServiceMocked.getAllEmployees()).thenReturn(getTestEmployeeData());
     when(employeeServiceMocked.getTop10Employees()).thenCallRealMethod();

      List<String> top10employees = employeeServiceMocked.getTop10Employees();
      assert top10employees.size() == 10;
      assert top10employees.get(0).equals("Sharyl Thiel");
      assert top10employees.get(9).equals("Bill Bob");

    }

    @Test
    void createEmployeeTest()  {
        String employeeJson = "{\n" +
                "  \"name\": \"Todd Isom\",\n" +
                "  \"salary\": 145000,\n" +
                "  \"age\" : 45,\n" +
                "  \"title\": \"duh man\"\n" +
                "}";

        Employee createdEmployee = employeeService.createEmployee(employeeJson);
        assert createdEmployee.getName().equals("Todd Isom");

    }

    @Test
    void deleteEmployeeTest() {
        String deleteEmployeeName = "Karey Labadie";
        String deletedEmployeeName = employeeService.deleteEmployee(deleteEmployeeName);
        assert deletedEmployeeName.equals("Karey Labadie");

    }


    List<Employee> getTestEmployeeData() {
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        employees.add(employee5);
        employees.add(employee6);
        employees.add(employee7);
        employees.add(employee8);
        employees.add(employee9);
        employees.add(employee10);
        employees.add(employee11);
        employees.add(employee12);
        return employees;
    }

}
