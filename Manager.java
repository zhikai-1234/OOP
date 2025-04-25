
public class Manager extends User {

    private TemplateProject projectInCharge;

    public Manager(String name, String userID, int age, String maritalStatus, String password) {
        super(name, userID, age, maritalStatus, password);
        this.projectInCharge = null;
    }

    public void setProjectInCharge(TemplateProject p) {
        this.projectInCharge = p;
    }

    public TemplateProject getProjectInCharge() {
        return this.projectInCharge;
    }
} 
    
