public abstract class User { //Make it abstract, so that no one can create a User object,
                                //so that everyone is either a Applicant, Manager or Officer
        
    private String name, UserID, password, maritalStatus;
    private int age;

    public User(String name, String UserID, int age, String maritalStatus) {
        this.name = name;
        this.UserID = UserID;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = "password";
    }

    public String getName() {
        return name;
    }

    public String getUserID() {
        return UserID;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void changePassword(String newPassword){
        this.password = newPassword;
    }

}
