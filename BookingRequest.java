public class BookingRequest {
    private Applicant applicant;
    private TemplateProject templateProject;
    private int flatType;
    private boolean approval;

    public BookingRequest(Applicant a, TemplateProject p, int flatType) {
        this.applicant = a;
        this.templateProject = p;
        this.flatType = flatType;
        this.approval = false;
    }
    
    // GETTERS //
    public Applicant getApplicant() { return applicant; }
    public TemplateProject getTemplateProject() { return templateProject; }
    public int getFlatType() { return flatType; }

    // SETTERS //
    public void approveBooking() {
        this.approval = true;
    }
}
