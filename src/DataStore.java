package main;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton DataStore — acts as the in-memory "database" for IIT-SHARE.
 * All GUI panels read/write through this class.
 */
public class DataStore {

    private static DataStore instance;

    private List<User> users = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<Request> requests = new ArrayList<>();
    private User currentUser = null;

    private DataStore() {
        seedData();
    }

    public static DataStore getInstance() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

    // ── Seed demo data ──────────────────────────────────────────────────────────
    private void seedData() {
        User a = new User("Student A", "2021-001", "pass1");
        User b = new User("Student B", "2021-002", "pass2");
        User c = new User("Student C", "2021-003", "pass3");
        users.add(a);
        users.add(b);
        users.add(c);

        Item labGown = new Item("Lab Gown", "White lab gown, size M", "Free", "#CSM #BIO #lab",
                a.getFullName(), a.getIdNumber());
        Item calc = new Item("Calculator", "Casio scientific calculator", "Free", "#COE #Calc #Math",
                b.getFullName(), b.getIdNumber());
        Item tripod = new Item("Tripod", "Camera tripod, adjustable height", "Free", "#CED #Film #Event",
                c.getFullName(), c.getIdNumber());
        items.add(labGown);
        items.add(calc);
        items.add(tripod);
    }

    // ── Auth ────────────────────────────────────────────────────────────────────
    public boolean login(String idNumber, String password) {
        for (User u : users) {
            if (u.getIdNumber().equals(idNumber) && u.getPassword().equals(password)) {
                currentUser = u;
                return true;
            }
        }
        return false;
    }

    public boolean register(String fullName, String idNumber, String password) {
        for (User u : users) {
            if (u.getIdNumber().equals(idNumber)) return false; // duplicate ID
        }
        users.add(new User(fullName, idNumber, password));
        return true;
    }

    public void logout() { currentUser = null; }
    public User getCurrentUser() { return currentUser; }

    // ── Items ───────────────────────────────────────────────────────────────────
    public List<Item> getAllItems() { return new ArrayList<>(items); }

    public List<Item> searchItems(String keyword, String college, String course) {
        List<Item> result = new ArrayList<>();
        String kw = keyword.toLowerCase().trim();
        String col = (college == null || college.equals("All Colleges")) ? "" : college.toLowerCase();
        String crs = (course == null || course.equals("All Courses")) ? "" : course.toLowerCase();

        for (Item item : items) {
            if (item.getOwnerIdNumber().equals(currentUser.getIdNumber())) continue; // skip own items
            boolean matchKw = kw.isEmpty()
                    || item.getName().toLowerCase().contains(kw)
                    || item.getHashtags().toLowerCase().contains(kw)
                    || item.getDetails().toLowerCase().contains(kw);
            boolean matchCol = col.isEmpty() || item.getHashtags().toLowerCase().contains(col.replace(" ", "").toLowerCase());
            boolean matchCrs = crs.isEmpty() || item.getHashtags().toLowerCase().contains(crs.replace(" ", "").toLowerCase());
            if (matchKw && matchCol && matchCrs && item.getStatus().equals("Available")) {
                result.add(item);
            }
        }
        return result;
    }

    public List<Item> getAvailableItems() {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (!item.getOwnerIdNumber().equals(currentUser.getIdNumber()) && item.getStatus().equals("Available")) {
                result.add(item);
            }
        }
        return result;
    }

    public void postItem(Item item) { items.add(item); }

    public List<Item> getMyItems() {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.getOwnerIdNumber().equals(currentUser.getIdNumber())) result.add(item);
        }
        return result;
    }

    // ── Requests ────────────────────────────────────────────────────────────────
    public void submitRequest(Request req) {
        requests.add(req);
        req.getItem().setStatus("Requested");
    }

    public List<Request> getMyRequests() {
        List<Request> result = new ArrayList<>();
        for (Request r : requests) {
            if (r.getRequester().getIdNumber().equals(currentUser.getIdNumber())) result.add(r);
        }
        return result;
    }

    public List<Request> getIncomingRequests() {
        List<Request> result = new ArrayList<>();
        for (Request r : requests) {
            if (r.getItem().getOwnerIdNumber().equals(currentUser.getIdNumber())) result.add(r);
        }
        return result;
    }
}