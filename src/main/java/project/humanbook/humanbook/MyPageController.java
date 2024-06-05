package project.humanbook.humanbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class MyPageController {

    @GetMapping("/my-page")
    public String myPage(Model model) {
        // 더미
        List<Book> books = getBooks(); // 이 메서드는 책 리스트를 반환해야 합니다
        model.addAttribute("books", books);
        model.addAttribute("loginId", "1234");
        model.addAttribute("nickname", "2345");
        model.addAttribute("role", "USER");
        return "myPage";
    }

    private List<Book> getBooks() {
        // 더미
        return Arrays.asList(
            new Book(3, "Book Title 111", "Author 123"),
            new Book(4, "Book Title 222", "Author 456"),
            new Book(5, "Book Title 333", "Author 789")
        );
    }

    static class Book {
        private int id;
        private String title;
        private String author;

        public Book(int id, String title, String author) {
            this.id = id;
            this.title = title;
            this.author = author;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
