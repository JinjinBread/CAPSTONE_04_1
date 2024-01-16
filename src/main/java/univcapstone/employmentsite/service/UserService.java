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

}
