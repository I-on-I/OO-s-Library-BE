package com.projectif.ooslibrary.comment.controller;

import com.projectif.ooslibrary.comment.domain.CommentDTO;
import com.projectif.ooslibrary.comment.domain.CommentVO;
import com.projectif.ooslibrary.comment.dto.CommentRequestDTO;
import com.projectif.ooslibrary.comment.dto.PageRequestDTO;
import com.projectif.ooslibrary.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

//@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //코멘트 생성
    @PostMapping("/comments/insert")
    public ResponseEntity<?> insertComment(CommentRequestDTO commentRequestDTO) throws Exception{
        commentService.insertComment(commentRequestDTO);
        System.out.println(commentRequestDTO);
        return ResponseEntity.ok().body("코멘트가 정상적으로 등록되었습니다.");
    }

    //코멘트 리스트(페이징 처리)
    @PostMapping("/comments/list")
    @ResponseBody
    public ResponseEntity<List<CommentDTO>> list(@RequestBody PageRequestDTO pageRequestDTO) throws Exception {

        List<CommentVO> comments = commentService.getComments(pageRequestDTO);

        //날짜 형태 변환
        List<CommentDTO> comment = comments.stream().map(CommentVO -> {
            CommentDTO dto = new CommentDTO();
            dto.setComment_pk(CommentVO.getComment_pk());
            dto.setMember_pk(CommentVO.getMember_pk());
            dto.setComment_title(CommentVO.getComment_title());
            dto.setComment_content(CommentVO.getComment_content());
            dto.setCreated_date(CommentVO.getCreated_date());
            dto.setModified_date(CommentVO.getModified_date());
            dto.setTotal_like(CommentVO.getTotal_like());
            dto.setBook_pk(CommentVO.getBook_pk());
            dto.setMy_library_pk(CommentVO.getMy_library_pk());

            return dto;
        }).collect(Collectors.toList());

        //html - body로 날짜 변환 된 데이터 이동
        return ResponseEntity.ok().body(comment);
    }

    //코멘트 수정
    @GetMapping("/comments/updateComment")
    public String updateComment(@RequestParam("comment_pk") Long comment_pk,
                                Model model) {
//        if (id != session.getAttribute("pk")) {
//            throw new SessionMemberNotMatchException("접근이 허용되지 않는 정보입니다");
//        }
        System.out.println(comment_pk);
        //List<CommentVO> commentList = commentService.getById(comment_pk);
        CommentVO comment = commentService.getById(comment_pk).get(0);
        System.out.println(comment);
        model.addAttribute("comment", comment);
        return "members/commentUpdate";
    }
    @PostMapping("/comments/updateComment")
    public ResponseEntity<?> updateComment(CommentRequestDTO commentRequestDTO) {
        commentService.updateComment(commentRequestDTO);
        return ResponseEntity.ok().body("댓글이 성공적으로 업데이트되었습니다.");
    }

    //코멘트 삭제
    @DeleteMapping("/comments/delete/{comment_pk}")
    public ResponseEntity<String> deleteComment(@PathVariable Long comment_pk) {
        commentService.deleteComment(comment_pk);
        System.out.println("댓글 삭제: " + comment_pk);

        return ResponseEntity.ok().body("댓글이 삭제되었습니다.");
    }
}

