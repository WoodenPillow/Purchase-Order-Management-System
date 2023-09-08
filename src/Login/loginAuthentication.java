package Login;

import java.io.*;
import java.util.*;

public class loginAuthentication {
    private final String filePath;
    private List<User> users;

    public loginAuthentication(String filePath) {
        this.filePath = filePath;
        loadUsers();
    }

    private void loadUsers() {
        users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 4) {
                    User user = new User(userData[0], userData[1], userData[2], userData[3]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading user credentials: " + e.getMessage(), e);
        }
    }

    public User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user; // User is authenticated
            }
        }
        return null; // Authentication failed
    }
}
