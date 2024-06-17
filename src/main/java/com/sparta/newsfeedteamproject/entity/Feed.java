package com.sparta.newsfeedteamproject.entity;

import com.sparta.newsfeedteamproject.dto.feed.FeedReqDto;
import com.sparta.newsfeedteamproject.exception.ExceptionMessage;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "NewsFeed") // 매핑할 테이블의 이름을 지정
@NoArgsConstructor
public class Feed extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "contents", nullable = false)
    private String contents;
    @Min(0)
    @Column(name = "likes", nullable = false)
    private Long likes;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "feed", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    public Feed(FeedReqDto reqDto, User user) {

        this.contents = reqDto.getContents();
        this.user = user;
        this.likes = 0L;
    }

    public void update(FeedReqDto reqDto) {

        this.contents = reqDto.getContents();
    }

    public void increaseLikes() {

        this.likes++;
    }

    public void decreaseLikes() {
        validateLikes();
        this.likes--;
    }
    public void validateLikes(){
        if(this.likes == 0){
            throw new IllegalArgumentException(ExceptionMessage.INVALID_REQUEST.getExceptionMessage());
        }
    }
}
