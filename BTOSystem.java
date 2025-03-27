import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BTOSystem {
     private List<User> userList;
     private List<Project> projectList;
     
     public BTOSystem() { 
         userList = new ArrayList<>();
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
         } catch (IOException e) {
             System.out.println("Error reading user file: " + e.getMessage());
         }
     }
     
     
     /*For the login part the for loop is to iterate through all the 
     objects created in the list and then match 1 by 1 until it finds the 
     matching password and UserID*/
    public void login(){
    	
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
                
                user.displayJobscope();  // display all the jobscope your role does.
                                        // I only put some of ur jobscope

                return;
            }
        }

        System.out.println("Invalid NRIC or password.");
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
        
        system.login();
    }
}


