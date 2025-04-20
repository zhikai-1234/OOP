import java.util.*;

public class OfficerPortal extends ApplicantPortal {

    private Applicant applicant;
	private List<TemplateProject> templateProjects;
	private Officer officer;
    private ApplicationHandler appHandler;
    private boolean asApplicant;

    Scanner sc = new Scanner(System.in);

	public OfficerPortal(Officer o) {
		super(o);
        this.officer = o;
        pm.loadTemplateProjects("ProjectList.csv");
        this.templateProjects = pm.getTemplateProjects();
        this.asApplicant = false; // set default intention to apply as applicant as false
	}

	@Override
	public void showOptions() {
		System.out.println("================APPLICANT OPTIONS================");
		super.showOptions();		
	}

    public void showOfficerOptions() {
        System.out.println("=================OFFICER OPTIONS=================");
        System.out.println();
        System.out.println("=================================================");
        System.out.println("1. Register to join a project as an officer");
        System.out.println("2. See officer registration status");
        System.out.println("3. Apply for another project that you are not handling");
        System.out.println("4. View/reply to enquiries regarding the project you are handling");
        System.out.println("5. Officially book a flat for a successful applicant");
        System.out.println("6. Generate a receipt for an applicant's successful booking");
        System.out.println("7. Quit");
    }

    @Override
    public void portal() {
        boolean exit = false;
        do {
            System.out.println("\nAre you logging in as an applicant or an officer today?");
            System.out.println("[1] Applicant");
            System.out.println("[2] Officer");
            System.out.println("[3] Exit");
            System.out.print("Enter your choice: ");
            int roleChoice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (roleChoice) {
                case 1 -> super.portal(); // Applicant menu
                case 2 -> handleOfficerActions();
                case 3 -> exit = true;
                default -> System.out.println("Invalid choice.");
            }
        } while (!exit);
    }

    private void handleOfficerActions() {
        boolean officerExit = false;
        do {
            showOfficerOptions();
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch(choice) {

                case 1 -> {
                    int i = 1;
                    for (TemplateProject p : templateProjects) {
                        System.out.printf("Project %d\n", i);
                        pm.display2and3RoomProjectDetails(p);
                    }
                    System.out.print("Enter number of project you wish to register for: ");
                    int projChoice = sc.nextInt();
                    sc.nextLine();
                    registerAsOfficer(templateProjects.get(projChoice - 1));
                }

                case 2 -> {
                    if (officer.getAppliedProject() == null) {
                        System.out.println("ERROR: You have not registered for any project as an officer.");
                    }
                    else {
                        viewRegistrationStatus(officer.getAppliedProject());
                    }
                }

                case 3 -> {
                    System.out.println("Feature currently unavailable...");
                }

                case 4 -> {
                    eh.replyToEnquiriesOfficer(officer, officer.getAssignedProject(), sc);
                }

                case 5 -> {
                    System.out.println("Feature currently unavailable...");
                }

                case 6 -> {
                    System.out.println("Feature currently unavailable...");
                }

                case 7 -> {
                     officerExit = true;
                }
                default -> System.out.println("Invalid selection. Please try again.");
            }
        }while (!officerExit);
} 




    public void registerAsOfficer(TemplateProject p) {
        if (p.getPendingOfficers().contains(officer.getName())) {
            System.out.printf("Your officer registration for %s is pending approval.\n", p.getName());
        }
        else if (p.getApprovedOfficers().contains(officer.getName())) {
            System.out.printf("You are already an approved officer for %s.\n", p.getName());
        }
        else {
            List<String> pendingOfficersCopy = p.getPendingOfficers();
            pendingOfficersCopy.add(officer.getName());
            p.setPendingOfficers(pendingOfficersCopy);
            officer.setOfficerRegistrationStatus("Applied");
            officer.setAppliedProject(p);
            System.out.printf("Successfully applied to be an officer for %s.\n", p.getName());
        }
    }

    public void viewRegistrationStatus(TemplateProject p) {
        if (p.getPendingOfficers().contains(officer.getName())) {
            System.out.printf("Your officer registration for %s is pending approval.\n", p.getName());
        }
        else if (p.getApprovedOfficers().contains(officer.getName())) {
            System.out.printf("You are already an approved officer for %s.\n", p.getName());
        }
        else {
            System.out.println("You did not register to be part of any project.");
        }
    }

	public void updateApprovedBooking(Applicant a) {
        BookingRequest request = ah.getBookingsPendingApproval().get(a);
        if(request == null) return;

        TemplateProject p = request.getTemplateProject();
        int flatType = request.getFlatType();

        // Convert TemplateProject to LiveProject
        LiveProject liveProject = new LiveProject (
            p.getName(),
            p.getNeighbourhood(),
            p.getType1(),
            p.getNumOfType1(),
            p.getType1Price(),
            p.getType2(),
            p.getNumOfType2(),
            p.getType2Price(),
            p.getOpenDate(),
            p.getCloseDate(),
            p.getManagerName(),
            p.getNumOfficers(),
            p.getVisibility()
        );

        // Update unit availability
        if(flatType == 1 && liveProject.getnType1() > 0) {
            p.setNumOfType1(liveProject.getnType1() - 1); // keep a record of remaining available flats in the template project
        } else if(flatType == 2 && liveProject.getnType2() > 0) {
            p.setNumOfType2(liveProject.getnType2() - 1); // keep a record of remaining available flats in the template project
        } else {
            System.out.println("No units available");
            return;
        }

        // Update applicant records
        a.setProjApplied(liveProject);
        a.setApplicationStatus("Flat Booked");
        a.setBookedFlat(true);
        
        // Update project manager
        ProjectManager pm = new ProjectManager();
        pm.addLiveProject(liveProject);
        
        ah.removeBookingsPendingApproval(a);
    }

}
