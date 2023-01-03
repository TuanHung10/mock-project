package com.th.form.account;

import com.th.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatingAccountForm {

    private String username;

    private String firstName;

    private String lastName;

    private Account.Role role;

    private int departmentId;

}
