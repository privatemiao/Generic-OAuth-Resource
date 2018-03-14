package org.mel.generic.auth.user.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.mel.generic.auth.common.View;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    //    是否有效
    private boolean enabled = true;

    //    账户是否未过期
    private boolean nonExpired = true;

    //    证书或者密码是否未过期
    private boolean credentialsNonExpired = true;

    //    是否未锁定
    private boolean nonLocked = true;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, boolean enabled, boolean nonExpired, boolean credentialsNonExpired, boolean nonLocked) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.nonExpired = nonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.nonLocked = nonLocked;
    }

    @JsonView(View.L1.class)
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @JsonView(View.L1.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonView(View.L1.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(View.L2.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isNonExpired() {
        return nonExpired;
    }

    public void setNonExpired(boolean nonExpired) {
        this.nonExpired = nonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isNonLocked() {
        return nonLocked;
    }

    public void setNonLocked(boolean nonLocked) {
        this.nonLocked = nonLocked;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
