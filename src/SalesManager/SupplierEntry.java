package SalesManager;

import java.io.*;
import java.util.*;

public class supplierEntry {

    private String supplierFilePath = "C:\\Users\\vince\\OneDrive\\Documents\\NetBeansProjects\\Purchase-Order-Management-System\\src\\PurchaseManager\\Supplier.txt";
    private List<Supplier> suppliers = new ArrayList<>();

    public supplierEntry() {
        loadSuppliers();
    }

    public void loadSuppliers() {
        try (BufferedReader buffer = new BufferedReader(new FileReader(supplierFilePath))) {
            String line;
            while ((line = buffer.readLine()) != null) {
                String[] supplierData = line.split(",");
                if (supplierData.length == 4) {
                    Supplier supplier = new Supplier(
                            supplierData[0],
                            supplierData[1],
                            supplierData[2],
                            supplierData[3]
                    );
                    suppliers.add(supplier);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading supplier file: " + e.getMessage());
        }
    }

    public void viewSuppliers() {
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers available.");
        } else {
            System.out.println("Supplier ID\tSupplier Name\tItem ID\tSupplier Country");
            for (Supplier supplier : suppliers) {
                System.out.println(supplier);
            }
        }
    }

    public void addSupplier(String supplierID, String supplierName, String itemID, String supplierCountry) {
        Supplier newSupplier = new Supplier(supplierID, supplierName, itemID, supplierCountry);
        suppliers.add(newSupplier);
        saveSuppliers();
    }

    public void editSupplier(String supplierID, String supplierName, String itemID, String supplierCountry) {
        Supplier editedSupplier = findSupplierByID(supplierID);
        if (editedSupplier != null) {
            editedSupplier.setSupplierName(supplierName);
            editedSupplier.setItemID(itemID);
            editedSupplier.setSupplierCountry(supplierCountry);
            saveSuppliers();
        } else {
            System.out.println("Supplier not found. Edit failed.");
        }
    }

    public void deleteSupplier(String supplierID) {
        Supplier deletedSupplier = findSupplierByID(supplierID);
        if (deletedSupplier != null) {
            suppliers.remove(deletedSupplier);
            saveSuppliers();
        } else {
            System.out.println("Supplier not found. Delete failed.");
        }
    }

    private void saveSuppliers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(supplierFilePath))) {
            for (Supplier supplier : suppliers) {
                writer.write(supplier.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing supplier file: " + e.getMessage());
        }
    }

    private Supplier findSupplierByID(String supplierID) {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierID().equals(supplierID)) {
                return supplier;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        supplierEntry supplierEntry = new supplierEntry();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nSupplier Management Menu:");
            System.out.println("1. View Suppliers");
            System.out.println("2. Add Supplier");
            System.out.println("3. Edit Supplier");
            System.out.println("4. Delete Supplier");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    supplierEntry.viewSuppliers();
                    break;
                case 2:
                    System.out.print("Enter Supplier ID: ");
                    String addSupplierID = scanner.nextLine();
                    System.out.print("Enter Supplier Name: ");
                    String addSupplierName = scanner.nextLine();
                    System.out.print("Enter Item ID: ");
                    String addItemID = scanner.nextLine();
                    System.out.print("Enter Supplier Country: ");
                    String addSupplierCountry = scanner.nextLine();
                    supplierEntry.addSupplier(addSupplierID, addSupplierName, addItemID, addSupplierCountry);
                    break;
                case 3:
                    System.out.print("Enter Supplier ID to Edit: ");
                    String editSupplierID = scanner.nextLine();
                    System.out.print("Enter New Supplier Name: ");
                    String editSupplierName = scanner.nextLine();
                    System.out.print("Enter New Item ID: ");
                    String editItemID = scanner.nextLine();
                    System.out.print("Enter New Supplier Country: ");
                    String editSupplierCountry = scanner.nextLine();
                    supplierEntry.editSupplier(editSupplierID, editSupplierName, editItemID, editSupplierCountry);
                    break;
                case 4:
                    System.out.print("Enter Supplier ID to Delete: ");
                    String deleteSupplierID = scanner.nextLine();
                    supplierEntry.deleteSupplier(deleteSupplierID);
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting Supplier Management Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
        }
    }
}
}

class Supplier {
    private String supplierID;
    private String supplierName;
    private String itemID;
    private String supplierCountry;

    public Supplier(String supplierID, String supplierName, String itemID, String supplierCountry) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.itemID = itemID;
        this.supplierCountry = supplierCountry;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public void setSupplierCountry(String supplierCountry) {
        this.supplierCountry = supplierCountry;
    }

    @Override
    public String toString() {
        return String.format("%-12s%-16s%-10s%-16s", 
            supplierID, 
            supplierName, 
            itemID, 
            supplierCountry
        );
    }

}

