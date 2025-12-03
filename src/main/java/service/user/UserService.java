package service.user;

import model.User;
import view.UserDTO;
import java.util.List;

public interface UserService {
    boolean addUser(String username, String password, String role);
    boolean deleteUser(Long id);
    List<UserDTO> findAllUsers();
    User findByUsername(String username);
    boolean assignRoleToUser(Long userId, String roleTitle);
    List<UserDTO> findAllEmployees();
}
