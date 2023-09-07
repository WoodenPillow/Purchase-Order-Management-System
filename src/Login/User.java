package Login;

public class User {
    private String userID;
    private String username;
    private String password;
    private String role;

    public User(String userID, String username, String password, String role) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return userID + "," + username + "," + password + "," + role;
    }
}



