package service.user;

import model.User;
import model.Role;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import view.UserDTO;
import model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public UserServiceImplementation(UserRepository userRepository,
                                     RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }


    @Override
    public boolean addUser(String username, String password, String role) {
        if (userRepository.existsByUserName(username)) {
            return false;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        Role dbRole = rightsRolesRepository.findRoleByTitle(role.toLowerCase());
        if (dbRole == null) {
            throw new IllegalArgumentException("Role " + role + " does not exist in DB!");
        }
        user.setRoles(List.of(dbRole));

        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        return userRepository.delete(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean assignRoleToUser(Long userId, String roleTitle) {
        Role dbRole = rightsRolesRepository.findRoleByTitle(roleTitle.toLowerCase());
        if (dbRole == null) {
            return false;
        }
        rightsRolesRepository.deleteRolesForUser(userId);
        return rightsRolesRepository.addRoleToUser(userId, dbRole.getId());
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> new UserDTO(
                        u.getId(),
                        u.getUsername(),
                        u.getRoles().get(0).getRole()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAllEmployees() {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getRoles().get(0).getRole().equalsIgnoreCase("employee"))
                .map(u -> new UserDTO(
                        u.getId(),
                        u.getUsername(),
                        "employee"
                ))
                .collect(Collectors.toList());
    }



}

