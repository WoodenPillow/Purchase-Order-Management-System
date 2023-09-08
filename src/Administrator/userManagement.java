package Administrator;

import java.io.*;
import java.util.*;
import Login.*;

public class userManagement {
    private static final String CREDENTIALS_FILE = "C:\\Users\\vince\\OneDrive\\Documents\\NetBeansProjects\\Purchase-Order-Management-System(POMS)\\src\\Login\\LoginCredentials.txt";
    private List<User> users = new ArrayList<>();

    public userManagement() {
        loadUsers();
    }

    public void loadUsers() {
        try (BufferedReader buffer = new BufferedReader(new FileReader(CREDENTIALS_FILE))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 4) { // Check for four fields
                    User user = new User(userData[0], userData[1], userData[2], userData[3]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data: " + e.getMessage());
        }
    }

    public void viewUserList() {
        System.out.println("\n============================================================");
        System.out.println("\tList of Users");
        System.out.println("\n============================================================");
        System.out.printf("%-15s %-15s %-10s %-10s%n", "User ID", "Username", "Password", "Role");

        for (User user : users) {
            System.out.printf("%-15s %-15s %-10s %-10s%n", user.getUserID(), user.getUsername(), user.getPassword(), user.getRole());
        }
    }

    public void addUser(String userID, String username, String password, String role) {
        // Check for duplicate usernames
        if (isUsernameDuplicate(username)) {
            System.out.println("User with the same username already exists. Adding failed.");
            return;
        }

        User newUser = new User(userID, username, password, role);
        users.add(newUser);
        saveUsers();
    }

    private boolean isUsernameDuplicate(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    public void editUser(String username, String newPassword, String newRole) {
        User editedUser = findUserByUsername(username);
        if (editedUser != null) {
            editedUser.setPassword(newPassword);
            editedUser.setRole(newRole);
            saveUsers();
        } else {
            System.out.println("User not found. Edit failed.");
        }
    }

    public void deleteUser(String username) {
        User deletedUser = findUserByUsername(username);
        if (deletedUser != null) {
            users.remove(deletedUser);
            saveUsers();
        } else {
            System.out.println("User not found. Delete failed.");
        }
    }

    public void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE))) {
            for (User user : users) {
                writer.write(user.getUserID() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getRole());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing user data: " + e.getMessage());
        }
    }

    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        userManagement userManagement = new userManagement();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nUser Management Menu:");
            System.out.println("1. View Users");
            System.out.println("2. Add User");
            System.out.println("3. Edit User");
            System.out.println("4. Delete User");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    userManagement.viewUserList();
                    break;
                case 2:
                    System.out.print("Enter User ID: ");
                    String addUserID = scanner.nextLine();
                    System.out.print("Enter Username: ");
                    String addUsername = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String addPassword = scanner.nextLine();
                    System.out.print("Enter Role: ");
                    String addRole = scanner.nextLine();
                    userManagement.addUser(addUserID, addUsername, addPassword, addRole);
                    break;
                case 3:
                    System.out.print("Enter Username to Edit: ");
                    String editUsername = scanner.nextLine();
                    System.out.print("Enter New Password: ");
                    String editPassword = scanner.nextLine();
                    System.out.print("Enter New Role: ");
                    String editRole = scanner.nextLine();
                    userManagement.editUser(editUsername, editPassword, editRole);
                    break;
                case 4:
                    System.out.print("Enter Username to Delete: ");
                    String deleteUsername = scanner.nextLine();
                    userManagement.deleteUser(deleteUsername);
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting User Management Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}

class User {
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
