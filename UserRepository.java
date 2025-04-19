import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserRepository {

    private List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    public void loadUsers(String filePath, String userType) {
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
                 if (parts.length < 4) continue;

                 String name = parts[0].trim();
                 String nric = parts[1].trim();
                 int age = Integer.parseInt(parts[2].trim());
                 String maritalStatus = parts[3].trim();

                 switch (userType.toLowerCase()) {

                    case "applicant" -> users.add(new Applicant(name, nric, age, maritalStatus));

                    case "officer" -> users.add(new Officer(name, nric, age, maritalStatus));

                    case "manager" -> users.add(new Manager(name, nric, age, maritalStatus));
                 }
            } reader.close();
         } 
         catch (IOException e) {
             System.out.println("Failed to read user list");
             e.printStackTrace();
            }
    }

    public <T extends User> T login(List<T> users, Scanner scanner) {

        System.out.print("Enter UserID: ");  
        String userID = scanner.nextLine().trim();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        for (T u : users) {
            if (u.getUserID().equalsIgnoreCase(userID) &&
                u.getPassword().equals(password)) {
                System.out.println("Login successful!");
                return u;
            }
        }

        System.out.println("Invalid login!");
        return null;
    }



    // GETTERS //
    public List<Applicant> getAllApplicants() {
        List <Applicant> allApplicants = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Applicant applicant) {
                allApplicants.add(applicant);
            }
        }
        return allApplicants;
    }

    public List<Officer> getAllOfficers() {
        List <Officer> allOfficers = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Officer officer) {
                allOfficers.add(officer);
            }
        }
        return allOfficers;
    }

    public List<Manager> getAllManagers() {
        List <Manager> allManagers = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Manager manager) {
                allManagers.add(manager);
            }
        }
        return allManagers;
    }

    public Applicant getApplicantByUserID(String userID) {
        List<Applicant> allApplicants = getAllApplicants();
        for (Applicant a : allApplicants) {
            if (a.getUserID().equals(userID)) {
                return a;
            }
        }
        System.out.println("Applicant not found");
        return null;
    }

    public Officer getOfficerByUserID(String userID) {
        List<Officer> allOfficers = getAllOfficers();
        for (Officer o : allOfficers) {
            if (o.getUserID().equals(userID)) {
                return o;
            }
        }
        System.out.println("Officer not found");
        return null;
    }

    public Manager getManagerByUserID(String userID) {
        List<Manager> allManagers = getAllManagers();
        for (Manager m : allManagers) {
            if (m.getUserID().equals(userID)) {
                return m;
            }
        }
        System.out.println("Manager not found");
        return null;
    }

    public List<User> getAllUsers() {
        return this.users;
    }
}