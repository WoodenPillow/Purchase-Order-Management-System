package PurchaseManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseManager {
    private List<generatePurchaseOrder> purchaseOrders;

    public PurchaseManager() {
        purchaseOrders = new ArrayList<>();
    }

    public void generatePurchaseOrder(String poId, String pmName) {
        generatePurchaseOrder purchaseOrder = new generatePurchaseOrder(poId, pmName);
        purchaseOrders.add(purchaseOrder);
        savePurchaseOrdersToFile();
        System.out.println("Purchase Order generated successfully!");
    }

    private void savePurchaseOrdersToFile() {
        try {
            FileWriter writer = new FileWriter("purchase_orders.txt");
            for (generatePurchaseOrder purchaseOrder : purchaseOrders) {
                String line = purchaseOrder.getPoId() + "," + purchaseOrder.getPmName() + "\n";
                writer.write(line);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while saving purchase orders: " + e.getMessage());
        }
    }
}
