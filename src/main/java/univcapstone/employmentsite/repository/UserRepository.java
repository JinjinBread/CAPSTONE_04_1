package univcapstone.employmentsite.repository;

import univcapstone.employmentsite.domain.User;

import java.util.Optional;

public interface UserRepository {
    //유저 회원가입
    User save(User user);
    //유저 ID로 찾기
    Optional<User> findById(String loginId);
    //유저 회원탈퇴
    int delete(Long user_id);
    //로그인
    public User login(String loginId, String password);
    //비밀번호 변경
    int changePassword(User user);

}
