public class Item {

    private String name;
    private String details;
    private String college; // e.g., "CCS"
    private String status;  // "Available" or "Rented"
    private User owner;     // Relationship: Item belongs to a User

    public Item(String name, String details, String college, User owner) {
        this.name = name;
        this.details = details;
        this.college = college;
        this.owner = owner;
        this.status = "Available";
    }

    // Getters & Setters
    public String getName() {
        return name;
    }
    public String getStatus() {

        return status;
    }

    public String getCollege() {
        return college;
    }

    public void setStatus(String status) {

        this.status = status;
    }
    public User getOwner() {
        return owner;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}

