package com.ismail.webclient;

import com.ismail.webclient.dto.Employee;
import com.ismail.webclient.service.EmployeeRestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class EmployeeRestClientTest
{
    private static final String baseUrl = "http://localhost:18080/employeeservice";

    private WebClient webClient = WebClient.create(baseUrl);

    EmployeeRestClient employeeRestClient = new EmployeeRestClient(webClient);

    @Test
    void getAllEmployees()
    {
        List<Employee> employeeList = employeeRestClient.getAllEmployees();

        employeeList.forEach(System.out::println);

        assertTrue(employeeList.size() > 0);
    }

    @Test
    void getEmployeeById()
    {
        Employee employee = employeeRestClient.getEmployeeById(1);

        System.out.println(employee);

        assertEquals(employee.getFirstName(), "Chris");

        assertTrue(employee != null);
    }

    @Test
    void getEmployeeById_NotFound()
    {
        //employeeRestClient.getEmployeeById(100);

        assertThrows(WebClientResponseException.class, () -> employeeRestClient.getEmployeeById(100));

    }

    @Test
    void getEmployeesByName()
    {
        List<Employee> employeeList = employeeRestClient.getEmployeesByName("Chris");

        employeeList.forEach(System.out::println);

        assertTrue(employeeList.size() > 0);
    }

    @Test
    void getEmployeeByName_NotFound()
    {
        //employeeRestClient.getEmployeeById(100);

        assertThrows(WebClientResponseException.class, () -> employeeRestClient.getEmployeesByName("Assssss"));

    }


    @Test
    void addNewEmployee()
    {
        Employee employee = Employee.builder()
                .id(null)
                .firstName("Iron")
                .lastName("Man")
                .gender("Male")
                .role("Architect")
                .age(50)
                .build();

        Employee employee2 = employeeRestClient.addNewEmployee(employee);

        System.out.println(employee2);

        assertTrue(employee2.getId() != null);


    }

    @Test
    void addNewEmployee_Invalid()
    {
        // first name is missing

        Employee employee = Employee.builder()
                .id(null)
                .firstName(null)
                .lastName("Man")
                .gender("Male")
                .role("Architect")
                .age(50)
                .build();

        assertThrows(WebClientResponseException.class, () -> employeeRestClient.addNewEmployee(employee));


    }

    @Test
    void updateEmployee()
    {
        Employee employee = employeeRestClient.getEmployeeById(1);
        employee.setFirstName(employee.getFirstName() + "1");

        Employee employee2 = employeeRestClient.updateEmployee(employee.getId(), employee);

        System.out.println(employee2);

        assertTrue(employee2.getFirstName().equals(employee.getFirstName()));


    }

    @Test
    void updateEmployee_NotFound()
    {
        Employee employee = employeeRestClient.getEmployeeById(1);
        employee.setFirstName(employee.getFirstName() + "1");

        assertThrows(WebClientResponseException.class, () -> employeeRestClient.updateEmployee(200, employee));



    }


    @Test
    void deleteEmployee()
    {
        Employee employee = Employee.builder()
                .id(null)
                .firstName("Iron")
                .lastName("Man")
                .gender("Male")
                .role("Architect")
                .age(50)
                .build();

        Employee employee2 = employeeRestClient.addNewEmployee(employee);
        System.out.println(employee2);

        String status = employeeRestClient.deleteEmployee(employee2.getId());

        System.out.println("Delete status: " + status);
       // assertTrue(employee2.getId() != null);


    }
}
