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
        System.out.printf("%-15s %-15s %-10s %-15s%n", "User ID", "Username", "Password", "Role");

        for (User user : users) {
            System.out.printf("%-15s %-15s %-10s %-15s%n", user.getUserID(), user.getUsername(), user.getPassword(), user.getRole());
        }
    }

    public void addUser(String userID, String username, String password, String role) {
        // Check for duplicate User IDs
        if (isUserIDDuplicate(userID)) {
            System.out.println("User with the same User ID already exists. Adding failed.");
            return;
        }

        User newUser = new User(userID, username, password, role);
        users.add(newUser);
        saveUsers();
    }

    private boolean isUserIDDuplicate(String userID) {
        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(userID)) {
                return true;
            }
        }
        return false;
    }

    public void editUser(String userID, String newPassword, String newRole) {
        User editedUser = findUserByUserID(userID);
        if (editedUser != null) {
            editedUser.setPassword(newPassword);
            editedUser.setRole(newRole);
            saveUsers();
        } else {
            System.out.println("User not found. Edit failed.");
        }
    }

    public void deleteUser(String userID) {
        User deletedUser = findUserByUserID(userID);
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

    public User findUserByUserID(String userID) {
        for (User user : users) {
            if (user.getUserID().equalsIgnoreCase(userID)) {
                return user;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        userManagement userManagement = new userManagement(); // Use userManagement class name
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
                    userManagement.viewUserList(); // Call viewUserList() on userManagement object
                    break;
                case 2:
                    System.out.print("Enter User ID (SMXXX, PMXXX, ADMXX): ");
                    String addUserID = scanner.nextLine().toUpperCase();
                    String role = getRoleFromUserID(addUserID);
                    if (role != null) {
                        System.out.print("Enter Username: ");
                        String addUsername = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        String addPassword = scanner.nextLine();
                        userManagement.addUser(addUserID, addUsername, addPassword, role); // Call addUser() on userManagement object
                    } else {
                        System.out.println("Invalid User ID format. Adding failed.");
                    }
                    break;
                case 3:
                    System.out.print("Enter User ID to Edit: ");
                    String editUserID = scanner.nextLine().toUpperCase();
                    String editRole = getRoleFromUserID(editUserID);
                    if (editRole != null) {
                        System.out.print("Enter New Password: ");
                        String editPassword = scanner.nextLine();
                        userManagement.editUser(editUserID, editPassword, editRole); // Call editUser() on userManagement object
                    } else {
                        System.out.println("Invalid User ID format. Edit failed.");
                    }
                    break;
                case 4:
                    System.out.print("Enter User ID to Delete: ");
                    String deleteUserID = scanner.nextLine().toUpperCase();
                    userManagement.deleteUser(deleteUserID); // Call deleteUser() on userManagement object
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

    private static String getRoleFromUserID(String userID) {
        if (userID.startsWith("SM")) {
            return "Sales_Manager";
        } else if (userID.startsWith("PM")) {
            return "Purchase_Manager";
        } else if (userID.startsWith("ADM")) {
            return "Administrator";
        }
        return null;
    }
}
