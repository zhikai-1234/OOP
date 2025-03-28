import java.util.Scanner;

public class HdbManager extends User {
    private String[] managedProj;
    private String[] approvedOfficer;

    public HdbManager(String UserID, String age, String maritalStatus, String password) {
        super(UserID, age, maritalStatus, password);
    }
    
    public void createProj(BTOSystem system){
        System.out.println(" testing: Creating");
    }

    public void editProj(){

    }

    public void deleteProj(){

    }

    public void approveOfficerRegistration(){

    }

    public void approveApplicantApplication(){

    }
    
    public void displayJobscope(BTOSystem system) {
    	
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Welcome, Manager!");
        System.out.println("1. Create Projects");
        System.out.println("2. Edit Projects");
        
        int choice = sc.nextInt();

        if (choice == 1) {
        	createProj(system);
        }
        
        
        
    }
}
