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
            if (accountRepository.findByName("admin").isEmpty()) {
                Account admin = new Account();
                admin.setName("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                accountRepository.save(admin);
            }

            if (accountRepository.findByName("user").isEmpty()) {
                Account account = new Account();
                account.setName("user");
                account.setPassword(passwordEncoder.encode("user123"));
                accountRepository.save(account);
            }
        };
    }
}
