package univcapstone.employmentsite.repository;

import univcapstone.employmentsite.domain.Member;

public interface MemberRepository {
    Member regist(Member member);
    int withdrawal(Member member);
    int login(String loginId,String password);
    int logout(Member member);
    int changeId(Member member);
    int changePassword(Member member);
}
