package project.humanbook.humanbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.humanbook.humanbook.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByLoginId(String loginId);

    Member findByLoginId(String loginId);
}