package org.mel.generic.auth.user.controller;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UserQueryCondition {

    @ApiModelProperty("登录名")
    private String username;

    public UserQueryCondition() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
