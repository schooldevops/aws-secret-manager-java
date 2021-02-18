package com.schooldevops.secret.testsecret;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecretController {

    @Value("${myproject.schooldevops.db.username}")
    private String username;

    @Value("${myproject.schooldevops.db.password}")
    private String password;

    @Value("${myproject.schooldevops.db.token}")
    private String token;

    @GetMapping("/secret/{value}")
    public String getSecretValue(@PathVariable String value) {
        if ("username".equals(value)) {
            return username;
        } else if ("password".equals(value)) {
            return password;
        } else if ("token".equals(value)) {
            return token;
        } else {
            return "NOE";
        }
    }

}
