package project.humanbook.humanbook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.humanbook.humanbook.service.SearchService;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "type", required = false) String type,
                              Model model) {
        List<Book> books;
        if (keyword != null && type != null) {
            if ("author".equalsIgnoreCase(type)) {
                books = searchService.searchBooksByAuthor(keyword);
            } else {
                books = searchService.searchBooksByTitle(keyword);
            }
            model.addAttribute("books", books);
        }
        return "search";
    }
}
