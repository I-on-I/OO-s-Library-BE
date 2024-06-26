package com.projectif.ooslibrary.member.controller;

import com.projectif.ooslibrary.member.dto.MemberCheckPasswordRequestDTO;
import com.projectif.ooslibrary.member.dto.MemberJoinRequestDTO;
import com.projectif.ooslibrary.member.dto.MemberResponseDTO;
import com.projectif.ooslibrary.member.dto.MemberUpdateRequestDTO;
import com.projectif.ooslibrary.member.exception.SessionMemberNotMatchException;
import com.projectif.ooslibrary.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final HttpSession session;

    // 회원 정보 한 건 조회 -> 나중에 삭제 처리된 회원은 안나오도록 하기.
    @GetMapping("/{id}")
    @ResponseBody
    public MemberResponseDTO getMember(@PathVariable("id") Long id) {

        if (id != session.getAttribute("pk")) {
            throw new SessionMemberNotMatchException("접근이 허용되지 않는 정보입니다");
        }

        return memberService.getMember(id);
    }

    // 회원 마이페이지 접근 시 -> 비밀 번호 체크 기능
    @PostMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(@RequestBody @Validated MemberCheckPasswordRequestDTO dto) {

        if (dto.getMemberPk() != session.getAttribute("pk")) {
            throw new SessionMemberNotMatchException("접근이 허용되지 않는 정보입니다");
        }

        return memberService.checkPassword(dto);
    }

    // 회원 전체 리스트 조회 - 어드민 전용
//    @GetMapping("")
//    public List<MemberResponseDTO> getMemberList() {
//        return memberService.getMemberList();
//    }

    // 회원 전체 리스트 조회 - 삭제 안된 회원들
    @GetMapping("")
    @ResponseBody
    public List<MemberResponseDTO> getMemberListNotDeleted() {

        if (session.getAttribute("pk") == null) {
            throw new SessionMemberNotMatchException("접근이 허용되지 않는 정보입니다");
        }

        return memberService.getMemberListExceptDeleted();
    }

    // 회원 가입 페이지 이동
    @GetMapping("/join")
    public String memberJoinPage(Model model) {
        model.addAttribute("member", new MemberJoinRequestDTO());
        return "members/join";
    }

    // 회원 가입
    @PostMapping("")
    public String memberJoin(@ModelAttribute("member") @Validated MemberJoinRequestDTO member, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "members/join";
        }

        if (memberService.memberJoin(member)) {
            return "redirect:/login";
        } else {
            return "redirect:/members/join";
        }

    }

    // 회원 수정 페이지 이동
    @GetMapping("/{id}/update")
    public String memberUpdatePage(@PathVariable("id") Long id, Model model) {

        if (id != session.getAttribute("pk")) {
            throw new SessionMemberNotMatchException("접근이 허용되지 않는 정보입니다");
        }

        MemberResponseDTO memberInfo = memberService.getMember(id);

        MemberUpdateRequestDTO member = MemberUpdateRequestDTO.builder()
                        .memberPk(memberInfo.getMemberPk())
                        .memberName(memberInfo.getMemberName())
                        .memberEmail(memberInfo.getMemberEmail())
                        .memberPassword(memberInfo.getMemberPassword())
                        .memberGender(memberInfo.getMemberGender())
                        .memberProfileImg(memberInfo.getMemberProfileImg())
                        .build();
        model.addAttribute("member", member);
        return "members/memberUpdate";
    }

    // 회원 수정
    @PostMapping("/{id}/update")
    public String memberUpdate(@PathVariable("id") Long id,
                               @ModelAttribute("dto") @Validated MemberUpdateRequestDTO dto,
                               BindingResult bindingResult,
                               Model model) {

        if (id != session.getAttribute("pk")) {
            throw new SessionMemberNotMatchException("접근이 허용되지 않는 정보입니다");
        }
        if (bindingResult.hasErrors()) {
            return "members/memberUpdate";
        }

        dto.setMemberPk(id);
        boolean updated = memberService.memberUpdate(dto);
        log.info("회원 정보 수정 성공 여부 = {}", updated);

        // 현재 세션 정보 변경
        if (updated) {
            MemberResponseDTO member = memberService.getMember((Long) session.getAttribute("pk"));
            session.setAttribute("name", member.getMemberName());
            session.setAttribute("profile", member.getMemberProfileImg());
        }

        return "redirect:/members/" + id + "/update";
    }

    // 회원 삭제
    @PostMapping("/{id}/delete")
    @ResponseBody
    public boolean memberDelete(@PathVariable("id") Long id, @RequestBody Map<String, String> passwordMap) {

        if (id != session.getAttribute("pk")) {
            throw new SessionMemberNotMatchException("접근이 허용되지 않는 정보입니다");
        }

        String memberPassword = passwordMap.get("memberPassword");
//        log.info("memberPassword = {}", memberPassword);
        boolean isDeleted = memberService.memberDelete(id, memberPassword);
        if (isDeleted) {
            log.info("회원 삭제 성공 -> 로그아웃하기");
            session.invalidate();
            // JSESSIONID 쿠키 삭제는 기본적으로 관리하는 서블릿 컨테이너가 시큐리티에서 HTTP 세션 초기화를 하면 삭제시켜줌.
        }
        return isDeleted;
    }

}
