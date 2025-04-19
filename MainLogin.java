import java.util.Scanner;

public class MainLogin {

public static void main(String[] args) {
    UserRepository userRepo = new UserRepository();
    userRepo.loadUsers("ApplicantList.csv", "applicant");
    
    // Print user count with two newlines after
    System.out.println("Total users loaded: " + userRepo.getAllApplicants().size() + "\n");
    
    Scanner sc = new Scanner(System.in);
    User user = null;

    while (user == null) {
        // Explicitly flush before login attempt
        System.out.flush();
        user = userRepo.login(userRepo.getAllApplicants());
        
        if (user == null) {
            System.out.println("\nTry again.\n");
        }
    }

        if (user instanceof Applicant applicant) {
            ApplicantPortal appPortal = new ApplicantPortal(applicant);
            appPortal.showOptions();
            appPortal.portal();
        }

        // future: add officer / manager cases
    }
}

