package univcapstone.employmentsite.repository;

import univcapstone.employmentsite.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConcreteUserRepository implements UserRepository{
    private static Map<Long,User> storage=new HashMap<>();
    private static long seqeunce=0L;
    @Override
    public User join(User user) {
        user.setId(++seqeunce);
        storage.put(user.getId(),user);
        return null;
    }

    @Override
    public Optional<User> findById(String loginId) {
         return storage.values().stream()
                .filter(user -> user.getLoginId().equals(loginId))
                .findAny();

    }

    @Override
    public int delete(int user_id) {
        storage.remove("user_id",user_id);
        return 0;
    }

    @Override
    public int changePassword(User user) {
        return 0;
    }
    /*
    @Override
    public int login(String loginId, String password) {
        Optional<User> matchingUser = storage.values().stream()
                .filter(user -> user != null && user.getLoginId().equals(loginId) && user.getPassword().equals(password))
                .findFirst();

        if (matchingUser.isPresent()) {
            // 일치하는 사용자가 존재함
            User user = matchingUser.get();
        } else {
            // 일치하는 사용자가 없음
        }
        return 0;
    }

    @Override
    public int logout(User user) {
        return 0;
    }
    */


}
