import java.util.Scanner;

public class MainLogin {

public static void main(String[] args) {
    UserRepository userRepo = new UserRepository();
    userRepo.loadUsers("ApplicantList.csv", "applicant");
    
    System.out.println("Total users loaded: " + userRepo.getAllApplicants().size());
    
    Scanner sc = new Scanner(System.in);
    User user = null;

    while (user == null) {
        // Explicitly flush before login attempt
        System.out.flush();
        user = userRepo.login(userRepo.getAllApplicants(), sc);
        
        if (user == null) {
            System.out.println("Try again.");
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

