/*public class Officer extends Applicant {
    private TemplateProject appliedProjectAsOfficer;
    private TemplateProject assignedProjectAsOfficer;

    public Officer(String name, String userID, int age, String maritalStatus, String password) {
        super(name, userID, age, maritalStatus, password);
        this.appliedProjectAsOfficer = null;
        this.assignedProjectAsOfficer = null;
    }

    //To prevent applying as applicant for same project
    @Override
    public void setProjApplied(TemplateProject p) {
        if (p.equals(this.getAssignedProjectAsOfficer())) {
            throw new IllegalStateException("Cannot apply for the same project you're handling as officer.");
        }
        super.setProjApplied(p);
    }

    // GETTERS //
    public TemplateProject getAppliedProjectAsOfficer() {
        return this.appliedProjectAsOfficer;
    }
    public TemplateProject getAssignedProjectAsOfficer() {
        return this.assignedProjectAsOfficer;
    }

    // SETTERS //
    public void setAppliedProjectAsOfficer(TemplateProject p) {
        this.appliedProjectAsOfficer = p;
    }
    public void setAssignedProjectAsOfficer(TemplateProject p) {
        this.assignedProjectAsOfficer = p;
    }
}*/

/**
 * Represents an officer in the BTO Management System.
 * An officer is also an applicant but has additional responsibilities, such as
 * being assigned to manage a specific project.
 * <p>
 * Officers can apply for projects as applicants, but cannot apply for the same project
 * they are assigned to manage.
 * </p>
 * 
 */
public class Officer extends Applicant {

    /**
     * The project the officer has applied to manage (as an officer).
     */
    private TemplateProject appliedProjectAsOfficer;

    /**
     * The project currently assigned to the officer.
     */
    private TemplateProject assignedProjectAsOfficer;

    /**
     * Constructs a new Officer with personal and login details.
     *
     * @param name the name of the officer
     * @param userID the unique ID of the officer
     * @param age the age of the officer
     * @param maritalStatus the marital status of the officer
     * @param password the password for login
     */
    public Officer(String name, String userID, int age, String maritalStatus, String password) {
        super(name, userID, age, maritalStatus, password);
        this.appliedProjectAsOfficer = null;
        this.assignedProjectAsOfficer = null;
    }

    /**
     * Overrides the applicant's setProjApplied method to prevent applying for
     * a project that the officer is already assigned to.
     *
     * @param p the project to apply for
     * @throws IllegalStateException if the project is the same as the assigned one
     */
    @Override
    public void setProjApplied(TemplateProject p) {
        if (p.equals(this.getAssignedProjectAsOfficer())) {
            throw new IllegalStateException("Cannot apply for the same project you're handling as officer.");
        }
        super.setProjApplied(p);
    }

    // GETTERS //

    /**
     * Returns the project the officer has applied to manage.
     *
     * @return the project applied as officer
     */
    public TemplateProject getAppliedProjectAsOfficer() {
        return this.appliedProjectAsOfficer;
    }

    /**
     * Returns the project assigned to the officer.
     *
     * @return the assigned project
     */
    public TemplateProject getAssignedProjectAsOfficer() {
        return this.assignedProjectAsOfficer;
    }

    // SETTERS //

    /**
     * Sets the project the officer has applied to manage.
     *
     * @param p the project to apply for
     */
    public void setAppliedProjectAsOfficer(TemplateProject p) {
        this.appliedProjectAsOfficer = p;
    }

    /**
     * Sets the project assigned to the officer.
     *
     * @param p the assigned project
     */
    public void setAssignedProjectAsOfficer(TemplateProject p) {
        this.assignedProjectAsOfficer = p;
    }
}

