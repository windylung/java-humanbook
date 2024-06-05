package project.humanbook.humanbook.service;

import org.springframework.stereotype.Service;
import project.humanbook.humanbook.Book;
import project.humanbook.humanbook.repository.SearchRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {
  private final SearchRepository searchRepository;

  public SearchService(SearchRepository searchRepository){
    this.searchRepository = searchRepository;
  }

  public List<Book> getAllBooks() {
    return searchRepository.findAll();
  }

  public List<Book> searchBooksByTitle(String keyword) {
    return searchRepository.findByTitleContaining(keyword);
  }

  public List<Book> searchBooksByAuthor(String keyword) {
    return searchRepository.findByAuthorContaining(keyword);
  }

  public Optional<Book> findById(Integer id){
    return searchRepository.findById(id);
  }

  public Book saveBook(Book book) {
    return searchRepository.save(book);
  }
}

