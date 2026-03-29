import java.util.HashMap;
import java.util.Scanner;

public class Login {
    // Our database now maps IDs to User objects
    private static final HashMap<Integer, User> users = new HashMap<>();

    public static void signup(int idNumber, String fullName, String password, int contactNum) {
        if (users.containsKey(idNumber)) {
            System.out.println("Signup failed: ID " + idNumber + " is already registered.");
            return;
        }
        // We create a new User object and store it
        users.put(idNumber, new User(fullName, idNumber, password, contactNum));
        System.out.println("Signup successful! Welcome, " + fullName + ".");
    }

    public static User login(int idNumber, String password) {
        User user = users.get(idNumber);

        if (user == null) {
            System.out.println("Login failed: ID not found.");
            return null;
        }

        // Comparing strings using .equals()
        if (!user.getPassword().equals(password)) {
            System.out.println("Login failed: Incorrect password.");
            return null;
        }

        System.out.println("Login successful! Welcome back, " + user.getFullName() + ".");
        return user;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n[1] Sign Up  [2] Log In  [3] Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Clear buffer

            if (choice == 1) {
                System.out.print("Full name: ");   String name = sc.nextLine();
                System.out.print("ID number: ");   int id  = sc.nextInt();
                sc.nextLine(); // Clear buffer
                System.out.print("Password: ");    String pass = sc.nextLine();
                System.out.print("Contact #: ");   int contact = sc.nextInt();
                signup(id, name, pass, contact);

            } else if (choice == 2) {
                System.out.print("ID number: ");   int id  = sc.nextInt();
                sc.nextLine(); // Clear buffer
                System.out.print("Password: ");    String pass = sc.nextLine();

                User logged = login(id, pass);
                if (logged != null) {
                    System.out.println("--- User Profile ---");
                    System.out.println("Name    : " + logged.getFullName());
                    System.out.println("Contact : " + logged.getContactNum());
                }
            } else {
                break;
            }
        }
        sc.close();
    }
}