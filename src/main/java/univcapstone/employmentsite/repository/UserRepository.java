package univcapstone.employmentsite.repository;

import univcapstone.employmentsite.domain.User;

public interface UserRepository {
    //유저 회원가입
    User regist(User user);
    //유저 회원탈퇴
    int withdrawal(int user_id);
    //유저 로그인
    int login(String loginId,String password);
    //유저 로그아웃
    int logout(User user);
    //아이디 변경
    int changeId(User user);
    //비밀번호 변경
    int changePassword(User user);
}
