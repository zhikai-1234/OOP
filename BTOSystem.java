import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.*;
import java.io.*;

public class BTOSystem { 
	
     private List<User> userList;
     private List<Project> projectList;
     private List<Enquiry> enquiryList;  
     
     public BTOSystem() { 
         userList = new ArrayList<>();
         projectList = new ArrayList<>();
	     enquiryList = new ArrayList<>();
     }
     
     /* This loadUserDataFromFIle is to read the csv file of the roles, so when login in, 
     for the BTOSystem to identify u are either applicant, officer or Manager, it first
     have to read from the csv file (below is the manager one): 
     Name	    NRIC	    Age	Marital Status	Password
     Michael	T8765432F	36	Single	        password
     Jessica	S5678901G	26	Married	        password
     then once u key in the UserID and password, it will match with what it read from 
     the file so if u key in ur NRIC and password correctly, it will know u are a Manager
     and then allow u to see the Manager jobscopes*/

     public List<User> loadUsersFromFile(String filePath, String role) {
         List<User> users = new ArrayList<>();
         try {
             BufferedReader reader = new BufferedReader(new FileReader(filePath));
             String line;
             boolean isFirstLine = true;

             while ((line = reader.readLine()) != null) {
                 if (isFirstLine) {
                     isFirstLine = false;
                     continue;
                 }
                 String[] parts = line.split(",");
                 if (parts.length < 5) continue;

                 String name = parts[0].trim();
                 String userID = parts[1].trim();
                 String age = parts[2].trim();
                 String maritalStatus = parts[3].trim();
                 String password = parts[4].trim();

                 User user = null;
                 if (role.equalsIgnoreCase("manager")) {
                     user = new HdbManager(name, userID, age, maritalStatus, password);
                 } else if (role.equalsIgnoreCase("applicant")) {
                     user = new Applicant(name, userID, age, maritalStatus, password);
                 } else if (role.equalsIgnoreCase("officer")) {
                     user = new HdbOfficer(name, userID, age, maritalStatus, password);
                 }
                 if (user != null) {
                     users.add(user);
                 }
             }
             reader.close();
         } catch (IOException e) {
             System.out.println("Failed to read " + filePath);
         }
         return users;
     }
     
     public List<Project> loadProjectsFromFile(String filePath) {
         List<Project> projects = new ArrayList<>();
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
                 int units1 = Integer.parseInt(parts[3].trim());
                 double price1 = Double.parseDouble(parts[4].trim());
                 String flatType2 = parts[5].trim();
                 int units2 = Integer.parseInt(parts[6].trim());
                 double price2 = Double.parseDouble(parts[7].trim());
                 String openDate = parts[8].trim();
                 String closeDate = parts[9].trim();
                 String manager = parts[10].trim();
                 int officerSlots = Integer.parseInt(parts[11].trim());

                 List<String> pendingOfficers = new ArrayList<>();
                 List<String> approvedOfficers = new ArrayList<>();

                 String pendingRaw = parts[12].replace("\"", "").trim();
                 String approvedRaw = parts[13].replace("\"", "").trim();

                 if (!pendingRaw.isEmpty()) {
                     String[] tokens = pendingRaw.split("\\|");
                     for (int j = 0; j < tokens.length; j++) {
                         pendingOfficers.add(tokens[j]);
                     }
                 }
                 if (!approvedRaw.isEmpty()) {
                     String[] tokens = approvedRaw.split("\\|");
                     for (int j = 0; j < tokens.length; j++) {
                         approvedOfficers.add(tokens[j]);
                     }
                 }

                 boolean visibility = Boolean.parseBoolean(parts[14].trim());

                 projects.add(new Project(projName, neighborhood, flatType1, units1, price1,
                         flatType2, units2, price2, openDate, closeDate,
                         manager, officerSlots, pendingOfficers, approvedOfficers, visibility));
             }
             reader.close();
         } catch (IOException e) {
             System.out.println("Failed to read project list");
         }
         return projects;
     }

     public void saveProjectsToFile(String filePath, List<Project> projectList) {
         try {
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

             writer.write("Project Name,Neighborhood,Type 1,Number of units for Type 1,Selling price for Type 1,Type 2,Number of units for Type 2,Selling price for Type 2,Application opening date,Application closing date,Manager,Officer Slot,Pending Officers,Approved Officers,Visibility\n");

             for (int i = 0; i < projectList.size(); i++) {
                 Project p = projectList.get(i);
                 String pending = String.join("|", p.getPendingOfficers());
                 String approved = String.join("|", p.getApprovedOfficers());

                 writer.write(
                     p.getProjName() + "," +
                     p.getNeighborhood() + "," +
                     p.getFlatType1() + "," +
                     p.getNumOfUnitsType1() + "," +
                     p.getPriceType1() + "," +
                     p.getFlatType2() + "," +
                     p.getNumOfUnitsType2() + "," +
                     p.getPriceType2() + "," +
                     p.getOpenDate() + "," +
                     p.getCloseDate() + "," +
                     p.getManagerName() + "," +
                     p.getOfficerSlots() + "," +
                     pending + "," +
                     approved + "," +
                     p.getVisibility() + "\n"
                 );
             }

             writer.close();
             System.out.println("Projects saved to file successfully.");
         } catch (IOException e) {
             System.out.println("Failed to save projects.");
         }
     }
         
     
