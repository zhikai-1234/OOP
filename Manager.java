
/* public class Manager extends User {

    private TemplateProject projectInCharge;

    public Manager(String name, String userID, int age, String maritalStatus, String password) {
        super(name, userID, age, maritalStatus, password);
        this.projectInCharge = null;
    }

    public void setProjectInCharge(TemplateProject p) {
        this.projectInCharge = p;
    }

    public TemplateProject getProjectInCharge() {
        return this.projectInCharge;
    }
} */


/**
 * Represents a manager in the BTO Management System.
 * A manager is responsible for overseeing a specific housing project.
 * This class allows for assigning and retrieving the project under their charge.
 * 
 * <p>Is derived from the {@link User} class, therefore it Inherits common user attributes and methods.</p>
 * 
 * @see TemplateProject
 * @see User
 */
public class Manager extends User {

    /**
     * The project assigned to the manager.
     */
    private TemplateProject projectInCharge;

    /**
     * Constructs a new Manager with personal and login details.
     *
     * @param name the name of the manager
     * @param userID the unique ID of the manager
     * @param age the age of the manager
     * @param maritalStatus the marital status of the manager
     * @param password the password for login
     */
    public Manager(String name, String userID, int age, String maritalStatus, String password) {
        super(name, userID, age, maritalStatus, password);
        this.projectInCharge = null;
    }

    /**
     * Assigns a project for the manager to oversee.
     *
     * @param p the project to assign
     */
    public void setProjectInCharge(TemplateProject p) {
        this.projectInCharge = p;
    }

    /**
     * Returns the project currently under the manager's charge.
     *
     * @return the assigned project
     */
    public TemplateProject getProjectInCharge() {
        return this.projectInCharge;
    }
}

    
