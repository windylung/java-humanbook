package project.humanbook.humanbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        bookService.addBook(book.getTitle(), book.getAuthor(), book.getDescription(), book.getContent());
        // model.addAttribute("books", bookService.getAllBooks());
        // System.out.println(model);
        // return "main";
        return "redirect:/";
    }

    @GetMapping("/book-detail/{id}")
    public String getBookDetail(@PathVariable("id") Integer bookId, Model model) {
        Book book = bookService.findById(bookId);
        model.addAttribute("title", book.getTitle());
        model.addAttribute("author", book.getAuthor());
        model.addAttribute("description", book.getDescription());
        model.addAttribute("content", book.getContent());
        return "bookDetail";
    }

}
