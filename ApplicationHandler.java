import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class ApplicationHandler {

    private List<Applicant> applicants;
    private List<TemplateProject> templateProjects;
    private Map<Applicant, TemplateProject> projectsPendingApproval;

    ProjectManager pm = new ProjectManager();
    UserRepository userRepo = new UserRepository();

    public ApplicationHandler() {
        this.applicants = userRepo.getAllApplicants();
        this.templateProjects = pm.getTemplateProjects();
        this.projectsPendingApproval = new HashMap<>();
    }

    public void applyForProject(Applicant a, TemplateProject p) {
        switch(a.getEligibilityStatus()) {
            case 0 -> {
                System.out.println("ERROR: Applicant not eligible for any projects.");
            }

            case 1 -> {
                if ((a.getApplicationStatus().equals("No Project Applied")||a.getApplicationStatus().equals("Unsuccessful"))) {
                    this.projectsPendingApproval.put(a, p);
                    a.setProjApplied(p);
                    a.setAppliedType(1);
                    a.setApplicationStatus("Pending Approval");
                    
                }
                else {
                    if (a.getAppliedType() == 2) {
                        System.out.println("ERROR: Applicant not eligible for this type of flat.");
                    }
                    else {
                        System.out.println("ERROR: Applicant already applied for a project.");
                    }
                }
            }

            case 2 -> {
                if ((a.getApplicationStatus().equals("No Project Applied")||a.getApplicationStatus().equals("Unsuccessful")) &&
                (a.getAppliedType() == 1 || a.getAppliedType() == 2)) {
                    this.projectsPendingApproval.put(a, p);
                    a.setProjApplied(p);
                    a.setApplicationStatus("Pending Approval");
                    
                }
                else {
                    System.out.println("ERROR: Applicant already applied for a project.");
                }
            }
        }
    }

    public String viewStatus(Applicant a) {
        return a.getApplicationStatus();
    }

    public void withdrawApplicationBeforeApproval(Applicant a) {
        if (projectsPendingApproval.containsKey(a) && !a.getApplicationStatus().equals("No Project Applied")) {
            projectsPendingApproval.remove(a);
            a.setApplicationStatus("No Project Applied");
            a.setAppliedType(-1);
            System.out.println("Application successfully withdrawn");
        }
        else {
            System.out.println("ERROR: No project to withdraw from for this user");
        }
    }


    public static void bookFlat(Applicant a){
        if(!"Approved".equals(a.getApplicationStatus())){
            System.out.println("ERROR: Applicant not approved for any project.");
            return;
        }

        TemplateProject p = a.getProjApplied();

        if(p == null) {
            System.out.println("ERROR: No project applied for.");
            return;
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("Numbers of unit for " + p.getName() + ":");
        if(a.getAppliedType() == 1){
                System.out.println("2-Room: " + p.getNumOfType1());
            }
            else if(a.getAppliedType() == 2){
                System.out.println("3-Room: " + p.getNumOfType2());
            }

        System.out.print("Do you want to book a flat? (Y/N): ");
        String choice = sc.nextLine().trim().toUpperCase();
        if(!choice.equals("Y")){
            System.out.println("Booking cancelled.");
        }
        else {
            OfficerPortal.submitBookingRequest(a, p, a.getAppliedType());
        }
        
    }

    public void viewAppliedProject(Applicant a) {
        if (projectsPendingApproval.containsKey(a)) {
            System.out.println("Applied project: ");
            TemplateProject pForDisplay = projectsPendingApproval.get(a);
            pForDisplay.displayProjectDetails();
        }
    }
}