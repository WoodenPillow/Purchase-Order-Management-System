package SalesManager;

import java.io.*;
import java.util.*;

public class itemEntry {
    private static final String FILE_PATH = "C:\\Users\\vince\\OneDrive\\Documents\\NetBeansProjects\\Purchase-Order-Management-System(POMS)\\src\\SalesManager\\Item.txt";
    private static final String FILE_PATH_BUFFER = "C:\\Users\\vince\\OneDrive\\Documents\\NetBeansProjects\\Purchase-Order-Management-System(POMS)\\src\\SalesManager\\Item_Buffer.txt";

    private static final int ITEM_FIELDS = 5; // Number of fields in an item record

    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Item Entry Menu:");
            System.out.println("1. View Items");
            System.out.println("2. Add Item");
            System.out.println("3. Edit Item");
            System.out.println("4. Delete Item");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewItems();
                    break;
                case 2:
                    addItem(scanner);
                    break;
                case 3:
                    editItem(scanner);
                    break;
                case 4:
                    deleteItem(scanner);
                    break;
                case 5:
                    System.out.println("Exiting Item Entry Menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void viewItems() {
        System.out.println("\n============================================================");
        System.out.println("\tList of Items");
        System.out.println("\n============================================================");
        System.out.printf("%-5s %-15s %-10s %-10s %-10s%n", "ID", "Name", "Price", "Quantity", "Supplier ID");

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] itemFields = line.split(",");
                if (itemFields.length == ITEM_FIELDS) {
                    System.out.printf("%-5s %-15s %-10s %-10s %-10s%n", itemFields[0], itemFields[1], itemFields[2], itemFields[3], itemFields[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addItem(Scanner scanner) {
        while (true) {
            viewItems();
            System.out.println("\nNew Item Entry:");
            String itemID = generateNewID();
            System.out.println("Item ID: " + itemID);

            System.out.print("Item Name (Input 0 to go back): ");
            String itemName = scanner.nextLine();

            if (itemName.equals("0")) {
                break;
            }

            if (isItemNameExists(itemName)) {
                System.out.println("Item with the same name already exists. Please enter a different name.");
                continue;
            }

            System.out.print("Item Price: ");
            double itemPrice = scanner.nextDouble();

            System.out.print("Item Quantity: ");
            int itemStock = scanner.nextInt();

            System.out.print("Supplier ID: ");
            String supplierID = scanner.next();
            scanner.nextLine(); // Consume newline

            String newItem = String.format("%s,%s,%.2f,%d,%s", itemID, itemName, itemPrice, itemStock, supplierID);
            writeItemToFile(newItem);
            System.out.println("Item added successfully.");
        }
    }

    private static void editItem(Scanner scanner) {
        int tracker = 0;
        viewItems();

        System.out.print("Select Item ID to edit (Input 0 to go back): ");
        String editItemID = scanner.nextLine();

        if (editItemID.equals("0")) {
            return;
        }

        List<String> bufferLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] itemFields = line.split(",");
                if (itemFields.length == ITEM_FIELDS) { // Use commas as separators
                    if (editItemID.equals(itemFields[0])) {
                        tracker++;
                        System.out.println("Original item info:");
                        System.out.println(line);
                        System.out.println("Input edited info:");
                        System.out.print("Item Name: ");
                        String itemName = scanner.nextLine();
                        System.out.print("Item Price: ");
                        double itemPrice = scanner.nextDouble();
                        System.out.print("Item Quantity: ");
                        int itemStock = scanner.nextInt();
                        System.out.print("Supplier ID: ");
                        String supplierID = scanner.next();
                        scanner.nextLine(); // Consume newline

                        String editedItem = String.format("%s,%s,%.2f,%d,%s", itemFields[0], itemName, itemPrice, itemStock, supplierID);
                        bufferLines.add(editedItem);
                    } else {
                        bufferLines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (tracker != 0) {
            writeItemsToBuffer(bufferLines);
            System.out.println("Item edited successfully.");
            renameBufferFile();
        } else {
            System.out.println("Item ID not found.");
        }
    }


    private static void deleteItem(Scanner scanner) {
        while (true) {
            int tracker = 0;
            viewItems();
            System.out.print("Delete Item ID (Input 0 to go back): ");
            String deleteItemID = scanner.nextLine();

            if (deleteItemID.equals("0")) {
                break;
            }

            List<String> bufferLines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] itemFields = line.split(",");
                    if (itemFields.length == ITEM_FIELDS) {
                        if (deleteItemID.equals(itemFields[0])) {
                            tracker++;
                            continue;  // Skip the item to be deleted
                        }
                        bufferLines.add(line);
                    }
                }
            } catch (IOException e) {
            e.printStackTrace();
            }

            if (tracker != 0) {
                writeItemsToBuffer(bufferLines);
                System.out.println("Item deleted successfully.");
                renameBufferFile();
            } else {
            System.out.println("Item ID not found.");
            }
        }
    }


    private static void writeItemToFile(String newItem) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(newItem);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing item file: " + e.getMessage());
        }
    }

    private static void writeItemsToBuffer(List<String> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH_BUFFER))) {
            for (String item : items) {
                writer.write(item);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void renameBufferFile() {
        File originalFile = new File(FILE_PATH);
        File bufferFile = new File(FILE_PATH_BUFFER);

        if (originalFile.delete() && bufferFile.renameTo(originalFile)) {
            System.out.println("File updated successfully.");
        } else {
            System.out.println("Error updating the file.");
        }
    }

    private static String generateNewID() {
        int maxID = 0;
        Set<String> existingIDs = new HashSet<>(); // Keep track of existing item IDs

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] itemFields = line.split(",");
                if (itemFields.length == ITEM_FIELDS) {
                    String itemID = itemFields[0];
                    existingIDs.add(itemID); // Add existing item IDs to the set

                    int itemIntID = Integer.parseInt(itemID.substring(1)); // Parse item ID as integer
                    if (itemIntID > maxID) {
                        maxID = itemIntID;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    // Generate the next available item ID
    String newID;
    do {
        maxID++; // Increment maxID
        newID = String.format("I%04d", maxID); // Format as item ID
    } while (existingIDs.contains(newID)); // Check if it already exists

    return newID;
}
  


    private static boolean isItemNameExists(String itemName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] itemFields = line.split("\\s+");
                if (itemFields.length == ITEM_FIELDS && itemName.equals(itemFields[1])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}

