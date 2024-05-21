package project.humanbook.humanbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import project.humanbook.humanbook.domain.entity.User;
import project.humanbook.humanbook.service.UserService;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService = new BookService();

    private final UserService userService;

    @GetMapping("/")
    public String home(Model model, @SessionAttribute(name = "userId", required = false) Long userId) {
        model.addAttribute("books", bookService.getAllBooks());

        User loginUser = userService.getLoginUserById(userId);

        if(loginUser != null) {
            model.addAttribute("nickname", loginUser.getNickname());
        }
        return "main";
    }

    @GetMapping("/logout") 
    public String logout(HttpServletRequest request, Model model, @SessionAttribute(name = "userId", required = false) Long userId) {
        HttpSession session = request.getSession(false);  // Session이 없으면 null return
        if(session != null) {
            session.invalidate();
        }

        model.addAttribute("books", bookService.getAllBooks());

        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        User loginUser = userService.getLoginUserById(userId);

        if(loginUser != null) {
            model.addAttribute("nickname", loginUser.getNickname());
        }
        return "redirect:/";
    }

    @PostMapping("/save")
    public String postMethodName(@ModelAttribute Book book, Model model) {
        bookService.addBook(book.getTitle(), book.getAuthor());
        // model.addAttribute("books", bookService.getAllBooks());
        // System.out.println(model);
        // return "main";
        return "redirect:/";
    }

    @GetMapping("/book-detail")
    public String getBookDetail(@RequestParam("id") Integer bookId, Model model, @SessionAttribute(name = "userId", required = false) Long userId) {
        Book book = bookService.findById(bookId);
        model.addAttribute("title", book.getTitle());
        model.addAttribute("author", book.getAuthor());

        User loginUser = userService.getLoginUserById(userId);

        if(loginUser != null) {
            model.addAttribute("nickname", loginUser.getNickname());
        }
        else {
            // login session이 없는 경우
        }
        return "bookDetail";
    }

    @GetMapping("/write")
    public String getWrite(Model model, @SessionAttribute(name = "userId", required = false) Long userId) {
        User loginUser = userService.getLoginUserById(userId);

        if(loginUser != null) {
            model.addAttribute("nickname", loginUser.getNickname());
        }
        else {
            // login session이 없는 경우
        }
        return "write";
    }

}
