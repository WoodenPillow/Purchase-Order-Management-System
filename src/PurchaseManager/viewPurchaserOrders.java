/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PurchaseManager;

import java.util.List;

/**
 *
 * @author user1
 */
public class viewPurchaserOrders {
    private List<generatePurchaseOrder> purchaseOrders;

    public viewPurchaserOrders(List<generatePurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public void viewPurchaseOrders() {
        System.out.println("List of Purchase Orders:");
        for (generatePurchaseOrder purchaseOrder : purchaseOrders) {
            System.out.println("PO ID: " + purchaseOrder.getPoId() + ", PM Name: " + purchaseOrder.getPmName());
        }
    }
}
