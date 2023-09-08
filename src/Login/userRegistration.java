package Login;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class userRegistration {
    private final String FILE_PATH = "C:\\Users\\vince\\OneDrive\\Documents\\NetBeansProjects\\Purchase-Order-Management-System(POMS)\\src\\Login\\LoginCredentials.txt";

    public void registerUser(String userID, String username, String password, String role) {
        // Create a string in the desired format for the new user
        String newUserString = userID + "," + username + "," + password + "," + role;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Write the new user's data to the file
            writer.write(newUserString);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing user credentials: " + e.getMessage());
        }
    }
}
