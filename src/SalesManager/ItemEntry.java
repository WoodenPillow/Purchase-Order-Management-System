package SalesManager;

import java.util.ArrayList;
import java.util.List;

public class ItemEntry {
    private List<Item> items;

    public ItemEntry() {
        items = new ArrayList<>();
    }

    public void addItem(Item newItem) {
        items.add(newItem);
    }

    public void deleteItem(String itemCode) {
        Item itemToRemove = null;
        for (Item item : items) {
            if (item.getItemCode().equals(itemCode)) {
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            items.remove(itemToRemove);
        }
    }

    public void editItem(String itemCode, Item newItemDetails) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemCode().equals(itemCode)) {
                items.set(i, newItemDetails);
                break;
            }
        }
    }

    // Other methods related to item management
}
