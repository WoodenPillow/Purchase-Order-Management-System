package SalesManager;

import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Joshua
 */

public class DailyItemWiseSalesEntry {
    String itemTextFile = "C:\\Users\\user1\\Desktop\\APU\\Year 2 Sem 1\\Object Oriented Development with Java\\Assignment\\Assignment\\Purchase-Order-Management-System\\src\\SalesManager\\Item.txt";
    List <String> salesEntryList = new ArrayList<>();
    
    public DailyItemWiseSalesEntry(){menu();}
    
    public void menu() {
        Scanner sc1 = new Scanner(System.in);
        int option;
        
        do{
            System.out.println("Welcome To Daily Item-Wise Sales Entry Management. \nPlease Select An Option. \n1. Add \n2. Edit \n3. Delete \n4. Exit\n");
            option = sc1.nextInt();
            
            switch (option){
                case 1: 
                    addDIWSE();
                    break;
                
                case 2:
                    editDIWSE();
                    break;
               
                case 3:
                    deleteDIWSE();
                    break;
                    
                case 4:
                    System.out.println("Exiting...");
                    return;
                    
                default:
                    System.out.println("Wrong Input. Please Enter Again.");
            }
        }while (option < 1 || option > 4);        
    }

    public void addDIWSE(){
        
        try (BufferedReader reader = new BufferedReader(new FileReader(itemTextFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print each line from the file
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            
        Scanner sc1 = new Scanner(System.in);
        boolean found = false;

        do {
            System.out.println("Please Enter The Item Code You Want To Add: ");
            String input = sc1.next();
    
            // Assume the data structure like a list to store file contents
            List<String> itemTextList = new ArrayList<>(); // Initialize your list

            // Now we need to read the file into itemTextList

            // Read the file into itemTextList
            try (BufferedReader fileReader = new BufferedReader(new FileReader(itemTextFile))) {
                String line;
                while ((line = fileReader.readLine()) != null) {
                    itemTextList.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (String item : itemTextList) {
                String[] columns = item.split(",");

                if (columns.length > 0 && columns[0].equals(input)) {
                    found = true;
                    System.out.println("Item That You Want: ");
                    System.out.println("Item Code: " + columns[0]);
                    System.out.println("Item Name: " + columns[1]);
                    System.out.println("Item Price: " + columns[2]);
                    System.out.println("Supplier ID: " + columns[4]);

                    // Prompt the user to enter date in  specific format
                    System.out.println("Please Enter The Date (in yyyy-MM-dd format): ");
                    String date = sc1.next();
                    // Parse the date string to a Date object
                    Date poDate = parseDate(date);
                    
                    // quantity
                    int quantitySold;
                    
                    do {
                        System.out.println("Please Enter The Quantity That Has Sold: ");
                        String inputQuantity = sc1.next();
                        
                        try {
                            quantitySold = Integer.parseInt(inputQuantity);
                            if (quantitySold <= 0) {
                                System.out.println("Please Enter A Valid Positive Integer Number.");
                            } else if (quantitySold > Integer.parseInt(columns[3])) {
                                System.out.println("Input Error: Quantity sold cannot exceed the available quantity.");
                                quantitySold = -1; // Set an invalid value to continue the loop
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid positive integer.");
                            quantitySold = -1; // Set an invalid value to continue the loop
                        }
                    } while (quantitySold <= 0);               

                    // totalSales
                    double totalSales = Integer.parseInt(columns[2]) * quantitySold;
                    System.out.println("\nTotal Sales: " + totalSales + "\n");
                   
                    String salesEntry = "Item Code: " + columns[0] + ",Item Name: " + columns[1] + ",Item Price: " + columns[2] + ",Item Quantity Sold: " + quantitySold + ",Date: " + poDate + ",Supplier ID: " + columns[4] + ",Total Sales: " + totalSales + "\n";
                    System.out.println(salesEntry);
                    salesEntryList.add(salesEntry);
            
                    // Save Updated Data Into Text File
                    saveDIWSE(salesEntryList, "SalesEntry.txt");
            
                    // Exit the loop when a valid item is found
                    break;
                }
            }
    
            if(!found) {
                System.out.println("Error: Item Code not found in the file. (You Are Only Able To Edit Date And Quantity Sold.)\n");
            }
        } while (!found); // Continue looping until a valid item is found        
    }
        
    public void editDIWSE(){
        String salesEntryTextFile = ("SalesEntry.txt");
        
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter the Item Code you want to edit: ");
        String editItemCode  = sc1.next();
        boolean found = false;
        
        List<String> itemTextList = new ArrayList<>();
        
        // Read the file into itemTextList
        try (BufferedReader fileReader = new BufferedReader(new FileReader(itemTextFile))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                itemTextList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
     
        for (int i = 0; i < salesEntryList.size(); i++) {
            String salesEntry = salesEntryList.get(i);
            String[] columns = salesEntry.split(",");

            if (columns.length > 0 && columns[0].equals(editItemCode)) {
                found = true;
                System.out.println("Item Found. Current Details:");
                System.out.print(salesEntryTextFile);
                System.out.println("Which One Do You Want To Edit? \n1. Date\n2. Quantity Sold\n(Enter '1' or '2')\n");
                int input = sc1.nextInt();
                switch(input){
                    case 1:
                        System.out.println("Enter New Quantity Sold: ");
                        int newQuantitySold = sc1.nextInt();
                        
                        double totalSales = newQuantitySold * Double.parseDouble(columns[2]);
                        
                        // Update the item entry with new values
                        columns[3] = String.valueOf(newQuantitySold);
                        columns[6] = String.valueOf(totalSales);
                        break;
                        
                    case 2:
                        System.out.println("Enter New Date (in yyyy-MM-dd format): ");
                        String newDateStr = sc1.next();
                        Date newDate = parseDate(newDateStr);
                        
                        if (newDate != null) {
                        // Format the new date and update it in the columns
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            columns[4] = dateFormat.format(newDate);
                        } else {
                            System.out.println("Invalid date format. Date not updated.");
                        }
                        
                        break;
                    
                    default:
                        System.out.println("Error Input. Please Enter Again.");
                }
                
                // Join the updated columns into a single string
                String updatedItem = String.join(",", columns);

                // Update the list with the edited item
                salesEntryList.set(i, updatedItem);
                
                //Save Updated Data Into Text File
                saveDIWSE(salesEntryList, "SalesEntry.txt");
                break;
            }
        }

        if (!found) {
            System.out.println("Error: Item Code not found in the file.");
            
        }
        
    }
    
    public void deleteDIWSE(){
        String salesEntryTextFile = ("SalesEntry.txt");
        
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter the Item Code you want to delete: ");
        String input = sc1.next();
        boolean found = false;

        for (int i = 0; i < salesEntryList.size(); i++) {
            String item = salesEntryList.get(i);
            String[] columns = item.split(",");

            if (columns.length > 0 && columns[0].equals(input)) {
                found = true;
                System.out.println("Item Found. Deleting...");

                // Remove the item from the list
                salesEntryList.remove(i);
                
                //Save Updated Data Into Text File
                saveDIWSE(salesEntryList, "SalesEntry.txt");
                return; // Exit the loop after deleting the item
            }
        }

        if (!found) {
            System.out.println("Error: Item Code not found in the file.");
        }
        
    }

    //Save Data Into File
    public void saveDIWSE(List<String> salesEntryList, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String entry : salesEntryList) {
                writer.write(entry);
                writer.newLine(); // Add a newline after each entry
            }
            System.out.println("Data Is Successfully Saved To " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Failed to save data to " + fileName);
        }
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
