package PurchaseManager;

import java.util.Scanner;

public class PurchaseManager {
    
    public static void main (String[] args){
        PurchaseManager pm = new PurchaseManager();
        pm.purchaseManagerMenu();  
    }

    public void purchaseManagerMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        
        while (!exit) {
            System.out.println("\nPurchase Manager Menu:");
            System.out.println("1. Generate Purchase Order");
            System.out.println("2. View Item List");
            System.out.println("3. View Purchase Order List");
            System.out.println("4. View Supplier List");
            System.out.println("5. View Purchase Requisition");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    generatePurchaseOrder genPO = new generatePurchaseOrder();
                    genPO.menu();
                    break;
                case 2:
                    itemList viewItem = new itemList();
                    viewItem.viewItems();
                    break;
                case 3:
                    purchaseOrderList viewPO = new purchaseOrderList();
                    viewPO.displayPurchaseOrders();
                    break;
                case 4:
                    supplierList viewSup = new supplierList();
                    viewSup.viewSuppliers();
                    break;
                case 5:
                    displayRequisition dPR = new displayRequisition();
                    dPR.displayPurchaseRequisitions();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting Purchase Manager Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}