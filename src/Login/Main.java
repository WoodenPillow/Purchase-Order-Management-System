package Login;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loginAuthentication authentication = new loginAuthentication("C:\\Users\\vince\\OneDrive\\Documents\\NetBeansProjects\\Purchase-Order-Management-System(POMS)\\src\\Login\\LoginCredentials.txt"); // Create an instance of loginAuthentication

        System.out.println("Welcome to the Purchase Order Management System by Group 55 (YAP MING SHEN, HEW YAO REN & JOSHUA CHONG KAI REN)!");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (choice == 1) {
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            // Authenticate the user
            User authenticatedUser = authentication.authenticateUser(username, password);

            if (authenticatedUser != null) {
                System.out.println("Login successful!");
                String role = authenticatedUser.getRole();
                System.out.println("Welcome, " + username + " (" + role + ")!");

                // Now, you can perform actions based on the user's role
                if ("Admin".equals(role)) {
                    // Execute admin functions
                    // For example: adminFunction(authenticatedUser);
                } else if ("Sales_Manager".equals(role)) {
                    // Execute sales manager functions
                    // For example: salesManagerFunction(authenticatedUser);
                } else if ("Purchase_Manager".equals(role)) {
                    // Execute purchase manager functions
                    // For example: purchaseManagerFunction(authenticatedUser);
                } else {
                    // Handle unrecognized roles
                    System.out.println("Unrecognized role: " + role);
                }
            } else {
                System.out.println("Login failed. Invalid credentials.");
            }
        } else if (choice == 2) {
            // User registration
            System.out.print("Enter User ID: ");
            String userID = scanner.nextLine();
            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();
            System.out.print("Enter Role: ");
            String role = scanner.nextLine();

            // Create an instance of userRegistration and register the new user
            userRegistration registration = new userRegistration();
            registration.registerUser(userID, username, password, role);

            System.out.println("Registration successful!");
        } else if (choice == 3) {
            System.out.println("Exiting Purchase Order Management System by YAP MING SHEN. Goodbye!");
            System.exit(0);
        } else {
            System.out.println("Invalid choice. Please enter a valid option.");
        }
    }
}
