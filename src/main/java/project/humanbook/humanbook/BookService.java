package project.humanbook.humanbook;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookService {
  private List<Book> books = new ArrayList<>();

  public BookService() {
    books.add(new Book(0, "세상의 모든 건강한 변화를 사랑합니다", "작가1", "설명1", "본문1"));
    books.add(new Book(1, "우당탕탕 서울 전세집 구하기", "작가2", "설명2", "본문2"));
    books.add(new Book(2, "How to Dance with Rhythm", "작가3", "설명3", "본문3"));
    books.add(new Book(3, "카세트테이프는 언제나 따뜻했다", "작가4", "설명4", "본문4"));
    books.add(new Book(4, "복에 겨운 소리", "작가5", "설명5", "본문5"));
  }

  public List<Book> getAllBooks() {
    return books;
  }

  public void addBook(String title, String author, String description, String content) {
    Integer len = books.size();
    books.add(new Book(len, title, author, description, content));
  }

  public Book findById(Integer id) {
    Book book = books.get(id);
    return book;
  }
}
