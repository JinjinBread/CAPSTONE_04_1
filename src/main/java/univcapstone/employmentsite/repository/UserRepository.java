package univcapstone.employmentsite.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.dto.UserEditDto;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em; //JPA

    public Long save(User user) {
        em.persist(user);
        return user.getId();
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

    public Optional<User> findByEmail(String email) {
        List<User> users = em.createQuery("select u from User u where u.email=:email", User.class)
                .setParameter("email", email)
                .getResultList();

        return users.stream().findAny();
    }

    public void delete(Long id) {
        User findUser = em.find(User.class, id);
        em.remove(findUser);
    }

    public void updatePassword(Long id, String password) {
        User user = em.find(User.class, id);
        user.updatePassword(password);
    }

    public void editUser(Long id, UserEditDto userData) {
        User user = em.find(User.class, id);
        user.updatePassword(userData.getPw());
        user.updateNickname(userData.getNickname());
    }

    public void updateNickname(Long id, String newNickname) {
        User user = em.find(User.class, id);
        user.updateNickname(newNickname);
    }
}
