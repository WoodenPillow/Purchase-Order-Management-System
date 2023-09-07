package PurchaseManager;

import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Joshua
 */

public class generatePurchaseOrder {
    
    String poTextFile = ("C:\\Users\\user1\\Desktop\\APU\\Year 2 Sem 1\\Object Oriented Development with Java\\Assignment\\Assignment\\Purchase-Order-Management-System\\src\\PurchaseManager\\PurchaseOrder.txt");
    String usersTextFile = ("C:\\Users\\user1\\Desktop\\APU\\Year 2 Sem 1\\Object Oriented Development with Java\\Assignment\\Assignment\\Purchase-Order-Management-System\\src\\Login\\LoginCredentials.txt");
    String prTextFile = ("C:\\Users\\user1\\Desktop\\APU\\Year 2 Sem 1\\Object Oriented Development with Java\\Assignment\\Assignment\\Purchase-Order-Management-System\\src\\PurchaseManager\\PurchaseRequisition.txt");
    
    private List<String> purchaseOrderList;
    
    public generatePurchaseOrder(){menu();}    
    
    public void menu() {
        Scanner sc1 = new Scanner(System.in);
        int option;
        
        do {
        System.out.println("Welcome To Purchase Order Management. \nPlease Select An Option. \n1. Add \n2. Edit \n3. Delete \n4. Exit \n");

            try {
                option = sc1.nextInt();

                switch (option) {
                    case 1:
                        addPO();
                        break;

                    case 2:
                        editPO();
                        break;

                    case 3:
                        deletePO();
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Wrong Input. Please Enter Again.");
                }
            } catch (java.util.InputMismatchException e) {
                // Handle non-integer input
                sc1.nextLine(); // Consume the invalid input
                System.out.println("Invalid input. Please enter a valid option (1-4).");
                option = 0; // Set option to 0 to repeat the loop
            }
        } while (option < 1 || option > 4);       
    }

    public void addPO(){
        Scanner sc1 = new Scanner(System.in);
        String newPOID = "";
        String pmID = "";
        
        while (true){
            // Prompt the user to enter the new Purchase Order ID
            System.out.println("Printing Purchase Order List...\nCurrent Purchase Order:\n");
            System.out.print(poTextFile);
            System.out.println("Please Enter A New Purchase Order ID: \n");
            newPOID = sc1.next();
            
            boolean isUnique =  true;
            
            //To Check Purchase Order ID Exists In Text File
            for (String purchaseOrder : purchaseOrderList) {
                String[] columns = purchaseOrder.split(",");
                
                if (columns.length > 0 && columns[0].equals(newPOID)) {
                    isUnique = false;
                    System.out.println("Purchase Order Exists. Please Enter A New Purchase Order ID. \n");
                    return;
                }
            }
            
            if(isUnique){
                // Add the new Purchase Order ID to the list since it's unique
                System.out.println("Purchase Order ID added successfully.\n");
                System.out.println("Proceed to next step...\n");
                break;
            }
        }
        
        // Prompt the user to enter date in specific format

        System.out.println("Please Enter The Date (in yyyy-MM-dd format): ");
        String enterDate = sc1.next();
        // Parse the date string to a Date object
        Date poDate = parseDate(enterDate);
        
        // Prompt the user to enter purchase manager ID
        boolean isValidPMID = false;
        
        while (!isValidPMID) {
            System.out.println("Printing Users List...\n");
            System.out.print(usersTextFile);
            System.out.println("Please Enter Purchase Manager ID. \n");
            pmID = sc1.next();
            
            // Proceed To Check If The Input Exists In 'Users.txt' File
            if (isPurchaseManagerIDExists(pmID)) {
                System.out.println("Proceed To Next Step...\n");
                isValidPMID = true; // Set to true to exit the loop when valid input is provided
            } else {
                System.out.println("Error: Purchase Manager Not Found.");
            }
        }
        
        // Prompt the user to enter purchase requistion ID
        boolean isValidPRID = false;
        
        while (!isValidPRID){
            System.out.println("Printing Purchase Requisition List...\n");
            System.out.print(prTextFile);
            System.out.println("Please Enter Purchase Requisition ID: \n");
            String prID = sc1.next();
            
            // Proceed To Check If The Input Exists In 'PurchaseRequisition.txt' File
            if (isPurchaseRequisitionIDExists(prID)){
                // Retrieve All The Corresponding Data from 'PurchaseRequisition.txt' File
                System.out.println("Retrieving Data...\n");
                String prData = getDataFromPurchaseRequisition(prID);
                
                if (prData != null) {
                    // Retrieve Data from 'PurchaseRequisition.txt' based on Purchase Requisition ID
                    // Split the prData into individual fields
                    String[] prFields = prData.split(",");

                    if (prFields.length >= 7) { // Ensure all required fields are present
                        String prItemID = prFields[1];
                        int prItemQuantity = Integer.parseInt(prFields[2]);
                        String prSupplierID = prFields[3];
                        String prDate = prFields[4];
                        double prUnitPrice = Double.parseDouble(prFields[5]);
                        double prTotalPrice = Double.parseDouble(prFields[6]);

                        // List out the extracted data
                        System.out.println("Purchase Requisition ID: " + prID);
                        System.out.println("Item ID: " + prItemID);
                        System.out.println("Item Quantity: " + prItemQuantity);
                        System.out.println("Supplier ID: " + prSupplierID);
                        System.out.println("Date: " + prDate);
                        System.out.println("Unit Price: " + prUnitPrice);
                        System.out.println("Total Price: " + prTotalPrice);
                        
                        String newPurchaseOrder = "Purchase Order ID: " + newPOID + ",Purchase Order Date: " + poDate + ",Purchase Manager ID: " + pmID + ",Purchase Requisition ID: " + prID + ",Item ID: " + prItemID + ",Item Quantity: " + prItemQuantity + ",Unit Price: " + prUnitPrice + ",Total Price: " + prTotalPrice + ",Supplier ID: " + prSupplierID + "\n";
                        
                        // Add the new Purchase Order into list
                        purchaseOrderList.add(newPurchaseOrder);
        
                        //Save Purchase Order List Into Text File
                        savePO(purchaseOrderList,"PurchaseOrderList");
                        break;
                    }
                    else{
                        System.out.println("Error: Incomplete data in Purchase Requisition.");
                    }
                }
            }
            else{
                System.out.println("Error: Purchase Requisition Not Found. \n");
            }
        }
    }
    
