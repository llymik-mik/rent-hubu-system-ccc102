package MAIN;

public class ValidationLogic {

       // (YYYY-NNNN format)
        public static boolean isValidID(String id) {
            return id.matches("\\d{4}-\\d{4}");
        }

        // Check if item can be requested
        public static boolean canRequestItem(Item item) {
            return "Available".equalsIgnoreCase(item.getStatus());
        }
    }

