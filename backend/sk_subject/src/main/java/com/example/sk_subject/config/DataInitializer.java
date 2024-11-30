package com.example.sk_subject.config;

import com.example.sk_subject.entity.Account;
import com.example.sk_subject.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (accountRepository.findByUsername("admin").isEmpty()) {
                Account admin = new Account();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");
                accountRepository.save(admin);
            }

            if (accountRepository.findByUsername("user").isEmpty()) {
                Account account = new Account();
                account.setUsername("user");
                account.setPassword(passwordEncoder.encode("user123"));
                account.setRole("ROLE_USER");
                accountRepository.save(account);
            }
        };
    }
}
