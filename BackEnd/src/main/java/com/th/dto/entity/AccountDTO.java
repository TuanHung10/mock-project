package com.th.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private int id;

    private String username;

    private String fullName;

    private String role;

    private String departmentName;

}
