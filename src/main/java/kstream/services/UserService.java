package kstream.services;

import kstream.domain.Role;
import kstream.domain.User;
import kstream.repositories.RoleRepository;
import kstream.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-05-02
 * Time: 11:18 PM
 */
@Service
public class UserService {

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private RoleRepository _roleRepository;

    @Autowired
    private PasswordEncoder _passwordEncoder;

    @Transactional
    public Boolean registerUser(String username, String password, String email, String roleName) {

        User user = new User();
        user.setUsername(username);

        user.setPassword(_passwordEncoder.encode(password));
        user.setEmail(email);
        user.setDisabled(false);

        Role role = _roleRepository.findByName(roleName);

        if (role == null) return false;

        user.addRole(role);

        _userRepository.save(user);

        return true;
    }

    public Boolean usernameExists(String username) {
        return _userRepository.findByUsername(username) != null;
    }
}
