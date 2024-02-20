package univcapstone.employmentsite.service;

import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.UserDeleteDto;
import univcapstone.employmentsite.dto.UserEditDto;
import univcapstone.employmentsite.ex.custom.UserAuthenticationException;
import univcapstone.employmentsite.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Transactional
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
                .orElseThrow(() -> new UserAuthenticationException("아이디 또는 비밀번호가 맞지 않습니다."));
    }

    /**
     * 로그인 ID 중복 검사 함수
     */
    public void validateDuplicateLoginId(User user) {
        userRepository.findByLoginId(user.getLoginId())
                .ifPresent(u -> {
                    throw new IllegalStateException(u + "은(는) 이미 존재하는 아이디입니다.");
                });
    }

    public void validateDuplicateLoginId(String userId) {
        if(userId == null){
            throw new IllegalStateException("아이디를 입력해주세요");
        }
        userRepository.findByLoginId(userId)
                .ifPresent(u -> {
                    throw new IllegalStateException(u + "은(는) 이미 존재하는 아이디입니다.");

                });
    }

    /**
     * 아이디로 회원 찾기
     * @param id
     * @return
     */
    public User findUserByLoginId(String id){
        return userRepository.findByLoginId(id)
                .orElse(null);
    }
    /**
     * ID 찾기
     *
     * @param name
     * @param email
     * @return
     */
    public User findId(String name, String email) {
        return userRepository.findByEmail(email)
                .filter(u -> u.getName().equals(name))
                .orElse(null);
    }

    /**
     * 비밀번호 찾기
     *
     * @param userId
     * @param name
     * @param email
     * @return
     */
    public User findPassword(String userId, String name, String email) {
        return userRepository.findByLoginId(userId)
                .filter(u -> u.getName().equals(name))
                .filter(u -> u.getEmail().equals(email))
                .orElse(null);
    }

    /**
     * 비밀번호 변경
     *
     * @param id
     * @param newPassword
     */
    public void updatePassword(Long id, String newPassword) {
        userRepository.updatePassword(id, newPassword);
    }

    /**
     * 계정 삭제
     * @param userDeleteDto
     */
    public void deleteUser(UserDeleteDto userDeleteDto){
        User user = userRepository.findByLoginId(userDeleteDto.getLoginId())
                .orElseThrow(() -> new IllegalStateException("삭제하려는 계정을 찾을 수 없습니다."));

        if (user.getPassword().equals(userDeleteDto.getPassword())) {
            userRepository.delete(user.getId());
        } else {
            throw new IllegalStateException("삭제하려는 계정의 패스워드가 일치하지 않습니다.");
        }
    }

    public void editUser(UserEditDto editDto){
        userRepository.findByLoginId(editDto.getLoginId())
                .ifPresent(u->{
                    userRepository.editUser(u.getId(),editDto);
                });
        userRepository.findByLoginId(editDto.getLoginId())
                .orElseThrow(()-> new IllegalStateException("수정하려는 계정을 찾을 수 없습니다."));
    }

    public void editPass(User user,String newPass){
        userRepository.updatePassword(user.getId(),newPass);
    }

    public void editNickname(User user, String newNickname) {
        userRepository.updateNickname(user.getId(),newNickname);
    }

}
