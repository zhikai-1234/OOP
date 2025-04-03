import java.util.Scanner;

public class Enquiry {
    public String enquireText;
    public String enquiryResponse;
    public String editChoice;
    public int userID, projectID;

    Scanner sc = new Scanner(System.in);

    public Enquiry(int uID, int pID) {
        this.enquireText = "No enquiry";
        this.enquiryResponse = "No Response";
        this.userID = uID;
        this.projectID = pID;
    }

    public void submitEnquiry(){
        if (this.enquireText.equals("No enquiry")) {
            System.out.print("Type enquiry here: ");
            this.enquireText = sc.nextLine();
        }
        else {
            System.out.println("Would you like to edit your inquiry? (Y/N)");
            this.enquireText = sc.nextLine();
        }
    }
        
    public void editEnquiry(){
        System.out.print("Enter your new inquiry: ");
        this.enquireText = sc.nextLine();
    }

    //public void deleteEnquiry(){}

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
        
}
