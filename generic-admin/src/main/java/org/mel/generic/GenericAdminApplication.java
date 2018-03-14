package org.mel.generic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class GenericAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenericAdminApplication.class, args);
    }


}
