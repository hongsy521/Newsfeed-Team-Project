package com.sparta.newsfeedteamproject.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("프로필 수정 테스트")
    public void updateTest(){
        LocalDateTime updateTime = LocalDateTime.now();
        user.update("hong","반갑습니다.","hong12345!!",updateTime);
        assertEquals("hong",user.getName());
        assertEquals("반갑습니다.",user.getUserInfo());
        assertEquals("hong12345!!",user.getPassword());
        assertEquals(updateTime,user.getModifiedAt());
    }

    @Test
    @DisplayName("리프레쉬 토큰 삭제 테스트")
    public void deleteRefreshTokenTest(){
        user.deleteRefreshToken();
        assertEquals("",user.getRefreshToken());
    }

    @Test
    @DisplayName("리프레쉬 토큰 업데이트 테스트")
    public void updateRefreshTokenTest(){
        user.setRefreshToken("abcdefg");
        assertEquals("abcdefg",user.getRefreshToken());
    }
}