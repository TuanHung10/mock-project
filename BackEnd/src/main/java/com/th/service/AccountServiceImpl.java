package com.th.service;

import com.th.entity.Account;
import com.th.form.account.AccountFilterForm;
import com.th.form.account.CreatingAccountForm;
import com.th.form.account.UpdatingAccountForm;
import com.th.repository.AccountRepository;
import com.th.specification.account.AccountSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Page<Account> getAllAccounts(Pageable pageable, String search, AccountFilterForm filterForm) {

        Specification<Account> where = AccountSpecification.buildWhere(search, filterForm);

        return accountRepository.findAll(where, pageable);

    }

    @Override
    public Account getAccountById(int id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public boolean isAccountExistsById(int id) {
        return accountRepository.existsById(id);
    }

    @Override
    public void createAccount(CreatingAccountForm form) {
//omit id field
        TypeMap<CreatingAccountForm, Account> typeMap = modelMapper.getTypeMap(CreatingAccountForm.class, Account.class);

        if (typeMap == null) { //if not already added
            //skip field
            modelMapper.addMappings(new PropertyMap<CreatingAccountForm, Account>() {

                @Override
                protected void configure() {
                    skip(destination.getId());
                }
            });
        }
//convert form to entity
        Account account = modelMapper.map(form, Account.class);
        accountRepository.save(account);
    }

    @Override
    public void updateAccount(int id, UpdatingAccountForm form) {
        Account account = getAccountById(id);
        account.setFirstName(form.getFirstName());
        account.setLastName(form.getLastName());
        account.setUsername(form.getUsername());
        accountRepository.save(account);
    }

    @Override
    public boolean isAccountExistsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void deleteAccounts(List<Integer> ids) {
        accountRepository.deleteByIds(ids);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);

        if (account == null) {
            //401
            throw new UsernameNotFoundException(username);
        }

        return new User(
                account.getUsername(),
                account.getPassword(),
                AuthorityUtils.createAuthorityList(account.getRole().toString())
        );
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

}
