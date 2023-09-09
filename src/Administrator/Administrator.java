package Administrator;

import java.util.Scanner;

public class Administrator {
    public void administratorMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nAdministrator's Menu:");
            System.out.println("1. Manage Data");
            System.out.println("2. Manage Users");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    dataKing dK = new dataKing();
                    dK.dKMenu();
                    break;
                case 2:
                    userManagement uM = new userManagement();
                    uM.userManagementMenu();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting Sales Manager Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
