package MAIN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

    public class IITShareService {
        // This acts as a temporary database in memory
        private List<Item> globalInventory = new ArrayList<>();
        private List<Transaction> allTransactions = new ArrayList<>();

        // Logic for Member 2 & 3 to add items
        public void postNewItem(Item newItem) {
            globalInventory.add(newItem);
            System.out.println("System: MAIN.Item " + newItem.getName() + " listed successfully.");
        }

        // Logic for the Dashboard Search
        public List<Item> searchItems(String query) {
            return globalInventory.stream()
                    .filter(i -> i.getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Logic for filtering by College
        public List<Item> filterByCollege(String college) {
            return globalInventory.stream()
                    .filter(i -> i.getCollege().equalsIgnoreCase(college))
                    .collect(Collectors.toList());
        }

        public List<Item> getAllAvailableItems() {
            return globalInventory.stream()
                    .filter(i -> i.getStatus().equals("Available"))
                    .collect(Collectors.toList());
        }
    }

