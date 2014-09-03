package kstream.security;

import kstream.domain.Role;
import kstream.domain.User;
import kstream.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-04
 * Time: 5:36 PM
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository _userRepo;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = _userRepo.findByUsername(s);

        if (user == null) throw new UsernameNotFoundException("Could not find username");

        boolean enabled = !user.getDisabled();

        Collection<GrantedAuthority> authorities = getAuthorities(user);

        UserDetailImpl userDetails =
                new UserDetailImpl(user.getUsername(), user.getPassword(), authorities, enabled);

        userDetails.setEmail(user.getEmail());
        userDetails.setId(user.getId());

        return userDetails;
    }

    private Collection<GrantedAuthority> getAuthorities(User user){

        Set<Role> roles = user.getRoles();

        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();

        for (Role role: roles){
            // get appropriate format for role, need ROLE prefix for roleVoter
            String roleName = String.format("ROLE_%s", role.getName().toUpperCase());
            authorityList.add(new SimpleGrantedAuthority(roleName));
        }

        return authorityList;
    }
}

