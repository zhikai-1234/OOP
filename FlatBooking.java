import java.util.Scanner;

public class FlatBooking {
    
    public void viewProjects(User user, Project[] projects) {
        System.out.println("Available Projects:");

        int count = 1;
        for (Project project : projects) {
            if (user instanceof Applicant) {
                Applicant applicant = (Applicant) user;
                int eligibilityStatus = applicant.getEligibilityStatus();

                if (eligibilityStatus == 1) { // Single and above 35
                    if (project.getFlatType1().equals("2-Room") && project.getVisibility()) {
                        System.out.println(count + ". " + project.getProjName() + " (2-Room)");
                        count++;
                    }
                } else if (eligibilityStatus == 2 && project.getVisibility()) { // Married and above 21
                    System.out.println(count + ". " + project.getProjName() + " (2-Room), (3-Room)");
                    count++;
                } else{
                    System.out.println("You are not eligible for any project.");
                }
            }
        }
    }

    public void selectProject(User user, Project[] projects) {
        viewProjects(user, projects);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please select the project number you want to apply for: ");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= projects.length) {
            Project selectedProject = projects[choice - 1];

            if (user instanceof Applicant) {
                Applicant applicant = (Applicant) user;

                System.out.println("Available flat types for this project:");
                if (selectedProject.getFlatType1().equals("2-Room")) {
                    System.out.println("1. 2-Room");
                }
                if (selectedProject.getFlatType2().equals("3-Room")) {
                    System.out.println("2. 3-Room");
                }

                System.out.print("Please select flat type (1 for 2-Room, 2 for 3-Room): ");
                int flatChoice = scanner.nextInt();
                String flatType = flatChoice == 1 ? "2-Room" : "3-Room";

                applicant.applyForProject(selectedProject, flatType);
            }
        } else {
            System.out.println("Invalid selection.");
        }
    }

}
