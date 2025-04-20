import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManagerPortal {
    private Manager manager;
    private ProjectManager projectManager;
    private ApplicationHandler applicationHandler;
    private EnquiryHandler enquiryHandler;

    Scanner scanner = new Scanner(System.in);

    public ManagerPortal(Manager manager, ProjectManager projectManager, ApplicationHandler appHandler, EnquiryHandler enquiryHandler) {
        this.manager = manager;
        this.projectManager = projectManager;
        this.applicationHandler = appHandler;
        this.enquiryHandler = enquiryHandler; 
    }

    public void showManagerOptions() {
        System.out.println("Welcome, Manager: " + manager.getName());
        System.out.println("1. Create Projects");
        System.out.println("2. Edit Projects");
        System.out.println("3. Delete Projects");
        System.out.println("4. View all Projects");
        System.out.println("5. View your own Projects");
        System.out.println("6. Approve Officer Registrations");
        System.out.println("7. Logout");
        System.out.print("Enter your choice: ");
    }

    public void portal() {
    	
        boolean exit = false;
        do {
        	showManagerOptions();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createProj();
                case 2 -> editProj();
                case 3 -> deleteProj();
                case 4 -> viewAllProj();
                case 5 -> filteredProj();
                case 6 -> manageOfficerApplications();
                case 7 -> {
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

        System.out.print("Enter number of Officer slots: ");
        int numOfficers = scanner.nextInt();

        System.out.print("Set project visibility (true/false): ");
        boolean visibility = scanner.nextBoolean();
        scanner.nextLine(); 

        TemplateProject project = new TemplateProject(name, neighborhood, type1, nType1, price1, type2, nType2, price2, openDate, closeDate, manager.getName(), numOfficers, visibility);

        projectManager.getTemplateProjects().add(project);

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
                    project.setOpenDate(scanner.nextLine());
                }
                case 10 -> {
                    System.out.print("Enter new Close Date (yyyy-mm-dd): ");
                    project.setCloseDate(scanner.nextLine());
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
        List<TemplateProject> templates = projectManager.getTemplateProjects();

        if (templates.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        System.out.println("=== ALL PROJECTS ===");
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
                p.displayProjectDetails();
            }
        }

        if (filtered.isEmpty()) {
            System.out.println("No managed projects.");
        }

        return filtered;
    }

    public void manageOfficerApplications() {
        List<TemplateProject> myProjects = filteredProj();

        if (myProjects.isEmpty()) return;

        System.out.println("Select a project to review officer applications:");
        for (int i = 0; i < myProjects.size(); i++) {
            System.out.println((i + 1) + ") " + myProjects.get(i).getName());
        }

        int projChoice = scanner.nextInt();
        scanner.nextLine();

        if (projChoice < 1 || projChoice > myProjects.size()) return;

        TemplateProject selectedProject = myProjects.get(projChoice - 1);
        List<String> pending = selectedProject.getPendingOfficers();

        if (pending.isEmpty()) {
            System.out.println("No pending officers.");
            return;
        }

        while (true) {
            System.out.println("\nPending Officers:");
            for (int i = 0; i < pending.size(); i++) {
                System.out.println((i + 1) + ") " + pending.get(i));
            }

            System.out.print("Enter officer number to review (0 to exit): ");
            int officerChoice = scanner.nextInt();
            scanner.nextLine();

            if (officerChoice == 0) break;
            if (officerChoice < 1 || officerChoice > pending.size()) {
                System.out.println("Invalid selection.");
                continue;
            }

            String officerName = pending.get(officerChoice - 1);

            System.out.print("Approve (A) or Reject (R) this officer? ");
            String decision = scanner.nextLine().trim().toUpperCase();

            if (decision.equals("A")) {
            	if (selectedProject.getRemainingOfficerSlots() <= 0) {
                    System.out.println("No remaining officer slots for this project.");
                } 
                else {
                    selectedProject.getApprovedOfficers().add(officerName);
                    pending.remove(officerName);
                    System.out.println("Officer " + officerName + " approved.");
                }
            }
            else if (decision.equals("R")) {
                pending.remove(officerName);
                System.out.println("Officer " + officerName + " rejected.");
            } else {
                System.out.println("Invalid choice. Try again.");
            }

            if (pending.isEmpty()) {
                System.out.println("No more pending officers.");
                break;
            }
        }
    }
    
    // Not very sure if it will work cause even if applicant send a request, i cant approve or reject it
    // because the session for Applicant will end once he logs out. 
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
}
