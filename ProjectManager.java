import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProjectManager {

    private List<TemplateProject> templateProjects; // for viewing
    private Map<String, LiveProject> liveProjects; // for editing
    private Map<String, UserFilter> userFilters = new HashMap<>();

    // CONSTRUCTOR //
    public ProjectManager(List<TemplateProject> templateProjects, Map<String, LiveProject> liveProjects) {
        this.templateProjects = templateProjects;
        this.liveProjects = liveProjects;
    }
    
    //For the filtering
    public UserFilter getFilterForUser(String userKey) {
        return userFilters.computeIfAbsent(userKey, k -> new UserFilter());
    }

   
    // LOADER METHODS //
    public void loadTemplateProjects(String filePath) {
        try {
             BufferedReader reader = new BufferedReader(new FileReader(filePath));
             String line;
             boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] parts = line.split(",", -1);
                if (parts.length < 15) continue;

                String projName = parts[0].trim();
                String neighborhood = parts[1].trim();
                String flatType1 = parts[2].trim();
                int numType1 = Integer.parseInt(parts[3].trim());
                double price1 = Double.parseDouble(parts[4].trim());
                String flatType2 = parts[5].trim();
                int numType2 = Integer.parseInt(parts[6].trim());
                double price2 = Double.parseDouble(parts[7].trim());
                String openDate = parts[8].trim();
                String closeDate = parts[9].trim();
                String managerName = parts[10].trim();
                int numOfficers = Integer.parseInt(parts[11].trim());
                String pendingOfficersField = parts[12].trim(); 
                String approvedOfficersField = parts[13].trim(); 
                boolean visibility = Boolean.parseBoolean(parts[14].trim());

                List<String> pendingOfficers = parseOfficerList(pendingOfficersField);
                List<String> approvedOfficers = parseOfficerList(approvedOfficersField);

                TemplateProject t = new TemplateProject(projName, neighborhood, flatType1, numType1, price1,
                flatType2, numType2, price2, openDate, closeDate, managerName, numOfficers, visibility);

                t.setPendingOfficers(pendingOfficers);
                t.setApprovedOfficers(approvedOfficers);

                templateProjects.add(t);

                
             }
             reader.close();
            }
         catch (IOException e) {
             System.out.println("Failed to read project list");
            }
    }

    private List<String> parseOfficerList(String field) {
        List<String> officers = new ArrayList<>();
        if (field != null && !field.trim().isEmpty()) {
            String[] names = field.split("\\|");
            for (String name : names) {
                name = name.trim();
                if (!name.isEmpty()) {
                    officers.add(name);
                }
            }
        }
        return officers;
    }

    // GETTERS //

    public List<TemplateProject> getTemplateProjects() {
        return this.templateProjects;
    }
  

    public Map<String, LiveProject> getLiveProjects() {
        return this.liveProjects;
    }

    // SETTERS //
    public void updateTemplateProjects(List<TemplateProject> p) {  // use for ANY updates made from the portal to push back to here
        this.templateProjects = p;
    }

    public void addLiveProject(Applicant a, LiveProject p) {
        this.liveProjects.put(a.getUserID(), p);
    }

    // DISPLAY //
    public void display2RoomProjectDetails(TemplateProject p) {
        if (p.getVisibility() == true) {
            //System.out.println("DEBUG: Type1 flats = " + p.getNumOfType1());
            System.out.println("---------------------------------------");
            System.out.printf("Project Name: %s\n", p.getName());
            System.out.printf("Neighborhood: %s\n", p.getNeighbourhood());
            System.out.println();
            System.out.printf("Flat Type 1: %s\n", p.getType1());
            System.out.printf("Units: %d\n", p.getNumOfType1());
            System.out.printf("Price: $%.2f\n", p.getType1Price());
            System.out.println("Application Period: " + p.getOpenDate() + " to " + p.getCloseDate());
            System.out.printf("Manager in charge: %s\n", p.getManagerName());
            System.out.println("Approved Officers: " + p.getApprovedOfficers());
            System.out.println("Pending Officers: " + p.getPendingOfficers());
        }
    }

    public void display2and3RoomProjectDetails(TemplateProject p) {
        //System.out.println("DEBUG: Type1 flats = " + p.getNumOfType1() + ", Type2 flats = " + p.getNumOfType2());
        if (p.getVisibility() == true) {
            System.out.println("---------------------------------------");
            System.out.printf("Project Name: %s\n", p.getName());
            System.out.printf("Neighborhood: %s\n", p.getNeighbourhood());
            System.out.println();
            System.out.printf("Flat Type 1: %s\n", p.getType1());
            System.out.printf("Units: %d\n", p.getNumOfType1());
            System.out.printf("Price: $%.2f\n", p.getType1Price());
            System.out.println();
            System.out.printf("Flat Type 2: %s\n", p.getType2());
            System.out.printf("Units: %d\n", p.getNumOfType2());
            System.out.printf("Price: $%.2f\n", p.getType2Price());
            System.out.println();
            System.out.println("Application Period: " + p.getOpenDate() + " to " + p.getCloseDate());
            System.out.printf("Manager in charge: %s\n", p.getManagerName());
            System.out.println("Approved Officers: " + p.getApprovedOfficers());
            System.out.println("Pending Officers: " + p.getPendingOfficers());
        }
    }

    // FOR MANAGER //
    public void generateFilteredReport(Scanner sc) {
        System.out.println("=== Generate Booking Report with Filters ===");

        System.out.println("Apply filters?");
        System.out.println("[1] All applicants");
        System.out.println("[2] Only married applicants");
        System.out.println("[3] Only 2-Room bookings");
        System.out.println("[4] Only 3-Room bookings");
        System.out.println("[5] Age above");
        System.out.println("[6] Filter by project name");
        System.out.println("[0] Cancel");

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(sc.nextLine());

        String maritalFilter = null;
        Integer flatTypeFilter = null;
        Integer age = null;
        String projectNameFilter = null;
       
        //Set the whatever filter the Manager wants to filter based of
        switch (choice) {
            case 0 -> { 
            	return; //Cancel the report
            	}
            case 2 -> maritalFilter = "Married"; //Filter by marriage
            case 3 -> flatTypeFilter = 1; //Filter by 2-Room
            case 4 -> flatTypeFilter = 2; //or 3-Room
            case 5 -> {
                System.out.print("Enter minimum age: "); //Or by age
                age = Integer.parseInt(sc.nextLine());
            }
            case 6 -> {
                System.out.print("Enter project name to filter by: "); //Or Project Name
                projectNameFilter = sc.nextLine().trim();
            }
        }

        System.out.println("\n=== Filtered Report ===");
        boolean found = false; //Set false first

        //Iterate over all LiveProject entries (each is tied to one applicant)
        for (Map.Entry<String, LiveProject> entry : liveProjects.entrySet()) {
            Applicant a = entry.getValue().getApplicant();
            
            //Skip if the applicant has not booked a flat yet
            if (!a.hasBookedFlat()){
            	continue;
            }

            //Apply marriage status filter if it is set
            if (maritalFilter != null && !a.getMaritalStatus().equalsIgnoreCase(maritalFilter)) {
            	continue;
            }
            //Apply flat type filter if it is set
            if (flatTypeFilter != null && a.getBookedFlatType() != flatTypeFilter) {
            	continue;
            }
            //Apply age filter if it is set
            if (age != null && a.getAge() < age) {
            	continue;
            }
            //Apply project name filter if it is set
            if (projectNameFilter != null && !a.getProjApplied().getName().equalsIgnoreCase(projectNameFilter)) {
            	continue;
            }

            //If all filters passed, set it as found and print applicant details
            found = true;
            String flatTypeLabel = (a.getBookedFlatType() == 1) ? "2-Room" : "3-Room";
            
            System.out.printf("Name: %s | Flat: %s | Project: %s | Age: %d | Marital: %s\n",
            a.getName(), flatTypeLabel, a.getProjApplied().getName(), a.getAge(), a.getMaritalStatus());
        }

        if (!found) System.out.println("No matching bookings found."); //Dont have
    }

    public LiveProject convertTemplateToLive(Applicant a, TemplateProject t) {
        LiveProject l = new LiveProject(t.getName(), t.getNeighbourhood(), t.getType1(), t.getNumOfType1(), t.getType1Price(), 
        t.getType2(), t.getNumOfType2(), t.getType2Price(), t.getOpenDate(), t.getCloseDate(), t.getManagerName(), t.getNumOfficers(), 
        t.getVisibility(), a);
        return l;
    }
    
    //Used for finding the exact project of the Applicant that have withdrawn after
    //booking of flats.
    public TemplateProject retrieveTemplateProject(String name) {
        for (TemplateProject p : templateProjects) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
    
    //Save to File
    public void saveTemplateProjectsToFile(String filePath) {
        try {
            FileWriter writer = new FileWriter("ProjectList.csv");

            writer.write("Project Name,Neighborhood,Type 1,Number of units for Type 1,Selling price for Type 1,");
            writer.write("Type 2,Number of units for Type 2,Selling price for Type 2,");
            writer.write("Application opening date,Application closing date,Manager,Officer Slot,Pending Officers,Approved Officers,Visibility\n");

            for (TemplateProject p : templateProjects) {
                writer.write(p.getName() + "," +
                             p.getNeighbourhood() + "," +
                             p.getType1() + "," +
                             p.getNumOfType1() + "," +
                             p.getType1Price() + "," +
                             p.getType2() + "," +
                             p.getNumOfType2() + "," +
                             p.getType2Price() + "," +
                             p.getOpenDate() + "," +
                             p.getCloseDate() + "," +
                             p.getManagerName() + "," +
                             p.getNumOfficers() + "," +
                             String.join("|", p.getPendingOfficers() == null ? new ArrayList<>() : p.getPendingOfficers()) + "," +
                             String.join("|", p.getApprovedOfficers() == null ? new ArrayList<>() : p.getApprovedOfficers()) + "," +
                             p.getVisibility() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to save ProjectList.csv");
        }
    }
}
