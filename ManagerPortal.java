import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManagerPortal implements PortalInterface{
    private Manager manager;
    private ProjectManager projectManager;
    private ApplicationHandler applicationHandler;
    private EnquiryHandler enquiryHandler;
    private Scanner sc;
    private UserFilter managerFilter = new UserFilter();

    Scanner scanner = new Scanner(System.in);

    public ManagerPortal(Manager manager, ProjectManager projectManager, ApplicationHandler appHandler, EnquiryHandler enquiryHandler,
    Scanner sc) {
        this.manager = manager;
        this.projectManager = projectManager;
        this.applicationHandler = appHandler;
        this.enquiryHandler = enquiryHandler; 
        this.sc = sc;
    }

    public void showOptions() {
        System.out.println("Welcome, Manager: " + manager.getName());
        System.out.println("[1] Create Projects");
        System.out.println("[2] Edit Projects");
        System.out.println("[3] Delete Projects");
        System.out.println("[4] View all Projects");
        System.out.println("[5] View your own Projects");
        System.out.println("[6] Approve Officer Registrations");
        System.out.println("[7] Approve/Reject applications");
        System.out.println("[8] Approve/reject withdrawal requests");
        System.out.println("[9] Generate a report of all active bookings");
        System.out.println("[10] Reply/View enquiries");
        System.out.println("[11] Change password");
        System.out.println("[12] Change Filter Settings");
        System.out.println("[13] Logout");
        System.out.print("Enter your choice: ");
    }

    public void portal() {
    	
        boolean exit = false;
        do {
        	showOptions();
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from 1 to 13.");
                continue; 
            }

            switch (choice) {
                case 1 -> createProj();
                case 2 -> editProj();
                case 3 -> deleteProj();
                case 4 -> viewAllProj();
                case 5 -> filteredProj();
                case 6 -> manageOfficerApplications();
                case 7 -> applicationHandler.approveApplication(sc, manager);
                case 8 -> applicationHandler.approveWithdrawals(sc);
                case 9 -> projectManager.generateReport();
                case 10 -> {
                    System.out.println("1. View enquiries of ALL projects");
                    System.out.println("2. View and reply to enquiries regarding YOUR projects");
                    System.out.print("Enter your choice: ");
                    int subChoice = sc.nextInt();
                    sc.nextLine();

                    if (subChoice == 1) {
                        enquiryHandler.showAllEnquiries();
                    } 
                    else if (subChoice == 2) {
                        enquiryHandler.replyToEnquiriesManager(manager, filteredProj(), sc);
                    } 
                    else {
                        System.out.println("Invalid choice.");
                    }
                }  
                case 11 -> changePassword();
                case 12 -> managerFilter.promptForFilters(manager.getName(), manager, projectManager.getTemplateProjects());
                case 13 -> {
                    System.out.println("Logging out...");
                    exit = true;
                }
                default -> System.out.println("Invalid choice. Please enter a number from 1 to 7.");
            }
        } while (!exit);
    }

    public void createProj() {
        System.out.print("Enter Project Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Neighbourhood: ");
        String neighborhood = scanner.nextLine();

        System.out.print("Enter Flat Type 1: ");
        String type1 = scanner.nextLine();

        System.out.print("Enter number of units for Flat Type 1: ");
        int nType1 = scanner.nextInt();

        System.out.print("Enter selling price for Flat Type 1: ");
        double price1 = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Flat Type 2: ");
        String type2 = scanner.nextLine();

        System.out.print("Enter number of units for Flat Type 2: ");
        int nType2 = scanner.nextInt();

        System.out.print("Enter selling price for Flat Type 2: ");
        double price2 = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Application Opening Date (yyyy-mm-dd): ");
        String openDate = scanner.nextLine();

        System.out.print("Enter Application Closing Date (yyyy-mm-dd): ");
        String closeDate = scanner.nextLine();
        
        if (checkDateConflict(openDate, closeDate, null)) {
            System.out.println("You are already managing a project during this application period.");
            return;
        }

        System.out.print("Enter number of Officer slots: ");
        int numOfficers = scanner.nextInt();

        System.out.print("Set project visibility (true/false): ");
        boolean visibility = scanner.nextBoolean();
        scanner.nextLine(); 

        TemplateProject project = new TemplateProject(name, neighborhood, type1, nType1, price1, type2, nType2, price2, openDate, closeDate, manager.getName(), numOfficers, visibility);

        projectManager.getTemplateProjects().add(project);

        manager.setProjectInCharge(project); // sets manager to be in charge of their own project

        System.out.println("Project successfully created.");
    }

    public void editProj() {
        List<TemplateProject> myProjects = filteredProj(); 

        if (myProjects.isEmpty()) {
            System.out.println("You have no projects to edit.");
            return;
        }

        System.out.println("Select a project to edit:");
        for (int i = 0; i < myProjects.size(); i++) {
            System.out.println((i + 1) + ") " + myProjects.get(i).getName());
        }

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > myProjects.size()) {
            System.out.println("Invalid project selection.");
            return;
        }

        TemplateProject project = myProjects.get(choice - 1);
        boolean editing = true;

        while (editing) {
            System.out.println("\nEditing project: " + project.getName());
            System.out.println("1. Project Name");
            System.out.println("2. Neighborhood");
            System.out.println("3. Flat Type 1");
            System.out.println("4. Units of Type 1");
            System.out.println("5. Price of Type 1");
            System.out.println("6. Flat Type 2");
            System.out.println("7. Units of Type 2");
            System.out.println("8. Price of Type 2");
            System.out.println("9. Open Date");
            System.out.println("10. Close Date");
            System.out.println("11. Officer Slots");
            System.out.println("12. Visibility");
            System.out.println("13. Exit Editing");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    System.out.print("Enter new Project Name: ");
                    project.setName(scanner.nextLine());
                }
                case 2 -> {
                    System.out.print("Enter new Neighborhood: ");
                    project.setNeighbourhood(scanner.nextLine());
                }
                case 3 -> {
                    System.out.print("Enter new Flat Type 1: ");
                    project.setType1(scanner.nextLine());
                }
                case 4 -> {
                    System.out.print("Enter new number of units for Flat Type 1: ");
                    project.setNumOfType1(scanner.nextInt());
                    scanner.nextLine();
                }
                case 5 -> {
                    System.out.print("Enter new price for Flat Type 1: ");
                    project.setType1Price(scanner.nextDouble());
                    scanner.nextLine();
                }
                case 6 -> {
                    System.out.print("Enter new Flat Type 2: ");
                    project.setType2(scanner.nextLine());
                }
                case 7 -> {
                    System.out.print("Enter new number of units for Flat Type 2: ");
                    project.setNumOfType2(scanner.nextInt());
                    scanner.nextLine();
                }
                case 8 -> {
                    System.out.print("Enter new price for Flat Type 2: ");
                    project.setType2Price(scanner.nextDouble());
                    scanner.nextLine();
                }
                case 9 -> {
                    System.out.print("Enter new Open Date (yyyy-mm-dd): ");
                    String newOpen = scanner.nextLine();
                    String currentClose = project.getCloseDate();

                    if (checkDateConflict(newOpen, currentClose, project)) {
                        System.out.println("Date conflict with another project. Change rejected.");
                    } 
                    else {
                        project.setOpenDate(newOpen);
                        System.out.println("Open date updated successfully.");
                    }
                }
                case 10 -> {
                    System.out.print("Enter new Close Date (yyyy-mm-dd): ");
                    String newClose = scanner.nextLine();
                    String currentOpen = project.getOpenDate();

                    if (checkDateConflict(currentOpen, newClose, project)) {
                        System.out.println("Date conflict with another project. Change rejected.");
                    } 
                    else {
                        project.setCloseDate(newClose);
                        System.out.println("Close date updated successfully.");
                    }
                }
                case 11 -> {
                    System.out.print("Enter new number of Officer Slots: ");
                    project.setNumOfficers(scanner.nextInt());
                    scanner.nextLine();
                }
                case 12 -> {
                    System.out.print("Set visibility (true/false): ");
                    project.setVisibility(scanner.nextBoolean());
                    scanner.nextLine();
                }
                case 13 -> editing = false;
                default -> System.out.println("Invalid option.");
            }
        }

        System.out.println("Project updated successfully.");
    }

    public void deleteProj() {
        List<TemplateProject> myProjects = filteredProj();

        if (myProjects.isEmpty()) return;

        System.out.print("Enter which project to delete: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > myProjects.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        TemplateProject projectToDelete = myProjects.get(choice - 1);
        projectManager.getTemplateProjects().remove(projectToDelete); 
    }

    public void viewAllProj() {
        List<TemplateProject> templates = managerFilter.filterProjects(projectManager.getTemplateProjects());

        if (templates.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        System.out.println("=== ALL PROJECTS (Filtered) ===");
        for (TemplateProject p : templates) {
            p.displayProjectDetails();
            System.out.println(); 
        }
    }
    
    public List<TemplateProject> filteredProj() {
        List<TemplateProject> all = projectManager.getTemplateProjects();
        List<TemplateProject> filtered = new ArrayList<>();

        for (TemplateProject p : all) {
            if (p.getManagerName().equalsIgnoreCase(manager.getName())) {
                filtered.add(p);
            }
        }

        List<TemplateProject> result = managerFilter.filterProjects(filtered); // Apply filter

        if (result.isEmpty()) {
            System.out.println("No managed projects match the current filter.");
        } else {
            for (TemplateProject p : result) {
                p.displayProjectDetails();
            }
        }

        return result;
    }


    public void manageOfficerApplications() {
        // SEARCH FOR PROJECT THAT MANAGER IS IN CHARGE OF //
        int indexOfProject = 0;
        List<TemplateProject> copyOfTemplateProjects = projectManager.getTemplateProjects();
        TemplateProject projInCharge = null;
        for (TemplateProject t : copyOfTemplateProjects) {
            if (t.getManagerName().equals(manager.getName())) {
                projInCharge = t;
                break;
            }
            indexOfProject++;
        }
        if (projInCharge != null) {
            List<String> pendingOfficers = projInCharge.getPendingOfficers();
            List<String> approvedOfficers = projInCharge.getApprovedOfficers();
            if (pendingOfficers.isEmpty()) {
                System.out.println("There are no officer applications pending for your project.");
                return;
            }
            System.out.println("These officers are requesting to assist with your project:");
            int i = 1;
            for (String officerName : pendingOfficers) {
                System.out.printf("[%d] %s ", i++, officerName);
            }
            System.out.print("\nEnter number of officer to process: ");
            int officerChoice = sc.nextInt();
            sc.nextLine();
            
            if (officerChoice < 1 || officerChoice > pendingOfficers.size()) {
                System.out.println("\nInvalid selection. Try again.\n");
                return;
            }

            System.out.println("\n[A] Approve | [R] Reject\n");
            System.out.print("Type choice here: ");
            String approval = sc.nextLine();

            if (approval.equalsIgnoreCase("a")) {
            	if (projInCharge.getRemainingOfficerSlots() > 0) {
                    approvedOfficers.add(pendingOfficers.get(officerChoice - 1));
                    pendingOfficers.remove(officerChoice - 1);
                    // UPDATE MAIN LIST //
                    projInCharge.setPendingOfficers(pendingOfficers);
                    projInCharge.setApprovedOfficers(approvedOfficers);
                    copyOfTemplateProjects.set(indexOfProject, projInCharge);
                    projectManager.updateTemplateProjects(copyOfTemplateProjects);
                    // CONFIRM SUCCESS //
                    System.out.println("\nOfficer successfully approved.\n");
            	}
                else {
                    System.out.println("\nApproval failed: No remaining officer slots available.\n");
                }
            }
            else if (approval.equalsIgnoreCase("r")) {
                pendingOfficers.remove(officerChoice - 1);
                // UPDATE MAIN LIST //
                projInCharge.setPendingOfficers(pendingOfficers);
                copyOfTemplateProjects.set(indexOfProject, projInCharge);
                projectManager.updateTemplateProjects(copyOfTemplateProjects);
                // CONFIRM SUCCESS //
                System.out.println("\nOfficer successfully rejected.\n");
            }
            else {
                System.out.println("\nInvalid selection. Try again.\n");
            }
        } 
        else {
            System.out.println("No project found for this manager.");
        }
    }

    public void manageBookingRequests() {
        if (applicationHandler.getBookingsPendingApproval().isEmpty()) {
            System.out.println("There are no booking requests to review.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        List<BookingRequest> requests = new ArrayList<>(applicationHandler.getBookingsPendingApproval().values());

        System.out.println("Pending Booking Requests:");
        for (int i = 0; i < requests.size(); i++) {
            BookingRequest r = requests.get(i);
            Applicant a = r.getApplicant();
            TemplateProject p = r.getTemplateProject();
            System.out.printf("%d) Applicant: %s | Project: %s | Flat Type: %s\n",
                    i + 1,
                    a.getName(),
                    p.getName(),
                    r.getFlatType() == 1 ? "2-Room" : "3-Room");
        }

        System.out.print("Select a request to review (0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > requests.size()) {
            System.out.println("Invalid choice or cancelled.");
            return;
        }

        BookingRequest selected = requests.get(choice - 1);
        Applicant applicant = selected.getApplicant();
        TemplateProject project = selected.getTemplateProject();
        int type = selected.getFlatType();

        if ((type == 1 && project.getNumOfType1() <= 0) || (type == 2 && project.getNumOfType2() <= 0)) {
            System.out.println("No units remaining for this flat type.");
            return;
        }

        System.out.print("Approve (A) or Reject (R) this application? ");
        String decision = scanner.nextLine().trim().toUpperCase();

        if (decision.equals("A")) {
            applicant.setApplicationStatus("Approved");
            applicant.setBookedFlat(true);

            if (type == 1) {
                project.setNumOfType1(project.getNumOfType1() - 1);
            } else {
                project.setNumOfType2(project.getNumOfType2() - 1);
            }

            applicationHandler.getBookingsPendingApproval().remove(applicant);
            System.out.println("Booking approved.");

        } else if (decision.equals("R")) {
            applicant.setApplicationStatus("Unsuccessful");
            applicationHandler.getBookingsPendingApproval().remove(applicant);
            System.out.println("Booking rejected.");
        } else {
            System.out.println("Invalid input. No action taken.");
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
                manager.changePassword(secondEntry);
                System.out.println("\nPassword successfully changed!\n");
                exit = true;
            }
        }
    }
    private boolean checkDateConflict(String newOpenStr, String newCloseStr, TemplateProject currentProject) {
        try {
            LocalDate newOpen = LocalDate.parse(newOpenStr.trim());
            LocalDate newClose = LocalDate.parse(newCloseStr.trim());

            for (TemplateProject p : projectManager.getTemplateProjects()) {
                 //Only check against other projects managed by the same manager
                if (!p.getManagerName().equals(manager.getName())) {
                	continue;
                }

                //If editing an existing project, skip checking against itself
                //check only other projects that is not currently editing
                if (currentProject != null && p.equals(currentProject)) continue;

                LocalDate existingOpen = LocalDate.parse(p.getOpenDate().trim());
                LocalDate existingClose = LocalDate.parse(p.getCloseDate().trim());

                //Overlap occurs if the new project period intersects with an existing one
                boolean overlaps = !(newClose.isBefore(existingOpen) || newOpen.isAfter(existingClose));

                if (overlaps) {
                    return true; //Conflict found
                }
            }
        } 
        catch (Exception e) {
            System.out.println("Invalid date format detected during conflict check.");
            return true;//if got invalid dates like 35 of may
        }

        return false; //No conflicts
    }
}



