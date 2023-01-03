package com.th.controller;

import com.th.dto.entity.AccountDTO;
import com.th.dto.entity.DepartmentDTO;
import com.th.dto.entity.DetailsAccountDTO;
import com.th.entity.Account;
import com.th.entity.Department;
import com.th.form.account.AccountFilterForm;
import com.th.form.account.CreatingAccountForm;
import com.th.form.account.UpdatingAccountForm;
import com.th.form.department.UpdatingDepartmentForm;
import com.th.service.AccountService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/accounts")
@CrossOrigin("*")
public class AccountController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountService accountService;

    @GetMapping()
    public ResponseEntity<?> getAllAccounts(Pageable pageable,
                                            @RequestParam(value = "search", required = false) String search, AccountFilterForm filterForm) {

        Page<Account> entityPages = accountService.getAllAccounts(pageable, search, filterForm);

//convert entities -> DTOs
        List<AccountDTO> dtos = modelMapper.map(entityPages.getContent(), new TypeToken<List<AccountDTO>>() {
        }.getType());

        Page<AccountDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

        return new ResponseEntity<>(dtoPages, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAccountByID(@PathVariable(name = "id") int id) {
        Account entity = accountService.getAccountById(id);

        DetailsAccountDTO dto = modelMapper.map(entity, DetailsAccountDTO.class);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createAccount(@RequestBody CreatingAccountForm form) {
        accountService.createAccount(form);
        return new ResponseEntity<String>("Create successfully!", HttpStatus.CREATED);
    }

    @GetMapping(value = "/username/{username}/exists")
    public ResponseEntity<?> existsByUsername(@PathVariable(name = "username") String username) {
        return new ResponseEntity<>(accountService.isAccountExistsByUsername(username), HttpStatus.OK) ;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable(name = "id") int id,
                                              @RequestBody UpdatingAccountForm form) {
        accountService.updateAccount(id, form);
        return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable(name = "id") int id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccounts(@RequestParam(name = "ids") List<Integer> ids) {
        accountService.deleteAccounts(ids);
        return new ResponseEntity<String>("Delete successfully!", HttpStatus.OK);
    }

}
