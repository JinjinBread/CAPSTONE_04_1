package univcapstone.employmentsite.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.repository.UserRepository;

import java.util.Collections;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return userRepository.findByLoginId(loginId)
                .map(user -> createUser(loginId, user))
                .orElseThrow(() -> new UsernameNotFoundException(loginId + "을(를) 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String loginId, User user) {

        if (!user.isActivated()) {
            throw new RuntimeException(loginId + " -> 활성화되어 있지 않습니다.");
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthority().toString());

        return new org.springframework.security.core.userdetails.User(user.getLoginId(),
                user.getPassword(),
                Collections.singleton(grantedAuthority));
    }
}
