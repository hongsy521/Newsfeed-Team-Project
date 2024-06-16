package com.sparta.newsfeedteamproject.dto.user;

import com.sparta.newsfeedteamproject.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResDto {
    private String username;
    private String name;
    private String email;
    private String userInfo;

    public ProfileResDto(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.userInfo = user.getUserInfo();
    }
}
