import java.util.Scanner;

public class MainLogin {

    public static void main(String[] args) {
        UserRepository userRepo = new UserRepository();
        userRepo.loadUsers("ApplicantList.csv", "applicant");
        userRepo.loadUsers("OfficerList.csv", "officer");
        
        System.out.println("Total users loaded: " + userRepo.getAllApplicants().size());
        
        Scanner sc = new Scanner(System.in);
        User user = null;

        while (user == null) {
            user = userRepo.login(userRepo.getAllUsers(), sc);
            
            if (user == null) {
                System.out.println("Try again.");
            }
        }

            if (user instanceof Applicant applicant) {
                ApplicantPortal appPortal = new ApplicantPortal(applicant);
                appPortal.portal();
            }

            else if (user instanceof Officer officer) {
                OfficerPortal offPortal = new OfficerPortal(officer);
                offPortal.portal();
            }

            // future: add manager case
    }
}

