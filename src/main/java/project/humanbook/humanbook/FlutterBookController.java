package project.humanbook.humanbook;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.humanbook.humanbook.service.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(originPatterns = "http://localhost:*")
public class FlutterBookController {

    private final BookService bookService;

    @GetMapping("/book/list")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}
