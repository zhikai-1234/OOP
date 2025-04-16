import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainLogin {

    public static void main(String[] args) {
        BTOSystem btosys = new BTOSystem();
        
        List<Project> projects = new ArrayList<>();
        
        projects = btosys.loadProjectsFromFile("ProjectList.csv");

        Scanner sc = new Scanner(System.in);

        do {
            User user = btosys.login();

            if (user instanceof Applicant applicant) {
            	ApplicantPortal appPortal = new ApplicantPortal(applicant, projects);
            	appPortal.showOptions();
                appPortal.portal();  // central method that handles user choices, loops, etc.
            }
                
            else if (user instanceof HdbOfficer hdbOfficer) {
                OfficerPortal offPortal = new OfficerPortal(hdbOfficer);
                offPortal.showOptions();
                offPortal.portal();
            } 
            
            //else if (user instanceof HdbManager) {
                //ManagerPortal manPortal = new ManagerPortal((HdbManager) user);
                //manPortal.showOptions();
                //manPortal.portal();
            //}


        } while (true);
    }
}
