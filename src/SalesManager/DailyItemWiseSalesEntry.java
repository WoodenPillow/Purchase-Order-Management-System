/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SalesManager;

import Administrator.fileHandler;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Joshua
 */

public class DailyItemWiseSalesEntry {
    private fileHandler obj1 = new fileHandler();
    private List <String> salesEntryList = new ArrayList<>();
    
    public DailyItemWiseSalesEntry(){}
    
    public void menu() {
        Scanner sc1 = new Scanner(System.in);
        int option;
        
        do{
            System.out.println("Welcome To Daily Item-Wise Sales Entry Management. \nPlease Select An Option. \n1. Add \n2. Edit \n3. Delete \n4. Save \n5. Exit \n");
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
                    saveDIWSE();
                    break;
                    
                case 5:
                    System.out.println("Exiting...");
                    return;
                    
                default:
                    System.out.println("Wrong Input. Please Enter Again.");
            }
        }while (option < 1 || option > 5);        
    }

    public void addDIWSE(){
        List <String> items = obj1.readData("ItemList.txt");
        System.out.println(items);
        
        System.out.println("Please Enter The Item Code You Want To Add: \n");
        Scanner sc1 = new Scanner(System.in);
        String input = sc1.next();
        boolean found = false;
        
        while(true){
            for (String item: items){
                String[] columns = item.split(",");
            
                if (columns.length > 0 && columns[0].equals(input)){//columns[0] depends on the index of itemID in the list
                    found = true;
                    System.out.println("Item That You Want: ");
                    System.out.println("Item Code: " + columns[0]); //Column name of list (Quantity Sold)(Item Name)(Supplier ID)(Unit Price)(Total Price)(Date)
                    System.out.println("Item Code: " + columns[0]); //Column name of list 
                    System.out.println("Item Code: " + columns[0]); //Column name of list
                    
                    /*
                    // Prompt for new data (Code Is Used Only When Below Attributes Are Not Entered In ItemEntry.txt)
                    System.out.println("Enter new quantity sold: ");
                    int newQuantity = sc1.nextInt();
                    System.out.println("Enter new unit price: ");
                    double newUnitPrice = sc1.nextDouble();
                    */
                    
                    String salesEntry = "Item Code: " + columns[0] + "\nItem Name: " + columns[1] + "\nSupplier ID: " + columns[2];
                    salesEntryList.add(salesEntry);
                            
                    //Save Updated Data Into Text File
                    saveDIWSE();
                    
                    break;
                }
            }
        
            if (found){
                System.out.println("Error: ItemCode not found in the file. \nPlease Enter Again.\n");
                found = false;
            }
        }
    }
    
    public void editDIWSE(){
        salesEntryList = obj1.readData("SalesEntry.txt");
        System.out.println(salesEntryList);
        
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter the Item Code you want to edit: ");
        String input = sc1.next();
        boolean found = false;
        
        for (int i = 0; i < salesEntryList.size(); i++) {
            String item = salesEntryList.get(i);
            String[] columns = item.split("\n");

            if (columns.length > 0 && columns[0].equals(input)) {
                found = true;
                System.out.println("Item Found. Current Details:");
                System.out.println("Item Code: " + columns[0]);
                System.out.println("Item Name: " + columns[1]);
                System.out.println("Supplier ID: " + columns[2]);

                // Prompt For Edit Item Value
                System.out.println("Enter new quantity sold: "); 
                int newQuantity = sc1.nextInt();
                System.out.println("Enter new unit price: ");
                double newUnitPrice = sc1.nextDouble();

                // Update the item entry with new values
                columns[3] = Integer.toString(newQuantity);
                columns[4] = Double.toString(newUnitPrice);

                // Join the updated columns into a single string
                String updatedItem = String.join("\n", columns);

                // Update the list with the edited item
                salesEntryList.set(i, updatedItem);
                
                //Save Updated Data Into Text File
                saveDIWSE();
                break;
            }
        }

        if (!found) {
            System.out.println("Error: Item Code not found in the file.");
        }
        
    }
    
    public void deleteDIWSE(){
        salesEntryList = obj1.readData("SalesEntry.txt");
        System.out.println(salesEntryList);
        
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter the Item Code you want to delete: ");
        String input = sc1.next();
        boolean found = false;

        for (int i = 0; i < salesEntryList.size(); i++) {
            String item = salesEntryList.get(i);
            String[] columns = item.split("\n");

            if (columns.length > 0 && columns[0].equals(input)) {
                found = true;
                System.out.println("Item Found. Deleting...");

                // Remove the item from the list
                salesEntryList.remove(i);
                
                //Save Updated Data Into Text File
                saveDIWSE();
                return; // Exit the loop after deleting the item
            }
        }

        if (!found) {
            System.out.println("Error: Item Code not found in the file.");
        }
        
    }
    
    public void saveDIWSE(){
        //Save Data Into File
        obj1.writeData("SalesEntry.txt", salesEntryList);
        System.out.println("Data Is Succussfully Saved To SalesEntry.txt");
    }
}
