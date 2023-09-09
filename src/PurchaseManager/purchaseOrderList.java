package PurchaseManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class purchaseOrderList {
    private static final String poTextFile = "C:\\Users\\user1\\Desktop\\APU\\Year 2 Sem 1\\Object Oriented Development with Java\\Assignment\\Assignment\\Purchase-Order-Management-System\\src\\SalesManager\\PurchaseOrder.txt";

    public purchaseOrderList() {}

    public void displayPurchaseOrders() {
        // Define column names
        System.out.println("\n============================================================");
        System.out.println("\tPurchase Orders");
        System.out.println("============================================================");
        System.out.printf("%-10s %-28s %-10s %-10s %-15s %-15s %-15s %-15s%n",
                "PO ID", "PO Date", "PR ID", "PR Item ID", "Quantity", "Unit Price", "Total Price", "Supplier ID");
        // Read the data from the text file and display it with columns
        try (BufferedReader buffer = new BufferedReader(new FileReader(poTextFile))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] poFields = line.split(",");
                if (poFields.length == 8) {
                    System.out.printf("%-10s %-15s %-10s %-10s %-15s %-15s %-15s %-15s%n",
                            poFields[0], poFields[1], poFields[2], poFields[3], poFields[4],poFields[5], poFields[6], poFields[7]);
                }else {
                // Handle missing or empty values
                System.out.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading PurchaseOrder.txt: " + e.getMessage());
        }
    }
}
