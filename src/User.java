package main;

public class User {
    private String fullName;
    private String idNumber;
    private String password;

    public User(String fullName, String idNumber, String password) {
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.password = password;
    }

    public String getFullName() { return fullName; }
    public String getIdNumber() { return idNumber; }
    public String getPassword() { return password; }
}
