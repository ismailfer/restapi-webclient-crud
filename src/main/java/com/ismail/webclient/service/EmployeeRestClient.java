package com.ismail.webclient.service;

import com.ismail.webclient.constants.EmployeeConstants;
import com.ismail.webclient.dto.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import static com.ismail.webclient.constants.EmployeeConstants.*;

import java.util.List;

@Slf4j
public class EmployeeRestClient
{
    private WebClient webClient;

    public EmployeeRestClient(WebClient webClient)
    {
        this.webClient = webClient;
    }

    // http://localhost:18080/employeeservice/v1/allEmployees

    public List<Employee> getAllEmployees()
    {
        return webClient.get()
                .uri(EmployeeConstants.GET_ALL_EMPLOYEES_V1)
                .retrieve()
                .bodyToFlux(Employee.class)
                .collectList()
                .block();

    }

    public Employee getEmployeeById(int employeeId)
    {
        try
        {
            return webClient.get()
                    .uri(EmployeeConstants.EMPLOYEE_BY_ID_V1, employeeId)
                    .retrieve()
                    .bodyToMono(Employee.class)
                    .block();
        }
        catch (WebClientResponseException we)
        {
            log.error("WebClientResponseException response code is [{}] and the response body is:\n{}", we.getRawStatusCode(), we.getResponseBodyAsString());
            //log.error("WebClientResponseException", we);

            throw we;
        }
        catch (Exception e)
        {
            log.error("Exception: {}", e.getMessage());
            //log.error("Exception", e);

            throw e;
        }

    }


    public List<Employee> getEmployeesByName(String employeeName)
    {
        String uri = UriComponentsBuilder.fromUriString(EmployeeConstants.GET_EMPLOYEES_BY_NAME_V1)
                .queryParam("employee_name", employeeName)
                .build()
                .toUriString();

        try
        {
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToFlux(Employee.class)
                    .collectList()
                    .block();
        }
        catch (WebClientResponseException we)
        {
            log.error("WebClientResponseException response code is [{}] and the response body is:\n{}", we.getRawStatusCode(), we.getResponseBodyAsString());
            //log.error("WebClientResponseException", we);

            throw we;
        }
        catch (Exception e)
        {
            log.error("Exception: {}", e.getMessage());
            //log.error("Exception", e);

            throw e;
        }

    }

    public Employee addNewEmployee(Employee employee)
    {
        try
        {
            return webClient.post()
                    .uri(ADD_EMPLOYEE_V1)
                    .syncBody(employee)
                    .retrieve()
                    .bodyToMono(Employee.class)
                    .block();
        }
        catch (WebClientResponseException we)
        {
            log.error("WebClientResponseException response code is [{}] and the response body is:\n{}", we.getRawStatusCode(), we.getResponseBodyAsString());
            //log.error("WebClientResponseException", we);

            throw we;
        }
        catch (Exception e)
        {
            log.error("Exception: {}", e.getMessage());
            //log.error("Exception", e);

            throw e;
        }
    }

    public Employee updateEmployee(int employeeId, Employee employee)
    {
        try
        {
            return webClient.put()
                    .uri(EMPLOYEE_BY_ID_V1, employeeId)
                    .syncBody(employee)
                    .retrieve()
                    .bodyToMono(Employee.class)
                    .block();
        }
        catch (WebClientResponseException we)
        {
            log.error("WebClientResponseException response code is [{}] and the response body is:\n{}", we.getRawStatusCode(), we.getResponseBodyAsString());
            //log.error("WebClientResponseException", we);

            throw we;
        }
        catch (Exception e)
        {
            log.error("Exception: {}", e.getMessage());
            //log.error("Exception", e);

            throw e;
        }
    }

    public String deleteEmployee(int employeeId)
    {
        try
        {
         return   webClient.delete()
                    .uri(EMPLOYEE_BY_ID_V1, employeeId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
        catch (WebClientResponseException we)
        {
            log.error("WebClientResponseException response code is [{}] and the response body is:\n{}", we.getRawStatusCode(), we.getResponseBodyAsString());
            //log.error("WebClientResponseException", we);

            throw we;
        }
        catch (Exception e)
        {
            log.error("Exception: {}", e.getMessage());
            //log.error("Exception", e);

            throw e;
        }
    }
}
