import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnquiryHandler {

    private ProjectManager pm;

    public EnquiryHandler(ProjectManager projManager) {
        this.pm = projManager;
    }

    public void submitEnquiry(Applicant a, Scanner sc) {
        
        int i = 1;
        for (TemplateProject p : pm.getTemplateProjects()) {
            System.out.printf("[%d] %s\n", i, p.getName());
            i++;
        }
        System.out.print("Enter the number for the project you want to submit an enquiry for: ");

        int projChoice = sc.nextInt();
        sc.nextLine();
        TemplateProject selectedProject = null;
        selectedProject = pm.getTemplateProjects().get(projChoice - 1);

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
    
        for (TemplateProject project : pm.getTemplateProjects()) {
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
    
    public void replyToEnquiriesOfficer(Officer o, TemplateProject proj, Scanner sc) { // must pass officer's assigned project as argument
        if (proj.getEnquiries().isEmpty()) {
            System.out.println("No enquiries found for this project.");
        }
        else {
            int i = 1;
            System.out.println("Displaying all available enquiries for this project...");
            System.out.println();
            for (Enquiry e : proj.getEnquiries()) {
                System.out.printf("[%d] Question: %s\n", i, e.getEnquiry());
                i++;
            }
            System.out.println("Enter the number of the enquiry you would like to reply to: ");
            int enquiryChoice = sc.nextInt();
            sc.nextLine();
            System.out.println("Now replying to:");
            System.out.println(proj.getEnquiries().get(enquiryChoice - 1).getEnquiry());
            System.out.println();
            System.out.println("Enter your response below:");
            String reply = sc.nextLine();
            proj.getEnquiries().get(enquiryChoice - 1).setResponse(reply);
            System.out.println("\nReply submitted successfully.");
        }
    }

    // FOR MANAGER //
    public void showAllEnquiries() {
        for (TemplateProject p : pm.getTemplateProjects()) {
            System.out.println("==============================================");
            System.out.println("Project name: " + p.getName());
            int i = 1;
            for (Enquiry e : p.getEnquiries()) {
                System.out.printf("[%d] Question: %s | Response: %s", i, e.getEnquiry(), e.getResponse());
            }
            System.out.println("==============================================");
        }
    }
    // must pass officer's assigned project as argument
    public void replyToEnquiriesManager(Manager m, List<TemplateProject> projects, Scanner sc) {
        if (projects.isEmpty()) {
            System.out.println("You are not managing any projects.");
            return;
        }

        System.out.println("Select a project to view its enquiries:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.printf("[%d] %s\n", i + 1, projects.get(i).getName());
        }

        System.out.print("Enter your choice: ");
        int projChoice = sc.nextInt();
        sc.nextLine();

        if (projChoice < 1 || projChoice > projects.size()) {
            System.out.println("Invalid project selection.");
            return;
        }

        TemplateProject selectedProject = projects.get(projChoice - 1);

        if (selectedProject.getEnquiries().isEmpty()) {
            System.out.println("No enquiries found for this project.");
            return;
        }

        System.out.println("Displaying all enquiries for " + selectedProject.getName() + ":");
        for (int i = 0; i < selectedProject.getEnquiries().size(); i++) {
            Enquiry e = selectedProject.getEnquiries().get(i);
            System.out.printf("[%d] Question: %s\n", i + 1, e.getEnquiry());
            System.out.printf("    Response: %s\n", e.getResponse());
        }

        System.out.print("Enter enquiry number to reply (0 to cancel): ");
        int enquiryIndex = sc.nextInt();
        sc.nextLine();

        if (enquiryIndex == 0) return;

        if (enquiryIndex < 1 || enquiryIndex > selectedProject.getEnquiries().size()) {
            System.out.println("Invalid enquiry selection.");
            return;
        }

        Enquiry selectedEnquiry = selectedProject.getEnquiries().get(enquiryIndex - 1);
        System.out.println("Now replying to: " + selectedEnquiry.getEnquiry());
        System.out.print("Enter your response: ");
        String response = sc.nextLine();
        selectedEnquiry.setResponse(response);
        System.out.println("Reply submitted successfully.");
    }

}