package idm.model;

/**
 * UserUpdate class contains user data for update
 *
 */

public class UserUpdate {

    private String username;
    private String email;
    private String bio;
    private String passwordOld;
    private String passwordNew;

    public UserUpdate(String username, String email, String bio,
                      String passwordOld, String passwordNew) {
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.passwordOld = passwordOld;
        this.passwordNew = passwordNew;
    }

    public UserUpdate() {
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }


}
