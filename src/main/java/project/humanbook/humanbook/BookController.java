package project.humanbook.humanbook;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import org.springframework.web.multipart.MultipartFile;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.entity.Book;
import project.humanbook.humanbook.service.BookService;
import project.humanbook.humanbook.service.MemberService;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;
    private final MemberService memberService;

//    @PostMapping("/api/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false); // Session이 없으면 null return
//        if (session != null) {
//            session.invalidate();
//            return ResponseEntity.ok("Logout successful"); // 로그아웃 성공 시 200 상태 코드와 함께 메시지 반환
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session does not exist"); // 세션이 없는 경우 401 상태
//                                                                                                  // 코드와 함께 메시지 반환
//        }
//    }

//    @PostMapping
//    public Book createBook(
//            @RequestParam("title") String title,
//            @RequestParam("author") String author,
//            @RequestParam("epub") MultipartFile epubFile,
//            @RequestParam(value = "isLiked", defaultValue = "false") boolean isLiked) {
//
//        try {
//            byte[] epubContent = epubFile.getBytes();
//            return bookService.saveBook(title, author, epubContent, isLiked);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to save book", e);
//        }
//    }

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
    // public String getBookDetail(@RequestParam("id") Integer bookId, Model model,
    // @SessionAttribute(name = "userId", required = false) Long userId) {
    // Book book = bookService.findById(bookId);
    // model.addAttribute("title", book.getTitle());
    // model.addAttribute("author", book.getAuthor());
    //
    // User loginUser = userService.getLoginUserById(userId);
    //
    // if(loginUser != null) {
    // model.addAttribute("nickname", loginUser.getNickname());
    // }
    // else {
    // // login session이 없는 경우
    // }
    // return "bookDetail";
    // }

    //
    // public String getBookDetail(@RequestParam("id") Integer bookId, Model model,
    // @SessionAttribute(name = "userId", required = false) Long userId) {
    // Book book = bookService.findById(bookId);
    // model.addAttribute("title", book.getTitle());
    // model.addAttribute("author", book.getAuthor());
    //
    // User loginUser = userService.getLoginUserById(userId);
    //
    // if(loginUser != null) {
    // model.addAttribute("nickname", loginUser.getNickname());
    // }
    // else {
    // // login session이 없는 경우
    // }
    // return "bookDetail";
    // }
    // public String getBookDetail(@RequestParam("id") Integer bookId, Model model,
    // @SessionAttribute(name = "userId", required = false) Long userId) {
    // Optional<Book> bookOptional = bookService.findById(bookId);

    // model.addAttribute("title", book.getTitle());
    // model.addAttribute("author", book.getAuthor());

    // return "bookDetail";
    // }

    @GetMapping("/write")
    public String getWrite(Model model, @SessionAttribute(name = "userId", required = false) Long userId) {

        // User loginUser = userService.getLoginUserById(userId);
        //
        // if(loginUser != null) {
        // model.addAttribute("nickname", loginUser.getNickname());
        // }
        // else {
        // // login session이 없는 경우
        // }

        return "write";
    }

}
