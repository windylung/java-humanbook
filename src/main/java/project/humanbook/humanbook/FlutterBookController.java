package project.humanbook.humanbook;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.domain.dto.LoginRequest;
import project.humanbook.humanbook.service.BookService;
import project.humanbook.humanbook.service.MemberService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
public class FlutterBookController {

    private final BookService bookService;
    private final MemberService memberService;

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
}
