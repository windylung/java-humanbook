package project.humanbook.humanbook.service;

import org.springframework.stereotype.Service;

import project.humanbook.humanbook.Book;
import project.humanbook.humanbook.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository){
    this.bookRepository = bookRepository;
  }
  public Book saveBook(Book book) {
    return bookRepository.save(book);
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Optional<Book> findById(Integer id){
    return bookRepository.findById(id);
  }
}
