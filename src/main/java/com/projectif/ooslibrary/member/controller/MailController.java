package com.projectif.ooslibrary.member.controller;

import com.projectif.ooslibrary.member.dto.EmaiCodelVerifyDTO;
import com.projectif.ooslibrary.member.dto.EmailVerifyRequestDTO;
import com.projectif.ooslibrary.member.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    // 이메일 인증 발송
    @PostMapping("")
    public String sendEmail(@RequestBody EmailVerifyRequestDTO dto) {

        String verifyCode = mailService.sendCertificationMail(dto.getEmail(), dto.getName());
        log.info("인증 코드 = {}", verifyCode);

        return "인증코드 발송 완료!!";
    }

    // 이메일 인증 버튼 -> 이메일 인증
    @PostMapping("/verify")
    public String verifyEmail(@RequestBody EmaiCodelVerifyDTO code) {

        String verifiedEmail = mailService.verifyEmailByCode(code.getCode());
        log.info("[MailController] [verifyEmail] 인증된 이메일 : {}", verifiedEmail);
        return verifiedEmail;
    }

    /**
     * 이메일 인증 발송 -> 이메일 인증 로직 통과 -> 새 비밀번호 인증 이메일로 보내주기
     * @param dto
     * @return 새로운 패스워드 발송
     */
    @PostMapping("/findPassword")
    public String findPassword(@RequestBody EmailVerifyRequestDTO dto) {
        String newPassword = mailService.sendNewPasswordMail(dto.getEmail(), dto.getName());
        log.info("새 비밀번호 = {}", newPassword);
        return newPassword;
    }

    /**
     * 이메일 인증 발송 -> 이메일 인증 로직 통과 -> 아이디 정보 보내주기
     * @param dto
     * @return memberId
     */
    @PostMapping("/findId")
    public String findMemberId(@RequestBody EmailVerifyRequestDTO dto) {
        String memberId = mailService.sendMemberId(dto.getEmail(), dto.getName());
        log.info("아이디 = {}", memberId);
        return memberId;
    }
}
