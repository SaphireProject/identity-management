package idm.data;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name ="usertable")
public class User /*implements UserDetails */{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private String username;

    //@JsonIgnore
    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String activationCode;

    public User() {
    }

    public User(String username , String password , String email , String activationCode , Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.activationCode = activationCode;
        this.roles = roles;
    }

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    /*
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

   */


   // @Override
    //public Collection</*? extends GrantedAuthority*/> getAuthorities() {
  //      return getRoles();
   // }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
