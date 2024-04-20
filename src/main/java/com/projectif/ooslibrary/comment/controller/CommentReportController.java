package com.projectif.ooslibrary.comment.controller;

import com.projectif.ooslibrary.comment.dto.CommentReportRequestDTO;
import com.projectif.ooslibrary.comment.service.CommentReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommentReportController {
    private final CommentReportService commentReportService;

    //신고 추가
    @PostMapping("/comments/report")
    public ResponseEntity<String> insertReportComment(CommentReportRequestDTO commentReportRequestDTO) {
        System.out.println(commentReportRequestDTO);
        commentReportService.insertReportComment(commentReportRequestDTO);

        return ResponseEntity.ok().body("신고 되었습니다.");
    }

    @GetMapping("/comments/report")
    public String updateComment(@RequestParam("comment_pk") Long comment_pk,
                                @RequestParam("member_pk") Long member_pk,
                                Model model) {
        model.addAttribute("comment_pk", comment_pk);
        model.addAttribute("member_pk", member_pk);
        return "members/commentReport";
    }

    //신고 수정
    @PutMapping("/report")
    public ResponseEntity<String> updateReportComment(@RequestBody CommentReportRequestDTO commentReportRequestDTO){
        boolean result = commentReportService.updateReportComment(commentReportRequestDTO);
        if(result)
            return ResponseEntity.ok("true");
        else
            return ResponseEntity.badRequest().body("false");
    }

    //신고 삭제(코멘트 + 멤버아이디)
    @DeleteMapping("/report")
    public ResponseEntity<String> deleteReportComment(@RequestBody Map<String, Long> ReportRequest){
        Long comment_pk = ReportRequest.get("comment_pk");
        Long member_pk = ReportRequest.get("member_pk");
        boolean result =  commentReportService.deleteReportComment(comment_pk, member_pk);
        if(result)
            return ResponseEntity.ok("true");
        else
            return ResponseEntity.badRequest().body("false");
    }
}
