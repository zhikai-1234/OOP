/*public class Applicant extends User{
    private TemplateProject projApplied;
    public String applicationStatus;
    private int eligibilityStatus;
    private int appliedFlatType;
    private boolean hasBookedFlat;
    private int bookedFlatType;

    public Applicant(String name, String userID, int age, String maritalStatus, String password) {
        super(name, userID, age, maritalStatus, password);
        this.projApplied = null;
        this.applicationStatus = "No Project Applied";
        this.eligibilityStatus = checkEligibility();
        this.appliedFlatType = -1;
        this.hasBookedFlat = false;
        this.bookedFlatType = -1;
    }

    // ELIGIBILITY METHODS //

    public int getEligibilityStatus(){
        return this.eligibilityStatus;
    }

    private int checkEligibility(){
        int age = this.getAge();
        if(this.getMaritalStatus().equals("Single") && age >= 35){
            return 1;
        }
        else if(this.getMaritalStatus().equals("Married") && age >= 21){
            return 2;
        }
        return 0;
    }

    // GETTERS //
    public String getApplicationStatus() {
        return this.applicationStatus;
    }

    public int getAppliedFlatType() {
        return this.appliedFlatType;
    }

    public int getBookedFlatType() {
        return this.bookedFlatType;
    }

    public TemplateProject getProjApplied() {
        return this.projApplied;
    }

    public boolean hasBookedFlat() {
        return this.hasBookedFlat;
    }

    // SETTERS //
    public void setApplicationStatus(String status) {
        this.applicationStatus = status;
    }

    public void setAppliedFlatType(int type) {
        this.appliedFlatType = type;
    }

    public void setBookedFlatType(int type) {
        this.bookedFlatType = type;
    }

    public void setProjApplied(TemplateProject proj) {
        this.projApplied = proj;
    }
    public void setBookedFlat(boolean status) {
        this.hasBookedFlat = status;
    }
}*/

/**
 * Represents an applicant.
 * This class stores information about the applicant's application status,
 * eligibility(based on age and marital status), and project/flat selections.
 */
public class Applicant extends User {

    /**
     * The project the applicant has applied to.
     */
    private TemplateProject projApplied;

    /**
     * Current application status of the applicant.
     */
    public String applicationStatus;

    /**
     * Eligibility status based on age and marital status.
     */
    private int eligibilityStatus;

    /**
     * Flat type applied for by the applicant.
     */
    private int appliedFlatType;

    /**
     * Whether the applicant has booked a flat.
     */
    private boolean hasBookedFlat;

    /**
     * The flat type that the applicant has booked.
     */
    private int bookedFlatType;

    /**
     * Constructs a new Applicant with personal and login details.
     * Automatically checks and stores eligibility based on age and marital status.
     *
     * @param name the name of the applicant
     * @param userID the unique user ID of the applicant
     * @param age the age of the applicant
     * @param maritalStatus the marital status of the applicant
     * @param password the password for login
     */
    public Applicant(String name, String userID, int age, String maritalStatus, String password) {
        super(name, userID, age, maritalStatus, password);
        this.projApplied = null;
        this.applicationStatus = "No Project Applied";
        this.eligibilityStatus = checkEligibility();
        this.appliedFlatType = -1;
        this.hasBookedFlat = false;
        this.bookedFlatType = -1;
    }

    // ELIGIBILITY METHODS //

    /**
     * Returns the eligibility status of the applicant.
     *
     * @return 0 if not eligible, 1 if single and 35+, 2 if married and 21+
     */
    public int getEligibilityStatus(){
        return this.eligibilityStatus;
    }

    /**
     * Checks eligibility based on age and marital status.
     *
     * @return eligibility status code
     */
    private int checkEligibility(){
        int age = this.getAge();
        if(this.getMaritalStatus().equals("Single") && age >= 35){
            return 1;
        }
        else if(this.getMaritalStatus().equals("Married") && age >= 21){
            return 2;
        }
        return 0;
    }

    // GETTERS //

    /**
     *returns the current application status
     * @return the current application status
     */
    public String getApplicationStatus() {
        return this.applicationStatus;
    }

    /**
     *returns the flat type applied for, by the applicant
     * @return the flat type the applicant has applied for
     */
    public int getAppliedFlatType() {
        return this.appliedFlatType;
    }

    /**
     * Returns the flat type the applicant has booked.
     *
     * @return the flat type that has been booked by the applicant 
     */
    public int getBookedFlatType() {
        return this.bookedFlatType;
    }

    /**
     * returns the applied project by the applicant
     * @return the project, the applicant has applied to
     */
    public TemplateProject getProjApplied() {
        return this.projApplied;
    }

    /**
     * Checks whether the applicant has booked a flat.
     * @return true if a flat has been booked, false otherwise
     */
    public boolean hasBookedFlat() {
        return this.hasBookedFlat;
    }

    // SETTERS //

    /**
     * Sets the application status.
     * @param status the new application status
     */
    public void setApplicationStatus(String status) {
        this.applicationStatus = status;
    }

    /**
     * Sets the flat type the applicant has applied for.
     * @param type the flat type
     */
    public void setAppliedFlatType(int type) {
        this.appliedFlatType = type;
    }

    /**
     * Sets the flat type the applicant has booked.
     * @param type the booked flat type
     */
    public void setBookedFlatType(int type) {
        this.bookedFlatType = type;
    }

    /**
     * Sets the project the applicant has applied to.
     * @param proj the TemplateProject object
     */
    public void setProjApplied(TemplateProject proj) {
        this.projApplied = proj;
    }

    /**
     * Sets the booked flat status.
     * @param status true if the applicant has booked a flat, false otherwise
     */
    public void setBookedFlat(boolean status) {
        this.hasBookedFlat = status;
    }
}

