/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SalesManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author user1
 */
public class DailyItemWiseSalesEntry extends ItemEntry {

    public DailyItemWiseSalesEntry(List<Item> items) {
        super(items); //ERROR
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
        Item selectedItem = findItemByCode(itemCode);

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
        
        // Save the updated sales and stock information
        saveDIWSE();
    }

    public void addDIWSE(Item newItem, int quantitySold) {
        items.add(newItem);
        newItem.setDailySales(quantitySold);
        // Update the item stock using your preferred method (not shown in the provided code)
    }

    public void saveDIWSE() {
        try {
            FileWriter writer = new FileWriter("daily_sales_data.txt");
            for (Item item : items) {
                String line = item.getItemCode() + "," + item.getDailySales() + "\n";
                writer.write(line);
            }
            writer.close();
            System.out.println("Daily sales data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error while saving daily sales data: " + e.getMessage());
        }
    }

    public void deleteDIWSE(Item item) {
        items.remove(item);
        // Update the item stock using your preferred method (not shown in the provided code)
    }

    public void editDIWSE(Item item, int newQuantitySold) {
        item.setDailySales(newQuantitySold);
        // Update the item stock using your preferred method (not shown in the provided code)
    }

    private Item findItemByCode(String itemCode) {
        for (Item item : items) {
            if (item.getItemCode().equalsIgnoreCase(itemCode)) {
                return item;
            }
        }
        return null;
    }
    
    /*
    // Getter and setter for daily sales
    public int getDailySales() {
        return dailySales;
    }

    public void setDailySales(int dailySales) {
        this.dailySales = dailySales;
    }
    */ //Need to be typed in Item()
}
