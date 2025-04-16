import java.util.List;
import java.util.Scanner;

public class ApplicantPortal {
	
	private Applicant applicant;
	private List<Project> projects;
	
	Scanner sc = new Scanner(System.in);
	
	public ApplicantPortal(Applicant a, List<Project> projs) {
		this.applicant = a;
		this.projects = projs;
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
				
			case 6 -> applicant.submitEnquiry();
				
			case 7 -> applicant.displayAllEnquiries();
				
			case 8 -> exit = true;
			
			default -> System.out.println("Invalid selection. Please try again.");
			}
		} while (!exit);	
	}

	public void viewProjects() {
		for (Project p : projects) {
			if (p.getVisibility()) {
				p.displayProjectDetails();
			}
		}
	}
}