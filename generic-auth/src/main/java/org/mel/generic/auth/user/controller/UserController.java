package org.mel.generic.auth.user.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.mel.generic.auth.common.View;
import org.mel.generic.auth.user.entity.User;
import org.mel.generic.auth.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    @ApiOperation("新增用户")
    public void create(@Valid @RequestBody UserCreateDTO userDTO) {

        userService.saveUser(userDTO);
    }

    @DeleteMapping("/users/{id}")
    @ApiOperation("删除用户")
    public void deleteUser(@ApiParam("用户ID") @PathVariable Long id) {

        userService.deleteUser(id);
    }

    @PutMapping("/users/{id}")
    @ApiOperation("修改用户")
    public void updateUser(@ApiParam("用户ID") @PathVariable Long id, UserCreateDTO dto) {

        userService.saveUser(id, dto);
    }

    @GetMapping("/users/{id}")
    @ApiOperation("查询用户")
    public User getUserInfo(@ApiParam("用户ID") @PathVariable Long id) {

        return userService.findById(id);
    }

    @JsonView(View.L1.class)
    @GetMapping("/users")
    @ApiOperation("搜索用户")
    public List<User> query(UserQueryCondition condition, Pageable page) {

        logger.debug("\r\nQuery parameter is: {}", condition);
        logger.debug("\r\nPageable is: {}", page);

        return userService.queryUser(condition, page);
    }

}
