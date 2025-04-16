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
                 if (parts.length < 15) continue;

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
             System.out.println("Failed to read project list");
            }
    }

    public User login(User user) {
    
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter UserID: ");
        String userID = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User u : users) {
            if (u.getUserID().equalsIgnoreCase(userID) &&
                u.getPassword().equals(password)) {
                System.out.println("Login successful!");
                return u;
            }
        }
        System.out.println("Invalid login!");
        return null;            //return your roles object, so if ur UserID
                                //and password matches a Manager object
                                //it will return a manager object. And
                                //you are able to display its jobscope.
                                //if the user is wrong, will return null.
    }
}