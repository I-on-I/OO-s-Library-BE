package com.projectif.ooslibrary.comment.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
public class CommentVO { //데이터 저장하는 공간
    private Long comment_pk;
    private Long member_pk;
    private String comment_title;
    private String comment_content;
    private Long level;
    private LocalDateTime created_date;
    private LocalDateTime modified_date;
    private short isDeleted;
    private Long total_like;
    private Long total_report;
    private Long parent_pk;
    private Long book_pk;
    private Long my_library_pk;

}
