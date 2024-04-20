package com.projectif.ooslibrary.comment.service;

import com.projectif.ooslibrary.comment.domain.CommentReportVO;
import com.projectif.ooslibrary.comment.dto.CommentReportRequestDTO;

import java.util.List;

public interface CommentReportService {
    List<CommentReportVO> listReportComment(CommentReportRequestDTO commentReportDTO);

    void insertReportComment(CommentReportRequestDTO commentReportRequestDTO);

    boolean updateReportComment(CommentReportRequestDTO commentReportRequestDTO);

    boolean deleteReportComment(Long comment_pk, Long member_pk);

}
