import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ApplicantPortal implements PortalInterface {
	
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
		System.out.println("[8] Change password");
        System.out.println("[9] Change Filter Settings");
        System.out.println("[10] Log out");
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
			    if (applicant.getEligibilityStatus() == 0) {
			        System.out.println("ERROR: Applicant not eligible for any projects.");
			        return;
			    }

			    UserFilter filter = pm.getFilterForUser(applicant.getName());
			    List<TemplateProject> filtered = filter.filterProjects(pm.getTemplateProjects());

			    if (filtered.isEmpty()) {
			        System.out.println("No matching projects found with the current filter settings.");
			    } else {
			        for (TemplateProject p : filtered) {
			            if (applicant.getMaritalStatus().equals("Single")) {
			                pm.display2RoomProjectDetails(p);
			            } else {
			                pm.display2and3RoomProjectDetails(p);
			            }
			        }
			    }
			}
			
			case 2 -> {
			    if (applicant.getEligibilityStatus() == 0) {
			        System.out.println("ERROR: Applicant not eligible to apply for projects.");
			        return;
			    } 

			    UserFilter filter = pm.getFilterForUser(applicant.getName());
			    List<TemplateProject> filtered = filter.filterProjects(pm.getTemplateProjects());

			    if (filtered.isEmpty()) {
			        System.out.println("No matching projects found with the current filter settings.");
			    } else {
			        int i = 1;
			        for (TemplateProject p : filtered) {
			            System.out.printf("\nProject %d\n", i);
			            if (applicant.getMaritalStatus().equals("Single")) {
			                pm.display2RoomProjectDetails(p);
			            } else {
			                pm.display2and3RoomProjectDetails(p);
			            }
			            i++;
			        } 

			        System.out.println("Choose a project to apply for:");
			        Scanner sc = new Scanner(System.in);
			        int projChoice = sc.nextInt();
			        sc.nextLine();

			        if (projChoice < 1 || projChoice > filtered.size()) {
			            System.out.println("Invalid project choice.");
			        } else {
			            TemplateProject selectedProject = filtered.get(projChoice - 1);
			            ah.applyForProject(applicant, selectedProject, sc);
			        }
			    }
			}
				
			case 3 -> ah.viewAppliedProject(applicant);
			
			case 4 -> ah.bookFlat(applicant, sc);
				
			case 5 -> {
				if (applicant.hasBookedFlat() == false) {
					ah.withdrawApplicationBeforeApproval(applicant);
				}
				else {
					ah.withdrawApplicationAfterApproval(applicant);
				}
			}
				
			case 6 -> eh.submitEnquiry(applicant, sc);
				
			case 7 -> eh.displayAndManageUserEnquiries(applicant);

			case 8 -> changePassword();
			
			case 9 -> {
		        if (applicant.getEligibilityStatus() == 0) {
		            System.out.println("You are not eligible to use project filters.");
		            return;
		        }
		        UserFilter filter = pm.getFilterForUser(applicant.getName());
		        filter.promptForFilters(applicant.getName(), applicant, pm.getTemplateProjects());
			}
			
			case 10 -> exit = true;
			
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

	public void changePassword() {
        boolean exit = false;
        while (!exit) {
            System.out.println("At any point, input [0] to exit.");
            System.out.print("Enter new password: ");
            String firstEntry = sc.nextLine();
            if (firstEntry.equals("0")) {
                break;
            }
            System.out.print("Enter new password again: ");
            String secondEntry = sc.nextLine();
            if (secondEntry.equals("0")) {
                break;
            }
            else if (!(firstEntry.equals(secondEntry))) {
                System.out.println("ERROR: passwords do not match. Try again.");
            }
            else if (firstEntry.equals(secondEntry)) {
                applicant.changePassword(secondEntry);
                System.out.println("\nPassword successfully changed!\n");
                exit = true;
            }
        }
    }
}
	