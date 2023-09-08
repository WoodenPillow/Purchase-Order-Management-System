package Login;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loginAuthentication authentication = new loginAuthentication("C:\\Users\\vince\\OneDrive\\Documents\\NetBeansProjects\\Purchase-Order-Management-System(POMS)\\src\\Login\\LoginCredentials.txt"); // Create an instance of loginAuthentication

        System.out.println("Welcome to the Purchase Order Management System!");
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
                System.out.println("Welcome, " + authenticatedUser.getUsername() + "!");
                // You can now perform actions for the authenticated user
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
            System.out.println("Exiting Purchase Order Management System. Goodbye!");
            System.exit(0);
        } else {
            System.out.println("Invalid choice. Please enter a valid option.");
        }
    }
}
