package SalesManager;

import java.io.*;
import java.util.*;

public final class supplierEntry {
    private static final String FILE_PATH = "C:\\Users\\user\\Desktop\\Uni Stuff\\Year 2 Semester 1\\OODJ\\Assignment\\Purchase-Order-Management-System\\src\\SalesManager\\Supplier.txt";
    private List<Supplier> suppliers = new ArrayList<>();

    public supplierEntry() {
        loadSuppliers();
    }

    public void loadSuppliers() {
        try (BufferedReader buffer = new BufferedReader(new FileReader(FILE_PATH))) {
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

    private void viewSuppliers() {
        System.out.println("\n============================================================");
        System.out.println("\tList of Suppliers");
        System.out.println("\n============================================================");
        System.out.printf("%-10s %-20s %-20s %-20s%n", "ID", "Name", "Item ID", "Supplier Country");

        for (Supplier supplier : suppliers) {
            System.out.printf("%-10s %-20s %-20s %-20s%n",
                    supplier.getSupplierID(), supplier.getSupplierName(),
                    supplier.getItemID(), supplier.getSupplierCountry());
        }
    }

    public void addSupplier(String supplierName, String itemID, String supplierCountry) {
        // Check for duplicate supplier names
        if (isSupplierNameDuplicate(supplierName)) {
            System.out.println("Supplier with the same name already exists. Adding failed.");
            return;
        }

        // Check if the Item ID matches the required pattern
        if (!itemID.matches("^I\\d+$")) {
            System.out.println("Invalid Item ID format. Item ID must start with 'I' followed by numbers.");
            return;
        }

        String supplierID = generateNewID();
        Supplier newSupplier = new Supplier(supplierID, supplierName, itemID, supplierCountry);
        suppliers.add(newSupplier);
        saveSuppliers();
    }



    // Helper method to check if a supplier name already exists
    private boolean isSupplierNameDuplicate(String supplierName) {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierName().equalsIgnoreCase(supplierName)) {
                return true;
            }
        }
        return false;
    }

    // Helper method to check if a supplier ID already exists
    private boolean isSupplierIDDuplicate(String supplierID) {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierID().equalsIgnoreCase(supplierID)) {
                return true;
            }
        }
        return false;
    }

    public void editSupplier(String supplierID, String supplierName, String itemID, String supplierCountry) {
        Supplier editedSupplier = findSupplierByID(supplierID);
        if (editedSupplier != null) {
            // Check if the Item ID is being changed
            if (!editedSupplier.getItemID().equalsIgnoreCase(itemID)) {
                // Item ID is being changed, generate a new Supplier ID
                String newSupplierID = generateNewID();
                editedSupplier.setItemID(itemID); // Update Item ID
                editedSupplier.setSupplierCountry(supplierCountry); // Update Supplier Country
                saveSuppliers();
            } else {
                // Item ID remains the same, update other fields
                editedSupplier.setSupplierName(supplierName);
                editedSupplier.setSupplierCountry(supplierCountry);
                saveSuppliers();
            }
        } else {
            System.out.println("Supplier not found. Edit failed.");
        }
    }


    // Helper method to check if an itemID already exists
    private boolean isItemIDDuplicate(String itemID) {
        for (Supplier supplier : suppliers) {
            if (supplier.getItemID().equalsIgnoreCase(itemID)) {
                return true;
            }
        }
        return false;
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Supplier supplier : suppliers) {
                // Use commas (,) as separators
                writer.write(supplier.getSupplierID() + "," +
                             supplier.getSupplierName() + "," +
                             supplier.getItemID() + "," +
                             supplier.getSupplierCountry());
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

    private String generateNewID() {
        int maxID = 0;
        Set<String> existingIDs = new HashSet<>(); // Keep track of existing supplier IDs

        for (Supplier supplier : suppliers) {
            String supplierID = supplier.getSupplierID();
            existingIDs.add(supplierID); // Add existing supplier IDs to the set

            int supplierIntID = Integer.parseInt(supplierID.substring(1)); // Parse supplier ID as an integer
            if (supplierIntID > maxID) {
                maxID = supplierIntID;
            }
        }

        // Generate the next available supplier ID
        String newID;
        do {
            maxID++; // Increment maxID
            newID = String.format("S%04d", maxID); // Format as a supplier ID
        } while (existingIDs.contains(newID)); // Check if it already exists

        return newID;
    }

    public static void menu() {
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
                    supplierEntry.viewSuppliers(); // Call viewSuppliers to display suppliers
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
                    supplierEntry.addSupplier(addSupplierName, addItemID, addSupplierCountry);
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getSupplierCountry() {
        return supplierCountry;
    }

    public void setSupplierCountry(String supplierCountry) {
        this.supplierCountry = supplierCountry;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s\t%s",
            supplierID,
            supplierName,
            itemID,
            supplierCountry
        );
    }
}



