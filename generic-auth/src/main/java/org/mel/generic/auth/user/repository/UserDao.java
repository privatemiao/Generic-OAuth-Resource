package org.mel.generic.auth.user.repository;

import org.mel.generic.auth.user.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
