package com.th.form.account;

import com.th.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountFilterForm {

    private Account.Role role;

    private String departmentName;

}
