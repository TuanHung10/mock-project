package com.th.service;

import com.th.entity.Account;
import com.th.entity.Department;
import com.th.form.account.AccountFilterForm;
import com.th.form.account.CreatingAccountForm;
import com.th.form.account.UpdatingAccountForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService extends UserDetailsService {

public Page<Account> getAllAccounts(Pageable pageable, String search, AccountFilterForm filterForm);

public Account getAccountById(int id);

public boolean isAccountExistsById(int id);

public void  createAccount(CreatingAccountForm form);

public void updateAccount(int id, UpdatingAccountForm form);

public boolean isAccountExistsByUsername(String username);

public void deleteAccount(int id);

public void deleteAccounts(List<Integer> ids);

public Account getAccountByUsername(String username);

}