/*    public void loadUserDataFromFile(String filePath, String role) {
         try {
             BufferedReader reader = new BufferedReader(new FileReader(filePath)); 
             String line;
             boolean isFirstLine = true;

             while ((line = reader.readLine()) != null) {
                 if (isFirstLine) {
                     isFirstLine = false; 
                     continue;
                 }

                 String[] parts = line.split(",");
                 if (parts.length < 5) {
                     System.out.println("Skipping malformed line: " + line);
                     continue;
                 }
                 
                 String name = parts[0].trim();
                 String UserID = parts[1].trim(); // nric
                 String age = parts[2].trim();
                 String maritalStatus = parts[3].trim();
                 String password = parts[4].trim();

                 User user = null;

                 if (role.equalsIgnoreCase("manager")) {
                     user = new HdbManager(name, UserID, age, maritalStatus, password);
                 } else if (role.equalsIgnoreCase("applicant")) {
                     user = new Applicant(name, UserID, age, maritalStatus, password);
                 } else if (role.equalsIgnoreCase("officer")) {
                     user = new HdbOfficer(name, UserID, age, maritalStatus, password);
                 }

                 if (user != null) {
                     userList.add(user); 
                 }
             }

             reader.close();
            
         } 
         
         catch (Exception e) {
        	    System.out.println("Something went wrong.");
        	}
     }
     
     /*public void loadProjectDataFromFile(String filePath) {
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
                 if (parts.length < 15) {
                     System.out.println("Skipping malformed line: " + line);
                     continue;
                 }

                 String projName = parts[0].trim();
                 String neighborhood = parts[1].trim();
                 String flatType1 = parts[2].trim();
                 int units1 = Integer.parseInt(parts[3].trim());
                 double price1 = Double.parseDouble(parts[4].trim());
                 String flatType2 = parts[5].trim();
                 int units2 = Integer.parseInt(parts[6].trim());
                 double price2 = Double.parseDouble(parts[7].trim());
                 String openDate = parts[8].trim();
                 String closeDate = parts[9].trim();
                 String manager = parts[10].trim();
                 int officerSlots = Integer.parseInt(parts[11].trim());
                 String pendingRaw = parts[12].trim();
                 String approvedRaw = parts[13].trim();
                 boolean visibility = Boolean.parseBoolean(parts[14].trim());
                 List<String> pendingOfficers = new ArrayList<>();
                 List<String> approvedOfficers = new ArrayList<>();
                 if (!pendingRaw.isEmpty()) {
                     pendingOfficers = Arrays.asList(pendingRaw.replace("\"", "").split("\\|"));
                 }
                 if (!approvedRaw.isEmpty()) {
                     approvedOfficers = Arrays.asList(approvedRaw.replace("\"", "").split("\\|"));                
                     //Instead of "Daniel,Emily" in the CSV,
                     //change to "Daniel|Emily"
                     //because of the line.split(",").
                 }

                 Project project = new Project(projName, neighborhood, flatType1, units1, price1, flatType2, units2, price2, openDate, closeDate, manager, officerSlots, pendingOfficers, approvedOfficers, visibility);
                 projectList.add(project);
             }

             reader.close();
            
         } 
         
         catch (Exception e) {
        	    System.out.println("Something went wrong.");
        	}
     }
     
     public void saveProjectsToFile(String filePath) {
    	    try {
    	        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

    	        // Write the header
    	        writer.write("Project Name,Neighborhood,Type 1,Number of units for Type 1,Selling price for Type 1,Type 2,Number of units for Type 2,Selling price for Type 2,Application opening date,Application closing date,Manager,Officer Slot,Pending Officers,Approved Officers,Visibility\n");


    	        // Loop through the list directly using projectList.get(i)
    	        for (int i = 0; i < projectList.size(); i++) {
    	        	String pendingOfficers = String.join("|", projectList.get(i).getPendingOfficers());
    	            String approvedOfficers = String.join("|", projectList.get(i).getApprovedOfficers());
    	            
    	            writer.write(
    	                projectList.get(i).getProjName() + "," +
    	                projectList.get(i).getNeighborhood() + "," +
    	                projectList.get(i).getFlatType1() + "," +
    	                projectList.get(i).getNumOfUnitsType1() + "," +
    	                projectList.get(i).getPriceType1() + "," +
    	                projectList.get(i).getFlatType2() + "," +
    	                projectList.get(i).getNumOfUnitsType2() + "," +
    	                projectList.get(i).getPriceType2() + "," +
    	                projectList.get(i).getOpenDate() + "," +
    	                projectList.get(i).getCloseDate() + "," +
    	                projectList.get(i).getManagerName() + "," +
    	                projectList.get(i).getOfficerSlots() + "," +
    	                pendingOfficers + "," +
    	                approvedOfficers + "," +
    	                projectList.get(i).getVisibility() + "\n"
    	            );
    	        }

    	        writer.close();
    	        System.out.println("HDB Project list has been updated.");
    	    } 
    	    catch (Exception e) {
    	        e.printStackTrace();  // <-- This will give you exact line and error type
    	    }
    	}*/
     
     
     
     /*For the login part the for loop is to iterate through all the 
      *objects created in the list and then match 1 by 1 until it finds the 
      *matching password and UserID */
      
    public User login(){
    	
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter UserID: ");
        String UserID = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (user.getUserID().equalsIgnoreCase(UserID) &&
                user.getPassword().equals(password)) {
            	
                System.out.println("Login successful!");
                
                return user;  //return your roles object, so if ur UserID
                              //and password matches a Manager object
                              //it will return a manager object. And
                              //you are able to display its jobscope.
            }
        }

        System.out.println("Invalid NRIC or password.");
        return null;
    }

