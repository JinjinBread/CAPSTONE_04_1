package univcapstone.employmentsite.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.Authority;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.*;
import univcapstone.employmentsite.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import univcapstone.employmentsite.token.TokenProvider;

import java.util.Optional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 아이디로 회원 찾기
     *
     * @param id
     * @return
     */
    public User findUserByLoginId(String id) {
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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));
        user.updatePassword(newPassword);
    }

    /**
     * 계정 삭제
     *
     * @param userDeleteDto
     */
    public void deleteUser(UserDeleteDto userDeleteDto) {
        User user = userRepository.findByLoginId(userDeleteDto.getLoginId())
                .orElseThrow(() -> new IllegalStateException("삭제하려는 계정을 찾을 수 없습니다."));

        if (user.getPassword().equals(userDeleteDto.getPassword())) {
            userRepository.delete(user);
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
        if (userId == null) {
            throw new IllegalStateException("아이디를 입력해주세요");
        }
        userRepository.findByLoginId(userId)
                .ifPresent(u -> {
                    throw new IllegalStateException(u + "은(는) 이미 존재하는 아이디입니다.");

                });
    }

//    public void editUser(UserEditDto editDto) {
//        User user = userRepository.findByLoginId(editDto.getLoginId())
//                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));
//        user.
//        userRepository.findByLoginId(editDto.getLoginId())
//                .orElseThrow(() -> new IllegalStateException("수정하려는 계정을 찾을 수 없습니다."));
//    }

    public void editPass(User user, String newPass) {
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));
        findUser.updatePassword(newPass);
    }

    public void editNickname(User user, String newNickname) {
        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));
        findUser.updateNickname(newNickname);
    }

}
