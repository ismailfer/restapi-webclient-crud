package com.ismail.webclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee
{
    private Integer id;

    private String firstName;

    private String lastName;

    private String gender;

    private Integer age;

    private String role;

}
