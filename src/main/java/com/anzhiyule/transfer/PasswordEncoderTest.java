package com.anzhiyule.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public void main() {
        String password = passwordEncoder.encode("bai");
        System.out.println(password);
    }
}
