/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SalesManager;

import java.util.*;
import java.io.*;
import Administrator.fileHandler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.*;

/**
 *
 * @author Oscar
 */
public class createPurchaseRequisition {
    private fileHandler obj1 = new fileHandler();
    private List <String> PurchaseRequisitionList;
    private String requisitionID;
    private String itemCode;
    private int itemQuantity;
    private double totalPrice;
    private String readfile1 = "/C:/Users/user/Desktop/Uni Stuff/Year 2 Semester 1/OODJ/Assignment/Purchase-Order-Management-System/src/SalesManager/Item.txt/";
    private String readfile2 = "/C:/Users/user/Desktop/Uni Stuff/Year 2 Semester 1/OODJ/Assignment/Purchase-Order-Management-System/src/SalesManager/PurchaseRequisition.txt";
    private int quantityRequired;
    private String requiredDate;
    private String supplierCode;
    private double unitPrice;
    
    public createPurchaseRequisition(){
        
    }

    public void menu(){
            Scanner sc1 = new Scanner(System.in);
            int option;
            
            do{
                System.out.println("You are about to create a Purchase Requisition.\nChoose an option to proceed.\n1. Add\n2. Edit\n3. Delete\n4. Exit");
                option = sc1.nextInt();
                
                switch(option){
                    case 1: 
                        addPR();
                        break;
                        
                   case 2:
                        editPR();
                        break;
               
                    case 3:
                        deletePR();
                        break;
                    
                    case 4:
                        System.out.println("Exiting...");
                        return;
                    
                    default:
                        System.out.println("Wrong Input. Please Enter Again.");
                }
            }while (option < 1 || option > 4);   
    }
    
    public void addPR(){
        System.out.println("Printing Item List...");
        try (BufferedReader reader = new BufferedReader(new FileReader(readfile1))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print each line from the file
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            try (BufferedReader purchaseRequisitionReader = new BufferedReader(new FileReader(readfile2))) {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
            while (true){
            System.out.println("Enter an ID for the purchase requisition:");
            String userEnteredId = inputReader.readLine();
            if (userEnteredId.matches("\\d+")) {
                int purchaseRequisitionId = Integer.parseInt(userEnteredId);
                String line;
                boolean idExists = false;
            while ((line = purchaseRequisitionReader.readLine()) != null) {
                // Assuming each line in PurchaseRequisition.txt contains an ID followed by other data
                String[] parts = line.split(","); // Split by whitespace
                if (parts.length > 0 && parts[0].equals(userEnteredId)) {
                    idExists = true;
                    break;
                }
            }
            
            if (idExists) {
                System.out.println("ID already exists in Purchase Requisition. Please try again.");
            } else {
                System.out.println("ID does not exist in Purchase Requisition. You can proceed to add it.");
                String userEnteredItemCode;
                boolean isValidItemCode = false;
                while (!isValidItemCode) {
                    System.out.println("Enter an item code from the item list (e.g., I0001):");
                    userEnteredItemCode = inputReader.readLine();

                // Check if the entered item code matches the expected format
                if (isValidItemCode(userEnteredItemCode)) {
                    if (isItemCodeExists(userEnteredItemCode)) {
                            isValidItemCode = true;
                            System.out.println("Item code is in the correct format and exists in the item list.");
                            
                            String[] itemDetails = getItemDetails(userEnteredItemCode);
                            if (itemDetails != null) {
                                unitPrice = Double.parseDouble(itemDetails[2]); // Assuming unit price is in the third column
                                supplierCode = itemDetails[4];
                                
                             while (true) {
                                try {
                                System.out.println("Enter the quantity required:");
                                quantityRequired = Integer.parseInt(inputReader.readLine());

                                // If the parsing succeeds, break out of the loop
                                break;
                                } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a valid integer for quantity.");
                                }
                            }

                            System.out.println("Enter the required date (e.g., YYYY-MM-DD):");
                            String requiredDate = inputReader.readLine();
                            
                            double totalPrice = quantityRequired * unitPrice;
                            
                            String purchaseRequisitionDetails = userEnteredItemCode + "," + quantityRequired + "," + requiredDate + "," + supplierCode + "," + unitPrice + "," + totalPrice;
                            savePurchaseRequisition(userEnteredId, purchaseRequisitionDetails);
                            System.out.println("Purchase requisition details saved successfully.");
                        }
                    }else {
                            System.out.println("Item code does not exist in the item list. Please try again.");
                            }
                    
                }else {
                       System.out.println("Item code is not in the correct format. Please try again.");
                        }  
                    }
                break;
            }
        } else {
                System.out.println("Invalid input. Please enter a valid numeric ID.");
            }
        }
   
    } catch (IOException e) {
         e.printStackTrace();
                }
    }
    