    public void editPO() {
        Scanner sc1 = new Scanner(System.in);

        // Prompt the user to enter the Purchase Order ID to edit
        System.out.println("Printing Purchase Order List...\nCurrent Purchase Order:\n");
        System.out.print(poTextFile);
        System.out.println("Please Enter the Purchase Order ID to Edit: \n");
        String editPOID = sc1.next();

        boolean isFound = false;

        // Loop through the existing purchase orders to find the one to edit
        for (int i = 0; i < purchaseOrderList.size(); i++) {
            String purchaseOrder = purchaseOrderList.get(i);
            String[] columns = purchaseOrder.split(",");

            if (columns.length > 0 && columns[0].equals(editPOID)) {
                isFound = true;
                System.out.println("Purchase Order Found. Current Details:");

                // Print the current purchase order details
                System.out.println("Purchase Order ID: " + columns[0]);
                System.out.println("Purchase Order Date: " + columns[1]);
                System.out.println("Purchase Manager ID: " + columns[2]);
                System.out.println("Purchase Requisition ID: " + columns[3]);
                System.out.println("Item ID: " + columns[4]);
                System.out.println("Item Quantity: " + columns[5]);
                System.out.println("Unit Price: " + columns[6]);
                System.out.println("Total Price: " + columns[7]);
                System.out.println("Supplier ID: " + columns[8]);

                // Prompt the user for editing options
                System.out.println("Select What You Want to Edit:");
                System.out.println("1. Purchase Order Date");
                System.out.println("2. Purchase Manager ID");
                System.out.println("3. Item Quantity");
                System.out.println("4. Exit (No Changes)");

                int editOption = sc1.nextInt();

                switch (editOption) {
                    case 1:
                        // Edit Purchase Order Date
                        System.out.println("Enter New Purchase Order Date (yyyy-MM-dd): ");
                        String newDateStr = sc1.next();
                        Date newDate = parseDate(newDateStr);
                    
                        if (newDate != null) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            columns[1] = dateFormat.format(newDate);
                            System.out.println("Purchase Order Date updated successfully.");
                        } else {
                            System.out.println("Invalid date format. Date not updated.");
                        }
                        break;

                    case 2:
                        // Edit Purchase Manager ID
                        System.out.println("Enter New Purchase Manager ID: ");
                        String newPMID = sc1.next();
                        // Check if the new PMID exists in the users list
                        if (isPurchaseManagerIDExists(newPMID)) {
                            columns[2] = newPMID;
                            System.out.println("Purchase Manager ID updated successfully.");
                        } else {
                            System.out.println("Error: Purchase Manager Not Found.");
                        }
                        break;

                    case 3:
                        // Edit Item Quantity
                        System.out.println("Enter New Item Quantity: ");
                        int newQuantity = sc1.nextInt();
                        columns[5] = String.valueOf(newQuantity);
                        double newTotalPrice = newQuantity * Double.parseDouble(columns[6]);
                        columns[7] = String.valueOf(newTotalPrice);
                        System.out.println("Item Quantity and Total Price updated successfully.");
                        break;

                    case 4:
                        System.out.println("No Changes Made.");
                        break;

                    default:
                        System.out.println("Invalid option. No Changes Made.");
                        break;
                }

                // Update the edited purchase order details
                String updatedPurchaseOrder = String.join(",", columns);
                purchaseOrderList.set(i, updatedPurchaseOrder);

                // Save the updated purchase order list into the text file
                savePO(purchaseOrderList, "PurchaseOrderList.txt");
                break; // Exit the loop after editing the purchase order
            }
        }

