package com.sparta.newsfeedteamproject.entity;

import com.sparta.newsfeedteamproject.dto.comment.CommentReqDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    private Comment comment;
    @MockBean
    private CommentReqDto mockCommentReqDto;
    @MockBean
    private Feed mockFeed;
    @MockBean
    private User mockUser;

    @BeforeEach
    public void setUp(){
        mockCommentReqDto = new CommentReqDto();
        mockFeed = new Feed();
        mockUser = new User();

        mockCommentReqDto.setContents("댓글 내용 111");
        comment = new Comment(mockCommentReqDto,mockFeed,mockUser,0L);
    }
    @Test
    @DisplayName("댓글 업데이트 테스트")
    public void updateTest(){
        comment.update("새로운 댓글입니다.");
        assertEquals("새로운 댓글입니다.", comment.getContents());
    }

    @Test
    @DisplayName("댓글 좋아요수 증가 테스트")
    public void increaseLikesTest(){
        comment.increaseLikes();
        assertEquals(1, comment.getLikes());
    }

    @Test
    @DisplayName("댓글 좋아요수 삭제 테스트")
    public void decreaseLikesTest(){
        comment.setLikes(2L);
        comment.decreaseLikes();
        assertEquals(1, comment.getLikes());
    }

    @Test
    @DisplayName("좋아요수 음수 불가 테스트")
    public void validateLikesTest(){
        comment.setLikes(0L);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> comment.validateLikes());
        assertEquals("유효하지 않은 요청입니다.", exception.getMessage());
    }
}