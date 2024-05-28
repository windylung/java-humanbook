package project.humanbook.humanbook;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.domain.dto.JoinRequest;
import project.humanbook.humanbook.domain.dto.LoginRequest;
import project.humanbook.humanbook.service.MemberService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("joinRequest", new JoinRequest());

        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model) {
        // 비밀번호 암호화 추가한 회원가입 로직으로 회원가입
        memberService.securityJoin(joinRequest);

        // 회원가입 시 홈 화면으로 이동
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    
    @GetMapping("/my-page")
    public String getMethodName(Authentication auth, Model model) {
        Member loginMember = memberService.getLoginMemberByLoginId(auth.getName());
        model.addAttribute("nickname", loginMember.getName());
        model.addAttribute("loginId", loginMember.getLoginId());
        model.addAttribute("role", loginMember.getRole());
        return "myPage";
    }
    
}
