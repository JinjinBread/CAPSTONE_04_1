package univcapstone.employmentsite.repository;

import univcapstone.employmentsite.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConcreteUserRepository {
    private static Map<Long,User> storage=new HashMap<>();
    private static long seqeunce=0L;
    public User save(User user) {
        user.setId(++seqeunce);
        storage.put(user.getId(),user);
        return null;
    }

    public Optional<User> findById(String loginId) {
         return storage.values().stream()
                .filter(user -> user.getLoginId().equals(loginId))
                .findAny();

    }

    public int delete(Long user_id) {
        storage.remove("user_id",user_id);
        return 0;
    }

    public int changePassword(User user) {
        return 0;
    }

    public User login(String loginId, String password) {
        Optional<User> matchingUser = storage.values().stream()
                .filter(user -> user != null && user.getLoginId().equals(loginId) && user.getPassword().equals(password))
                .findFirst();

        if (matchingUser.isPresent()) {
            User user = matchingUser.get();
            return user;
        } else {
            return null;
        }

    }

    /*
    @Override
    public int logout(User user) {
        return 0;
    }
    */


}
