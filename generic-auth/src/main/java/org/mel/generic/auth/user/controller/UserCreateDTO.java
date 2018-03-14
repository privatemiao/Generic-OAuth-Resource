package org.mel.generic.auth.user.controller;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

public class UserCreateDTO {

    @ApiModelProperty("登录名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    //    是否有效
    @ApiModelProperty("是否有效")
    private boolean enabled = true;

    //    账户是否未过期
    @ApiModelProperty("账户是否未过期")
    private boolean nonExpired = true;

    //    证书或者密码是否未过期
    @ApiModelProperty("证书或者密码是否未过期")
    private boolean credentialsNonExpired = true;

    //    是否未锁定
    @ApiModelProperty("是否未锁定")
    private boolean nonLocked = true;

    @ApiModelProperty("拥有角色")
    private String[] roles;

    public UserCreateDTO() {
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
