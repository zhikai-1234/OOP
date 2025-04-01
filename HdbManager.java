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

    public void editProj(BTOSystem system){
    	
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.println("Here are all the projects to be edited: ");
    	for(int i = 0; i < system.getProjectList().size(); i++) {
    		System.out.println((i+1) + ") "+ system.getProjectList().get(i).getProjName());
    	}
    	
    	System.out.println("Enter which project to be edited (Enter the Numerical Value): ");
    	int choice = scanner.nextInt();
    	choice = choice - 1;
    	scanner.nextLine();
    	
    	Project editProj = system.getProjectList().get(choice);

        boolean editing = true;
        while (editing) {
            System.out.println("\nEditing project: " + editProj.getProjName());
            System.out.println("What would you like to edit?");
            System.out.println("1. Project Name");
            System.out.println("2. Neighborhood");
            System.out.println("3. Type 1");
            System.out.println("4. Number of units for Type 1");
            System.out.println("5. Selling price for Type 1");
            System.out.println("6. Type 2");
            System.out.println("7. Number of units for Type 2");
            System.out.println("8. Selling price for Type 2");
            System.out.println("9. Application opening date");
            System.out.println("10. Application closing date");
            System.out.println("11. Officer Slot");
            System.out.println("12. Save and Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    System.out.print("Enter new project name: ");
                    editProj.setProjName(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter new neighborhood: ");
                    editProj.setNeighborhood(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Enter new type 1: ");
                    editProj.setFlatType1(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Enter new number of units for type 1: ");
                    editProj.setNumOfUnitsType1(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.print("Enter new selling price for type 1: ");
                    editProj.setPriceType1(scanner.nextDouble());
                    scanner.nextLine();
                    break;
                case 6:
                    System.out.print("Enter new type 2: ");
                    editProj.setFlatType2(scanner.nextLine());
                    break;
                case 7:
                    System.out.print("Enter new number of units for type 2: ");
                    editProj.setNumOfUnitsType2(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 8:
                    System.out.print("Enter new selling price for type 2: ");
                    editProj.setPriceType2(scanner.nextDouble());
                    scanner.nextLine();
                    break;
                case 9:
                    System.out.print("Enter new application opening date (yyyy-mm-dd): ");
                    editProj.setOpenDate(scanner.nextLine());
                    break;
                case 10:
                    System.out.print("Enter new application closing date (yyyy-mm-dd): ");
                    editProj.setCloseDate(scanner.nextLine());
                    break;
                case 11:
                    System.out.print("Enter new officer slot: ");
                    editProj.setOfficerSlots(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 12:
                    editing = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        System.out.println("Changes has been made.");
        system.saveProjectsToFile("ProjectList.csv");
    }
    	

    public void deleteProj(BTOSystem system){
    	
    	Scanner scanner = new Scanner(System.in);
    	
    	System.out.println("Here are all the projects to be edited: ");
    	for(int i = 0; i < system.getProjectList().size(); i++) {
    		System.out.println((i+1) + ") "+ system.getProjectList().get(i).getProjName());
    	}
    	
    	System.out.println("Enter which project to be deleted (Enter the Numerical Value): ");
    	int choice = scanner.nextInt();
    	choice = choice - 1;
    	scanner.nextLine();
    	
    	system.getProjectList().remove(choice); 
    	
    	System.out.println("Project will be deleted.");
    	system.saveProjectsToFile("ProjectList.csv");
    	
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
        System.out.println("3. Delete Projects");
        
        int choice = scanner.nextInt();

        if (choice == 1) {
        	createProj(system);
        }
        if (choice == 2) {
        	editProj(system);
        }
        if (choice == 3) {
        	deleteProj(system);
        }
              
    }
}
