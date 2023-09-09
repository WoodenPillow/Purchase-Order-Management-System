package Login;

import Administrator.Administrator;
import SalesManager.SalesManager;
import PurchaseManager.PurchaseManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loginAuthentication authentication = new loginAuthentication("C:\\Users\\vince\\OneDrive\\Documents\\NetBeansProjects\\Purchase-Order-Management-System(POMS)\\src\\Login\\LoginCredentials.txt"); // Create an instance of loginAuthentication

        System.out.println("Welcome to the Purchase Order Management System by Group 55 (YAP MING SHEN, HEW YAO REN & JOSHUA CHONG KAI REN)!");
        System.out.println("1. Login");
        System.out.println("2. Exit");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

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

                if ("Administrator".equals(role)) {
                    Administrator adm = new Administrator();
                    adm.administratorMenu();
                } 
                
                else if ("Sales_Manager".equals(role)) {
                    SalesManager sm = new SalesManager();
                    sm.salesManagerMenu();
                    
                }              
                else if ("Purchase_Manager".equals(role)) {
                    PurchaseManager pm = new PurchaseManager();
                    pm.purchaseManagerMenu(); 
                    
                }
                
            } 
            
            else {
                System.out.println("Login failed. Invalid credentials.");
            }    
        } 
        else if (choice == 2) {
            System.out.println("Exiting Purchase Order Management System by YAP MING SHEN. Goodbye!");
            System.exit(0);
        } 
        else {
            System.out.println("Invalid choice. Please enter a valid option.");
        }
    }
}
