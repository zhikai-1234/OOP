import java.util.Scanner;

public class Officer extends Applicant {
    private TemplateProject appliedProject;
    private TemplateProject assignedProjectAsOfficer;
    private boolean isApprovedAsOfficer;
    private String officerRegistrationStatus;
    private Scanner scanner = new Scanner(System.in);

    public Officer(String name, String userID, int age, String maritalStatus) {
        super(name, userID, age, maritalStatus);
        this.appliedProject = null;
        this.assignedProjectAsOfficer = null;
        this.isApprovedAsOfficer = false;
        this.officerRegistrationStatus = "Not Registered"; 
    }

    //To preven applying as applicant for same project
    @Override
    public void setProjApplied(TemplateProject p) {
        if (p.equals(this.getAssignedProject())) {
            throw new IllegalStateException("Cannot apply for the same project you're handling as officer.");
        }
        super.setProjApplied(p);
    }

    // GETTERS //
    public TemplateProject getAppliedProject() {
        return this.appliedProject;
    }
    public TemplateProject getAssignedProject() {
        return this.assignedProjectAsOfficer;
    }

    // SETTERS //
    public void setAppliedProject(TemplateProject p) {
        this.appliedProject = p;
    }
    public void setAssignedProjectAsOfficer(TemplateProject p) {
        this.assignedProjectAsOfficer = p;
    }
    public void setApprovalAsOfficer(boolean truthVal) {
        this.isApprovedAsOfficer = truthVal;
    }
    public void setOfficerRegistrationStatus(String status) {
        this.officerRegistrationStatus = status;
    }
}
