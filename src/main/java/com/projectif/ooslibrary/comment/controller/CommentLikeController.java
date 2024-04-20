package com.projectif.ooslibrary.comment.controller;

import com.projectif.ooslibrary.comment.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    //특정 코멘트의 좋아요
    @PostMapping("comments/likes/{comment_pk}/{member_pk}")
    public ResponseEntity<?> likeComment(@PathVariable Long comment_pk,
                                         @PathVariable Long member_pk) {
        commentLikeService.likeComment(comment_pk, member_pk);

        return ResponseEntity.ok().build();
    }

    //특정 코멘트의 좋아요 수
    @GetMapping("/likes/{comment_pk}")
    public int getLikeComment(@PathVariable Long comment_pk) {
        return commentLikeService.getLikeComment(comment_pk);
    }

    //특정 코멘트의 좋아요 취소
    @DeleteMapping("/comments/likes/{comment_pk}/{member_pk}")
    public ResponseEntity<?> deleteLikeComment(@PathVariable Long comment_pk,
                                               @PathVariable Long member_pk) {
        commentLikeService.deleteLikeComment(comment_pk, member_pk);

        return ResponseEntity.ok().build();
    }
}
