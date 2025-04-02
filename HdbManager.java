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
        
        if (checkDateConflict(openingDate, closingDate, system, null)) {
            System.out.println("You are already managing a project during this application period.");
            return;
        }
        
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
    	List<Project> myProjects = filteredProj(system);
    	
    	if(myProjects.isEmpty()) {
    		return;
    	}
    	System.out.println("Above are all the projects that you are in charged of to be edited.");
    	
    	System.out.println("Enter which project to be edited (Enter the Numerical Value): ");
    	int choice = scanner.nextInt();
    	choice = choice - 1;
    	scanner.nextLine();
    	
    	Project editProject = myProjects.get(choice);
    	

        boolean editing = true;
        while (editing) {
            System.out.println("\nEditing project: " + editProject.getProjName());
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
                    editProject.setProjName(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter new neighborhood: ");
                    editProject.setNeighborhood(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Enter new type 1: ");
                    editProject.setFlatType1(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Enter new number of units for type 1: ");
                    editProject.setNumOfUnitsType1(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.print("Enter new selling price for type 1: ");
                    editProject.setPriceType1(scanner.nextDouble());
                    scanner.nextLine();
                    break;
                case 6:
                    System.out.print("Enter new type 2: ");
                    editProject.setFlatType2(scanner.nextLine());
                    break;
                case 7:
                    System.out.print("Enter new number of units for type 2: ");
                    editProject.setNumOfUnitsType2(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 8:
                    System.out.print("Enter new selling price for type 2: ");
                    editProject.setPriceType2(scanner.nextDouble());
                    scanner.nextLine();
                    break;
                case 9:
                    System.out.print("Enter new application opening date (yyyy-mm-dd): ");
                    editProject.setOpenDate(scanner.nextLine());
                    break;
                case 10:
                    System.out.print("Enter new application closing date (yyyy-mm-dd): ");
                    editProject.setCloseDate(scanner.nextLine());
                    break;
                case 11:
                    System.out.print("Enter new officer slot: ");
                    editProject.setOfficerSlots(scanner.nextInt());
                    scanner.nextLine();
                    break;
                case 12:
                	String newOpeningDate = editProject.getOpenDate();
                	String newClosingDate = editProject.getCloseDate();
                	
                	if(checkDateConflict(newOpeningDate, newClosingDate, system, editProject)) {
                		System.out.println("You are already managing another project during this period.");
                	    return;
                	}
                	else { 
                        editing = false;
                        System.out.println("Changes has been made.");
                        system.saveProjectsToFile("ProjectList.csv"); 
                	}
                	break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }            
    }
    	
    public void deleteProj(BTOSystem system){
    	
    	Scanner scanner = new Scanner(System.in);
    	List<Project> myProjects = filteredProj(system);
    	
    	if(myProjects.isEmpty()) {
    		return;
    	}  	
    	
    	System.out.println("Above are all the projects that you are in charged of that can be deleted.");

    	System.out.println("Enter which project to be deleted (Enter the Numerical Value): ");
    	int choice = scanner.nextInt();
    	choice = choice - 1;
    	scanner.nextLine();
    	
    	system.getProjectList().remove(myProjects.get(choice));
    	
    	System.out.println("Project will be deleted.");
    	system.saveProjectsToFile("ProjectList.csv");
    	
    }
    
    public boolean checkDateConflict(String newOpeningDate, String newClosingDate, BTOSystem system, Project editProject) {
        
    	List<Project> projectList = system.getProjectList();
    	
    	for (int i = 0; i < projectList.size(); i++) {
            Project existingProj = projectList.get(i);
            
            /*Iterate the projectList to match 1 by 1 if the Manager has already been assigned to a project
            if manager is assigned already, check if that HDBproject is the one that is currently being edited, 
            if its not then start checking if the dates overlap.*/
            if (existingProj.getManagerName().equalsIgnoreCase(this.getName()) && existingProj != editProject) {
            	
                String existingOpen = existingProj.getOpenDate();
                String existingClose = existingProj.getCloseDate();
                
                /*Comparing Strings of the Date to see if the existing project will overlap with the 
                 *newly created/edited Project application dates*/
                if (existingOpen.compareTo(newClosingDate) <= 0 && existingClose.compareTo(newOpeningDate) >= 0) {
                        return true;
                }
    	    }
        }
    	return false;
    }
    
    public List<Project> filteredProj(BTOSystem system){
    	
        List<Project> allProjects = system.getProjectList();
        List<Project> filtered = new ArrayList<>(); 
        
        System.out.println("\nProjects you're managing:");
        
        int count = 1;
        
        for (int i = 0; i < allProjects.size(); i++) {
        	
            Project p = allProjects.get(i);
            
            /* This iterate through the full list of BTO projects in the system.
             * For each project, it checks if the manager in charge matches the current logged-in manager.
             * If it matches, the project is printed and added to the filtered list.
             * It also return the filtered list so that it can be used in the editProj() and deleteProj()
             * to ensure the Managers can only edit and delete the projects that their handling.*/
            if (p.getManagerName().equalsIgnoreCase(this.getName())) {
                System.out.println("---------------------------------------");
                System.out.println(count + ") " + p.getProjName());
                System.out.println("Neighborhood: " + p.getNeighborhood());
                System.out.println();
                System.out.println("Flat Type 1: " + p.getFlatType1());
                System.out.println("Units: " + p.getNumOfUnitsType1());
                System.out.println("Price: $" + p.getPriceType1());
                System.out.println();
                System.out.println("Flat Type 2: " + p.getFlatType2());
                System.out.println("Units: " + p.getNumOfUnitsType2());
                System.out.println("Price: $" + p.getPriceType2());
                System.out.println();
                System.out.println("Application Period: " + p.getOpenDate() + " to " + p.getCloseDate());
                System.out.println("Officer Slots: " + p.getOfficerSlots());
                System.out.println("Officers Assigned: " + String.join(", ", p.getOfficers()));
                System.out.println("---------------------------------------");
                filtered.add(p);
                count++;
            }
        }
        if(filtered.isEmpty()) {
        	System.out.println("You currently are not in charged of any Projects.");
        }
        return filtered; //return the filtered List to be used in the editProj() and deleteProj().  
    }
    
    public void viewAllProj(BTOSystem system) {
        List<Project> allProjects = system.getProjectList();

        if (allProjects.isEmpty()) {
            System.out.println("There are no projects available at this point of time.");
            return;
        }
        
        System.out.println();
        System.out.println("List of all current projects:");
        for (int i = 0; i < allProjects.size(); i++) {
            Project p = allProjects.get(i);
            System.out.println("--------------------------------------------------");
            System.out.println("Project " + (i + 1));
            System.out.println("Name: " + p.getProjName());
            System.out.println("Neighborhood: " + p.getNeighborhood());
            System.out.println();
            System.out.println("Flat Type 1: " + p.getFlatType1());
            System.out.println("Units: " + p.getNumOfUnitsType1());
            System.out.println("Price: $" + p.getPriceType1());
            System.out.println();
            System.out.println("Flat Type 2: " + p.getFlatType2());
            System.out.println("Units: " + p.getNumOfUnitsType2());
            System.out.println("Price: $" + p.getPriceType2());
            System.out.println();
            System.out.println("Application Period: " + p.getOpenDate() + " to " + p.getCloseDate());
            System.out.println("Manager in charged: " + p.getManagerName());
            System.out.println("Officer Slots: " + p.getOfficerSlots());
            System.out.println("Officers Assigned: " + String.join(", ", p.getOfficers()));
            System.out.println("--------------------------------------------------");
        }
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
        System.out.println("4. View all Projects");
        System.out.println("5. View your own Projects");
        
        
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
        	
        if (choice == 4) {
        	viewAllProj(system);
        }
    
        if (choice == 5) {
        	filteredProj(system);
        }
              
    }
}
