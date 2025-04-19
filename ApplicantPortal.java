import java.util.List;
import java.util.Scanner;

public class ApplicantPortal {
	
	private Applicant applicant;
	private List<TemplateProject> templateProjects;
	Scanner sc = new Scanner(System.in);

	ProjectManager pm = new ProjectManager();
	ApplicationHandler ah = new ApplicationHandler();
	EnquiryHandler eh = new EnquiryHandler();
	
	public ApplicantPortal(Applicant a) {
		this.applicant = a;
		this.templateProjects = pm.getTemplateProjects();
	}
	
	
	public void showOptions() {
		System.out.println("Welcome, " + applicant.getName());
		System.out.println("1. View Projects");
        System.out.println("2. Apply for project");
        System.out.println("3. View applied project and application status");
        System.out.println("4. Book a flat (pending successful application)");
        System.out.println("5. Request a withdrawal from application");
        System.out.println("6. Submit an enquiry");
        System.out.println("7. View all enquiries");
        System.out.println("8. Quit Program");
        System.out.print("Enter your choice: ");
	}
	
	public void portal() {
		sc.nextLine();
		System.out.print("Enter your choice: ");
		int choice = sc.nextInt();
		sc.nextLine();
		boolean exit = false;
		do {
			switch (choice) {
			
			case 1 -> viewProjects(applicant);
			
			case 2 -> {
				int i = 1;
				for (TemplateProject p : templateProjects) {
					System.out.printf("Project %d\n", i);
					pm.displayProjectDetails(p);
					i++;
				}
				System.out.println("Choose from these projects");
				int projChoice = sc.nextInt();
				ah.applyForProject(applicant, templateProjects.get(i - 1));
				System.out.println("Project successfully applied.");
				}
				
			case 3 -> ah.viewAppliedProject(applicant);
			
			case 4 -> ah.bookFlat(applicant);
				
			case 5 -> ah.withdrawApplicationBeforeApproval(applicant);
				
			case 6 -> eh.submitEnquiry(applicant);
				
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
			pm.displayProjectDetails(appliedProject);
		}
	
		System.out.println("\n=== AVAILABLE PROJECTS ===");
		for (TemplateProject p : templateProjects) {
			boolean isApplied = p.equals(appliedProject);
			boolean isEligible = false;

			if(a.getEligibilityStatus() == 1 && p.getType1().equals("2-Room")) {
				isEligible = true;
			} else if (a.getEligibilityStatus() == 2 && (p.getType1().equals("2-Room") || p.getType2().equals("3-Room"))) {
				isEligible = true;
			}

			if(p.getVisibility() && isEligible && !isApplied) {
				pm.displayProjectDetails(appliedProject);
			}	
    	}
	}
}
	