import java.util.Scanner;

public class Officer extends Applicant {
    private String assignedProjectAsOfficer;
    private boolean isApprovedAsOfficer;
    private String officerRegistrationStatus;
    private Scanner scanner = new Scanner(System.in);

    public Officer(String name, String userID, int age, String maritalStatus) {
        super(name, userID, age, maritalStatus);
        this.assignedProjectAsOfficer = null;
        this.isApprovedAsOfficer = false;
        this.officerRegistrationStatus = "Not Registered"; 
    }
}
