package Administrator;

import java.util.Scanner;
import PurchaseManager.generatePurchaseOrder;
import SalesManager.DailyItemWiseSalesEntry;
import SalesManager.createPurchaseRequisition;
import SalesManager.itemEntry;
import SalesManager.supplierEntry;


public class dataKing {
    private Scanner Scanner;

    public dataKing() {
        Scanner = new Scanner(System.in);
    }

    public void dKMenu() {
        while (true) {
            System.out.println("Data King1 (Management) Menu:");
            System.out.println("1. Edit Items (SalesManager)");
            System.out.println("2. Edit Suppliers (SalesManager)");
            System.out.println("3. Edit SalesEntries (SalesManager)");
            System.out.println("4. Edit PurchaseRequisitions (SalesManager)");
            System.out.println("5. Edit PurchaseOrders (PurchaseManager)");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = Scanner.nextInt();
            Scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    itemEntry itementry = new itemEntry();
                    itementry.editItem(Scanner);
                    break;
                case 2:
                    supplierEntry supplierentry = new supplierEntry();
                    supplierentry.menu();
                    break;
                case 3:
                    DailyItemWiseSalesEntry diwse = new DailyItemWiseSalesEntry();
                    diwse.menu();
                    break;
                case 4:
                    createPurchaseRequisition createpr = new createPurchaseRequisition();
                    createpr.editPR();
                    break;
                case 5:
                    generatePurchaseOrder genPO = new generatePurchaseOrder();
                    genPO.menu();
                    break;
                case 6:
                    System.out.println("Exiting Data King (Management) Menu.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
