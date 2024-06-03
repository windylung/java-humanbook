package project.humanbook.humanbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.humanbook.humanbook.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
}
