package project.humanbook.humanbook.service;

import org.springframework.stereotype.Service;

import project.humanbook.humanbook.entity.Book;
import project.humanbook.humanbook.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository){
    this.bookRepository = bookRepository;
  }
  public Book saveBook(String title, String author, byte[] epubContent, boolean isLiked) {
    Book book = new Book();
    book.setTitle(title);
    book.setAuthor(author);
    book.setEpubContent(epubContent);
    book.setLiked(isLiked);
    return bookRepository.save(book);
  }

  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  public Optional<Book> findById(Integer id){
    return bookRepository.findById(id);
  }
}
