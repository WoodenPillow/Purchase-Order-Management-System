package SalesManager;

public class Main {
    public static void main(String[] args) {
        SalesManager salesManager = new SalesManager();

        // Create and manage items and suppliers using the SalesManager
        Item newItem = new Item("001", "Product A", "SUP001");
        salesManager.addItem(newItem);

        Supplier newSupplier = new Supplier("SUP001", "Supplier X");
        salesManager.addSupplier(newSupplier);

        // Perform other operations using the SalesManager class
    }
}