// methods needed for HDBOfficer //

	
    public Applicant getApplicant(String nric) {
        for (User user : userList) {
            if (user instanceof Applicant && user.getUserID().equalsIgnoreCase(nric)) {
                return (Applicant) user;
            }
        }
        return null;
    }

  
    public boolean hasAppliedAsApplicant(String userID, String projectName) {
        for (User user : userList) {
            if (user instanceof Applicant && user.getUserID().equalsIgnoreCase(userID)) {
                Applicant applicant = (Applicant) user;
                return applicant.getAssignedProject() != null &&
                       applicant.getAssignedProject().equalsIgnoreCase(projectName);
            }
        }
        return false;
    }


	
    public boolean isOfficerForOtherProject(String name) {
        for (Project project : projectList) {
            if (project.getPendingOfficers().contains(name) || project.getApprovedOfficers().contains(name)) { //check the pending and approved officer
                return true;
            }
        }
        return false;
    }


    public void submitOfficerApplication(HdbOfficer officer, String projectName) {
        for (Project project : projectList) {
            if (project.getProjName().equalsIgnoreCase(projectName)) {
                project.getPendingOfficers().add(officer.getName()); //I changed to getName
                saveProjectsToFile("ProjectList.csv");
            }
        }
    }


    public String getProjectDetails(String projectName) {
        for (Project project : projectList) {
            if (project.getProjName().equalsIgnoreCase(projectName)) {
                return project.toString(); // Assumes toString() is properly implemented
            }
        }
        return "Project not found.";
    }


    public String getEnquiry(String applicantID, String projectName) {
        // Placeholder - Implement based on your actual Enquiry data structure
        return "Enquiry from " + applicantID + " for project " + projectName;
    }

	

    public void updateFlatCount(String projectName, String flatType, int quantity) {
        for (Project project : projectList) {
            if (project.getProjName().equalsIgnoreCase(projectName)) {
                if (flatType.equalsIgnoreCase(project.getFlatType1())) {
                    project.setNumOfUnitsType1(project.getNumOfUnitsType1() - quantity);
                } else if (flatType.equalsIgnoreCase(project.getFlatType2())) {
                    project.setNumOfUnitsType2(project.getNumOfUnitsType2() - quantity);
                }
                saveProjectsToFile("ProjectList.csv");
            }
        }
    }

	

    public boolean isApplicantSuccessful(String applicantID, String projectName) {
        // For now, assume any applicant with a matching assignedProject is successful
        for (User user : userList) {
            if (user instanceof Applicant) {
                Applicant a = (Applicant) user;
                if (a.getUserID().equalsIgnoreCase(applicantID) &&
                    projectName.equalsIgnoreCase(a.getAssignedProject())) {
                    return true;
                }
            }
        }
        return false;
    }


	
    public void updateApplicantProjectStatus(String applicantID, String status) {
        Applicant applicant = getApplicant(applicantID);
        if (applicant != null) {
            applicant.setProjectStatus(status);
        }
    }


	
    public void updateApplicantFlatType(String applicantID, String flatType) {
        Applicant applicant = getApplicant(applicantID);
        if (applicant != null) {
            applicant.setFlatType(flatType);
        }
    }


	
    public void loadUserDate(){

    }

    public void createNewUser(){

    }

    public void loadProj(){

    }
    
    public List<Project> getProjectList() {
    	return projectList;
    }

    public List<User> getUserList() {
        return userList;
    }

