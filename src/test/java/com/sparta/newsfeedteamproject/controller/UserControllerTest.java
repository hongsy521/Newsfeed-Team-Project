package com.sparta.newsfeedteamproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newsfeedteamproject.dto.MessageResDto;
import com.sparta.newsfeedteamproject.dto.user.ProfileResDto;
import com.sparta.newsfeedteamproject.dto.user.SignupReqDto;
import com.sparta.newsfeedteamproject.dto.user.UpdateReqDto;
import com.sparta.newsfeedteamproject.dto.user.UserAuthReqDto;
import com.sparta.newsfeedteamproject.entity.User;
import com.sparta.newsfeedteamproject.security.UserDetailsImpl;
import com.sparta.newsfeedteamproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private SignupReqDto signupReqDto;
    private UserAuthReqDto userAuthReqDto;
    private UpdateReqDto updateReqDto;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    @BeforeEach
    public void mockUserSetUp() {
        signupReqDto = new SignupReqDto();
        signupReqDto.setUsername("Hhongsy0521");
        signupReqDto.setPassword("Hhongsy0521!");
        signupReqDto.setName("hongsy");
        signupReqDto.setEmail("hongsy@gmail.com");
        signupReqDto.setUserInfo("한줄소개 아무거나");

        userAuthReqDto = new UserAuthReqDto();
        userAuthReqDto.setUsername("Hhongsy0521");
        userAuthReqDto.setPassword("Hhongsy0521!");

        updateReqDto = new UpdateReqDto();
        updateReqDto.setPassword("Hhongsy0521!");
        updateReqDto.setNewPassword("yahoo12345!");
        updateReqDto.setNewName("hong");
        updateReqDto.setNewUserInfo("야호");
    }

    @Test
    public void testSignup() throws Exception {
        Mockito.doNothing().when(userService).signup(any(SignupReqDto.class));

        mockMvc.perform(post("/users/signup")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(signupReqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다!"));
    }

    @Test
    public void testWithdraw() throws Exception {
        Mockito.doNothing().when(userService).withdraw(eq(1L), any(UserAuthReqDto.class), any(UserDetailsImpl.class));

        mockMvc.perform(put("/users/status/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userAuthReqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원 탈퇴가 완료되었습니다!"));
    }

    @Test
    public void testLogout() throws Exception {
        Mockito.doNothing().when(userService).logout(eq(1L), any(UserDetailsImpl.class));

        mockMvc.perform(post("/users/logout/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetProfile() throws Exception {
        User user = new User();
        ProfileResDto profileResDto = new ProfileResDto(user);
        profileResDto.setUsername("Hhongsy0521");
        profileResDto.setName("hongsy");
        profileResDto.setEmail("hongsy@gmail.com");
        profileResDto.setUserInfo("한줄소개 아무거나");

        Mockito.when(userService.getProfile(eq(1L))).thenReturn(profileResDto);

        mockMvc.perform(get("/users/profile/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("프로필 조회가 완료되었습니다!"))
                .andExpect(jsonPath("$.data.username").value("Hhongsy0521"))
                .andExpect(jsonPath("$.data.name").value("hongsy"))
                .andExpect(jsonPath("$.data.email").value("hongsy@gmail.com"))
                .andExpect(jsonPath("$.data.userInfo").value("한줄소개 아무거나"));
    }

    @Test
    public void testEditProfile() throws Exception {
        User user = new User();
        ProfileResDto profileResDto = new ProfileResDto(user);
        profileResDto.setUsername("Hhongsy0521");
        profileResDto.setName("hong");
        profileResDto.setEmail("hongsy@gmail.com");
        profileResDto.setUserInfo("야호");

        Mockito.when(userService.editProfile(eq(1L), any(UpdateReqDto.class), any(UserDetailsImpl.class))).thenReturn(profileResDto);

        mockMvc.perform(put("/users/profile/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateReqDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("프로필 수정이 완료되었습니다!"))
                .andExpect(jsonPath("$.data.username").value("Hhongsy0521"))
                .andExpect(jsonPath("$.data.name").value("hong"))
                .andExpect(jsonPath("$.data.email").value("hongsy@gmail.com"))
                .andExpect(jsonPath("$.data.userInfo").value("야호"));
    }
}
