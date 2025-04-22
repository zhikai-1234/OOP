import java.util.*;

public class OfficerPortal extends ApplicantPortal {

    private Applicant applicant;
	private Officer officer;
    private ApplicationHandler appHandler;
    private boolean asApplicant;

    private ProjectManager pm;
    private ApplicationHandler ah;
    private EnquiryHandler eh;

    Scanner sc = new Scanner(System.in);

	public OfficerPortal(Officer o, ProjectManager pm, ApplicationHandler ah, EnquiryHandler eh) {
		super(o, pm, ah, eh);
        this.officer = o;
        this.pm = pm;
        this.ah = ah;
        this.eh = eh;
        this.asApplicant = false; // set default intention to apply as applicant as false
	}

	@Override
	public void showOptions() {
		System.out.println("================APPLICANT OPTIONS================");
		super.showOptions();		
	}

    public void showOfficerOptions() {
        System.out.println("=================OFFICER OPTIONS=================");
        System.out.println("\nWelcome, " + officer.getName() + "\n");
        System.out.println("[1] Register to join a project as an officer");
        System.out.println("[2] See officer registration status");
        System.out.println("[3] Apply for another project that you are not handling");
        System.out.println("[4] View/reply to enquiries regarding the project you are handling");
        System.out.println("[5] Officially book a flat for a successful applicant");
        System.out.println("[6] Generate a receipt for an applicant's successful booking");
        System.out.println("[7] Change password");
        System.out.println("[8] Change Filter Settings");
        System.out.println("[9] Exit Officer Menu");
    }

