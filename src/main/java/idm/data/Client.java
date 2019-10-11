package idm.data;


import javax.persistence.*;



@Entity
public class Client {
    @Id
    @GeneratedValue
    private Long client_id;

    private String secret_id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    public Client() {
    }

    public Client(String secret_id, User user) {
        this.user = user;
        this.secret_id = secret_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public void setSecret_id(String secret_id) {
        this.secret_id = secret_id;
    }

    public Long getClient_id() {
        return client_id;
    }

    public String getSecret_id() {
        return secret_id;
    }

}