// ADDED STUFF//
    public void addEnquiry(Enquiry enquiry) {
        enquiryList.add(enquiry);
    }

    public String getEnquiry(String applicantID, String projectName) {
        int userID = getUserIDAsInt(applicantID);
        int projectID = getProjectID(projectName);

        for (Enquiry e : enquiryList) {
            if (e.userID == userID && e.projectID == projectID) {
                return "Enquiry:\n" + e.enquireText + "\nResponse:\n" + e.enquiryResponse;
            }
        }

        return "No enquiry found for " + applicantID + " on project " + projectName;
    }

    private int getUserIDAsInt(String userID) {
        try {
            return Integer.parseInt(userID.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int getProjectID(String projectName) {
        for (int i = 0; i < projectList.size(); i++) {
            if (projectList.get(i).getProjName().equalsIgnoreCase(projectName)) {
                return i;
            }
        }
        return -1;
    }


    
    public static void main(String[] args) {
        BTOSystem system = new BTOSystem();
        
        /* The 3 lines of code below is to load all users from their respective CSV files:
        ManagerList.csv → creates HdbManager objects
        ApplicantList.csv → creates Applicant objects
        OfficerList.csv → creates HdbOfficer objects
        These users are stored in userList for login verification later. */
        system.loadUsersFromFile("ManagerList.csv", "manager"); 
        system.loadUsersFromFile("ApplicantList.csv", "applicant");
        system.loadUsersFromFile("OfficerList.csv", "officer");
        
        system.loadProjectsFromFile("ProjectList.csv"); //Read the Project Data csv file
        
        User user = system.login();
        if (user != null) {
        	/* The line below is a method call to call the displayjobscope
        	 * method of your individual roles jobscopes. Also
        	 * We have to pass in the system object so that in 
        	 * your respective role classes you will be able to 
        	 * use the BTOSystem variables like accessing the
        	 * projectList or userList.*/
            user.displayJobscope(system); //During runtime, user will be
                                          //treated as your roles object
                                          //calling its own overwritten
                                          //displayJobscope method(polymorphism)
                                                               
        }
   
    }
}


