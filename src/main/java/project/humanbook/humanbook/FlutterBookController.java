package project.humanbook.humanbook;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.domain.dto.LoginRequest;
import project.humanbook.humanbook.service.BookService;
import project.humanbook.humanbook.service.MemberService;
import project.humanbook.humanbook.service.SearchService;

import java.util.List;

@RequiredArgsConstructor
@RestController
//@CrossOrigin(originPatterns = "http://localhost:*")
public class FlutterBookController {

    @Autowired
    private SearchService searchService;
    private final BookService bookService;
    private final MemberService memberService;

    // response = http.get("http://humanbook.kr/book/list)
    @GetMapping("/book/list")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }


    @PostMapping("/login/member")
    public ResponseEntity<?> loginPage(@RequestBody LoginRequest request) {
        Member member = memberService.login(request);
        if (member == null) {
            return ResponseEntity.status(401).body("error: Invalid login credentials");
        } else {
            return ResponseEntity.ok(member);
        }
    }

    @GetMapping("/search/flutter")
    public List<Book> searchBooks(@RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "type", required = false) String type) {
        List<Book> books = null;
        if (keyword != null && type != null) {
            if ("author".equalsIgnoreCase(type)) {
                books = searchService.searchBooksByAuthor(keyword);
            } else {
                books = searchService.searchBooksByTitle(keyword);
            }
        }
        return books;
    }
}
