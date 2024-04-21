package project.humanbook.humanbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

    @GetMapping("/write")
    public String getWrite(Model model) {
        // model.addAttribute("data", "책 id");
        return "write";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        // model.addAttribute("data", "책 id");
        return "login";
    }

    @GetMapping("/my-page")
    public String getMyPage(Model model) {
        model.addAttribute("data", "사용자 id");
        return "myPage";
    }

}
