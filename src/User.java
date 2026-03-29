public class User {
        private String fullName;
        private int idNumber;
        private String password;
        private int contactNum;

        // The Constructor now matches the class name "User"
        public User(String fullName, int idNumber, String password, int contactNum) {
            this.fullName = fullName;
            this.idNumber = idNumber;
            this.password = password;
            this.contactNum = contactNum;
        }

        // Getters
        public int getIdNumber()    { return idNumber; }
        public String getFullName() { return fullName; }
        public String getPassword() { return password; }
        public int getContactNum()  { return contactNum; }
    }