        if (!isFound) {
            System.out.println("Error: Purchase Order ID not found.");
        }
    }
    
    public void deletePO(){
        System.out.print(poTextFile);//To Let User Know What Purchase Order Exists
        
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter the Purchase Order ID you want to delete: ");
        String input = sc1.next();
        boolean found = false;

        for (int i = 0; i < purchaseOrderList.size(); i++) {
            String item = purchaseOrderList.get(i);
            String[] columns = item.split(",");

            if (columns.length > 0 && columns[0].equals(input)) {
                found = true;
                System.out.println("Purhcase Order Found. Deleting...");

                // Remove the item from the list
                purchaseOrderList.remove(i);
                
                savePO(purchaseOrderList,"PurchaseOrder.txt");
                return; // Exit the loop after deleting the item
            }
        }

        if (!found) {
            System.out.println("Error: Purchase Order not found in the file.");
        }  
    }
    
    //Save Data Into File
    public void savePO(List<String> purchaseOrderList, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String entry : purchaseOrderList) {
                writer.write(entry);
                writer.newLine(); // Add a newline after each entry
            }
            System.out.println("Data Is Successfully Saved To " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Failed to save data to " + fileName);
        }
    }
    
 
    // Function to check if the Purchase Manager ID exists in 'LoginCredentials.txt'
    private boolean isPurchaseManagerIDExists(String pmID) {        
        try (BufferedReader fileReader = new BufferedReader(new FileReader(usersTextFile))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                // Split the line by comma to extract the user ID
                String[] columns = line.split(",");
                if (columns.length > 0 && columns[0].equals(pmID)) {
                    // If a match is found, return true
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If no match is found, return false
        return false;
    }
    
    // Function to check if a Purchase Requistion ID exists in the text file
    private boolean isPurchaseRequisitionIDExists (String prID) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("PurchaseRequistion.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                // Check if the line from the text file matches the input
                if (line.equals(prID)) {
                    reader.close();
                    return true; // Input exists in the text file
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Input does not exist in the text file
    }
    
    // Retrieve Data From 'PurchaseRequisition,txt' file
    private static String getDataFromPurchaseRequisition(String prID) {
        String purchaseRequisitionTextFile = "PurchaseRequisition.txt";

        try (BufferedReader fileReader = new BufferedReader(new FileReader(purchaseRequisitionTextFile))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > 0 && columns[0].equals(prID)) {
                    return line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Helper method to parse a date string to a Date object
    private Date parseDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;

        do {
            try {
                parsedDate = dateFormat.parse(dateStr);
                if (!dateStr.equals(dateFormat.format(parsedDate))) {
                    // The entered date does not match the expected format
                    System.out.println("Error: The entered date does not match the expected format (yyyy-MM-dd).");
                    System.out.println("Please Enter The Date in yyyy-MM-dd format: ");
                    Scanner sc1 = new Scanner(System.in);
                    dateStr = sc1.next();
                }
            } catch (ParseException e) {
                System.out.println("Error: Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        
            if (parsedDate == null) {
                // Prompt the user for a new dateStr value only if the initial input was not in the correct format
                System.out.println("Please Enter The Date in yyyy-MM-dd format: ");
                Scanner sc1 = new Scanner(System.in);
                dateStr = sc1.next();
            }
        } while (parsedDate == null);

        return parsedDate;
    }

}
