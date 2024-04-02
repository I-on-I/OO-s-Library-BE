package com.projectif.ooslibrary.member.controller;

import com.projectif.ooslibrary.member.dto.MemberResponseDTO;
import com.projectif.ooslibrary.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginPage() {
        return "test/login";
    }

    @PostMapping("/login_success")
    @ResponseBody
//    public String login_success(HttpServletRequest request) {
    public String login_success(HttpServletRequest request) {

        // 현재 인증된 사용자 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // 사용자 이름 가져오기 > member의 memberId값 & 회원 정보 가져오기
        String memberId = ((UserDetails) auth.getPrincipal()).getUsername();
        MemberResponseDTO member = memberService.getMember(memberId);
        // 세션에 로그인 정보 넣기 - PK, Id, Profile 이미지 3개
        HttpSession session = request.getSession(false);
        session.setAttribute("id", memberId);
        session.setAttribute("pk", member.getMemberPk());
        session.setAttribute("name", member.getMemberName());
        session.setAttribute("profile", member.getMemberProfileImg());

        /** TODO : 리액트에 session / cookie를 어떻게 던져줄 지 고민.
         *  - 스프링 컨트롤러로 오브젝트 or String을 보내도 헤더에 JSESSIONID 쿠키를 생성해서 보내줌
         *  - 세션 / 쿠키 방식을 위해 굳이 쿠키를 생성하지 않고 스프링 시큐리티가 생성해 보내주는 세션아이디를 쓰면 될 것 같음.
         */


//        return "redirect:/";
        return "세션 테스트!!! ID=" + memberId + " PK=" + member.getMemberPk() + " ProfileSrc=" + member.getMemberProfileImg();
    }
}
