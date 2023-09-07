package PurchaseManager;

import Administrator.fileHandler;

import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.*;

/**
 *
 * @author Joshua
 */

public class generatePurchaseOrder {
    private String poID;
    private Date poDate;
    private String pmID;
    private String prID;
    private String itemID;
    private int itemQuantity;
    private double totalPrice = 0.0;
    private String supplierID;
    double itemPrice = 0.0;
    
    
    private fileHandler obj1 = new fileHandler();
    private List <String> purchaseOrderList = new ArrayList<>();
    
    public generatePurchaseOrder(){}    
    
    public void menu() {
        Scanner sc1 = new Scanner(System.in);
        int option;
        
        do{
            System.out.println("Welcome To Purchase Order Management. \nPlease Select An Option. \n1. Add \n2. Edit \n3. Delete \n4. Save \n5. Exit \n");
            option = sc1.nextInt();
            
            switch (option){
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
                    savePO();
                    break;
                    
                case 5:
                    System.out.println("Exiting...");
                    return;
                    
                default:
                    System.out.println("Wrong Input. Please Enter Again.");
            }
        }while (option < 1 || option > 5);        
    }

    public void addPO(){
        System.out.println(obj1.readData("PurchaseRequisition.txt"));
        
        Scanner sc1 = new Scanner(System.in);
        Pattern pattern1 = Pattern.compile("^PO//d+$");
        
        while (true){
            // Prompt the user to enter the new Purchase Order ID
            System.out.println("Printing Purchase Order List...\nCurrent Purchase Order:\n");
            System.out.println(obj1.readData("PurchaseOrder.txt"));
            System.out.println("Please Enter The New Purchase Order ID: (ex: PO1 or PM11)");
            String newPOID = sc1.next();
            
            Matcher matcher = pattern1.matcher(newPOID);
            
            if (!matcher.matches()){
                System.out.println("Error. \n");
            }
            else{
                // Proceed To Check if the input exists in the text file
                if (isPurchaseOrderIDExists(newPOID)) {
                    System.out.println("The Purchase Order ID already exists in the text file.");
                } else {
                    System.out.println("The Purchase Order ID is valid and does not exist in the text file.");
                }
                System.out.println("Proceed To Next Step...");
                break;
            }
        }    
        
        // Prompt the user to enter date in  specific format
        System.out.println("Please Enter The Order Date (in yyyy-MM-dd format): ");
        String newPODateStr = sc1.next();
        // Parse the date string to a Date object (you may need to handle exceptions)
        Date poDate = parseDate(newPODateStr);
        
        // Prompt the user to enter purchase manager ID
        Pattern pattern2 = Pattern.compile("^PM//d+$");
        
        while (true){
            System.out.println("Printing User List...");
            System.out.println(obj1.readData("Users.txt"));
            System.out.println("Please Enter Purchase Manager ID. (ex: PM1 or PM11)");
            pmID = sc1.next();
            
            Matcher matcher = pattern2.matcher(pmID);
            
            if (!matcher.matches()){
                System.out.println("Error. \n");
            }
            else{
                //Proceed To Check If The Input Exists In 'Users.txt' File
                if (isPurchaseManagerIDExists(pmID)){
                    System.out.println("Proceed To Next Step...");
                    break;
                }
                else{
                    System.out.println("Error.");
                }
            }
        } 
        
        // Prompt the user to enter purchase requistion ID
        Pattern pattern3 = Pattern.compile("^PR//d+$");
        
        while (true){
            System.out.println("Printing Purchase Requisition List...");
            System.out.println(obj1.readData("PurchaseRequisition.txt"));
            System.out.println("Please Enter Purchase Requisition ID: (ex: PR1 or PR11)");
            prID = sc1.next();
            
            Matcher matcher = pattern3.matcher(prID);
            
            if (!matcher.matches()){
                System.out.println("Error. \n");
            }
            else{
                //Proceed To Check If The Input Exists In 'PurchaseRequisition.txt' File
                if (isPurchaseRequisitionIDExists(prID)){
                    System.out.println("Proceed To Next Step...");
                    break;
                }
                else{
                    System.out.println("Error.");
                }
            }
        }
        
        // Prompt the user to enter item ID
        Pattern pattern4 = Pattern.compile("^I//d+$");
        
        while (true){
            System.out.println("Printing Item List...");
            System.out.println(obj1.readData("Item.txt"));
            System.out.println("Please Enter Item ID: (ex: I1 or I11)");
            itemID = sc1.next();
            
            Matcher matcher = pattern4.matcher(itemID);
            
            if (!matcher.matches()){
                System.out.println("Error. \n");
            }
            else{
                //Proceed To Check If The Input Exists In 'Item.txt' File
                if (isItemIDExists(itemID)){
                    // Retrieve the item name and price from 'Item.txt' based on item ID
                    String[] itemData = getItemDataFromItemList(itemID);

                    if (itemData != null && itemData.length >= 2) {
                        String itemName = itemData[1]; 
                        itemPrice = Double.parseDouble(itemData[2]);
                        itemQuantity = Integer.parseInt(itemData[3]);

                        System.out.println("Item Name: " + itemName);
                        System.out.println("Item Price: " + itemPrice);
                        
                        itemQuantity = 0;
                        boolean validQuantity = false;
                        
                        while (!validQuantity) {
                            System.out.println("Please Enter Item Quantity: ");

                            if (sc1.hasNextInt()) {
                                itemQuantity = sc1.nextInt();
                                if (itemQuantity >= 0) {
                                    validQuantity = true;
                                } else {
                                    System.out.println("Error: Invalid Input. Please Enter A Valid Integer. ");
                                }
                            } else {
                                System.out.println("Error: Invalid Input. Please Enter A Valid Integer. ");
                                sc1.next();
                            }
                        }
                    }                    
                    break;
                }
                else{
                    System.out.println("Error.");
                }
            }
        }
       
        
        // Prompt the user to enter supplier ID
        Pattern pattern5 = Pattern.compile("^S//d+$");
        
        while (true){
            System.out.println("Printing Supplier List...");
            System.out.println(obj1.readData("Supplier.txt"));
            System.out.println("Please Enter Supplier ID: (ex: S1 or S11)");
            supplierID = sc1.next();
            
            Matcher matcher = pattern5.matcher(supplierID);
            
            if (!matcher.matches()){
                System.out.println("Error. \n");
            }
            else{
                //Proceed To Check If The Input Exists In 'Supplier.txt' File
                if (isSupplierIDExists(supplierID)){
                    System.out.println("Proceed To Next Step...");
                    break;
                }
                else{
                    System.out.println("Error.");
                }
            }
        }
        
        String newPurchaseOrder = "Purchase Order ID: " + poID + "\nPurchase Order Date: " + poDate + "\nPurchase Manager ID: " + pmID + "\nPurchase Requisition ID: " + prID + "\nItem ID: " + itemID + "\nItem Quantity: " + itemQuantity + "\nItem Price: " + itemPrice + "\nTotal Price: " + totalPrice + "\nSupplier ID: " + supplierID + "\n\n";
        // Add the new Purchase Order into list
        purchaseOrderList.add(newPurchaseOrder);
        
        //Save Purchase Order List Into Text File
        savePO();
        
    }
    
