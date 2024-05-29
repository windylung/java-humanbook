package project.humanbook.humanbook.service;

import lombok.RequiredArgsConstructor;
import project.humanbook.humanbook.domain.Member;
import project.humanbook.humanbook.domain.dto.CustomSecurityUserDetails;
import project.humanbook.humanbook.repository.MemberRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByLoginId(username);

        if (member != null) {
            return new CustomSecurityUserDetails(member);
        }
        return null;
    }
}
