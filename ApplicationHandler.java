import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class ApplicationHandler {

    private List<TemplateProject> templateProjects;
    private ProjectManager pm;
    private UserRepository userRepo;

    private Map<Applicant, TemplateProject> projectsPendingApproval;
    private Map<Applicant, BookingRequest> bookingsPendingApproval;


    public ApplicationHandler(ProjectManager projManager, UserRepository userRepo, Map<Applicant, TemplateProject> projectsPendingApproval, Map<Applicant, BookingRequest> bookingsPendingApproval) {
        this.pm = projManager;
        this.userRepo = userRepo;
        this.templateProjects = pm.getTemplateProjects();
        this.projectsPendingApproval = projectsPendingApproval;
        this.bookingsPendingApproval = bookingsPendingApproval;
    }

    public void applyForProject(Applicant a, TemplateProject p, Scanner sc) {
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
                    System.out.println("Success! Application now pending approval...");
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
                if (a.getProjApplied() == null) {
                    System.out.println("Would you like to apply for a 2-Room or 3-Room?");
                    System.out.println("[1] 2-Room");
                    System.out.println("[2] 3-Room");
                    int roomChoice = sc.nextInt();
                    sc.nextLine();
                    if (roomChoice == 1 && p.getNumOfType1() > 0) {
                        this.projectsPendingApproval.put(a, p);
                        a.setAppliedType(1);
                        a.setProjApplied(p);
                        a.setApplicationStatus("Pending Approval");
                        System.out.println("Success! Application now pending approval...");
                    }
                    else if (roomChoice == 2 && p.getNumOfType2() > 0) {
                        this.projectsPendingApproval.put(a, p);
                        a.setAppliedType(2);
                        a.setProjApplied(p);
                        a.setApplicationStatus("Pending Approval");
                        System.out.println("Success! Application now pending approval...");
                    }
                    else if (p.getNumOfType1() == 0 || p.getNumOfType2() == 0) {
                        System.out.println("ERROR: No more flats remaining for selected type.");
                    }
                    else {
                        System.out.println("ERROR: Applicant already applied for a project.");
                    }
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


    public void bookFlat(Applicant a, Scanner sc){
        if(!"Approved".equals(a.getApplicationStatus())){
            System.out.println("ERROR: Applicant not approved for any project.");
            return;
        }

        TemplateProject p = a.getProjApplied();

        if(p == null) {
            System.out.println("ERROR: No project applied for.");
            return;
        }

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
            submitBookingRequest(a, p, a.getAppliedType());
        }
        
    }

    public void submitBookingRequest(Applicant a, TemplateProject p, int flatType) {
		if(a.hasBookedFlat() || !"Approved".equals(a.getApplicationStatus())) {
            System.out.println("Applicant not eligible for booking");
            return;
        }
		bookingsPendingApproval.put(a, new BookingRequest(a, p, flatType));
        a.setApplicationStatus("Pending Booking Approval");
	}

    public void viewAppliedProject(Applicant a) {
        if (projectsPendingApproval.containsKey(a)) {
            System.out.println("Applied project: ");
            TemplateProject pForDisplay = projectsPendingApproval.get(a);
            pForDisplay.displayProjectDetails();
        }
    }

    public Map<Applicant, BookingRequest> getBookingsPendingApproval() {
        return this.bookingsPendingApproval;
    }

    public void removeBookingsPendingApproval(Applicant a) {
        this.bookingsPendingApproval.remove(a);
    }

    // GETTERS //
    public Map<Applicant, BookingRequest> getBookingPendingApproval() {
        return this.bookingsPendingApproval;
    }
}