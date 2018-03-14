package org.mel.generic.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome! This is Administrator Server";
    }

    @GetMapping("/api/v1/status")
    public String status() {
        return "★ Yo! This is Generic Administrator Server.";
    }

}
