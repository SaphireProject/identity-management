package idm.model;


/**
 * UserDtoWithId class contains user data include id
 *
 */
public class UserDtoWithId {

    private Long id;
    private String username;
    private String email;

    public UserDtoWithId() {
    }

    public UserDtoWithId(String username , String email , Long id) {
        this.id=id;
        this.username = username;
        this.email = email;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
