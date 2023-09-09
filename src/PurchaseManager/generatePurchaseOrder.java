package PurchaseManager;

import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class generatePurchaseOrder  {
    
    private static final String poTextFile = "C:\\Users\\user1\\Desktop\\APU\\Year 2 Sem 1\\Object Oriented Development with Java\\Assignment\\Assignment\\Purchase-Order-Management-System\\src\\PurchaseManager\\PurchaseOrder.txt";
    private static final String usersTextFile = "C:\\Users\\user1\\Desktop\\APU\\Year 2 Sem 1\\Object Oriented Development with Java\\Assignment\\Assignment\\Purchase-Order-Management-System\\src\\Login\\LoginCredentials.txt";
    private static final String prTextFile = "C:\\Users\\user1\\Desktop\\APU\\Year 2 Sem 1\\Object Oriented Development with Java\\Assignment\\Assignment\\Purchase-Order-Management-System\\src\\SalesManager\\PurchaseRequisition.txt";
    
    private String purchaseManagerID;
    private String purchaseManagerRole;
    
    private List<String> purchaseOrderList = new ArrayList<>();
    
    public generatePurchaseOrder(){}
    
    public generatePurchaseOrder(String purchaseManagerID, String purchaseManagerRole) {
        this.purchaseManagerID = purchaseManagerID;
        this.purchaseManagerRole = purchaseManagerRole;
    }
    
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
                        System.out.println("\nWrong Input. Please Enter Again.\n");
                }
                
            } catch (java.util.InputMismatchException e) {
                sc1.nextLine();
                System.out.println("\nInvalid input. Please enter a valid option (1-4).\n");
                option = 0;
            }
        } while (option < 1 || option > 4);       
    }

    public void addPO(){
        Scanner sc1 = new Scanner(System.in);
        String newPOID = "";
        String pmID = "";
        
        while (true){
            System.out.println("\nPrinting Current Purchase Order...");
            try (BufferedReader buffer = new BufferedReader(new FileReader(poTextFile))) {
                String line;
                while ((line = buffer.readLine()) != null) {
                    System.out.println(line);
                }
            }catch (IOException e) {
                System.err.println("Error reading PurchaseOrder.txt: " + e.getMessage());
            }
            
            System.out.println("\nPlease Enter A New Purchase Order ID: \n");
            newPOID = sc1.next();
            
            boolean isUnique =  true;
            
            for (String purchaseOrder : purchaseOrderList) {
                String[] columns = purchaseOrder.split(",");
                
                if (columns.length > 0 && columns[0].equals(newPOID)) {
                    isUnique = false;
                    System.out.println("Purchase Order Exists. Please Enter A New Purchase Order ID. \n");
                    break;
                }
            }
            
            if(isUnique){
                System.out.println("\nPurchase Order ID added successfully.\n");
                System.out.println("Proceed to next step...\n");
            }
            
            System.out.println("Please Enter The Date (in yyyy-MM-dd format): ");
            String enterDate = sc1.next();
            Date poDate = parseDate(enterDate);
            
            System.out.println("Proceed To Next Step...\n");
            System.out.println("Printing Purchase Requisition List...\n");
            
            try (BufferedReader buffer = new BufferedReader(new FileReader(prTextFile))) {
                String line;
                while ((line = buffer.readLine()) != null) {
                    System.out.println(line);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            
            boolean isValidPRID = false;
        
            while (!isValidPRID){
                System.out.println("Please Enter Purchase Requisition ID: \n");
                int prID = sc1.nextInt();
                
                if (isPurchaseRequisitionIDExists(prID)){
                    System.out.println("Retrieving Data...\n");
                    String prData = getDataFromPurchaseRequisition(prID);
                
                    if (prData != null) {
                        String[] prFields = prData.split(",");

                        if (prFields.length >= 7) { 
                            String prItemID = prFields[1];
                            int prItemQuantity = Integer.parseInt(prFields[2]);
                            String prDate = prFields[3];
                            String prSupplierID = prFields[4];
                            double prUnitPrice = Double.parseDouble(prFields[5]);
                            double prTotalPrice = Double.parseDouble(prFields[6]);

                            System.out.println("Purchase Requisition ID: " + prID);
                            System.out.println("Item ID: " + prItemID);
                            System.out.println("Item Quantity: " + prItemQuantity);
                            System.out.println("Supplier ID: " + prSupplierID);
                            System.out.println("Date: " + prDate);
                            System.out.println("Unit Price: " + prUnitPrice);
                            System.out.println("Total Price: " + prTotalPrice);

                            String newPurchaseOrder = newPOID + "," + poDate + "," + prID + "," + prItemID + "," + prItemQuantity + "," + prUnitPrice + "," + prTotalPrice + "," + prSupplierID;

                            purchaseOrderList.add(newPurchaseOrder);

                            savePO();
                            menu();
                            return;
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
            break;
        }        
    }
    
    public void editPO() {
        Scanner sc1 = new Scanner(System.in);
            
        do{
            System.out.println("Printing Purchase Order List...\nCurrent Purchase Order:");
            try (BufferedReader buffer = new BufferedReader(new FileReader(poTextFile))) {
                String line;
                while ((line = buffer.readLine()) != null) {
                    System.out.println(line);
                }
            }catch (IOException e) {
                System.err.println("Error reading PurchaseOrder.txt: " + e.getMessage());
            }
            System.out.println("Please Enter the Purchase Order ID to Edit: ");
            String editPOID = sc1.next();
            boolean isFound = false;
            for (int i = 0; i < purchaseOrderList.size(); i++) {
                String purchaseOrder = purchaseOrderList.get(i);
                String[] columns = purchaseOrder.split(",");

                if (columns.length > 0 && columns[0].equals(editPOID)) {
                    isFound = true;
                    System.out.println("Purchase Order Found. Current Details:");
                    System.out.println("Purchase Order ID: " + columns[0]);
                    System.out.println("Purchase Order Date: " + columns[1]);
                    System.out.println("Purchase Requisition ID: " + columns[2]);
                    System.out.println("Item ID: " + columns[3]);
                    System.out.println("Item Quantity: " + columns[4]);
                    System.out.println("Unit Price: " + columns[5]);
                    System.out.println("Total Price: " + columns[6]);
                    System.out.println("Supplier ID: " + columns[7]);

                    System.out.println("Select What You Want to Edit:");
                    System.out.println("1. Purchase Order Date");
                    System.out.println("2. Item Quantity");
                    System.out.println("3. Exit (No Changes)");

                    int editOption = sc1.nextInt();
                    sc1.nextLine();

                    switch (editOption) {
                        case 1:
                            System.out.println("Enter New Purchase Order Date (yyyy-MM-dd): ");
                            String newDateStr = sc1.next();
                    
                            // Parse the user-entered date into the desired format
                            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat outputDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");

                            try {
                                Date newDate = inputDateFormat.parse(newDateStr);
                                String formattedDate = outputDateFormat.format(newDate);

                                columns[1] = formattedDate;
                                System.out.println("Purchase Order Date updated successfully.");
                            } catch (ParseException e) {
                                System.out.println("Invalid date format. Date not updated.");
                            }
                            break;
                        case 2:
                            System.out.println("Enter New Item Quantity: ");
                            int newQuantity = sc1.nextInt();
                            columns[4] = String.valueOf(newQuantity);
                            double newTotalPrice = newQuantity * Double.parseDouble(columns[6]);
                            columns[6] = String.valueOf(newTotalPrice);
                            System.out.println("Item Quantity and Total Price updated successfully.");
                            break;
                        case 3:
                            System.out.println("No Changes Made.");
                            break;
                        default:
                            System.out.println("Invalid option. No Changes Made.");
                            break;
                    }
                    String updatedPurchaseOrder = String.join(",", columns);
                    purchaseOrderList.set(i, updatedPurchaseOrder);
                    savePO();
                    menu();
                    return;
                }
            }
            if (!isFound) {
                System.out.println("Error: Purchase Order ID not found. Please Enter Again.\n");
            }
        }while(true);  
    }
    
    public void deletePO(){
        System.out.println(purchaseOrderList);
        
        while(true){
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
                    purchaseOrderList.remove(i);
                    savePO();
                    menu();
                    return;
                }
                break;
            }
            if (!found) {
                System.out.println("Error: Purchase Order not found in the file.");
            }
        }
    }
        
    public void savePO() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(poTextFile))) {
            for (String entry : purchaseOrderList) {
                writer.write(entry);
                writer.newLine();
            }
            System.out.println("Data Is Successfully Saved To 'PurchaseOrder.txt'");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Failed to save data to 'PurchaseOrder.txt'");
        }
    }
    
    private boolean isPurchaseRequisitionIDExists (int prID) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(prTextFile));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(String.valueOf(prID))) {
                reader.close();
                return true; 
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; 
    }
    
    private static String getDataFromPurchaseRequisition(int prID) {
        String purchaseRequisitionTextFile = "PurchaseRequisition.txt";
        String result = null;
        
        try (BufferedReader fileReader = new BufferedReader(new FileReader(prTextFile))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > 0 && columns[0].equals(String.valueOf(prID))) {
                    result = line;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Date parseDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;

        do {
            try {
                parsedDate = dateFormat.parse(dateStr);
                if (!dateStr.equals(dateFormat.format(parsedDate))) {
                    System.out.println("Error: The entered date does not match the expected format (yyyy-MM-dd).");
                    System.out.println("Please Enter The Date in yyyy-MM-dd format: ");
                    Scanner sc1 = new Scanner(System.in);
                    dateStr = sc1.next();
                }
            } catch (ParseException e) {
                System.out.println("Error: Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        
            if (parsedDate == null) {
                System.out.println("Please Enter The Date in yyyy-MM-dd format: ");
                Scanner sc1 = new Scanner(System.in);
                dateStr = sc1.next();
            }
        } while (parsedDate == null);

        return parsedDate;
    }
}
