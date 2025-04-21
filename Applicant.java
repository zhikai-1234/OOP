public class Applicant extends User{
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
}
