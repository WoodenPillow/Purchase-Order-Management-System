/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PurchaseManager;

/**
 *
 * @author user1
 */
public class PurchaseOrder {
    private String poId;
    private String pmName;

    public PurchaseOrder(String poId, String pmName) {
        this.poId = poId;
        this.pmName = pmName;
    }

    public String getPoId() {
        return poId;
    }

    public String getPmName() {
        return pmName;
    }
}
