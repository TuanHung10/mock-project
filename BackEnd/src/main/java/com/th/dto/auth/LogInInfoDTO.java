package com.th.dto.auth;

import com.th.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LogInInfoDTO {

    private Integer id;

    private String username;

    private String fullName;

    private String firstName;

    private String lastName;

    private Account.Role role;


}
