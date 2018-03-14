package org.mel.generic.auth.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.boot.jaxb.SourceType;
import org.junit.Test;
import org.mel.generic.auth.common.BaseTest;
import org.mel.generic.auth.common.util.BeanMapper;
import org.mel.generic.auth.user.controller.UserCreateDTO;
import org.mel.generic.auth.user.entity.User;
import org.mel.generic.auth.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void whenQueryUsersSuccess() throws Exception {

        MvcResult result = mvc.perform(
                get("/api/v1/users")
                        .param("username", "mel")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sort", "username,desc")
        ).andExpect(
                status().isOk()
        ).andReturn();

        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    public void whenGetUserInfoSuccess() throws Exception {

        MvcResult result = mvc.perform(
                get("/api/v1/users/1")
        ).andExpect(status().isOk())
                .andReturn();
        System.out.println(String.format("whenGetUserInfoSuccess user: %s", result.getResponse().getContentAsString()));
    }


    @Test
    public void whenCreateUserSuccess() throws Exception {

        String content = "{\"username\": \"john\", \"password\": \"pass\", \"roles\": [\"staff\", \"service\"]}";
        mvc.perform(
                post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content)
        ).andExpect(status().isOk());

    }

    @Test
    public void whenDeleteSuccess() throws Exception {

        mvc.perform(
                delete("/api/v1/users/1")
        ).andExpect(status().isOk());
    }

    @Test
    public void whenUpdateSuccess() throws Exception {

        User user = userService.findById(1L);
        System.out.println(String.format("Older user: %s", user));

        UserCreateDTO dto = BeanMapper.map(user, UserCreateDTO.class);
        dto.setPassword("P@ssw0rd");
        dto.setRoles(new String[]{"admin", "staff", "service"});

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(dto);
        System.out.println(String.format("DTO is: %s", content));

        mvc.perform(
                put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content)
        ).andExpect(status().isOk());

        System.out.println(String.format("Now user: %s", userService.findById(1L)));
    }
}
