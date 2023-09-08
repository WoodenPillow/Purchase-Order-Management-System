package SalesManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class displayRequisition {
    private String FILE_PATH = "/C:/Users/user/Desktop/Uni Stuff/Year 2 Semester 1/OODJ/Assignment/Purchase-Order-Management-System/src/SalesManager/PurchaseRequisition.txt/";
    
public void displayPurchaseRequisitions() {
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
        String line;
        Scanner scanner = new Scanner(System.in);
        
        // Create a map to store requisition details
        Map<String, String> requisitionDetails = new HashMap<>();

        // Display existing Purchase Requisition IDs and store details
        System.out.println("Existing Purchase Requisition IDs:");
        while ((line = reader.readLine()) != null) {
            // Assuming each line in PurchaseRequisition.txt contains information in the specified format
            String[] parts = line.split(",");
            if (parts.length >= 7) {
                String id = parts[0];
                String itemId = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                String date = parts[3];
                String supplierCode = parts[4];
                double unitPrice = Double.parseDouble(parts[5]);
                double totalPrice = Double.parseDouble(parts[6]);
                
                // Format the details as a string
                String details = String.format(
                    "Item ID: %s, Quantity: %d, Date: %s, Supplier Code: %s, Unit Price: %.2f, Total Price: %.2f",
                    itemId, quantity, date, supplierCode, unitPrice, totalPrice);

                requisitionDetails.put(id, details);
                System.out.println("ID: " + id);
            }
        }
        
        System.out.print("Enter the ID of the purchase requisition you want to view: ");
        String userEnteredId = scanner.nextLine();
        
        // Display the selected requisition's details
        if (requisitionDetails.containsKey(userEnteredId)) {
            String selectedDetails = requisitionDetails.get(userEnteredId);
            System.out.println("Selected Requisition Details:");
            System.out.println(selectedDetails);
        } else {
            System.out.println("Purchase requisition with the entered ID was not found.\nExiting...");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}