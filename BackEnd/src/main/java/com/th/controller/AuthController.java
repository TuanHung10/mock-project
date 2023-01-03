package com.th.controller;

import com.th.dto.auth.LogInInfoDTO;
import com.th.entity.Account;
import com.th.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountService accountService;

    @GetMapping("login")
    public ResponseEntity<?> login(Principal principal) {

        String username = principal.getName();
        Account entity = accountService.getAccountByUsername(username);

        //convert entity -> DTO
        LogInInfoDTO dto = modelMapper.map(entity, LogInInfoDTO.class);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
