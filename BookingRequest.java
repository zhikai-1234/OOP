public class BookingRequest {
    private Applicant applicant;
    private TemplateProject templateProject;
    private int flatType;

    public BookingRequest(Applicant a, TemplateProject p, int flatType) {
        this.applicant = a;
        this.templateProject = p;
        this.flatType = flatType;
    }
    
    // Getters
    public Applicant getApplicant() { return applicant; }
    public TemplateProject getTemplateProject() { return templateProject; }
    public int getFlatType() { return flatType; }
}
