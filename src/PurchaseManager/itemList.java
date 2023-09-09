package PurchaseManager;

import java.io.*;
import java.util.*;

public class itemList {
    private static final String FILE_PATH = "C:\\Users\\vince\\OneDrive\\Documents\\NetBeansProjects\\Purchase-Order-Management-System(POMS)\\src\\SalesManager\\Item.txt";

    public static void main(String[] args) {
        viewItems();
    }

    public static void viewItems() {
        System.out.println("\n============================================================");
        System.out.println("\tList of Items");
        System.out.println("\n============================================================");
        System.out.printf("%-5s %-15s %-10s %-10s %-15s%n", "ID", "Name", "Price", "Quantity", "Supplier ID");

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] itemFields = line.split(",");
                if (itemFields.length == 5) {
                    System.out.printf("%-5s %-15s %-10s %-10s %-15s%n",
                            itemFields[0], itemFields[1], itemFields[2], itemFields[3], itemFields[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


