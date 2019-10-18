package idm.model;

import javax.validation.constraints.NotNull;


/**
 * UserRegistrationDto class contains user data for registration
 *
 */
public class UserRegistrationDto {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;

    private String bio;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
