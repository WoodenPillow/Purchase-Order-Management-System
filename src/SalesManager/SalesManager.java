package SalesManager;

public class SalesManager {
    private itemEntry itemEntry;
    private supplierEntry supplierEntry;

    public SalesManager() {
        itemEntry = new itemEntry();
        supplierEntry = new supplierEntry();
    }

    // Methods to interact with item and supplier management
    public void addItem(Item newItem) {
        itemEntry.addItem(newItem);
    }

    public void deleteItem(String itemCode) {
        itemEntry.deleteItem(itemCode);
    }

    public void editItem(String itemCode, Item newItemDetails) {
        itemEntry.editItem(itemCode, newItemDetails);
    }

    public void addSupplier(Supplier newSupplier) {
        supplierEntry.addSupplier(newSupplier);
    }

    public void deleteSupplier(String supplierCode) {
        supplierEntry.deleteSupplier(supplierCode);
    }

    public void editSupplier(String supplierCode, Supplier newSupplierDetails) {
        supplierEntry.editSupplier(supplierCode, newSupplierDetails);
    }

    // Other methods related to sales management
}
