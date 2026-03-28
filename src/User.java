public class User {

    private String fullName;
    private int idNumber;
    String password;
    int contactNum;

    public User (String fullName, int idNumber, String password, int contactNum){
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.password = password;
        this.contactNum = contactNum;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public String getFullName() {
        return fullName;
    }


}
