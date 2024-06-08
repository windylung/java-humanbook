package project.humanbook.humanbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.domain.dto.JoinRequest;
import project.humanbook.humanbook.domain.dto.LoginRequest;
import project.humanbook.humanbook.service.MemberService;
import org.springframework.validation.BindingResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody JoinRequest joinRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid data");
        }
        if (memberService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate login ID");
        }
        memberService.securityJoin(joinRequest);
        return ResponseEntity.ok("Join successful");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Member member = memberService.login(loginRequest);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }
        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/my-page")
    public ResponseEntity<?> getMyPage(Authentication auth) {
        if (auth == null || auth.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        Member loginMember = memberService.getLoginMemberByLoginId(auth.getName());
        if (loginMember == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member not found");
        }
        return ResponseEntity.ok(loginMember);
    }
}
