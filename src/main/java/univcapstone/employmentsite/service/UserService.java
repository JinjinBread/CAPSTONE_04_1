package univcapstone.employmentsite.service;

import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.repository.ConcreteUserRepository;
import univcapstone.employmentsite.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository=new ConcreteUserRepository();

    /**
     * 회원가입
     */
    public User registUser(User user){
        validateDuplicateUser(user);
        userRepository.join(user);
        return user;
    }

    private void validateDuplicateUser(User user) {
        userRepository.findById(user.getLoginId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다");
                });
    }

}
