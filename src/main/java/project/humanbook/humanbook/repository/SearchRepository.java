package project.humanbook.humanbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.humanbook.humanbook.entity.Book;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleContaining(String keyword);
    List<Book> findByAuthorContaining(String keyword);
}
