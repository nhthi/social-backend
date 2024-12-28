package vn.nht.social.response;

public class AuthRespone {
    private String token;
    private String message;
    public AuthRespone(String token, String message) {
        this.token = token;
        this.message = message;
    }
    public AuthRespone(){}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
