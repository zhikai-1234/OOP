import java.util.Scanner;

public class MainLogin {

    public static void main(String[] args) {
        UserRepository userRepo = new UserRepository();
        userRepo.loadUsers("ApplicantList.csv", "applicant");
        userRepo.loadUsers("OfficerList.csv", "officer");
        userRepo.loadUsers("ManagerList.csv", "manager");

        ProjectManager pm = new ProjectManager();
	    ApplicationHandler ah = new ApplicationHandler();
	    EnquiryHandler eh = new EnquiryHandler();
        
        System.out.println("Total users loaded: " + userRepo.getAllApplicants().size());
        
        Scanner sc = new Scanner(System.in);
        User user = null;

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
                ManagerPortal manPortal = new ManagerPortal(manager, pm, eh);
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
            }
            else {
                System.out.println("\nNew login...\n");
            }
        }
    }
}

