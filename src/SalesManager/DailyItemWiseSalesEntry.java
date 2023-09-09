package SalesManager;

import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DailyItemWiseSalesEntry {
    String itemTextFile = "C:\\Users\\user1\\Desktop\\APU\\Year 2 Sem 1\\Object Oriented Development with Java\\Assignment\\Assignment\\Purchase-Order-Management-System\\src\\SalesManager\\Item.txt";
    String salesEntryTextFile = "C:\\Users\\user1\\Desktop\\APU\\Year 2 Sem 1\\Object Oriented Development with Java\\Assignment\\Assignment\\Purchase-Order-Management-System\\src\\SalesManager\\SalesEntry.txt";
    List <String> salesEntryList = new ArrayList<>();
    
    public DailyItemWiseSalesEntry(){menu();}
    
    public void menu() {
        Scanner sc1 = new Scanner(System.in);

        System.out.println("Welcome To Daily Item-Wise Sales Entry Management. \nPlease Select An Option. \n1. Add \n2. Edit \n3. Delete \n4. Exit\n");
        try {
            int option = sc1.nextInt();
            switch (option) {
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
                    break;
                default:
                    System.out.println("Wrong Input. Please Enter Again.");
            }
        } catch (java.util.InputMismatchException e) {
            sc1.nextLine(); 
            System.out.println("\nInvalid input. Please enter a valid option (1-4).\n");
        }
    }

        public void addDIWSE(){

            Scanner sc1 = new Scanner(System.in);
            boolean found = false;
            List <String> itemTextList = new ArrayList<>();
            System.out.println("\nPrinting Items...");
            try (BufferedReader buffer = new BufferedReader(new FileReader(itemTextFile))) {
                String line;
                while ((line = buffer.readLine()) != null) {
                    System.out.println(line);
                    itemTextList.add(line); 
                }
            }catch (IOException e) {
                System.err.println("Error reading Item.txt: " + e.getMessage());
            }     
            do {
                System.out.println("Please Enter The Item Code You Want To Add: ");
                String input = sc1.next();
                for (String item : itemTextList) {
                    String[] columns = item.split(",");
                    if (columns.length > 0 && columns[0].equals(input)) {
                        found = true;
                        System.out.println("Item Code: " + columns[0]);
                        System.out.println("Item Name: " + columns[1]);
                        System.out.println("Item Price: " + columns[2]);
                        System.out.println("Item Quantity: " + columns[3]);
                        System.out.println("Supplier ID: " + columns[4]);
                        System.out.println("Please Enter The Date (in yyyy-MM-dd format): ");
                        String date = sc1.next();
                        Date Date = parseDate(date);
                        int quantitySold; 
                        double totalSales = 0.0; 
                        double itemPrice = Double.parseDouble(columns[2]);
                        do {
                            System.out.println("Please Enter The Quantity That Has Sold: ");
                            if (sc1.hasNextInt()) {
                                quantitySold = sc1.nextInt();
                                sc1.nextLine(); 
                                int availableQuantity = Integer.parseInt(columns[3]);
                                if (quantitySold <= 0) {
                                    System.out.println("Please Enter A Valid Positive Integer Number.");
                                } else if (quantitySold > availableQuantity) {
                                    System.out.println("Input Error: Quantity sold cannot exceed the available quantity.");
                                    quantitySold = -1;
                                } else {
                                totalSales = itemPrice * quantitySold;
                                totalSales += totalSales;
                                break;
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a valid positive integer.");
                                sc1.nextLine(); 
                                quantitySold = -1;
                            }
                        } while (quantitySold <= 0);
                        System.out.println("Quantity Sold: " + quantitySold);
                        System.out.println("Total Sales: " + totalSales);
                        String salesEntry = columns[0] + "," + columns[1] + "," + columns[2] + "," + quantitySold + "," + Date + "," + columns[4] + "," + totalSales;
                        System.out.println(salesEntry);
                        salesEntryList.add(salesEntry);
                        saveDIWSE();
                        break;
                    }
                }
                if(!found) {
                    System.out.println("Error: Item Code not found in the file.\n");
                }
            } while (!found);    
        }
        
    public void editDIWSE(){
    
        Scanner sc1 = new Scanner(System.in);
        boolean found = false;
        System.out.println("Printing Item List...");
        boolean fileIsEmpty = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(salesEntryTextFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); 
                fileIsEmpty = false; 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  
        do {
            System.out.println("Enter the Item Code you want to edit: ");
            String editItemCode = sc1.next();
            for (int i = 0; i < salesEntryList.size(); i++) {
                String salesEntry = salesEntryList.get(i);
                String[] columns = salesEntry.split(",");
                if (columns.length > 0 && columns[0].equals(editItemCode)) {
                    found = true;
                System.out.println("Item Found. \nCurrent Details:");
                    System.out.println(salesEntryList);
                    boolean validInput = false;
                    while(!validInput){
                        System.out.println("Which One Do You Want To Edit? \n1. Date\n2. Quantity Sold\n(Enter '1' or '2')\n");
                        int input = sc1.nextInt();
                        switch(input){
                        case 1:
                            System.out.println("Enter New Date (in yyyy-MM-dd format): ");
                            String newDateStr = sc1.next();
                            Date newDate = parseDate(newDateStr);
                            if (newDate != null) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                columns[4] = dateFormat.format(newDate);
                            } else {
                                System.out.println("Invalid date format. Date not updated.");
                            }
                            break;
                        case 2:
                            int newQuantitySold;
                            int availableQuantity = getAvailableQuantity(editItemCode);
                            do {
                                System.out.println("Please Enter The Quantity That Has Sold: ");
                                if (sc1.hasNextInt()) {
                                    newQuantitySold = sc1.nextInt();
                                    sc1.nextLine();                                    
                                    if (newQuantitySold <= 0) {
                                        System.out.println("Please Enter A Valid Positive Integer Number.");
                                    } else if (newQuantitySold > availableQuantity) {
                                        System.out.println("Input Error: Quantity sold cannot exceed the available quantity.");
                                        newQuantitySold = -1;
                                    } else {
                                        break;
                                    }
                                } else {
                                    System.out.println("Invalid input. Please enter a valid positive integer.");
                                    sc1.nextLine();
                                    newQuantitySold = -1; 
                                }
                            } while (newQuantitySold <= 0);
                            System.out.println("Updated Quantity Sold: " + newQuantitySold);
                            break;
                        default:
                            System.out.println("Error Input. Please Enter Again.");
                        }
                        break;
                    }
                    String updatedItem = String.join(",", columns);
                    salesEntryList.set(i, updatedItem);
                    saveDIWSE();
                    System.out.println("Back to the main menu...");
                    menu();              
                }
            }
            if (!found) {
                System.out.println("Error: Item Code not found in the file. Please Enter a Valid Item Code.");
            }
        }while (!found);
    }  

    
    public void deleteDIWSE(){
        boolean fileIsEmpty = true; 
        System.out.println("Printing Items...");
        try (BufferedReader reader = new BufferedReader(new FileReader(salesEntryTextFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                fileIsEmpty = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                salesEntryList.remove(i);
                saveDIWSE();
                return; 
            }
        }
        if (!found) {
            System.out.println("Error: Item Code not found in the file.");
        }
    }

    public void saveDIWSE() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(salesEntryTextFile))) {
            for (String entry : salesEntryList) {
                writer.write(entry);
                writer.newLine(); 
            }
            System.out.println("Data Is Successfully Saved To 'SalesEntry.txt'");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Failed to save data to 'SalesEntry.txt'");
        }
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
    
    private int getAvailableQuantity(String itemCode) {
        try (BufferedReader reader = new BufferedReader(new FileReader(itemTextFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > 0 && columns[0].equalsIgnoreCase(itemCode)) {
                    return Integer.parseInt(columns[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
