package project.humanbook.humanbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String getMethodName(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "main";
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
}
