package com.sparta.newsfeedteamproject.entity;

import com.sparta.newsfeedteamproject.dto.comment.CommentReqDto;
import com.sparta.newsfeedteamproject.exception.ExceptionMessage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "contents", nullable = false)
    private String contents;
    @Column(name = "likes", nullable = false)
    private Long likes;
    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(CommentReqDto reqDto, Feed feed, User user, Long likes) {
        this.contents = reqDto.getContents();
        this.feed = feed;
        this.user = user;
        this.likes = likes;
    }

    public void update(String contents) {
        this.contents = contents;
    }

    public void increaseLikes() {
        this.likes++;
    }

    public void decreaseLikes() {
        validateLikes();
        this.likes--;
    }

    public void validateLikes(){
        if(this.likes == 0) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_REQUEST.getExceptionMessage());
        }
    }
}