import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MainLogin {

    public static void main(String[] args) {
        BTOSystem btosys = new BTOSystem();
        
        List<Project> projects = new ArrayList<Project>();
        
        btosys.loadUserDataFromFile("resources/ApplicantList.csv", "applicant");
        btosys.loadProjectDataFromFile("resources/ProjectList.csv");
        projects = btosys.getProjectList();

        Scanner sc = new Scanner(System.in);

        do {
            User user = btosys.login();

            if (user instanceof Applicant) {
            	ApplicantPortal appPortal = new ApplicantPortal((Applicant) user, projects);
            	appPortal.showOptions();
                appPortal.portal();  // central method that handles user choices, loops, etc.
            }
                
            else if (user instanceof HdbOfficer) {
                OfficerPortal offPortal = new OfficerPortal((HdbOfficer) user);
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
