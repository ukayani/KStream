package kstream.dtos;

import kstream.validation.annotations.FieldMatch;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: kayani
 * Date: 2014-05-02
 * Time: 11:31 PM
 */
@FieldMatch(source = "password", target = "password2")
public class UserRegistrationForm {

    private Integer id;

    @NotBlank
    @Size(min=3, max=20)
    private String username;

    @Email
    @NotEmpty
    private String email;

    @NotBlank
    private String password;
    @NotBlank
    private String password2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
