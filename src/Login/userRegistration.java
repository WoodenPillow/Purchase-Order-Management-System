package Login;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class userRegistration {
    private final String FILE_PATH = "C:\\Users\\vince\\OneDrive\\Documents\\NetBeansProjects\\Purchase-Order-Management-System(POMS)\\src\\Login\\LoginCredentials.txt";

    public void registerUser(String userID, String username, String password) {
        // Automatically recognize the role by looking at the alphabets in front of the userID
        String detectedRole = getRoleFromUserID(userID);

        // Check for duplicate User IDs
        if (isUserIDDuplicate(userID)) {
            System.out.println("User with the same User ID already exists. Registration failed.");
            return;
        }

        // Create a string in the desired format for the new user
        String newUserString = userID + "," + username + "," + password + "," + detectedRole;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Write the new user's data to the file
            writer.write(newUserString);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing user credentials: " + e.getMessage());
        }

        // Display the success message only once
        System.out.println("Registration successful!");
    }

    // Modify getRoleFromUserID to be case-insensitive and return null for an unrecognized role
    private String getRoleFromUserID(String userID) {
        if (userID.toUpperCase().startsWith("ADM")) {
            return "Administrator";
        } else if (userID.toUpperCase().startsWith("SM")) {
            return "Sales_Manager";
        } else if (userID.toUpperCase().startsWith("PM")) {
            return "Purchase_Manager";
        }
        return null;
    }
    
    // Check for duplicate User IDs
    private boolean isUserIDDuplicate(String userID) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length > 0 && userData[0].equalsIgnoreCase(userID)) {
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error checking for duplicate User IDs: " + e.getMessage());
        }
        return false;
    }
}
