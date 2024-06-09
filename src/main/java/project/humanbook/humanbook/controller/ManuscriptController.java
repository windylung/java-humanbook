package project.humanbook.humanbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.humanbook.humanbook.entity.Manuscript;
import project.humanbook.humanbook.service.CustomUserDetailsService;
import project.humanbook.humanbook.service.ManuscriptService;
import project.humanbook.humanbook.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/api/manuscripts")
public class ManuscriptController {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private ManuscriptService manuscriptService;
    @Autowired
    private MemberService memberService;

    @PostMapping
    public Manuscript createManuscript(@RequestBody ManuscriptRequest request) {
        return manuscriptService.saveManuscript(request.getUserId(), request.getStep(), request.getTitle(), request.getContent());
    }

    @GetMapping("/user")
    public List<Manuscript> getManuscriptsByUserId(Authentication authentication) {
        Long id = memberService.findByLoginId(authentication.getName()).getId();
        System.out.println(id);
        return manuscriptService.getManuscriptsByUserId(id);
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
