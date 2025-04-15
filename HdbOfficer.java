import java.util.Scanner;

public class HdbOfficer extends Applicant {
    private String assignedProjectAsOfficer;
    private boolean isApprovedAsOfficer;
    private String officerRegistrationStatus;
    private Scanner scanner = new Scanner(System.in);

    public HdbOfficer(String name, String userID, String age, String maritalStatus, String password) {
        super(name, userID, age, maritalStatus, password);
        this.assignedProjectAsOfficer = null;
        this.isApprovedAsOfficer = false;
        this.officerRegistrationStatus = "Not Registered"; 
    }

    public void registerForProj(BTOSystem system){
        System.out.print("Enter project name to register for as officer: ");
        String projectName = scanner.nextLine();

        if (system.hasAppliedAsApplicant(this.getUserID(), projectName)) {
            System.out.println("Error: You cannot register as an HDB Officer for a project you applied to as an Applicant.");
            return;
        }

        if (system.isOfficerForOtherProject(this.getUserID())) {
            System.out.println("Error: You are already registered as an officer for another project.");
            return;
        }

        this.officerRegistrationStatus = "Pending Approval";
        system.submitOfficerApplication(this, projectName);
        System.out.println("Your registration request has been submitted for approval.");
    }

    public void showRegistrationStatus() {
        System.out.println("Officer registration status: " + this.officerRegistrationStatus);
    }

    // This would be called by the Manager
    public void approveRegistration(String projectName) {
        this.assignedProjectAsOfficer = projectName;
        this.isApprovedAsOfficer = true;
        this.officerRegistrationStatus = "Approved";
    }

    public void viewProjectDetails(BTOSystem system){
        if (!isApprovedAsOfficer) {
            System.out.println("Error: Your officer registration is not approved yet.");
            return;
        }

        System.out.println("Project Details for " + assignedProjectAsOfficer + ":");
        System.out.println(system.getProjectDetails(assignedProjectAsOfficer)); 
    }

    public void respondToEnquiries(BTOSystem system){
        if (!isApprovedAsOfficer) {
            System.out.println("Error: Your officer registration is not approved yet.");
            return;
        }

        System.out.print("Enter Applicant NRIC for enquiry: ");
        String applicantNRIC = scanner.nextLine();

        Enquiry enquiry = system.getEnquiry(assignedProjectAsOfficer, applicantNRIC);
        if (enquiry == null) {
            System.out.println("No enquiries found for this applicant.");
            return;
        }

        System.out.println("Enquiry from " + enquiry.getApplicantName() + ": " + enquiry.getMessage());
        System.out.print("Enter response: ");
        String response = scanner.nextLine();
        enquiry.setResponse(response);
        System.out.println("Response sent.");
    }

    public void updateFlatAvailability(BTOSystem system) {
        if (!isApprovedAsOfficer) {
            System.out.println("Error: Your officer registration is not approved yet.");
            return;
        }

        System.out.print("Enter flat type to update: ");
        String flatType = scanner.nextLine();
        System.out.print("Enter number of units sold: ");
        int unitsSold = scanner.nextInt();
        scanner.nextLine();

        system.updateFlatCount(assignedProjectAsOfficer, flatType, unitsSold);
        System.out.println("Flat availability updated.");
    }

    public void updateApplicantStatus(BTOSystem system) {
        if (!isApprovedAsOfficer) {
            System.out.println("Error: Your officer registration is not approved yet.");
            return;
        }

        System.out.print("Enter applicant NRIC: ");
        String applicantNRIC = scanner.nextLine();

        if (!system.isApplicantSuccessful(applicantNRIC, assignedProjectAsOfficer)) {
            System.out.println("Error: Applicant did not successfully apply for this project.");
            return;
        }

        System.out.print("Enter flat type chosen (2-Room/3-Room): ");
        String flatType = scanner.nextLine();

        system.updateApplicantProjectStatus(applicantNRIC, "booked");
        system.updateApplicantFlatType(applicantNRIC, flatType);
        System.out.println("Applicant status updated to 'booked'. Flat type: " + flatType);
    }

    public void generateReceipt(BTOSystem system){
        if (!isApprovedAsOfficer) {
            System.out.println("Error: Your officer registration is not approved yet.");
            return;
        }

        System.out.print("Enter applicant NRIC: ");
        String applicantNRIC = scanner.nextLine();

        Applicant applicant = system.getApplicant(applicantNRIC);
        if (applicant == null || !assignedProjectAsOfficer.equals(applicant.getAssignedProject())) {
            System.out.println("Error: You can only generate receipts for applicants in your project.");
            return;
        }

        System.out.println("---- Receipt ----");
        System.out.println("Applicant Name: " + applicant.getName());
        System.out.println("NRIC: " + applicant.getUserID());
        System.out.println("Age: " + applicant.getAge());
        System.out.println("Marital Status: " + applicant.getMaritalStatus());
        System.out.println("Flat Type: " + applicant.getFlatType());
        System.out.println("Project: " + assignedProjectAsOfficer);
        System.out.println("-----------------");
    }

    public void displayJobscope(BTOSystem system) {
        while (true) {
            System.out.println("\nWelcome, Officer " + getName());
            System.out.println("1. Register for a Project");
            System.out.println("2. View Project Details");
            System.out.println("3. Respond to Enquiries");
            System.out.println("4. Update Flat Availability");
            System.out.println("5. Update Applicant Status");
            System.out.println("6. Generate Receipt");
            System.out.println("7. Show Registration Status");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1: registerForProj(system); break;
                case 2: viewProjectDetails(system); break;
                case 3: respondToEnquiries(system); break;
                case 4: updateFlatAvailability(system); break;
                case 5: updateApplicantStatus(system); break;
                case 6: generateReceipt(system); break;
                case 7: showRegistrationStatus(); break;
                case 8: System.out.println("Logging out..."); return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
