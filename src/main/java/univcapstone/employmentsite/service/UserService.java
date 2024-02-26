package univcapstone.employmentsite.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.Authority;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.UserDeleteDto;
import univcapstone.employmentsite.dto.UserDto;
import univcapstone.employmentsite.dto.UserEditDto;
import univcapstone.employmentsite.dto.UserLoginDto;
import univcapstone.employmentsite.ex.custom.UserAuthenticationException;
import univcapstone.employmentsite.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입
     */
    public Long join(UserDto userDto) {

        validateDuplicateLoginId(userDto.getLoginId()); //중복 로그인 아이디 검증

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .loginId(userDto.getLoginId())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    /**
     * 로그인
     */
    public User login(UserLoginDto userLoginDto) {
        return userRepository.findByLoginId(userLoginDto.getLoginId())
                .filter(u -> u.getPassword().equals(userLoginDto.getPassword()))
                .orElseThrow(() -> new UserAuthenticationException("아이디 또는 비밀번호가 맞지 않습니다."));
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

    /**
     * 로그인 ID 중복 검사 함수
     */
    private void validateDuplicateLoginId(User user) {
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
