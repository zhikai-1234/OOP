
public class Manager extends User {

    private TemplateProject projectInCharge;

    public Manager(String name, String userID, int age, String maritalStatus) {
        super(name, userID, age, maritalStatus);
        this.projectInCharge = null;
    }

    public void setProjectInCharge(TemplateProject p) {
        this.projectInCharge = p;
    }

    public TemplateProject getProjectInCharge() {
        return this.projectInCharge;
    }
} 
    
/*    public void createProj(BTOSystem system){
        
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
        
        String managerName = this.getName(); //Auto assigned the manager that is creating the project as this manager
        
        System.out.print("Enter number of Officer slots: ");
        int slots = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Set project visibility (1 = ON, 0 = OFF): ");
        int visibilityInput = scanner.nextInt();
        boolean visibility;
        if (visibilityInput == 1) {
            visibility = true;
        } else {
            visibility = false;
        }

        
        List<String> pendingOfficers = new ArrayList<>();
        List<String> approvedOfficers = new ArrayList<>(); //putting it empty list first because
                                                           //need to approve or reject officers later
        
        Project newProject = new Project(projName, neighborhood, type1, units1, price1, type2, units2, price2, openingDate, closingDate, managerName, slots, pendingOfficers, approvedOfficers, visibility);
        
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
            System.out.println("12. Visibility (1 for visible / 0 for hidden)");
            System.out.println("13. Save and Exit");

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
                	System.out.print("Enter visibility (1 for visible / 0 for hidden): ");
                    int visInput = scanner.nextInt();
                    scanner.nextLine();
                    boolean visibility;
                    if (visInput == 1) {
                        visibility = true;
                    } else {
                        visibility = false;
                    }
                    editProject.setVisibility(visibility);
                    break;
                case 13:
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
            /*if (existingProj.getManagerName().equalsIgnoreCase(this.getName()) && existingProj != editProject) {
            	
                String existingOpen = existingProj.getOpenDate();
                String existingClose = existingProj.getCloseDate();
                
                /*Comparing Strings of the Date to see if the existing project will overlap with the 
                 *newly created/edited Project application dates*/
                /*if (existingOpen.compareTo(newClosingDate) <= 0 && existingClose.compareTo(newOpeningDate) >= 0) {
                        return true;
                }
    	    }
        }
    	return false;
    }
    
    public List<Project> filteredProj(BTOSystem system){
    	
        List<Project> allProjects = system.getProjectList();
        List<Project> filtered = new ArrayList<>(); 
     
        for (int i = 0; i < allProjects.size(); i++) {
        	
            Project p = allProjects.get(i);
            
            /* This iterate through the full list of BTO projects in the system.
             * For each project, it checks if the manager in charge matches the current logged-in manager.
             * If it matches, the project is printed and added to the filtered list.
             * It also return the filtered list so that it can be used in the editProj() and deleteProj()
             * to ensure the Managers can only edit and delete the projects that their handling.*/
            /*if (p.getManagerName().equalsIgnoreCase(this.getName())) {
                filtered.add(p);
            }
        }
        
        if(filtered.isEmpty()) {
        	System.out.println("You currently are not in charged of any Projects.");
        }
        else {
            //Call the print function to show project details so that Manager can see
        	//before editing or deleting.
            viewOwnProj(filtered);
        }
        return filtered; //return the filtered List to be used in the editProj() and deleteProj().  
    }
    
    
    public void viewOwnProj(List<Project> filteredProjects) {
    	
        System.out.println("\nProjects you're managing:");
        
        for (int i = 0; i < filteredProjects.size(); i++) {
            System.out.println((i + 1) + ") ");
            filteredProjects.get(i).displayProjectDetails();  //Let Project class handle printing itself
        }
    }

    
    public void viewAllProj(BTOSystem system) {
        List<Project> allProjects = system.getProjectList();

        if (allProjects.isEmpty()) {
            System.out.println("There are no projects available at this point of time.");
            return;
        }
        
        for (int i = 0; i < allProjects.size(); i++) {
            Project p = allProjects.get(i);
            p.displayProjectDetails();
        }
    }

    public void approveOfficerRegistration(BTOSystem system) {
        List<Project> myProjects = filteredProj(system);
        Scanner scanner = new Scanner(System.in);

        if (myProjects.isEmpty()) return;

        System.out.println("Select a project to review officer applications:");
        for (int i = 0; i < myProjects.size(); i++) {
            System.out.println((i + 1) + ") " + myProjects.get(i).getProjName());
        }

        int projChoice = scanner.nextInt();
        scanner.nextLine();

        if (projChoice < 1 || projChoice > myProjects.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Project selectedProject = myProjects.get(projChoice - 1);
        List<String> pending = selectedProject.getPendingOfficers();

        if (pending.isEmpty()) {
            System.out.println("No pending officers for this project.");
            return;
        }

        System.out.println("Pending Officers:");
        for (int i = 0; i < pending.size(); i++) {
            System.out.println((i + 1) + ") " + pending.get(i));
        }

        System.out.print("Enter number of officer to approve (or 0 to cancel): ");
        int officerChoice = scanner.nextInt();
        scanner.nextLine();

        if (officerChoice < 1 || officerChoice > pending.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        String officerName = pending.get(officerChoice - 1);
        selectedProject.approveOfficer(officerName);
        system.saveProjectsToFile("ProjectList.csv");
        System.out.println("Officer " + officerName + " has been approved.");
    }

    public void approveApplicantApplication(){
    }
    
    public void displayJobscope(BTOSystem system) {

        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        
        while (true) {
            System.out.println("\nWelcome, Manager!");
            System.out.println("1. Create Projects");
            System.out.println("2. Edit Projects");
            System.out.println("3. Delete Projects");
            System.out.println("4. View all Projects");
            System.out.println("5. View your own Projects");
            System.out.println("6. Approve Officer Registrations");
            System.out.println("7. Logout");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline

            switch (choice) {
                case 1:
                    createProj(system);
                    break;
                case 2:
                    editProj(system);
                    break;
                case 3:
                    deleteProj(system);
                    break;
                case 4:
                    viewAllProj(system);
                    break;
                case 5:
                    filteredProj(system);
                    break;
                case 6:
                    approveOfficerRegistration(system);
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return;  
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 7.");
            }
        }
    }
    
    
}*/
