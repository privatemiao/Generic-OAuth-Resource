package org.mel.generic.auth.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ResourceController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome! This Authentication Server.";
    }

    @GetMapping("/api/v1/principal")
    public Principal user(Principal principal) {
        System.out.println(String.format("Access /principal \r\n\t%s", principal));
        return principal;
    }

    @GetMapping("/api/v1/status")
    public String status() {
        return "★ Yo! This is Authentication Server.";
    }

}
