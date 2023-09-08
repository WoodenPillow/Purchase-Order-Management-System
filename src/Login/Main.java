package Login;

import java.util.Scanner;
import PurchaseManager.*;
import SalesManager.*;

public class Main {
    public static void main(String[] args) {
        loginAuthentication loginAuth = new loginAuthentication();
        userRegistration userRegistration = new userRegistration();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    User authenticatedUser = loginAuth.authenticateUser(username, password);
                    if (authenticatedUser != null) {
                        System.out.println("Login successful.");
                        System.out.println("Welcome, " + authenticatedUser.getUsername());
                        System.out.println("Role: " + authenticatedUser.getRole());
                        
                        // Determine the user's role and launch the corresponding class
                        if ("Purchase_Manager".equals(authenticatedUser.getRole())) {
                            PurchaseManager.PurchaseManager(null);
                        } else if ("Sales_Manager".equals(authenticatedUser.getRole())) {
                            SalesManager.SalesManager(null);
                        }
                    } else {
                        System.out.println("Login failed. Invalid credentials.");
                    }
                    break;
                case 2:
                    System.out.print("Enter new user ID: ");
                    String newUserID = scanner.nextLine();
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    System.out.print("Enter role: ");
                    String newRole = scanner.nextLine();

                    userRegistration.registerUser(newUserID, newUsername, newPassword, newRole);
                    System.out.println("User registered successfully.");
                    break;
                case 3:
                    System.out.println("Exiting the application.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