    public void editPO(){
        purchaseOrderList = obj1.readData("PurchaseOrder.txt");
        System.out.println(purchaseOrderList);
        
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter the Purchase Order ID you want to edit: ");
        String input = sc1.next();
        boolean found = false;
        
        for (int i = 0; i < purchaseOrderList.size(); i++) {
            String purchaseOrder = purchaseOrderList.get(i);
            String[] columns = purchaseOrder.split("\n");

            if (columns.length > 0 && columns[0].equals(input)) {
                found = true;
                System.out.println("Purchase Order Found. Current Details:");
                System.out.println("1. Purchase Order ID: " + columns[0]);
                System.out.println("2. Order Date: " + columns[1]);
                System.out.println("3. Purchase Manager ID: " + columns[2]);
                System.out.println("4. Purchase Requisition ID: " + columns[3]);
                System.out.println("5. Item ID & Item Quantity: " + columns[4]);
                System.out.println("6. Supplier ID: " + columns[5]);
                System.out.println("Select a field to edit (1-6): ");
                int choice = sc1.nextInt();
                
                switch (choice) {
                    case 1:
                        // Edit Purchase Order ID
                        System.out.println("Printing Purchase Order List...");
                        System.out.println(obj1.readData("PurchaseOrder.txt"));
                        System.out.println("Enter new Purchase Order ID: ");
                        String newPOID = sc1.next();
                        columns[0] = newPOID;
                        break;
                    case 2:
                        // Edit Order Date
                        System.out.println("Printing Purchase Order List...");
                        System.out.println(obj1.readData("PurchaseOrder.txt"));
                        System.out.println("Enter new Order Date (yyyy-MM-dd format): ");
                        String newPODateStr = sc1.next();
                        Date newPODate = parseDate(newPODateStr);
                        columns[1] = newPODate.toString();
                        break;
                    case 3:
                        // Edit Purchase Manager ID
                        boolean validPmID = false;
                        
                        while (!validPmID) {
                            System.out.println("Printing Purchase Order List...");
                            System.out.println(obj1.readData("PurchaseOrder.txt"));
                            System.out.println("Enter new Purchase Manager ID: ");
                            String newPmID = sc1.next();
                            // Check if the new Purchase Manager ID exists in 'Users.txt'
                            if (isPurchaseManagerIDExists(newPmID)) {
                                columns[2] = newPmID;
                                validPmID = true; // Exit the loop if a valid ID is provided
                            } else {
                                System.out.println("Error: Invalid Purchase Manager ID. Please enter a valid one.");
                            }
                        }
                        break;
                        
                    case 4:
                        // Edit Purchase Requisition ID
                        boolean validPRID = false;
                        
                        while (!validPRID) {
                            System.out.println("Printing Purchase Order List...");
                            System.out.println(obj1.readData("PurchaseOrder.txt"));
                            System.out.println("Enter new Purchase Requisition ID: ");
                            String newPRID = sc1.next();
                            // Check if the new Purchase Requisition ID exists in 'PurchaseRequisition.txt'
                            if (isPurchaseRequisitionIDExists(newPRID)) {
                                columns[2] = newPRID;
                                validPRID = true; // Exit the loop if a valid ID is provided
                            } else {
                                System.out.println("Error: Invalid Purchase Requisition ID. Please enter a valid one.");
                            }
                        }
                        break;
                        
                    case 5:
                        // Edit Item ID
                        boolean validItemID = false;
                        
                        while (!validItemID) {
                            System.out.println("Printing Purchase Order List...");
                            System.out.println(obj1.readData("PurchaseOrder.txt"));
                            System.out.println("Enter new Item ID: ");
                            String newItemID = sc1.next();
                            // Check if the new Item ID exists in 'ItemList.txt'
                            if (isItemIDExists(newItemID)) {
                                columns[2] = newItemID;
                                validItemID = true; // Exit the loop if a valid ID is provided
                            } else {
                                System.out.println("Error: Invalid Item ID. Please enter a valid one.");
                            }
                        }
                        break;
                        
                    case 6:
                        /// Edit Supplier ID
                        boolean validSupplierID = false;
                        
                        while (!validSupplierID) {
                            System.out.println("Printing Purchase Order List...");
                            System.out.println(obj1.readData("PurchaseOrder.txt"));
                            System.out.println("Enter new Supplier ID: ");
                            String newSupplierID = sc1.next();
                            // Check if the new Supplier ID exists in 'Suppplier.txt'
                            if (isSupplierIDExists(newSupplierID)) {
                                columns[2] = newSupplierID;
                                validSupplierID = true; // Exit the loop if a valid ID is provided
                            } else {
                                System.out.println("Error: Invalid Supplier ID. Please enter a valid one.");
                            }
                        }
                        break;
                }
                
                // Join the updated columns into a single string
                String updatedPO = String.join("\n", columns);

                // Update the list with the edited purchase order
                purchaseOrderList.set(i, updatedPO);
                
                savePO();
                break;
            }
        }

        if (!found) {
            System.out.println("Error: Purchase Order ID not found in the file.");
        }
        
    }
    
