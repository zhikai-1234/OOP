import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnquiryHandler {

    private List<Applicant> applicants;
    private List<TemplateProject> templateProjects;

    ProjectManager pm = new ProjectManager();

    public EnquiryHandler() {
        this.applicants = new ArrayList<>();
        this.templateProjects = pm.getTemplateProjects();
    }

    public void submitEnquiry(Applicant a) {
        Scanner sc = new Scanner(System.in);
    
        for (TemplateProject p : templateProjects) {
            pm.displayProjectDetails(p);
        }
        System.out.print("Enter the project name you want to submit an enquiry for: ");
        String projectName = sc.nextLine().trim();
        TemplateProject selectedProject = null;
        for (TemplateProject p : templateProjects) {
            if (p.getName().equalsIgnoreCase(projectName)) {
                selectedProject = p;
                break;
            }
        }
        if (selectedProject == null) {
            System.out.println("Project not found. Please check the name and try again.");
            return;
        }
        System.out.print("Enter your enquiry: ");
        String enquiryText = sc.nextLine();
        Enquiry enquiry = new Enquiry(a.getUserID(), selectedProject.getName());
        enquiry.enquireText = enquiryText;
        selectedProject.addEnquiry(enquiry);
        System.out.println("Enquiry submitted successfully!");
    }

    public void displayAndManageUserEnquiries(Applicant applicant) {
        Scanner sc = new Scanner(System.in);
        List<TemplateProject> userProjects = new ArrayList<>();
        List<Enquiry> userEnquiries = new ArrayList<>();
    
        for (TemplateProject project : templateProjects) {
            for (Enquiry enquiry : project.getEnquiries()) {
                if (enquiry.getUserID().equals(applicant.getUserID())) {
                    userProjects.add(project);
                    userEnquiries.add(enquiry);
                }
            }
        }
        if (userEnquiries.isEmpty()) {
            System.out.println("You have not submitted any enquiries.");
            return;
        }
    
        // Display all enquiries
        System.out.println("Your enquiries:");
        for (int i = 0; i < userEnquiries.size(); i++) {
            System.out.println("[" + (i + 1) + "] Project: " + userProjects.get(i).getName());
            System.out.println("    Enquiry: " + userEnquiries.get(i).getEnquiry());
            System.out.println("    Response: " + userEnquiries.get(i).getResponse());
            System.out.println("-----------------------------------");
        }
    
        System.out.print("Enter enquiry number to edit/withdraw (0 to exit): ");
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice == 0) return;
        if (choice < 1 || choice > userEnquiries.size()) {
            System.out.println("Invalid selection.");
            return;
        }
    
        Enquiry selectedEnquiry = userEnquiries.get(choice - 1);
        TemplateProject selectedProject = userProjects.get(choice - 1);
    
        System.out.print("[E] Edit  [W] Withdraw  [X] Cancel: ");
        String action = sc.nextLine().trim().toUpperCase();
    
        switch (action) {
            case "E":
                System.out.print("Enter new enquiry text: ");
                String newText = sc.nextLine();
                selectedEnquiry.enquireText = newText;
                System.out.println("Enquiry updated!");
                break;
            case "W":
                selectedProject.getEnquiries().remove(selectedEnquiry);
                System.out.println("Enquiry withdrawn!");
                break;
            case "X":
                System.out.println("Operation cancelled.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
}