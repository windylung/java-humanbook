package project.humanbook.humanbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.entity.Manuscript;
import project.humanbook.humanbook.service.CustomUserDetails;
import project.humanbook.humanbook.service.CustomUserDetailsService;
import project.humanbook.humanbook.service.ManuscriptService;

import java.util.List;

@RestController
@RequestMapping("/api/manuscripts")
public class ManuscriptController {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private ManuscriptService manuscriptService;

    @PostMapping
    public Manuscript createManuscript(@RequestBody ManuscriptRequest request) {
        return manuscriptService.saveManuscript(request.getUserId(), request.getStep(), request.getTitle(), request.getContent());
    }

    @GetMapping("/user")
    public String getManuscriptsByUserId(@AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println("userDetails.toString() = " + userDetails.toString());
        return "test";
    }
}


class ManuscriptRequest {
    private Long userId;
    private Integer step;
    private String title;
    private String content;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
