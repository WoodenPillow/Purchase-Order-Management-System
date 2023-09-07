package PurchaseManager;

import java.util.Scanner;
import java.util.List;
import Login.User;
import SalesManager.*;

public class PurchaseManager extends User {

    public void purchaseManagerMenu(List<Item> items, List<Supplier> suppliers, List<PurchaseOrder> purchaseOrders) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nPurchase Manager Menu:");
            System.out.println("1. Generate Purchase Order");
            System.out.println("2. View Item List");
            System.out.println("3. View Purchase Order List");
            System.out.println("4. View Supplier List");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    generatePurchaseOrder(items, suppliers);
                    break;
                case 2:
                    viewItemList(items);
                    break;
                case 3:
                    viewPurchaseOrderList(purchaseOrders);
                    break;
                case 4:
                    viewSupplierList(suppliers);
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting Purchase Manager Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
