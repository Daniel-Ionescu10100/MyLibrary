package service.user;

import view.UserDTO;
import java.util.List;

public interface UserService {
    boolean addUser(String username, String password, String role);
    boolean deleteUser(Long id);
    List<UserDTO> findAllUsers();
}
