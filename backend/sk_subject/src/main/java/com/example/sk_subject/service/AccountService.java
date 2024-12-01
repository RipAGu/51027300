package com.example.sk_subject.service;

import com.example.sk_subject.entity.Account;
import com.example.sk_subject.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account findByUsername(String username) {
        return accountRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
}
