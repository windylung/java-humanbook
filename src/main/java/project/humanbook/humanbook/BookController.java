package project.humanbook.humanbook;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.service.MemberService;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService = new BookService();
    private final MemberService memberService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("books", bookService.getAllBooks());

        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        Member loginMember = memberService.getLoginMemberByLoginId(loginId);

        if (loginMember != null) {
            model.addAttribute("nickname", loginMember.getName());
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

        // User loginUser = userService.getLoginUserById(userId);

        // if(loginUser != null) {
        //     model.addAttribute("nickname", loginUser.getNickname());
        // }
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

        // User loginUser = userService.getLoginUserById(userId);

        // if(loginUser != null) {
        //     model.addAttribute("nickname", loginUser.getNickname());
        // }
        // else {
        //     // login session이 없는 경우
        // }
        return "bookDetail";
    }

    @GetMapping("/write")
    public String getWrite(Model model, @SessionAttribute(name = "userId", required = false) Long userId) {
        // User loginUser = userService.getLoginUserById(userId);

        // if(loginUser != null) {
        //     model.addAttribute("nickname", loginUser.getNickname());
        // }
        // else {
        //     // login session이 없는 경우
        // }
        return "write";
    }

}