    public void deletePO(){
        purchaseOrderList = obj1.readData("PurchaseOrder.txt");
        System.out.println(purchaseOrderList);
        
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter the Purchase Order ID you want to delete: ");
        String input = sc1.next();
        boolean found = false;

        for (int i = 0; i < purchaseOrderList.size(); i++) {
            String item = purchaseOrderList.get(i);
            String[] columns = item.split("\n");

            if (columns.length > 0 && columns[0].equals(input)) {
                found = true;
                System.out.println("Purhcase Order Found. Deleting...");

                // Remove the item from the list
                purchaseOrderList.remove(i);
                
                savePO();
                return; // Exit the loop after deleting the item
            }
        }

        if (!found) {
            System.out.println("Error: Item Code not found in the list.");
        }  
    }
    
    public void savePO(){
        obj1.writeData("SalesEntry.txt", purchaseOrderList);
        System.out.println("Data Is Succussfully Saved To SalesEntry.txt");
    }
    
    // Helper method to parse a date string to a Date object
    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Error parsing date. Please enter the date in yyyy-MM-dd format.");
            return null; // Handle the parsing error gracefully
        }
    } 
    
    // Function to check if a Purchase Order ID exists in the text file
    private boolean isPurchaseOrderIDExists (String poID) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("PurchaseOrder.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                // Check if the line from the text file matches the input
                if (line.equals(poID)) {
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
    
    // Function to check if Purchase Manager ID exists in the 'Users.txt' file
    private boolean isPurchaseManagerIDExists(String pmID) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Users.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                // Check if the line from the 'Users.txt' file matches the input
                if (line.equals(pmID)) {
                    reader.close();
                    return true; // Input exists in the 'Users.txt' file
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // Input does not exist in the 'Users.txt' file
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
    
    // Function to check if a Purchase Requistion ID exists in the text file
    private boolean isItemIDExists (String itemID) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Item.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                // Check if the line from the text file matches the input
                if (line.equals(itemID)) {
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
    
    // Function to check if a Purchase Requistion ID exists in the text file
    private boolean isSupplierIDExists (String supplierID) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Supplier.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                // Check if the line from the text file matches the input
                if (line.equals(supplierID)) {
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
    
    // Function to retrieve item name and price from 'Item.txt' based on item ID
    private String[] getItemDataFromItemList(String itemID) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Item.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split("\\s+"); // Assuming columns are separated by whitespace

                if (columns.length >= 5 && columns[0].equals(itemID)) {
                    // Return item name (columns[1]) and item price (columns[2])
                    return new String[]{columns[1], columns[2]};
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Item data not found for the given item ID
    }

    
}
