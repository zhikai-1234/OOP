import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;

public class ApplicantPortal {
	
	private Applicant applicant;
	private List<Project> projects;
	private List<ProjectApplication> allProjectApplications;
	
	Scanner sc = new Scanner(System.in);
	
	public ApplicantPortal(Applicant a, List<Project> projs) {
		this.applicant = a;
		this.projects = projs;
		this.allProjectApplications = new ArrayList<ProjectApplication>();
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
			
			case 2 -> applyForProject();
				
			case 3 -> viewAppliedProject();
			
			case 4 -> bookFlat();
				
			case 5 -> requestWithdrawal();
				
			case 6 -> submitEnquiry();
				
			case 7 -> displayAllEnquiries();
				
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
	
	private int checkEligibility(Applicant a){
        int age = Integer.parseInt(a.getAge());
        if(a.getMaritalStatus().equals("Single") && age >= 35){
            return 1;
        }
        else if(a.getMaritalStatus().equals("Married") && age >= 21){
            return 2;
        }
        return 0;
    }

	public void applyForProject() {
		/* Eligibility 0: Not eligible at all
		 * Eligibility 1: 2-Room only
		 * Eligibility 2: Both 2 and 3-Room
		 */
		int applicantEligibility = checkEligibility(applicant);
		
		switch (applicantEligibility) {
		
			case 0 -> System.out.println("You are not eligible to apply for any project.");
			
			case 1 -> {
				System.out.println("Which project type would you like to apply for?");
				System.out.println();
				int i = 1;
				for (Project p : projects) {
					System.out.printf("(%d) %s | Units left: %d\n", i, p.getProjName(), p.getNumOfUnitsType1());
					i++;
				}
				System.out.print("Enter your choice: ");
				int projChoice = sc.nextInt();
				sc.nextLine();
				if (projects.get(projChoice - 1).getNumOfUnitsType1() == 0) {
					System.out.println("Invalid choice: No more units left");
				}
				else {
					System.out.println("Successful! Creating your application now...");
				}
			}
			
			case 2 -> {
				System.out.println("Would you like to apply for a 2-Room or 3-Room flat?");
				System.out.println("(1) 2-Room");
				System.out.println("(2) 3-Room");
				int nRooms = sc.nextInt();
				sc.nextLine();
				
				if (nRooms == 1) {
					System.out.println("Which 2-room project type would you like to apply for?");
					System.out.println();
					int i = 1;
					for (Project p : projects) {
						System.out.printf("(%d) %s | Units left: %d\n", i, p.getProjName(), p.getNumOfUnitsType1());
						i++;
					}
				}
				
				else if (nRooms == 2) {
					System.out.println("Which 3-room project type would you like to apply for?");
					System.out.println();
					int i = 1;
					for (Project p : projects) {
						System.out.printf("(%d) %s | Units left: %d\n", i, p.getProjName(), p.getNumOfUnitsType2());
						i++;
					}
				}
				
				else {
					System.out.println("Invalid choice");
				}
			}
			
			default -> System.out.println("Invalid eligibility");
		
		}
	}

	public void viewAppliedProject() {
		// TODO Auto-generated method stub
		
	}

	public void bookFlat() {
		// TODO Auto-generated method stub
		
	}

	public void requestWithdrawal() {
		// TODO Auto-generated method stub
		
	}

	public void submitEnquiry() {
		// TODO Auto-generated method stub
		
	}

	public void displayAllEnquiries() {
		// TODO Auto-generated method stub
		
	}

}
