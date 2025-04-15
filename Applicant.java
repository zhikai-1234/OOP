import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Applicant extends User{
    private Project projApplied;
    public String applicationStatus;
    private List<Enquiry> enquiryList = new ArrayList<>();
    private int eligibilityStatus;
    private int appliedFlatType;
    Scanner scan = new Scanner(System.in);

    public Applicant(String name, String UserID, String age, String maritalStatus, String password) {
        super(name, UserID, age, maritalStatus, password);
        this.eligibilityStatus = checkEligibility();
    }
    public int getEligibilityStatus(){
        return eligibilityStatus;
    }

    private int checkEligibility(){
        int age = Integer.parseInt(this.getAge());
        if(this.getMaritalStatus().equals("Single") && age >= 35){
            return 1;
        }
        else if(this.getMaritalStatus().equals("Married") && age >= 21){
            return 2;
        }
        return 0;
    }

    public void applyForProject(Project project, String flatType){
    if(projApplied != null){
        System.out.println("You have already applied for an project.");
        return;
    }

        if (eligibilityStatus == 1 && flatType.equals("2-Room")) {
            this.projApplied = project;
            appliedFlatType = 1;
            applicationStatus = "Applied";
            project.addApplicant(this, 1);  // Add applicant to 2-Room list
            System.out.println("You have successfully applied for " + project.getProjName() + " (2-Room).");
        } else if (eligibilityStatus == 2) {  // Eligibility 2: can apply for both 2-Room and 3-Room
            if (flatType.equals("2-Room")) {
                appliedFlatType = 1;
                this.projApplied = project;
                applicationStatus = "Applied";
                project.addApplicant(this, 1);  // Add to 2-Room applicants list
                System.out.println("You have successfully applied for " + project.getProjName() + " (2-Room).");
            } else if (flatType.equals("3-Room")) {
                this.projApplied = project;
                appliedFlatType = 2; 
                applicationStatus = "Applied";
                project.addApplicant(this, 2);  // Add to 3-Room applicants list
                System.out.println("You have successfully applied for " + project.getProjName() + " (3-Room).");
            }
        } else {
            System.out.println("You do not meet the eligibility requirements.");
        }
    }

public String getUserID() {
    return super.getUserID();
}

    public Project getProjApplied() {
        return projApplied;
    }

public String getName() {
    return super.getName(); //Same for this also.
}



    public void viewStatus(){
        if (projApplied == null) {
            System.out.println("You have not applied for any project.");
        } else {
            System.out.println("Project: " + projApplied.getProjName());
            System.out.println("Status: " + applicationStatus);
    }
}

    public void setApplicationStatus(String status) {
        this.applicationStatus = status;
    }

    public void withdrawApplication() {
        if (projApplied == null) {
            System.out.println("You have not applied for any project.");
            return;
        }

        else{
            projApplied.removeApplicant(this, appliedFlatType);
            projApplied = null;
            applicationStatus = null;
        
            System.out.println("Your application has been successfully withdrawn.");
        }
    }

    

    public void bookFlat(){

    }
    
    public void displayJobscope(BTOSystem system) {
        System.out.println("Welcome, Applicant ");
        System.out.println("1. View Projects");
        System.out.println("2. Apply for Project");
        System.out.println("3. View Application Status");
        System.out.println("4. Withdraw Application");
        System.out.println("5. Send Enquires");

        int choice = scan.nextInt();
        FlatBooking flatBooking = new FlatBooking();
        List<Project> projectList = system.getProjectList();  // Assuming getProjectList() returns List<Project>
        Project[] projectsArray = projectList.toArray(new Project[0]);  // Convert to array
        switch (choice){
            case 1: 
                    flatBooking.viewProjects(this, projectsArray);
                    break;

            case 2: 
                    flatBooking.selectProject(this, projectsArray);
                    break;

            case 3:
                    viewStatus();
                    break;
            case 4: 
                    withdrawApplication();
                    break;
        }


        
    }

}
