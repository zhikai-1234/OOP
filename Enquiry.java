import java.util.Scanner;

public class Enquiry {
    public String enquireText;
    public String enquiryResponse;
    public String editChoice;
    public String userID;

    Scanner sc = new Scanner(System.in);

    public Enquiry(String uID, String Project) {
        this.enquireText = "No enquiry";
        this.enquiryResponse = "No response";
        this.userID = uID;
        this.project = Project;
    }

    // GETTERS //
    public String getEnquiry() {
        return this.enquireText;
    }

    public String getResponse() {
        return this.enquiryResponse;
    }

    public String getUserID() {
        return this.userID;
        
    }public String getProject() {
        return this.project;
    }
}

    /* public void submitEnquiry(){
        if (this.enquireText.equals("No enquiry")) {
            System.out.print("Type enquiry here: ");
            this.enquireText = sc.nextLine();
        }
        else {
            System.out.println("Would you like to edit your inquiry? (Y/N)");
            this.editChoice = sc.nextLine();
            if (this.editChoice.equalsIgnoreCase("y")) {
                this.editEnquiry();
            }
            else {
                System.out.println("Enquiry exited");
            }
        }
    }
        
    public void editEnquiry() {
        System.out.print("Enter your new inquiry: ");
        this.enquireText = sc.nextLine();
    }

    public void deleteEnquiry() {
        if (this.enquireText.equals("No enquiry")) {
            System.out.println("Enquiry is empty");
        }
        else {
            System.out.println("Enquiry deleted.");
        }
    }

    public void showEnquiry() {
        System.out.printf("%-10s %-10s\n", "User ID", "Project ID");
        System.out.println("Enquiry:");
        System.out.print(this.enquireText + '\n');
    }

    public void respondToEnquiry() {
        System.out.println("Response: ");
        this.enquiryResponse = sc.nextLine();    
    }

    public void showResponse() {
        this.showEnquiry();
        System.out.println("Response: " + this.enquiryResponse);
    }
        
} */
