package kstream.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-04-04
 * Time: 5:34 PM
 */
public class UserDetailImpl implements UserDetails {

    private Long id;

    private String email;

    private String username;

    private String password;

    private Boolean enabled;

    private Collection<GrantedAuthority> authorities;

    public UserDetailImpl(String username, String password, Collection<GrantedAuthority> authorities, Boolean enabled){
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getPassword() {
        return this.password;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getUsername() {
        return this.username;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.enabled;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