    @Override
    public void portal() {
        boolean exit = false;
        do {
            System.out.println("\nAre you logging in as an applicant or an officer today?");
            System.out.println("[1] Applicant");
            System.out.println("[2] Officer");
            System.out.println("[3] Log out");
            System.out.print("Enter your choice: ");
            int roleChoice;
            while (true) {
                System.out.print("Enter your choice: ");
                if (sc.hasNextInt()) {
                    roleChoice = sc.nextInt();
                    sc.nextLine(); // consume the newline
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); // consume the invalid input
                }
            }


            switch (roleChoice) {
                case 1 -> super.portal(); 
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
                UserFilter filter = pm.getFilterForUser(officer.getName());
                List<TemplateProject> filtered = filter.filterProjects(pm.getTemplateProjects());

                if (filtered.isEmpty()) {
                    System.out.println("No matching projects found with the current filter settings.");
                } else {
                    int i = 1;
                    for (TemplateProject p : filtered) {
                        System.out.printf("Project %d\n", i);
                        pm.display2and3RoomProjectDetails(p);
                        i++;
                    }

                    System.out.print("Enter number of project you wish to register for: ");
                    int projChoice = sc.nextInt();
                    sc.nextLine();

                    if (projChoice < 1 || projChoice > filtered.size()) {
                        System.out.println("Invalid project choice.");
                    } else {
                        registerAsOfficer(filtered.get(projChoice - 1));
                    }
                }
            }

                case 2 -> {
                    if (officer.getAppliedProjectAsOfficer() == null) {
                        System.out.println("ERROR: You have not registered for any project as an officer.");
                    }
                    else {
                        viewRegistrationStatus(officer.getAppliedProjectAsOfficer());
                    }
                }

                case 3 -> {
                    List<TemplateProject> allProjects = pm.getTemplateProjects();
                    List<TemplateProject> eligibleProjects = new ArrayList<>();

                    for (TemplateProject project : allProjects) {
                        boolean isHandling = project.getApprovedOfficers().contains(officer.getName()) 
                                            || project.getPendingOfficers().contains(officer.getName());
                        if (!isHandling) {
                            eligibleProjects.add(project);
                        }
                     }
                    if (eligibleProjects.isEmpty()) {
                        System.out.println("You are not eligible to apply for any other projects.");
                    } 
                    else { System.out.println("\nAvailable Projects:");
                        int i = 1;
                        for (TemplateProject p : eligibleProjects) {
                            System.out.printf("[%d] %s - %s\n", i, p.getName(), p.getNeighbourhood());
                            i++;
                        }
                
                        System.out.print("Select project to apply as applicant: ");
                        int projChoice = sc.nextInt();
                        sc.nextLine();
                
                        if (projChoice < 1 || projChoice > eligibleProjects.size()) {
                            System.out.println("Invalid selection");
                        }
                        else {
                            TemplateProject selectedProject = eligibleProjects.get(projChoice - 1);
                            
                            // Verify officer meets applicant requirements
                            if (officer.getEligibilityStatus() > 0) {
                                ah.applyForProject(officer, selectedProject, sc);
                            } else {
                                System.out.println("You do not meet applicant eligibility requirements");
                            }
                        }
                    }
                }

                case 4 -> {
                    System.out.println(officer.getAssignedProjectAsOfficer().getEnquiries());
                    eh.replyToEnquiriesOfficer(officer, officer.getAssignedProjectAsOfficer(), sc);
                }

                case 5 -> {
                    UserRepository userRepo = new UserRepository();
                    System.out.println("Enter the UserID of the applicant to book a flat for: ");
                    String id = sc.nextLine();
                    updateApprovedBooking(userRepo.getApplicantByUserID(id));
                }

                case 6 -> {
                    UserRepository userRepo = new UserRepository();
                    System.out.println("Enter the UserID of the applicant to generate a receipt for: ");
                    String id = sc.nextLine();
                    System.out.println("Generating receipt for successful booking...");
                    generateReceipt(userRepo.getApplicantByUserID(id));
                }

                case 7 -> changePassword();
                
                case 8 -> {
                    UserFilter filter = pm.getFilterForUser(officer.getName());
                    filter.promptForFilters(officer.getName(), officer, pm.getTemplateProjects());
                }

                case 9 -> {
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
            officer.setAppliedProjectAsOfficer(p);
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
            p.getVisibility(),
            a
        );

        // Update unit availability
        if(flatType == 1 && liveProject.getNumOfType1() > 0) {
            p.setNumOfType1(liveProject.getNumOfType1() - 1); // keep a record of remaining available flats in the template project
        } else if(flatType == 2 && liveProject.getNumOfType2() > 0) {
            p.setNumOfType2(liveProject.getNumOfType2() - 1); // keep a record of remaining available flats in the template project
        } else {
            System.out.println("No units available");
            return;
        }

        // Update applicant records
        a.setProjApplied(liveProject);
        a.setApplicationStatus("Booked");
        a.setBookedFlat(true);
        a.setBookedFlatType(flatType);
        
        // Update project manager
        pm.addLiveProject(a, liveProject);
        
        ah.removeBookingsPendingApproval(a);
    }

    public void generateReceipt(Applicant a) {
        System.out.println("=======================================");
        System.out.println("\nRECEIPT FOR SUCCESSFUL BOOKING\n");
        System.out.println("Applicant's name: " + a.getName());
        System.out.println("Applicant's NRIC: " + a.getUserID());
        System.out.println("Applicant's Age: " + a.getAge());
        System.out.println("Applicant's Marital Status: " + a.getMaritalStatus());
        System.out.println();
        System.out.printf("Flat Type Booked: %d-Room Flat\n", a.getBookedFlatType() + 1);
        System.out.println("\nPROJECT DETAILS:\n");
        TemplateProject applicantProj = a.getProjApplied();
        System.out.println("Project Name: " + applicantProj.getName());
        System.out.println("Neighbourhood: " + applicantProj.getNeighbourhood());
        if (a.getBookedFlatType() == 1) {
            System.out.printf("Unit Price: %.2f\n" + applicantProj.getType1Price());
        }
        else if (a.getBookedFlatType() == 2) {
            System.out.printf("Unit Price: %.2f\n" + applicantProj.getType2Price());
        }
        System.out.println("Name of Manager: " + applicantProj.getManagerName());
        System.out.println("List of approved officers:");
        for (String officerName : applicantProj.getApprovedOfficers()) {
            System.out.println(officerName);
        }
    }
}
