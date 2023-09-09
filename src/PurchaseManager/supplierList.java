package PurchaseManager;

import java.io.*;
import java.util.*;

public class supplierList {
    private static final String FILE_PATH = "C:\\Users\\vince\\OneDrive\\Documents\\NetBeansProjects\\Purchase-Order-Management-System(POMS)\\src\\SalesManager\\Supplier.txt";

    public static void viewSuppliers() {
        System.out.println("\n============================================================");
        System.out.println("\tList of Suppliers");
        System.out.println("\n============================================================");
        System.out.printf("%-10s %-20s %-20s %-20s%n", "ID", "Name", "Item ID", "Supplier Country");

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] supplierFields = line.split(",");
                if (supplierFields.length == 4) {
                    System.out.printf("%-10s %-20s %-20s %-20s%n",
                    supplierFields[0], supplierFields[1], supplierFields[2], supplierFields[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            }
    }
}
