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
			int choice;
			try {
			    choice = Integer.parseInt(sc.nextLine().trim());
			} 
			catch (NumberFormatException e) {
			    System.out.println("Invalid input. Enter Number Only");
			    continue;
			}

			switch (choice) {
			
			case 1 -> {
			    if (applicant.getEligibilityStatus() == 0) {
			        System.out.println("ERROR: Applicant not eligible for any projects.");
			        return;
			    }

			    UserFilter filter = pm.getFilterForUser(applicant.getName());
			    List<TemplateProject> filtered = filter.filterProjects(pm.getTemplateProjects());

			    boolean found = false;
			    for (TemplateProject p : filtered) {
			        if (!p.isWithinApplicationPeriod()) {
			        	continue; //Skip all the closed projects
			        }
			        if (applicant.getMaritalStatus().equals("Single")) {
			            pm.display2RoomProjectDetails(p);
			        } else {
			            pm.display2and3RoomProjectDetails(p);
			        }
			        found = true;
			    }

			    if (!found) {
			        System.out.println("No open projects found");
			    }
			}
			
			case 2 -> {
			    if (applicant.getEligibilityStatus() == 0) {
			        System.out.println("ERROR: Applicant not eligible to apply for projects.");
			        return;
			    } 

			    UserFilter filter = pm.getFilterForUser(applicant.getName());
			    List<TemplateProject> filtered = filter.filterProjects(pm.getTemplateProjects());

			    List<TemplateProject> openProjects = new ArrayList<>();
			    for (TemplateProject p : filtered) {
			        if (p.isWithinApplicationPeriod()) {
			            openProjects.add(p);
			        }
			    }

			    if (openProjects.isEmpty()) {
			        System.out.println("No matching open projects available to apply.");
			    } 
			    else {
			        int i = 1;
			        for (TemplateProject p : openProjects) {
			            System.out.printf("\nProject %d\n", i);
			            if (applicant.getMaritalStatus().equals("Single")) {
			                pm.display2RoomProjectDetails(p);
			            }
			            else {
			                pm.display2and3RoomProjectDetails(p);
			            }
			            i++;
			        }

			        System.out.println("Choose a project to apply for:");
			        int projChoice;
			        try {
			            projChoice = Integer.parseInt(sc.nextLine().trim());
			        } 
			        catch (NumberFormatException e) {
			            System.out.println("Invalid input. Enter Number Only");
			            return;
			        }

			        if (projChoice < 1 || projChoice > openProjects.size()) {
			            System.out.println("Invalid project choice.");
			        } 
			        else {
			            TemplateProject selectedProject = openProjects.get(projChoice - 1);
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
			
			//We Should still allow applicants to enquire details of projects that are
			//either not open for application yet or no visibility so that they can still ask
			//like when open? etc etc
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
	