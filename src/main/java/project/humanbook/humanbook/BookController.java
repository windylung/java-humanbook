package project.humanbook.humanbook;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.service.BookService;
import project.humanbook.humanbook.service.MemberService;

@Controller
@RequiredArgsConstructor

public class BookController {

    private final BookService bookService;
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

        return "main"; //html
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model, @SessionAttribute(name = "userId", required = false) Long userId) {
        HttpSession session = request.getSession(false);  // Session이 없으면 null return
        if(session != null) {
            session.invalidate();
        }

        model.addAttribute("books", bookService.getAllBooks());

//        User loginUser = userService.getLoginUserById(userId);
        // User loginUser = userService.getLoginUserById(userId);

//        if(loginUser != null) {
//            model.addAttribute("nickname", loginUser.getNickname());
//        }
        // if(loginUser != null) {
        //     model.addAttribute("nickname", loginUser.getNickname());
        // }
        return "redirect:/";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/";
    }

    @GetMapping("/book-detail")
    public String getBookDetail(@RequestParam("id") Integer bookId, Model model) {
        Optional<Book> bookOptional = bookService.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            model.addAttribute("title", book.getTitle());
            model.addAttribute("author", book.getAuthor());
            return "bookDetail";
        } else {
            model.addAttribute("error", "No book found with ID " + bookId);
            return "redirect:/";
        }
    }
//
//    public String getBookDetail(@RequestParam("id") Integer bookId, Model model, @SessionAttribute(name = "userId", required = false) Long userId) {
//        Book book = bookService.findById(bookId);
//        model.addAttribute("title", book.getTitle());
//        model.addAttribute("author", book.getAuthor());
//
//        User loginUser = userService.getLoginUserById(userId);
//
//        if(loginUser != null) {
//            model.addAttribute("nickname", loginUser.getNickname());
//        }
//        else {
//            // login session이 없는 경우
//        }
//        return "bookDetail";
//    }

//
//    public String getBookDetail(@RequestParam("id") Integer bookId, Model model, @SessionAttribute(name = "userId", required = false) Long userId) {
//        Book book = bookService.findById(bookId);
//        model.addAttribute("title", book.getTitle());
//        model.addAttribute("author", book.getAuthor());
//
//        User loginUser = userService.getLoginUserById(userId);
//
//        if(loginUser != null) {
//            model.addAttribute("nickname", loginUser.getNickname());
//        }
//        else {
//            // login session이 없는 경우
//        }
//        return "bookDetail";
//    }
    // public String getBookDetail(@RequestParam("id") Integer bookId, Model model, @SessionAttribute(name = "userId", required = false) Long userId) {
    //     Optional<Book> bookOptional = bookService.findById(bookId);

    //     model.addAttribute("title", book.getTitle());
    //     model.addAttribute("author", book.getAuthor());

    //     return "bookDetail";
    // }

    @GetMapping("/write")
    public String getWrite(Model model, @SessionAttribute(name = "userId", required = false) Long userId) {

//        User loginUser = userService.getLoginUserById(userId);
//
//        if(loginUser != null) {
//            model.addAttribute("nickname", loginUser.getNickname());
//        }
//        else {
//            // login session이 없는 경우
//        }

        return "write";
    }

}
