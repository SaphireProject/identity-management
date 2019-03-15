package idm.model;

public class AuthUserResponse {
    private Object token;
    private String username;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AuthUserResponse(Object token , String username, String email) {
        this.token = token;
        this.username = username;
        this.email = email;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
