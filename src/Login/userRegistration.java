package Login;

import java.io.*;
import java.util.*;

public class userRegistration {
    private final String FILE_PATH = "C:\\Users\\vince\\OneDrive\\Documents\\NetBeansProjects\\Purchase-Order-Management-System(POMS)\\src\\Login\\LoginCredentials.txt";

    public void registerUser(String userID, String username, String password, String role) {
        User newUser = new User(userID, username, password, role);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(newUser.toString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing user credentials: " + e.getMessage());
        }
    }
}

