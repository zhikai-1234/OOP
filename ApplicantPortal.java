import java.util.List;
import java.util.Scanner;

public class ApplicantPortal {
	
	private Applicant applicant;
	private List<Project> projects;
	private ApplicationHandler appHandler;
	Scanner sc = new Scanner(System.in);
	
	public ApplicantPortal(Applicant a, List<Project> projs, ApplicationHandler appHandler) {
		this.applicant = a;
		this.projects = projs;
		this.appHandler = appHandler;
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
			
			case 1 -> viewProjects();
			
			case 2 -> ApplicationHandler.applyForProject(applicant);
				
			case 3 -> ApplicationHandler.viewAppliedProject(applicant);
			
			case 4 -> ApplicationHandler.bookFlat(applicant);
				
			case 5 -> ApplicationHandler.requestWithdrawal(applicant);
				
			case 6 -> EnquiryHandler.submitEnquiry();
				
			case 7 -> applicant.displayAllEnquiries();
				
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
	
		Project appliedProject = a.getProjApplied();
	
		// Display applied project (even if hidden)
		if (appliedProject != null) {
			System.out.println("\n=== APPLIED PROJECT ===");
			appliedProject.displayProjectDetails();
		}
	
		System.out.println("\n=== AVAILABLE PROJECTS ===");
		for (Project p : projects) {
			boolean isApplied = p.equals(appliedProject);
			boolean isEligible = false;

			if(a.getEligibilityStatus() == 1 && p.getFlatType1().equals("2-Room")) {
				isEligible = true;
			} else if (a.getEligibilityStatus() == 2 && (p.getFlatType1().equals("2-Room") || p.getFlatType2().equals("3-Room"))) {
				isEligible = true;
			}

			if(p.getVisibility() && isEligible && !isApplied) {
				p.displayProjectDetails();
			}	
    	}
	}
}
	