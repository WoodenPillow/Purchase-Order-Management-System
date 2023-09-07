package PurchaseManager;

import java.util.List;

/**
 *
 * @author user1
 */
public class purchaseOrderList {
    private List<generatePurchaseOrder> purchaseOrders;

    public purchaseOrderList(List<generatePurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public void viewPurchaseOrders() {
        System.out.println("List of Purchase Orders:");
        for (generatePurchaseOrder purchaseOrder : purchaseOrders) {
            System.out.println("PO ID: " + purchaseOrder.getPoId() + ", PM Name: " + purchaseOrder.getPmName());
        }
    }
}
