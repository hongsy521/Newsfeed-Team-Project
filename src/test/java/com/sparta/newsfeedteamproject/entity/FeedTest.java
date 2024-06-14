package com.sparta.newsfeedteamproject.entity;

import com.sparta.newsfeedteamproject.dto.feed.FeedReqDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class FeedTest {

    private Feed feed;
    @MockBean
    private User mockUser;
    @MockBean
    private FeedReqDto mockFeedReqDto;

    @BeforeEach
    public void setUp(){
        mockUser = new User();
        mockFeedReqDto = new FeedReqDto();
        mockFeedReqDto.setContents("피드 내용 111");
        feed = new Feed(mockFeedReqDto,mockUser);
    }

    @Test
    @DisplayName("피드 업데이트 테스트")
    public void updateTest(){
        FeedReqDto updateMockFeed = new FeedReqDto();
        updateMockFeed.setContents("업데이트한 피드 내용 ~");
        feed.update(updateMockFeed);
        assertEquals("업데이트한 피드 내용 ~",feed.getContents());
    }

    @Test
    @DisplayName("피드 좋아요수 증가 테스트")
    public void increaseLikesTest(){
        feed.increaseLikes();
        assertEquals(1, feed.getLikes());
    }

    @Test
    @DisplayName("피드 좋아요수 감소 테스트")
    public void decreaseLikesTest(){
        feed.setLikes(2L);
        feed.decreaseLikes();
        assertEquals(1, feed.getLikes());
    }

    @Test
    @DisplayName("좋아요수 음수 불가 테스트")
    public void validateLikesTest(){
        feed.setLikes(0L);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> feed.validateLikes());
        assertEquals("유효하지 않은 요청입니다.", exception.getMessage());
    }
}