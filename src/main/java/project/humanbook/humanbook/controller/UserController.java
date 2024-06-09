package project.humanbook.humanbook.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.service.MemberService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final MemberService memberService;

    public UserController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/mypage")
    public Member getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        // UserDetails에서 loginId를 가져와서 회원 정보를 조회
        String loginId = userDetails.getUsername();
        return memberService.findByLoginId(loginId);
    }
}
