package com.sparta.newsfeedteamproject.dto.feed;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedReqDto {

    @NotBlank
    private String contents;
}