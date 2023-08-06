package SalesManager;

import java.util.ArrayList;
import java.util.List;

public class SupplierEntry {
    private List<Supplier> suppliers;

    public SupplierEntry() {
        suppliers = new ArrayList<>();
    }

    public void addSupplier(Supplier newSupplier) {
        suppliers.add(newSupplier);
    }

    public void deleteSupplier(String supplierCode) {
        Supplier supplierToRemove = null;
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierCode().equals(supplierCode)) {
                supplierToRemove = supplier;
                break;
            }
        }
        if (supplierToRemove != null) {
            suppliers.remove(supplierToRemove);
        }
    }

    public void editSupplier(String supplierCode, Supplier newSupplierDetails) {
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierCode().equals(supplierCode)) {
                suppliers.set(i, newSupplierDetails);
                break;
            }
        }
    }

    // Other methods related to supplier management
}
