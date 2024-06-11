package project.humanbook.humanbook.service;

import org.springframework.stereotype.Service;

import project.humanbook.humanbook.domain.dto.BookDto;
import project.humanbook.humanbook.entity.Book;
import project.humanbook.humanbook.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository){
    this.bookRepository = bookRepository;
  }
  public Book save(Book book) {

    return bookRepository.save(book);
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Optional<Book> findById(Integer id){
    return bookRepository.findById(id);
  }
  public List<BookDto> findAllBooks() {
    List<Book> books = bookRepository.findAll();
    return books.stream()
            .map(book -> new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.isLiked()))
            .collect(Collectors.toList());
  }
}
