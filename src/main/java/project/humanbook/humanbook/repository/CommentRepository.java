package project.humanbook.humanbook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.humanbook.humanbook.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Optional<Comment> findByBoardIdAndId(int articleId, int id);
}
