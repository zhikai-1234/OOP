import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BTOSystem {
	
     private List<User> userList;
     private List<Project> projectList;
     
     public BTOSystem() { 
         userList = new ArrayList<>();
         projectList = new ArrayList<>();
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

     public void loadUserDataFromFile(String filePath, String role) {
         try {
             BufferedReader reader = new BufferedReader(new FileReader(filePath)); 
             String line;
             boolean isFirstLine = true;

             while ((line = reader.readLine()) != null) {
                 if (isFirstLine) {
                     isFirstLine = false; // skip header
                     continue;
                 }

                 String[] parts = line.split(",");
                 if (parts.length < 5) {
                     System.out.println("Skipping malformed line: " + line);
                     continue;
                 }

                 String UserID = parts[1].trim(); // NRIC
                 String age = parts[2].trim();
                 String maritalStatus = parts[3].trim();
                 String password = parts[4].trim();

                 User user = null;

                 if (role.equalsIgnoreCase("manager")) {
                     user = new HdbManager(UserID, age, maritalStatus, password);
                 } else if (role.equalsIgnoreCase("applicant")) {
                     user = new Applicant(UserID, age, maritalStatus, password);
                 } else if (role.equalsIgnoreCase("officer")) {
                     user = new HdbOfficer(UserID, age, maritalStatus, password);
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
     
     public void loadProjectDataFromFile(String filePath) {
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
                 if (parts.length < 13) {
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
                 String officerSlots = parts[11].trim();
                 List<String> officers = Arrays.asList(parts[12].split("\\s*,\\s*"));

                 Project project = new Project(projName, neighborhood, flatType1, units1, price1,
                         flatType2, units2, price2, openDate, closeDate, manager, officerSlots, officers);

                 projectList.add(project);
             }

             reader.close();
            
         } 
         
         catch (Exception e) {
        	    System.out.println("Something went wrong.");
        	}
     }
     
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
    

    public void loadUserDate(){

    }

    public void createNewUser(){

    }

    public void loadProj(){

    }
    
    public static void main(String[] args) {
        BTOSystem system = new BTOSystem();
        
        /* The 3 lines of code below is to load all users from their respective CSV files:
        ManagerList.csv → creates HdbManager objects
        ApplicantList.csv → creates Applicant objects
        OfficerList.csv → creates HdbOfficer objects
        These users are stored in userList for login verification later. */
        system.loadUserDataFromFile("ManagerList.csv", "manager"); 
        system.loadUserDataFromFile("ApplicantList.csv", "applicant");
        system.loadUserDataFromFile("OfficerList.csv", "officer");
        
        system.loadProjectDataFromFile("ProjectList.csv"); //Read the Project Data csv file
        
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


