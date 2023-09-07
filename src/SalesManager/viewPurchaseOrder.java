package SalesManager;

import Administrator.fileHandler;

import java.util.List;

public class viewPurchaseOrder {
        private fileHandler obj1 = new fileHandler();

    public void displayPurchaseOrders() {
        List<String> purchaseOrders = obj1.readData("PurchaseOrder.txt");
        if (purchaseOrders.isEmpty()) {
            System.out.println("No purchase orders found.");
        } else {
            System.out.println("Purchase Orders:");
            for (String purchaseOrder : purchaseOrders) {
                System.out.println(purchaseOrder);
                System.out.println("------------------------");
            }
        }
    }
}
