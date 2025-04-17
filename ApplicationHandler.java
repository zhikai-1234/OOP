import java.util.ArrayList;
import java.util.List;

public class ApplicationHandler {

    private List<Applicant> applicants;
    private List<LiveProject> liveProjects;
    private List<TemplateProject> templateProjects;

    ProjectManager pm = new ProjectManager();

    public ApplicationHandler() {
        this.applicants = new ArrayList<>();
        this.liveProjects = new ArrayList<>();
        this.templateProjects = pm.getTemplateProjects();
    }
}

/*   public void applyForProject(Project project, String flatType){
    if(projApplied != null){
        System.out.println("You have already applied for an project.");
        return;
    }

        if (eligibilityStatus == 1 && flatType.equals("2-Room")) {
            this.projApplied = project;
            appliedFlatType = 1;
            applicationStatus = "Applied";
            project.addApplicant(this, 1);  // Add applicant to 2-Room list
            System.out.println("You have successfully applied for " + project.getProjName() + " (2-Room).");
        } else if (eligibilityStatus == 2) {  // Eligibility 2: can apply for both 2-Room and 3-Room
            if (flatType.equals("2-Room")) {
                appliedFlatType = 1;
                this.projApplied = project;
                applicationStatus = "Applied";
                project.addApplicant(this, 1);  // Add to 2-Room applicants list
                System.out.println("You have successfully applied for " + project.getProjName() + " (2-Room).");
            } else if (flatType.equals("3-Room")) {
                this.projApplied = project;
                appliedFlatType = 2; 
                applicationStatus = "Applied";
                project.addApplicant(this, 2);  // Add to 3-Room applicants list
                System.out.println("You have successfully applied for " + project.getProjName() + " (3-Room).");
            }
        } else {
            System.out.println("You do not meet the eligibility requirements.");
        }
    }

// NOTE: overriding of getters and setters from User is unneccesary

    public void viewStatus(){
        if (projApplied == null) {
            System.out.println("You have not applied for any project.");
        } else {
            System.out.println("Project: " + projApplied.getProjName());
            System.out.println("Status: " + applicationStatus);
    }
}

    public void setApplicationStatus(String status) {
        this.applicationStatus = status;
    }

    public void withdrawApplication() {
        if (projApplied == null) {
            System.out.println("You have not applied for any project.");
            return;
        }

        else{
            projApplied.removeApplicant(this, appliedFlatType);
            projApplied = null;
            applicationStatus = null;
        
            System.out.println("Your application has been successfully withdrawn.");
        }
    }

    

    public void bookFlat(){

    }
} */