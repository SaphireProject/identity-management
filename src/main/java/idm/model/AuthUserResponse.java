package idm.model;

public class AuthUserResponse {
    private Object token;
    private String username;

    public AuthUserResponse(Object token , String username) {
        this.token = token;
        this.username = username;
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
