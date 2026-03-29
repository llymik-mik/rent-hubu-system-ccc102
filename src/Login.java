import java.util.HashMap;
import java.util.Scanner;

public class Login {

    private String fullName;
    private int idNumber;
    private String password;
    private int contactNum;

    public User(String fullName, int idNumber, String password, int contactNum) {
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.password = password;
        this.contactNum = contactNum;
    }

    public int getIdNumber()   {
        return idNumber; }
    public String getFullName() {
        return fullName; }
    public String getPassword() {
        return password; }
    public int getContactNum()  {
        return contactNum; }


    // ── Auth System ──────────────────────────────────────────────────────────

    // idNumber → User
    private static final HashMap<Integer, User> users = new HashMap<>();

    public static void signup(int idNumber, String fullName, String password, int contactNum) {
        if (users.containsKey(idNumber)) {
            System.out.println("Signup failed: ID " + idNumber + " is already registered.");
            return;
        }
        users.put(idNumber, new User(fullName, idNumber, password, contactNum));
        System.out.println("Signup successful! Welcome, " + fullName + ".");
    }

    public static User login(int idNumber, String password) {
        User user = users.get(idNumber);

        if (user == null) {
            System.out.println("Login failed: ID not found.");
            return null;
        }
        if (!user.getClass().equals(password)) {
            System.out.println("Login failed: Incorrect password.");
            return null;
        }

        System.out.println("Login successful! Welcome back, " + user.getFullName() + ".");
        return user;
    }


    // ── Demo ─────────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n[1] Sign Up  [2] Log In  [3] Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Full name: ");   sc.nextLine();
                String name = sc.nextLine();
                System.out.print("ID number: ");   int id  = sc.nextInt();
                System.out.print("Password: ");    sc.nextLine();
                String pass = sc.nextLine();
                System.out.print("Contact #: ");   int contact = sc.nextInt();
                signup(id, name, pass, contact);

            } else if (choice == 2) {
                System.out.print("ID number: ");   int id  = sc.nextInt();
                System.out.print("Password: ");    sc.nextLine();
                String pass = sc.nextLine();
                User logged = login(id, pass);
                if (logged != null) {
                    System.out.println("  Name    : " + logged.getFullName());
                    System.out.println("  Contact : " + logged.getContactNum());
                }

            } else {
                System.out.println("Goodbye!");
                break;
            }
        }

        sc.close();
    }
}