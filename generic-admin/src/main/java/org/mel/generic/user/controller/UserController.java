package org.mel.generic.user.controller;

import org.mel.generic.user.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/{id}")
    public User loadById(@PathVariable Long id) {
        User user = new User("privatemiao", "Mel", "Suzhou");
        return user;
    }
}
