package idm.model;

public class UserDto {

    private String username;
    private String email;
    private String bio;

    public UserDto() {
    }

    public UserDto(String username , String email , String bio) {
        this.username = username;
        this.email = email;
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}