package univcapstone.employmentsite.repository;

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

    private final EntityManager em; //JPA

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

    public Optional<User> findByEmail(String email){
        List<User> users = em.createQuery("select u from User u where u.email=:email",User.class)
                .setParameter("email",email)
                .getResultList();

        return users.stream().findAny();
    }

    public void delete(Long id) {
        User findUser = em.find(User.class, id);
        em.remove(findUser);
    }

    public void updatePassword(Long id, String password) {
        em.createQuery("update User u set u.password = :password where u.id = :id")
                .setParameter("password", password)
                .executeUpdate();
    }

}
