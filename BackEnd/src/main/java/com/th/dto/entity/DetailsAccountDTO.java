package com.th.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailsAccountDTO {

    private int id;

    private String username;

    private String firstName;

    private String lastName;

    private String role;

    private String departmentName;

}
