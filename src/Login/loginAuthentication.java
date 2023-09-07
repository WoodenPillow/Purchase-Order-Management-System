package Login;

// LoginAuthentication.java
import java.io.*;
import java.util.*;

public class loginAuthentication {
    private final String FILE_PATH = "C:\\Users\\vince\\OneDrive\\Documents\\NetBeansProjects\\Purchase-Order-Management-System(POMS)\\src\\Login\\LoginCredentials.txt";
    private List<User> users = new ArrayList<>();

    public loginAuthentication() {
        loadUsers();
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 4) {
                    User user = new User(userData[0], userData[1], userData[2], userData[3]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user credentials: " + e.getMessage());
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

