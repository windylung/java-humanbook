package project.humanbook.humanbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.humanbook.humanbook.entity.Manuscript;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManuscriptRepository extends JpaRepository<Manuscript, Long> {
    List<Manuscript> findAllByUserId(Long userId);
    Optional<Manuscript> findByUserIdAndStep(Long userId, int step);
}