import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class HdbManager extends User {
    private String[] managedProj;
    private String[] approvedOfficer;

    public HdbManager(String name, String UserID, String age, String maritalStatus, String password) {
        super(name, UserID, age, maritalStatus, password);
    }
    
    public void createProj(BTOSystem system){
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter Project Name: ");
        String projName = scanner.nextLine();
        
        System.out.print("Enter Neighborhood: ");
        String neighborhood = scanner.nextLine();
        
        System.out.print("Enter Flat Type(1): ");
        String type1 = scanner.nextLine();
        
        System.out.print("Enter number of units for Flat Type 1: ");
        int units1 = scanner.nextInt();
        
        System.out.print("Enter selling price for Flat Type 1: ");
        double price1 = scanner.nextDouble();
        scanner.nextLine(); 
        
        System.out.print("Enter Flat Type(2): ");
        String type2 = scanner.nextLine();
        
        System.out.print("Enter number of units for Flat Type 2: ");
        int units2 = scanner.nextInt();
        
        System.out.print("Enter selling price for Flat Type 2: ");
        double price2 = scanner.nextDouble();
        scanner.nextLine(); 
        
        System.out.print("Enter Application Opening Date (yyyy-mm-dd): ");
        String openingDate = scanner.nextLine();
        
        System.out.print("Enter Application Closing Date (yyyy-mm-dd): ");
        String closingDate = scanner.nextLine();
        
        String managerName = this.getName();
        
        System.out.print("Enter number of Officer slots: ");
        int slots = scanner.nextInt();
        
        List<String> officers = new ArrayList<>(); //putting it empty list first because
                                                   //need to approve or reject officers later
        
        Project newProject = new Project(projName, neighborhood, type1, units1, price1, type2, units2, price2, openingDate, closingDate, managerName, slots, officers);
        
        system.getProjectList().add(newProject);
        system.saveProjectsToFile("ProjectList.csv");
        
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
    	
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome, Manager!");
        System.out.println("1. Create Projects");
        System.out.println("2. Edit Projects");
        
        int choice = scanner.nextInt();

        if (choice == 1) {
        	createProj(system);
        }
        
        
        
    }
}
