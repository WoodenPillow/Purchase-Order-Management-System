package SalesManager;
import java.util.Scanner;

public class CreatePurchaseRequisition {
    private String Name;
    private String Company;
    private String itemName;
    private String itemCode;
    private int Quantity;
    private double Price;
    private double TotalPrice;
    private String requiredDate;
    private String Purpose;
    
    
    public void acceptUserInput(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter following details to create a Purchase Requisition.");
        
        System.out.print("Name: ");
        Name = scanner.nextLine();
        
        System.out.print("Company: ");
        Company = scanner.nextLine();
        
        System.out.print("Item Name: ");
        itemName = scanner.nextLine();
        
        System.out.print("Item Code: ");
        itemCode = scanner.nextLine();

        System.out.print("Quantity: ");
        Quantity = scanner.nextInt();

        // Consume the newline character left in the buffer after reading the integer
        scanner.nextLine();
        
        System.out.print("Unit Price: ");
        Price = scanner.nextDouble();
        
        // Consume the newline character left in the buffer after reading the double
        scanner.nextLine();
        
        System.out.print("Enter Required Date (YYYY-MM-DD): ");
        requiredDate = scanner.nextLine();
         
        System.out.print("Enter Purpose: ");
        Purpose = scanner.nextLine();
    }
    
    public String getName(){
        return Name;
    }
    
    public String getCompany(){
        return Company;
    }
    
    public String getitemCode(){
        return itemCode;
    }
    
    public String getitemName(){
        return itemName;
    }

    public int getQuantity(){
        return Quantity;
    }
    
    public double getPrice(){
        return Price;
    }
    
    public String getRequiredDate(){
        return requiredDate;
    }
    
    public String getPurpose(){
        return Purpose;
    }
    
    public double calculateTotalPrice(){
        TotalPrice = Quantity * Price;
        return TotalPrice;
    }
}
