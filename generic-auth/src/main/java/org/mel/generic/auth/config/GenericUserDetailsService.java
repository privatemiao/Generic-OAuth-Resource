package org.mel.generic.auth.config;

import org.mel.generic.auth.user.entity.Role;
import org.mel.generic.auth.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenericUserDetailsService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    System.out.println(String.format("Load user %s", username));

    org.mel.generic.auth.user.entity.User user = userService.findByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException(String.format("Can not found user %s", username));
    }

    System.out.println(String.format("Found %s", user));

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    for (Role role : user.getRoles()) {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    }

    return new User(username, user.getPassword(),
      true, true, true, true,
      authorities);
  }
}
