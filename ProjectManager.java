import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectManager {

    private List<TemplateProject> templateProjects; // for viewing
    private List<LiveProject> liveProjects; // for editing

    // CONSTRUCTOR //
    public ProjectManager() {
        this.templateProjects = new ArrayList<>();
        this.liveProjects = new ArrayList<>();
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
                 int numOfficers = Integer.parseInt(parts[11].trim());
                 boolean visibility = Boolean.parseBoolean(parts[14].trim());

                 templateProjects.add(new TemplateProject(projName, neighborhood, flatType1, numType1, price1,
                         flatType2, numType2, price2, openDate, closeDate, numOfficers, visibility));
             }
             reader.close();
         } 
         catch (IOException e) {
             System.out.println("Failed to read project list");
            }
    }


}