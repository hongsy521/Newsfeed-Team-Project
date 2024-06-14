package com.sparta.newsfeedteamproject.entity;

import com.sparta.newsfeedteamproject.dto.feed.FeedReqDto;
import org.junit.jupiter.api.BeforeEach;
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
    public void updateTest(){
        FeedReqDto updateMockFeed = new FeedReqDto();
        updateMockFeed.setContents("업데이트한 피드 내용 ~");
        feed.update(updateMockFeed);
        assertEquals("업데이트한 피드 내용 ~",feed.getContents());
    }

    @Test
    public void increaseLikesTest(){
        feed.increaseLikes();
        assertEquals(1, feed.getLikes());
    }

    @Test
    public void decreaseLikesTest(){
        feed.setLikes(2L);
        feed.decreaseLikes();
        assertEquals(1, feed.getLikes());
    }

    @Test
    public void validateLikesTest(){
        feed.setLikes(0L);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> feed.validateLikes());
        assertEquals("유효하지 않은 요청입니다.", exception.getMessage());
    }
}