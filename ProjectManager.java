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
    public void generateReport() {
        for (Map.Entry<String, LiveProject> liveProj : liveProjects.entrySet()) {
            Applicant currentApplicant = liveProj.getValue().getApplicant();
            System.out.printf("\n Applicant Name: %s\n", currentApplicant.getName());
            System.out.printf("Flat Type: %d-Room | Project Name: %s | Age: %d | Marital Status: %s\n", 
            currentApplicant.getAppliedFlatType(), currentApplicant.getProjApplied().getName(), currentApplicant.getAge(), 
            currentApplicant.getMaritalStatus());
        }
    }

    public LiveProject convertTemplateToLive(Applicant a, TemplateProject t) {
        LiveProject l = new LiveProject(t.getName(), t.getNeighbourhood(), t.getType1(), t.getNumOfType1(), t.getType1Price(), 
        t.getType2(), t.getNumOfType2(), t.getType2Price(), t.getOpenDate(), t.getCloseDate(), t.getManagerName(), t.getNumOfficers(), 
        t.getVisibility(), a);
        return l;
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