    public void editPR(){
        try (BufferedReader purchaseRequisitionReader = new BufferedReader(new FileReader(readfile2))) {
        System.out.println("Existing Purchase Requisition IDs:");
        String line;
        while ((line = purchaseRequisitionReader.readLine()) != null) {
            // Assuming each line in PurchaseRequisition.txt contains information in the specified comma-separated format
            String[] parts = line.split(",");
            if (parts.length >= 7) {
                String purchaseRequisitionId = parts[0];
                System.out.println(purchaseRequisitionId);
            }
        }
        } catch (IOException e) {
        e.printStackTrace();
    }
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("You are about to edit Purchase Requisition. (Go to delete if you want to change for the requisiition of whole item)");

        // Prompt the user to choose a specific purchase requisition ID
        try {
            System.out.println("Enter the ID of the purchase requisition you want to edit:");
            String editId = inputReader.readLine();

            // Check if the entered ID exists in PurchaseRequisition.txt
            if (isPurchaseRequisitionIdExists(editId)) {
                // Load and display the existing purchase requisition details
                String existingDetails = getPurchaseRequisitionDetails(editId);
                System.out.println("Existing details:");
                System.out.println(existingDetails);
                
                // Prompt the user for changes
                System.out.println("Enter new values for the following fields (leave blank to keep existing values):");

                // Prompt for Quantity Required
                System.out.print("Quantity Required: ");
                String newQuantityInput = inputReader.readLine();
                int newQuantity = newQuantityInput.isEmpty() ? parseQuantity(existingDetails) : Integer.parseInt(newQuantityInput);
                
                // Prompt for Required Date
                System.out.print("Required Date (YYYY-MM-DD): ");
                String newRequiredDateInput = inputReader.readLine();
                String newRequiredDate = newRequiredDateInput.isEmpty() ? parseRequiredDate(existingDetails) : newRequiredDateInput;
                
                String[] parts = existingDetails.split(",");
                double newTotalPrice = newQuantity * Double.parseDouble(parts[4]);
                System.out.println(newTotalPrice);
                // Update the details string with the new values
                String updatedDetails = updatePurchaseRequisitionDetails(existingDetails, newQuantity, newRequiredDate, newTotalPrice);

                // Save the updated details
                saveUpdatedPurchaseRequisition(editId, updatedDetails);
                System.out.println("Purchase requisition details updated successfully.");
            } else {
                System.out.println("ID does not exist in Purchase Requisition.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void deletePR(){
    try (BufferedReader purchaseRequisitionReader = new BufferedReader(new FileReader(readfile2))) {
        System.out.println("Existing Purchase Requisition IDs:");
        String line;
        while ((line = purchaseRequisitionReader.readLine()) != null) {
            // Assuming each line in PurchaseRequisition.txt contains information in the specified comma-separated format
            String[] parts = line.split(",");
            if (parts.length >= 7) {
                String purchaseRequisitionId = parts[0];
                System.out.println(purchaseRequisitionId);
            }
        }
        } catch (IOException e) {
        e.printStackTrace();
    }
    BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("You are about to delete Purchase Requisition.");

        // Prompt the user to choose a specific purchase requisition ID
        try {
            System.out.println("Enter the ID of the purchase requisition you want to edit:");
            String deleteId = inputReader.readLine();
            if (isPurchaseRequisitionIdExists(deleteId)) {
                try (BufferedReader purchaseRequisitionReader = new BufferedReader(new FileReader(readfile2))) {
        String line;
        StringBuilder newContent = new StringBuilder(); // To store the new content

        while ((line = purchaseRequisitionReader.readLine()) != null) {
            // Assuming each line in PurchaseRequisition.txt contains information in the specified comma-separated format
            String[] parts = line.split(",");

            if (parts.length >= 1) {
                String purchaseRequisitionId = parts[0];

                // Check if the current line matches the ID to be deleted
                if (purchaseRequisitionId.equals(deleteId)) {
                    continue; // Skip this line, effectively deleting it
                }
            }

            // Append the line to the new content
            newContent.append(line).append(System.lineSeparator());
        }

        // Write the new content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(readfile2))) {
            writer.write(newContent.toString());
        }

        System.out.println("Purchase requisition with ID " + deleteId + " deleted successfully.");
    } catch (IOException e) {
        e.printStackTrace();
    }
            }
    }catch (IOException e) {
        e.printStackTrace();
    }
    }
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Helper Methods    
    private boolean isValidItemCode(String itemCode) {
        // Check if the item code matches the pattern "I" followed by four digits
        return itemCode.matches("I\\d{4}");
    }  
    
    private boolean isItemCodeExists(String itemCode) {
        List<String> itemCodes = new ArrayList<>();
        try (BufferedReader itemReader = new BufferedReader(new FileReader(readfile1))) {
            String itemLine;
            while ((itemLine = itemReader.readLine()) != null) {
                String[] itemParts = itemLine.split(",");
                if (itemParts.length > 0) {
                    String codeFromList = itemParts[0].trim(); // Get the item code from the file
                     if (codeFromList.equalsIgnoreCase(itemCode)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
     private String[] getItemDetails(String itemCode) {
        try (BufferedReader itemReader = new BufferedReader(new FileReader(readfile1))) {
            String itemLine;
            while ((itemLine = itemReader.readLine()) != null) {
                String[] itemParts = itemLine.split(",");
                if (itemParts.length > 0 && itemParts[0].trim().equalsIgnoreCase(itemCode)) {
                    return itemParts;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
     
      private void savePurchaseRequisition(String id, String details) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(readfile2, true))) {
            // Append the new purchase requisition details to the file
            writer.write(id + "," + details);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      
      private int parseQuantity(String details) {
        String[] parts = details.split(",");
        if (parts.length >= 2) {
            try {
                return Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
      
      private String parseRequiredDate(String details) {
        String[] parts = details.split(",");
        if (parts.length >= 3) {
            return parts[2].trim();
        }
        return "";
    }
      
      private String updatePurchaseRequisitionDetails(String existingDetails, int newQuantity, String newRequiredDate, double newTotalPrice) {
        String[] parts = existingDetails.split(",");
        if (parts.length >= 2) {
            parts[1] = String.valueOf(newQuantity);
        }
        if (parts.length >= 3 && !newRequiredDate.isEmpty()) {
            parts[2] = newRequiredDate;
        }
        if (parts.length >= 6) { 
        parts[5] = String.valueOf(newTotalPrice);
        }
        return String.join(",", parts);
    }
      
      private void saveUpdatedPurchaseRequisition(String id, String updatedDetails) {
        try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(readfile2))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(id + ",")) {
                        lines.add(id + "," + updatedDetails);
                    } else {
                        lines.add(line);
                    }
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(readfile2))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      
      private String getPurchaseRequisitionDetails(String id) {
        try (BufferedReader purchaseRequisitionReader = new BufferedReader(new FileReader(readfile2))) {
            String line;
            while ((line = purchaseRequisitionReader.readLine()) != null) {
                // Assuming each line in PurchaseRequisition.txt contains an ID followed by other data
                String[] parts = line.split(",", 2); // Split by whitespace, limit to 2 parts
                if (parts.length > 0 && parts[0].equals(id)) {
                    return parts[1]; // Return details excluding the ID
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
      
      private boolean isPurchaseRequisitionIdExists(String id) {
    try (BufferedReader purchaseRequisitionReader = new BufferedReader(new FileReader(readfile2))) {
        String line;
        while ((line = purchaseRequisitionReader.readLine()) != null) {
            // Assuming each line in PurchaseRequisition.txt contains an ID followed by other data
            String[] parts = line.split(","); // Split by whitespace, limit to 2 parts
            if (parts.length > 0 && parts[0].equals(id)) {
                return true;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return false;
}
}
