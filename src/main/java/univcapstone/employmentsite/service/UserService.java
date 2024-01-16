package univcapstone.employmentsite.service;

import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.repository.ConcreteUserRepository;
import univcapstone.employmentsite.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.repository.UserRepository;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원가입
     */
    public Long join(User user) {
        validateDuplicateLoginId(user); //중복 로그인 아이디 검증
        userRepository.save(user);
        return user.getId();
    }

    /**
     * 로그인
     */
    public User login(String loginId, String password) {
        return userRepository.findByLoginId(loginId)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }

    private void validateDuplicateLoginId(User user) {
        userRepository.findByLoginId(user.getLoginId())
                .ifPresent(u -> {
                    throw new IllegalStateException(u + "은(는) 이미 존재하는 아이디입니다.");
                });
    }

}
