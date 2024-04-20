package com.projectif.ooslibrary.comment.service;

public interface CommentLikeService {

    void likeComment(Long comment_pk, Long member_pk);

    int getLikeComment(Long commentPk);

    void deleteLikeComment(Long comment_pk, Long member_pk);
}
