package main;

public class Request {
    private Item item;
    private User requester;
    private String borrowDate;
    private String returnDate;
    private String contactNumber;
    private String message;
    private String status; // Pending, Approved, Rejected

    public Request(Item item, User requester, String borrowDate,
                   String returnDate, String contactNumber, String message) {
        this.item = item;
        this.requester = requester;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.contactNumber = contactNumber;
        this.message = message;
        this.status = "Pending";
    }

    public Item getItem() { return item; }
    public User getRequester() { return requester; }
    public String getBorrowDate() { return borrowDate; }
    public String getReturnDate() { return returnDate; }
    public String getContactNumber() { return contactNumber; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}