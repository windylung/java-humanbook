package project.humanbook.humanbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {
    private final BookService bookService = new BookService();

    @GetMapping("/")
    public String getMethodName(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "main";
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
    public String getBookDetail(@RequestParam("id") Integer bookId, Model model) {
        Book book = bookService.findById(bookId);
        model.addAttribute("title", book.getTitle());
        model.addAttribute("author", book.getAuthor());
        return "bookDetail";
    }

}
