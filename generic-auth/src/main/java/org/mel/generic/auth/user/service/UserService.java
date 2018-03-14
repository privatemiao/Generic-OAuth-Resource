package org.mel.generic.auth.user.service;

import org.mel.generic.auth.common.exception.UserNotExistException;
import org.mel.generic.auth.common.util.BeanMapper;
import org.mel.generic.auth.user.controller.UserCreateDTO;
import org.mel.generic.auth.user.controller.UserQueryCondition;
import org.mel.generic.auth.user.entity.User;
import org.mel.generic.auth.user.repository.RoleDao;
import org.mel.generic.auth.user.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }


    public User findById(Long id) {
        User user = userDao.findOne(id);
        if (user == null) {
            throw new UserNotExistException();
        }
        return user;
    }

    public List<User> queryUser(UserQueryCondition condition, Pageable page) {
        return (List<User>) userDao.findAll();
    }


    public void saveUser(Long id, UserCreateDTO dto) {

        User user = BeanMapper.map(dto, User.class);
        user.setId(id);
        user.getRoles().clear();
        if (dto.getRoles().length > 0) {
            for (String roleName : dto.getRoles()) {
                user.addRole(roleDao.findByName(roleName));
            }
        }
        save(user);
    }

    public void saveUser(UserCreateDTO dto) {

        saveUser(null, dto);
    }

    public void save(User user) {

        System.out.println(String.format("Service save user %s", user));
        if (user.getId() == null) {
            userDao.save(user);
        } else {
            User currentUser = userDao.findOne(user.getId());
            BeanMapper.copy(user, currentUser);
            userDao.save(currentUser);
        }
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }

}
