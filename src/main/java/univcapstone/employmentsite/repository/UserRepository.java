package univcapstone.employmentsite.repository;

<<<<<<< HEAD
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
=======
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    @Transactional
    public void save(User user) {
        em.persist(user);
    }

    public Optional<User> findByLoginId(String loginId) {
        List<User> users = em.createQuery("select u from User u where u.loginId = :loginId", User.class)
                .setParameter("loginId", loginId)
                .getResultList();

        return users.stream().findAny();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
>>>>>>> 8c17459dc5b763643558a8152485d0f74f3e0d54

}
