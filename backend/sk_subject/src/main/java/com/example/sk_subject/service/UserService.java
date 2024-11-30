package com.example.sk_subject.service;

import com.example.sk_subject.entity.Account;
import com.example.sk_subject.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account registerUser(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword())); // 비밀번호 암호화
        return accountRepository.save(account);
    }
}
