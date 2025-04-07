import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Applicant extends User{
    public String projApplied;
    public String applicationStatus;
    public String[] enquiryList;
    private int eligibilityStatus;
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

    public void applyForProject(Project project){
        if(projApplied != null){
            System.out.println("You have already applied for an project.");
            return;
        }

        if(this.getMaritalStatus().equals("Single") && Integer.parseInt(this.getAge()) >= 35){
            if(project.getFlatType1().equals("2-Room")){
                projApplied = project.getProjName();
                applicationStatus = "Applied";
                System.out.println("You have sucessfully applied for "+project.getProjName() + "(2-Room).");
            }
            else if (this.getMaritalStatus().equals("Married") && Integer.parseInt(this.getAge()) >=  21){
                projApplied = project.getProjName();
                applicationStatus = "Applied";
                System.out.println("You have sucessfully applied for "+project.getProjName() + "(2-Room).");
            }
            else{
                System.out.println("You do not meet the age or martial status requirement to apply. ");
            }
        }
    }

    public void viewStatus(){

    }

    public void withdrawApplication(){

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
        switch (choice){
            case 1: List<Project> projectList = system.getProjectList();  // Assuming getProjectList() returns List<Project>
                    Project[] projectsArray = projectList.toArray(new Project[0]);  // Convert to array
                    flatBooking.viewProject(this, projectsArray); // Use the instance to call viewProj
            break;
            case 2: applyForProject(null);
        }


        
    }

}