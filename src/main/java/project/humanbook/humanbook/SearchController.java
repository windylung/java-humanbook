package project.humanbook.humanbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.humanbook.humanbook.service.SearchService;
 import project.humanbook.humanbook.entity.Book;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public List<Book> searchBooks(@RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "type", required = false) String type) {
        if (keyword != null && type != null) {
            if ("author".equalsIgnoreCase(type)) {
                return searchService.searchBooksByAuthor(keyword);
            } else {
                return searchService.searchBooksByTitle(keyword);
            }
        }
        return searchService.getAllBooks();
    }
}

