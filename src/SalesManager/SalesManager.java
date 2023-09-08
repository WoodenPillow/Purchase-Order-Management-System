package SalesManager;

import java.util.Scanner;
import java.util.List;
import Login.*;

public class SalesManager extends User {
    
    public void salesManagerMenu(List<Item> items, List<PurchaseRequisition> requisitions, List<PurchaseOrder> purchaseOrders) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nSales Manager Menu:");
            System.out.println("1. Create Purchase Requisition");
            System.out.println("2. Enter Daily Item-Wise Sales");
            System.out.println("3. Display Purchase Requisition");
            System.out.println("4. Enter Item Details");
            System.out.println("5. Enter Supplier Details");
            System.out.println("6. View Purchase Order");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    createPurchaseRequisition(items);
                    break;
                case 2:
                    enterDailyItemWiseSales(items);
                    break;
                case 3:
                    displayPurchaseRequisition(requisitions);
                    break;
                case 4:
                    enterItemDetails(items);
                    break;
                case 5:
                    enterSupplierDetails(suppliers);
                    break;
                case 6:
                    viewPurchaseOrder(purchaseOrders);
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting Sales Manager Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
