package SalesManager;

import SalesManager.createPurchaseRequisition;

public class displayRequisition {
    public void display(createPurchaseRequisition requisition){
    System.out.println("Generating Purchase Requisition...");
    try {
            // Sleep for 3 seconds (3000 milliseconds)
            Thread.sleep(3000);
        } catch (InterruptedException e){
            // Handle the exception if necessary
            e.printStackTrace();
        }
    System.out.println("Complete!");
    System.out.println("+----------------------------------------+");
    System.out.println("|           Purchase Requisition         |");
    System.out.println("+----------------------------------------+");
    System.out.println(  "|Name          | " + requisition.getName());
    System.out.println(  "|Company       | " + requisition.getCompany());
    System.out.println(  "|Item Name     | " + requisition.getitemName());
    System.out.println(  "|Item Code     | " + requisition.getitemCode());
    System.out.println(  "|Quantity      | " + requisition.getQuantity());
    System.out.println(  "|Unit Price    | RM" + requisition.getPrice());
    System.out.println(  "|Total Price   | RM" + requisition.calculateTotalPrice());
    System.out.println(  "|Required Date | " + requisition.getRequiredDate());
    System.out.println(  "|Purpose       | " + requisition.getPurpose());
    System.out.println("+----------------------------------------+");
    }

}
