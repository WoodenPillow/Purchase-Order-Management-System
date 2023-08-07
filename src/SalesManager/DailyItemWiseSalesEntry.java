/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SalesManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author user1
 */
public class DailyItemWiseSalesEntry extends ItemEntry {
    
    public DailyItemWiseSalesEntry(List<Item> items){
        super(List<Item> items)
    }
    
    public void dailyItemWiseSalesEntry() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("------ Daily Item-wise Sales Entry ------");

        // Display available items for sales entry
        System.out.println("Available Items:");
        for (Item item : items) {
            System.out.println(item.getItemCode() + ". " + item.getItemName());
        }

        System.out.print("Enter the item code: ");
        String itemCode = scanner.nextLine();
        Item selectedItem = null;
    }
    
    // Find the selected item by code
        for (Item item : items) {
            if (item.getItemCode().equalsIgnoreCase(itemCode)) {
                selectedItem = item;
                break;
            }
        }

        if (selectedItem == null) {
            System.out.println("Invalid item code. Returning to the main menu.");
            return;
        }

        System.out.print("Enter the quantity sold: ");
        int quantitySold = scanner.nextInt();

        if (quantitySold <= 0) {
            System.out.println("Invalid quantity sold. Returning to the main menu.");
            return;
        }

        // Update the item's daily sales and stock
        selectedItem.setDailySales(selectedItem.getDailySales() + quantitySold);
        // Update the item stock using your preferred method (not shown in the provided code)

        System.out.println("Daily sales entry successful!");
    }
    public void addDIWSE(){
    
    }
    
    public void saveDIWSE(){
        
    }

    public void deleteDIWSE(){
        
    }
    
    public void editDIWSE(){
        
    }
    
}
