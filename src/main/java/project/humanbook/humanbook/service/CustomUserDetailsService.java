package project.humanbook.humanbook.service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.repository.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId);
        if (member == null) {
            throw new UsernameNotFoundException("User not found with loginId: " + loginId);
        }

        // Null 또는 빈 문자열 검사
        if (member.getLoginId() == null || member.getLoginId().isEmpty() ||
                member.getPassword() == null || member.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }

        return new User(member.getLoginId(), member.getPassword(), member.getAuthorities());
    }
}
