package idm.data;


import idm.model.UserData;

import javax.persistence.*;

@Entity
@Table(name ="usertable")
public class User{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String bio;

    public User() {
    }

    public User(String username , String password , String email , String bio, Role roles) {
        this.username = username;
        this.password = password;
        this.bio=bio;
        this.email = email;
        this.roles = roles;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Enumerated(EnumType.STRING)
    private Role roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserData toUserData() { return new UserData(id, username, roles); }
}
