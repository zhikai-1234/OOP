import java.util.Scanner;

public class ApplicantPortal {
	
	private Applicant applicant;
	Scanner sc = new Scanner(System.in);

	private ProjectManager pm;
	private ApplicationHandler ah;
	private EnquiryHandler eh;
	
	public ApplicantPortal(Applicant a, ProjectManager pm, ApplicationHandler ah, EnquiryHandler eh) {
		this.applicant = a;
		this.pm = pm;
		this.ah = ah;
		this.eh = eh;
		System.out.println("Number of projects: " + pm.getTemplateProjects().size());
		System.out.println("Applicant's marital status: " + applicant.getMaritalStatus());
		System.out.println("Applicant's eligibility status: " + applicant.getEligibilityStatus());
	}
	
	
	public void showOptions() {
		System.out.println();
		System.out.println("=================================================");
		System.out.println("Welcome, " + applicant.getName());
		System.out.println("[1] View Projects");
        System.out.println("[2] Apply for project");
        System.out.println("[3] View applied project and application status");
        System.out.println("[4] Book a flat (pending successful application)");
        System.out.println("[5] Request a withdrawal from application");
        System.out.println("[6] Submit an enquiry");
        System.out.println("[7] View all enquiries");
        System.out.println("[8] Log out");
		System.out.println("=================================================");
		System.out.println();
	}
	
	public void portal() {

		boolean exit = false;

		do {

			showOptions();
			System.out.print("Enter your choice: ");
			int choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			
			case 1 -> { 
				if(applicant.getEligibilityStatus() == 0) {
					System.out.println("ERROR: Applicant not eligible for any projects.");
					return;
				}
				else if (applicant.getMaritalStatus().equals("Single")) {
					for (TemplateProject p : pm.getTemplateProjects()) {
						pm.display2RoomProjectDetails(p);
					}
				}
				else if (applicant.getMaritalStatus().equals("Married")) {
					for (TemplateProject p : pm.getTemplateProjects()) {
						pm.display2and3RoomProjectDetails(p);
					}
				}
			}
			
			case 2 -> {
				int i = 1;
				for (TemplateProject p : pm.getTemplateProjects()) {
					System.out.println();
					System.out.printf("Project %d\n", i);
					pm.display2and3RoomProjectDetails(p);
					i++;
				}
				System.out.println("Choose from these projects");
				int projChoice = sc.nextInt();
				ah.applyForProject(applicant, pm.getTemplateProjects().get(projChoice - 1), sc);
				}
				
			case 3 -> ah.viewAppliedProject(applicant);
			
			case 4 -> ah.bookFlat(applicant, sc);
				
			case 5 -> {
				if (applicant.hasBookedFlat() == false) {
					ah.withdrawApplicationBeforeApproval(applicant);
				}
				//else {
					//ah.withdrawApplicationAfterApproval(applicant);
				//}
			}
				
			case 6 -> eh.submitEnquiry(applicant, sc);
				
			case 7 -> eh.displayAndManageUserEnquiries(applicant);
				
			case 8 -> exit = true;
			
			default -> System.out.println("Invalid selection. Please try again.");
			}
		} while (!exit);	
	}

	public void viewProjects(Applicant a) {
		if (a.getEligibilityStatus() == 0) {
			System.out.println("ERROR: Applicant not eligible for any projects.");
			return;
		}
	
		TemplateProject appliedProject = a.getProjApplied();
	
		// Display applied project (even if hidden)
		if (appliedProject != null) {
			System.out.println("\n=== APPLIED PROJECT ===");
			pm.display2and3RoomProjectDetails(appliedProject);
		}
	
		System.out.println("\n=== AVAILABLE PROJECTS ===");
		for (TemplateProject p : pm.getTemplateProjects()) {
			boolean isApplied = p.equals(appliedProject);
			boolean isEligible = false;

			if(a.getEligibilityStatus() == 1 && p.getType1().equals("2-Room")) {
				isEligible = true;
			} else if (a.getEligibilityStatus() == 2 && (p.getType1().equals("2-Room") || p.getType2().equals("3-Room"))) {
				isEligible = true;
			}

			if(p.getVisibility() && isEligible && !isApplied) {
				pm.display2and3RoomProjectDetails(appliedProject);
			}	
    	}
	}
}
	