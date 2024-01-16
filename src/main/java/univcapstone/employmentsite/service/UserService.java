package univcapstone.employmentsite.service;

<<<<<<< HEAD
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.repository.ConcreteUserRepository;
import univcapstone.employmentsite.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository=new ConcreteUserRepository();
=======
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
>>>>>>> 8c17459dc5b763643558a8152485d0f74f3e0d54

    /**
     * 회원가입
     */
<<<<<<< HEAD
    public User registUser(User user){
        validateDuplicateLoginId(user);
        userRepository.save(user);
        return user;
    }

    private void validateDuplicateLoginId(User user) {
        userRepository.findById(user.getLoginId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }

    public User deleteUser(User user){
        userRepository.delete(user.getId());
        return user;
    }

    public User login(String login_id,String password){
        return userRepository.login(login_id,password);
    }

=======

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

>>>>>>> 8c17459dc5b763643558a8152485d0f74f3e0d54
}
