/*public abstract class User { //Make it abstract, so that no one can create a User object,
                                //so that everyone is either a Applicant, Manager or Officer
        
    private String name, UserID, password, maritalStatus;
    private int age;

    public User(String name, String UserID, int age, String maritalStatus, String password) {
        this.name = name;
        this.UserID = UserID;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
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

}*/


/**
 * Abstract base class representing a user in the BTO Management System. Since it's abstract no one can create a user object.
 * This class provides common attributes and methods for all types of users,
 * such as Applicants, Officers, and Managers.
 * 
 */
public abstract class User {

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The unique ID of the user.
     */
    private String UserID;

    /**
     * The password for user authentication.
     */
    private String password;

    /**
     * The marital status of the user.
     */
    private String maritalStatus;

    /**
     * The age of the user.
     */
    private int age;

    /**
     * Constructs a User with the specified attributes.
     *
     * @param name the name of the user
     * @param UserID the unique user ID
     * @param age the user's age
     * @param maritalStatus the marital status of the user
     * @param password the user's password
     */
    public User(String name, String UserID, int age, String maritalStatus, String password) {
        this.name = name;
        this.UserID = UserID;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }

    /**
     *returns the user's name
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     *returns the user's user ID
     * @return the user's unique user ID
     */
    public String getUserID() {
        return UserID;
    }

    /**
     *returns the password set by the user
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     *returns user's age
     * @return the user's age
     */
    public int getAge() {
        return age;
    }

    /**
     * returns user's marital status
     * @return the user's marital status
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Changes the user's password.
     *
     * @param newPassword - if the user wants to set a new password
     */
    public void changePassword(String newPassword){
        this.password = newPassword;
    }
}

