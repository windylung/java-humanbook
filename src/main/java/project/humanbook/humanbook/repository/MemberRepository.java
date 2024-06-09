package project.humanbook.humanbook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import project.humanbook.humanbook.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // Optional<Member> findByLoginIdOpt(String loginId);

    boolean existsByLoginId(String loginId);

    Member findByLoginId(String loginId);

}