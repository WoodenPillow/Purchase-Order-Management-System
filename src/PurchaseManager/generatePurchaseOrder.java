package PurchaseManager;

public class generatePurchaseOrder {
    private String poId;
    private String pmName;

    public generatePurchaseOrder(String poId, String pmName) {
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
