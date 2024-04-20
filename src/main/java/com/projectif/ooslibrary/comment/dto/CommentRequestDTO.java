package com.projectif.ooslibrary.comment.dto;

import lombok.*;

@Getter
@Setter
@ToString
public class CommentRequestDTO { //등록, 수정
    private Long comment_pk;
    private Long member_pk;
    private String comment_title;
    private String comment_content;
    private Long book_pk;
    private Long my_library_pk;
}
