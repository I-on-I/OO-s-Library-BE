package com.projectif.ooslibrary.comment.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class CommentDTO {
    @Setter
    private Long comment_pk;
    @Setter
    private Long member_pk;
    @Setter
    private String comment_title;
    @Setter
    private String comment_content;
    private String created_date;
    private String modified_date;
    @Setter
    private short isDeleted;
    @Setter
    private Long total_like;
    @Setter
    private Long total_report;
    @Setter
    private Long book_pk;
    @Setter
    private Long my_library_pk;

    public void setCreated_date(LocalDateTime created_date) {
        //날짜 형태 변경
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        this.created_date = created_date.format(formatter);
    }

    public void setModified_date(LocalDateTime modified_date) {
        if (modified_date != null) {
            //null이 아닌 경우에만 형태 변환해서 출력
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            this.modified_date = modified_date.format(formatter);
        } else {

        }
    }

}
