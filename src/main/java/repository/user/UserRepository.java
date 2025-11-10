package repository.user;

import model.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findByUsernameAndPassword(String userName, String password);
    boolean save (User user);
    void removeAll();
    boolean existsByUserName(String username);
}
