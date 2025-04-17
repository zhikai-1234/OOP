import java.util.ArrayList;
import java.util.List;

public class EnquiryHandler {

    private List<Applicant> applicants;
    private List<TemplateProject> templateProjects;

    ProjectManager pm = new ProjectManager();

    public EnquiryHandler() {
        this.applicants = new ArrayList<>();
        this.templateProjects = pm.getTemplateProjects();
    }

    public void submitEnquiry(Applicant a) {
        for (TemplateProject p : templateProjects) {
        pm.displayProjectDetails(p);
        }
    } 
}