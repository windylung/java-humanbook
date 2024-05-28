package project.humanbook.humanbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
  private final BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository){
    this.bookRepository = bookRepository;
  }
  public Book saveBook(Book book) {
    return bookRepository.save(book);
  }

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public Optional<Book> findById(Integer id){
    return bookRepository.findById(id);
  }
}
