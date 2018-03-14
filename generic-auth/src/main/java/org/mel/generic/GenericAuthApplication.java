package org.mel.generic;

import org.mel.generic.auth.user.entity.Role;
import org.mel.generic.auth.user.entity.User;
import org.mel.generic.auth.user.repository.RoleDao;
import org.mel.generic.auth.user.repository.UserDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class GenericAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenericAuthApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initDB(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        return (args) -> {
            System.out.println("Save default roles [admin & staff]");
            roleDao.save(new Role("admin", "管理员"));
            roleDao.save(new Role("staff", "员工"));
            roleDao.save(new Role("service", "客服"));

            System.out.println("Save default users [admin/admin & mel/password]");
            User userAdmin = new User("admin", passwordEncoder.encode("admin"));
            userAdmin.addRole(roleDao.findByName("admin"));
            userDao.save(userAdmin);
            User userMel = new User("mel", passwordEncoder.encode("password"));
            userMel.addRole(roleDao.findByName("staff"));
            userMel.addRole(roleDao.findByName("service"));
            userDao.save(userMel);

            System.out.println("Fetch users");
            List<User> users = (List<User>) userDao.findAll();
            users.forEach(user -> System.out.println(user));

            System.out.println("Find by username [admin]");
            System.out.println(userDao.findByUsername("admin"));
            System.out.println("Find by username [mel]");
            System.out.println(userDao.findByUsername("mel"));
        };
    }
}
