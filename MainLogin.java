import java.util.*;

public class MainLogin {

    public static void main(String[] args) {
        UserRepository userRepo = new UserRepository();
        userRepo.loadUsers("ApplicantList.csv", "applicant");
        userRepo.loadUsers("OfficerList.csv", "officer");
        userRepo.loadUsers("ManagerList.csv", "manager");

        // GLOBAL DATA STRUCTURES //
        List<TemplateProject> templateProjects = new ArrayList<>();
        Map<String, LiveProject> liveProjects = new HashMap<>();
        Map<Applicant, TemplateProject> projectsPendingApproval = new HashMap<>();
        Map<Applicant, TemplateProject> approvedProjects = new HashMap<>();
        Map<Applicant, TemplateProject> rejectedProjects = new HashMap<>();
        Map<Applicant, BookingRequest> bookingsPendingApproval = new HashMap<>(); 
        Map<Applicant, TemplateProject> withdrawalsPendingApproval = new HashMap<>();

        // GLOBAL HANDLER CLASSES //
        ProjectManager pm = new ProjectManager(templateProjects, liveProjects);
        pm.loadTemplateProjects("ProjectList.csv");
	    ApplicationHandler ah = new ApplicationHandler(pm, userRepo, projectsPendingApproval, approvedProjects, rejectedProjects,
        bookingsPendingApproval, withdrawalsPendingApproval);
	    EnquiryHandler eh = new EnquiryHandler(pm);
        
        // System.out.println("Total users loaded: " + userRepo.getAllApplicants().size()); (DEBUG STATEMENT)
        
        Scanner sc = new Scanner(System.in);
        User user;

        System.out.println("System starting...");
        System.out.println();
        System.out.println("┌" + "─".repeat(42) + "┐");
        System.out.println("│                                          │");
        System.out.println("│    ██████    ███      ███    ██████      │");
        System.out.println("│    ██    ██ ██ ██    ██ ██ ██            │");
        System.out.println("│    ██████   ██  ██  ██  ██   █████       │");
        System.out.println("│    ██    ██ ██   ████   ██        ██     │");
        System.out.println("│    ██████   ██    ██    ██  ██████       │");
        System.out.println("│                                          │");
        System.out.println("│  Welcome to HDB Flat Management System   │");
        System.out.println("│                                          │");
        System.out.println("│                 SC2002                   │");
        System.out.println("│                 FCSA                     │");
        System.out.println("│                 TEAM 3                   │");
        System.out.println("└" + "─".repeat(42) + "┘");
        System.out.println();

        // AUTO-ASSIGN OFFICERS TO PROJECTS BASED ON PROVIDED DATA //
        for (TemplateProject t : pm.getTemplateProjects()) {
            for (Officer o : userRepo.getAllOfficers()) {
                if (t.getApprovedOfficers().contains(o.getName())) {
                    o.setAssignedProjectAsOfficer(t);
                }
                else if (t.getPendingOfficers().contains(o.getName())) {
                    o.setAppliedProjectAsOfficer(t);
                }
            }
        }

        // AUTO-ASSIGN MANAGERS TO THEIR RESPECTIVE PROJECTS BASED ON PROVIDED DATA //
        for (TemplateProject t : pm.getTemplateProjects()) {
            for (Manager m : userRepo.getAllManagers()) {
                if (t.getManagerName().equals(m.getName())) {
                    m.setProjectInCharge(t);
                }
            }
        }
        
        // DEBUG STATEMENT //
        /* for (Officer o: userRepo.getAllOfficers()) {
            if (o.getAssignedProjectAsOfficer() == null && o.getAppliedProjectAsOfficer() != null) { // applied but not approved
                System.out.printf("%s : %s", o.getName(), o.getAppliedProjectAsOfficer());
            }
            else if (o.getAssignedProjectAsOfficer() != null && o.getAppliedProjectAsOfficer() == null) { // approved and not applied
                System.out.printf("%s : %s", o.getName(), o.getAssignedProjectAsOfficer());
            }
        } */

        boolean quitProgram = false;

        while (!quitProgram) {

            user = userRepo.login(userRepo.getAllUsers(), sc);

            if (user == null) {
                System.out.println("Try again.");
            }

            else if (user instanceof Officer officer) {
                OfficerPortal offPortal = new OfficerPortal(officer, pm, ah, eh);
                offPortal.portal();
            }   

            else if (user instanceof Applicant applicant) {
                ApplicantPortal appPortal = new ApplicantPortal(applicant, pm, ah, eh);
                appPortal.portal();
            }

            else if (user instanceof Manager manager) {
                ManagerPortal manPortal = new ManagerPortal(manager, pm, ah, eh, sc);
                manPortal.portal();
            }

            System.out.println("\nSuccessfully logged out!\n");
            System.out.println("======================");
            System.out.println("[Any key] Log in again");
            System.out.println("[E] Exit program");
            System.out.println("======================");
            System.out.print("Enter your choice: ");
            String reLogIn = sc.nextLine().toLowerCase();

            if (reLogIn.equals("e")) {
                quitProgram = true;
                pm.saveTemplateProjectsToFile("ProjectList.csv");
              
                userRepo.saveAllUsers();
            }
            else {
                System.out.println("\nNew login...\n");
            }
        }
    }
}

