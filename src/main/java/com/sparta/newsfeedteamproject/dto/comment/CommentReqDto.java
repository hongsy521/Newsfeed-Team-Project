package com.sparta.newsfeedteamproject.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentReqDto {
    @NotBlank(message = "[contents:blank] 댓글 내용을 작성해주세요!")
    private String contents;
}