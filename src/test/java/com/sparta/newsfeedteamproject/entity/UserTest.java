package com.sparta.newsfeedteamproject.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    public void setUp(){
        user = new User("hongsy12345","hongsy12345!","hongsy","hongsy@gmail.com","안녕하세요.",Status.ACTIVATE, LocalDateTime.now());
    }

    @Test
    public void updateTest(){
        LocalDateTime updateTime = LocalDateTime.now();
        user.update("hong","반갑습니다.","hong12345!!",updateTime);
        assertEquals("hong",user.getName());
        assertEquals("반갑습니다.",user.getUserInfo());
        assertEquals("hong12345!!",user.getPassword());
        assertEquals(updateTime,user.getModifiedAt());
    }

    @Test
    public void deleteRefreshTokenTest(){
        user.deleteRefreshToken();
        assertEquals("",user.getRefreshToken());
    }

    @Test
    public void updateRefreshTokenTest(){
        user.setRefreshToken("abcdefg");
        assertEquals("abcdefg",user.getRefreshToken());
    }
}