import java.util.List;
import java.util.Map;
import java.util.Scanner;
public class ApplicationHandler {

    private List<TemplateProject> templateProjects;
    private ProjectManager pm;
    private UserRepository userRepo;

    private Map<Applicant, TemplateProject> projectsPendingApproval;
    private Map<Applicant, TemplateProject> approvedProjects;
    private Map<Applicant, TemplateProject> rejectedProjects;
    private Map<Applicant, BookingRequest> bookingsPendingApproval;
    private Map<Applicant, TemplateProject> withdrawalsPendingApproval;


    public ApplicationHandler(ProjectManager projManager, UserRepository userRepo, Map<Applicant, 
    TemplateProject> projectsPendingApproval, Map<Applicant, TemplateProject> approvedProjects,
    Map<Applicant, TemplateProject> rejectedProjects, Map<Applicant, BookingRequest> bookingsPendingApproval,
    Map<Applicant, TemplateProject> withdrawalsPendingApproval) {
        this.pm = projManager;
        this.userRepo = userRepo;
        this.templateProjects = pm.getTemplateProjects();
        this.projectsPendingApproval = projectsPendingApproval;
        this.approvedProjects = approvedProjects;
        this.rejectedProjects = rejectedProjects;
        this.bookingsPendingApproval = bookingsPendingApproval;
        this.withdrawalsPendingApproval = withdrawalsPendingApproval;
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
                    a.setAppliedFlatType(1);
                    a.setApplicationStatus("Pending Approval");
                    System.out.println("Success! Application now pending approval...");
                }
                else {
                    if (a.getAppliedFlatType() == 2) {
                        System.out.println("ERROR: Applicant not eligible for this type of flat.");
                    }
                    else {
                        System.out.println("ERROR: Applicant already applied for a project.");
                    }
                }
            }

            case 2 -> {
            	//Make sure if applicant status is unsuccessful, they can go ahead and apply again
            	if (a.getApplicationStatus().equals("No Project Applied") || a.getApplicationStatus().equals("Unsuccessful")) {
                    System.out.println("Would you like to apply for a 2-Room or 3-Room?");
                    System.out.println("[1] 2-Room");
                    System.out.println("[2] 3-Room");
                    int roomChoice = sc.nextInt();
                    sc.nextLine();
                    if (roomChoice == 1 && p.getNumOfType1() > 0) {
                        this.projectsPendingApproval.put(a, p);
                        a.setAppliedFlatType(1);
                        a.setProjApplied(p);
                        a.setApplicationStatus("Pending Approval");
                        System.out.println("Success! Application now pending approval...");
                    }
                    else if (roomChoice == 2 && p.getNumOfType2() > 0) {
                        this.projectsPendingApproval.put(a, p);
                        a.setAppliedFlatType(2);
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
            withdrawalsPendingApproval.put(a, a.getProjApplied());
            System.out.println("\nSuccessfully submitted withdrawal request.\n");
        }
        else {
            System.out.println("\nERROR: No project to withdraw from for this user\n");
        }
    }

    public void withdrawApplicationAfterApproval(Applicant a) {
        if (approvedProjects.containsKey(a)) {
            //approvedProjects.remove(a);
            //a.setProjApplied(null);
            //a.setApplicationStatus("No Project Applied");
            //a.setAppliedFlatType(-1);
            withdrawalsPendingApproval.put(a, a.getProjApplied());
            System.out.println("\nSuccessfully submitted withdrawal request.\n");
        }
        else {
            System.out.println("Approved project not found");
        }
    }

    // FOR OFFICER //
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

        if(a.getAppliedFlatType() == 1){
                System.out.println("2-Room: " + p.getNumOfType1());
            }

        else if(a.getAppliedFlatType() == 2){
            System.out.println("3-Room: " + p.getNumOfType2());
        }

        System.out.print("Do you want to book a flat? (Y/N): ");
        String choice = sc.nextLine().trim().toUpperCase();
        if(!choice.equals("Y")){
            System.out.println("Booking cancelled.");
        }
        else {
            submitBookingRequest(a, p, a.getAppliedFlatType());
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

    public Applicant getApplicantFromName(String name) {
        for (Applicant a : userRepo.getAllApplicants()) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    public Applicant getApplicantFromNRIC(String nric) {
        for (Applicant a : userRepo.getAllApplicants()) {
            if (a.getUserID().equals(nric)) {
                return a;
            }
        }
        return null;
    }

    public Map.Entry<Applicant, TemplateProject> retrieveApplicationWithNRIC(String nric) {
        Applicant a = getApplicantFromNRIC(nric);
        if (approvedProjects.containsKey(a)) {
            return Map.entry(a, approvedProjects.get(a));
        }
        return null; 
    }
    
    // FOR MANAGER //
    public void approveApplication(Scanner sc, Manager manager) { 
    	boolean hasApplications = false;
    	
    	//Check if there is any application for this manager's projects so that other Manager not handling
    	//can't accept or reject.
        for (Map.Entry<Applicant, TemplateProject> entry : projectsPendingApproval.entrySet()) {
            if (entry.getValue().getManagerName().equals(manager.getName())) {
                hasApplications = true;
                break;
            }
        }
        //If don't have then return
        if (!hasApplications) {
            System.out.println("\nNo applications to approve or reject.\n");
            return;
        }
    	
        for (Map.Entry<Applicant, TemplateProject> application : projectsPendingApproval.entrySet()) {
        	//To ensure only the manager in charge of project can approve applicant request
            if (!application.getValue().getManagerName().equals(manager.getName())) {
                continue; 
            }
            
            Applicant applicant = application.getKey();
            TemplateProject project = application.getValue();
            //To remove the applicant request from showing up again whenever the manager
            //approves or rejects them.
            String status = applicant.getApplicationStatus();
            if (status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("Booked") || status.equalsIgnoreCase("Unsuccessful")) {
                continue;
            }
            
            System.out.printf("\nApplicant: %s || Applied Project: %s\n", applicant.getName(), project.getName());
            
                int type = applicant.getAppliedFlatType();
                int flatsLeft;
                int displayRoom;

                if (type == 1) {
                    flatsLeft = project.getNumOfType1();
                    displayRoom = 2;
                } 
                else {
                    flatsLeft = project.getNumOfType2();
                    displayRoom = 3;
                }
                System.out.printf("Flat Type: %d-Room || Flats Left: %d\n", displayRoom, flatsLeft);
        }

        System.out.print("Enter the applicant's name to approve/reject their application(or 0 to cancel): ");
        String applicantName = sc.nextLine().trim(); 
        if(applicantName.equals("0")) {
        	System.out.println("Cancelled");
        	return;
        }
        Applicant selectedApplicant = getApplicantFromName(applicantName);
        if (selectedApplicant == null) {
            System.out.println("Error: Applicant with name '" + applicantName + "' was not found.");
            return;
        }
        System.out.println("[A] Approve || [R] Reject");
        System.out.print("Enter your choice: ");
        String approval = sc.nextLine().toLowerCase();

        if (approval.equals("a")) {
            approvedProjects.put(getApplicantFromName(applicantName), getApplicantFromName(applicantName).getProjApplied());
            getApplicantFromName(applicantName).setApplicationStatus("Approved");
            System.out.println("Application successfully approved.");
        }

        else if (approval.equals("r")) {
            rejectedProjects.put(getApplicantFromName(applicantName), getApplicantFromName(applicantName).getProjApplied());
            getApplicantFromName(applicantName).setApplicationStatus("Unsuccessful");
            System.out.println("Application successfully rejected.");
        }
    }

    public void approveWithdrawals(Scanner sc) {
        for (Map.Entry<Applicant, TemplateProject> withdrawal : withdrawalsPendingApproval.entrySet()) {  
            System.out.println("\nWithdrawal request from:");
            System.out.printf("Name: %s || Project Applied: %s || Approval Status: %s\n", withdrawal.getKey().getName(), 
            withdrawal.getValue().getName(), withdrawal.getKey().getApplicationStatus());

        }
        System.out.print("Enter the applicant's name to approve/reject their withdrawal: ");
        String applicantName = sc.nextLine();
        System.out.println("[A] Approve || [R] Reject");
        System.out.print("Enter your choice: ");
        String approval = sc.nextLine().toLowerCase();

        Applicant applicant = getApplicantFromName(applicantName);

        if (applicant == null) {
            System.out.println("Applicant not found.");
            return;
        }
        
      
        if (approval.equals("a")) {

        	// If the applicant had already booked a flat and now wishes to withdraw,
        	// we need to properly reverse the booking effect by restoring the flat unit count,
        	// and resetting the applicant’s booking and application status.
        	// 
        	// Because the applicant’s getProjApplied() is now a LiveProject object when
        	// the officer helped booked the Project in the Officer class.
        	// We dont update the LiveProject because it is only used at runtime for 
        	// displaying and processing bookings. So, we locate and update
        	// the corresponding TemplateProject stored in the main project list,
        	// as this version is the one saved to file.
        	//
        	// The method retrieveTemplateProject(...) in the ProjectManager Class
        	// will return the original TemplateProject 
        	// based on the project name, allowing us to restore the original flat unit
        	// availability when first read from file.
            if (applicant.getApplicationStatus().equalsIgnoreCase("Booked")) {
            	TemplateProject project = pm.retrieveTemplateProject(applicant.getProjApplied().getName());
                // Restore flat count
                if (applicant.getBookedFlatType() == 1) {
                    project.setNumOfType1(project.getNumOfType1() + 1);
                } else if (applicant.getBookedFlatType() == 2) {
                    project.setNumOfType2(project.getNumOfType2() + 1);
                }

                //Reset applicant's booked status
                applicant.setBookedFlat(false);
                applicant.setBookedFlatType(-1);
            }
            
            //If the Manager approves the rejection then just update applicant status to
            //Unsuccessful so that applicant still can view the applied proj and also
            //can apply for another hdb project.
            approvedProjects.remove(applicant);
            bookingsPendingApproval.remove(applicant);

            //Reset application status
            applicant.setApplicationStatus("Unsuccessful");

            withdrawalsPendingApproval.remove(applicant);
            System.out.println("Withdrawal successfully approved.");
        } 
        else if (approval.equals("r")) {
            withdrawalsPendingApproval.remove(applicant);
            System.out.println("Withdrawal successfully rejected."); 
        }
    }

    public void displayAllBookings() {
        for (Map.Entry<Applicant, BookingRequest> booking : bookingsPendingApproval.entrySet()) {
            System.out.println("\nBooking request from:");
            System.out.printf("Name: %s || Project Applied: %s || Room Type: %d-Room\n", booking.getKey().getName(), 
            booking.getKey().getProjApplied().getName(), booking.getKey().getAppliedFlatType() + 1);
        }
    }

    public void approveBooking(BookingRequest b, Scanner sc) {
        displayAllBookings();
        System.out.print("Enter the name of the applicant to approve/reject their booking: ");
        String applicantName = sc.nextLine();
        Applicant applicant = getApplicantFromName(applicantName);
        System.out.print("[A] Approve | [R] Reject: ");
        String choice = sc.nextLine();
        if (choice.equalsIgnoreCase("a")) {
            b.approveBooking();
            System.out.println("\nBooking successfully approved.\n");
        }   
        else if (choice.equalsIgnoreCase("r")) {
            bookingsPendingApproval.remove(applicant);
            System.out.println("\nBooking successfully rejected.\n");
        }
    }

    // GETTERS //
    public void viewAppliedProject(Applicant a) {
        if(a.getProjApplied() == null) {
            System.out.println("No project applied for.");
            return;
        }
        if (projectsPendingApproval.containsKey(a)) {
            System.out.println("Applied project: ");
            TemplateProject applicantProject = a.getProjApplied();
            System.out.printf("Project Name: %s || Project Type: %d-Room\n", applicantProject.getName(), a.getAppliedFlatType() + 1);
            System.out.println("Application status: " + a.getApplicationStatus());
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