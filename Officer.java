public class Officer extends Applicant {
    private TemplateProject appliedProjectAsOfficer;
    private TemplateProject assignedProjectAsOfficer;

    public Officer(String name, String userID, int age, String maritalStatus, String password) {
        super(name, userID, age, maritalStatus, password);
        this.appliedProjectAsOfficer = null;
        this.assignedProjectAsOfficer = null;
    }

    //To prevent applying as applicant for same project
    @Override
    public void setProjApplied(TemplateProject p) {
        if (p.equals(this.getAssignedProjectAsOfficer())) {
            throw new IllegalStateException("Cannot apply for the same project you're handling as officer.");
        }
        super.setProjApplied(p);
    }

    // GETTERS //
    public TemplateProject getAppliedProjectAsOfficer() {
        return this.appliedProjectAsOfficer;
    }
    public TemplateProject getAssignedProjectAsOfficer() {
        return this.assignedProjectAsOfficer;
    }

    // SETTERS //
    public void setAppliedProjectAsOfficer(TemplateProject p) {
        this.appliedProjectAsOfficer = p;
    }
    public void setAssignedProjectAsOfficer(TemplateProject p) {
        this.assignedProjectAsOfficer = p;
    }
}
