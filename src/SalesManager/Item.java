package SalesManager;

public class Item {
    private String itemCode;
    private String itemName;
    private String supplierId;

    public Item(String itemCode, String itemName, String supplierId) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.supplierId = supplierId;
    }

    // Getters and setters for item attributes
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    // Other methods if needed
}
