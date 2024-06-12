package project.humanbook.humanbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.humanbook.humanbook.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
