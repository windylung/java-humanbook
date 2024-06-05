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
        // 가상의 데이터로 모델을 채웁니다
        List<Book> books = getBooks(); // 이 메서드는 책 리스트를 반환해야 합니다
        model.addAttribute("books", books);
        model.addAttribute("loginId", "user123");
        model.addAttribute("nickname", "User Nickname");
        model.addAttribute("role", "ROLE_USER");
        return "myPage";
    }

    private List<Book> getBooks() {
        // 실제 데이터베이스나 다른 소스에서 책 데이터를 가져오는 로직을 구현해야 합니다
        return Arrays.asList(
            new Book(3, "Book Title 3", "Author 3"),
            new Book(4, "Book Title 4", "Author 4"),
            new Book(5, "Book Title 5", "Author 5")
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
