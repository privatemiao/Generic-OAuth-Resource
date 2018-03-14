package org.mel.generic.auth.user.repository;

import org.mel.generic.auth.user.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
