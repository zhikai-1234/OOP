public class HdbManager extends User {
    private String[] managedProj;
    private String[] approvedOfficer;

    public HdbManager(String UserID, String age, String maritalStatus, String password) {
        super(UserID, age, maritalStatus, password);
    }
    
    public void createProj(){

    }

    public void editProj(){

    }

    public void deleteProj(){

    }

    public void approveOfficerRegistration(){

    }

    public void approveApplicantApplication(){

    }
    
    public void displayJobscope() {
        System.out.println("Welcome, Manager!");
        System.out.println("1. Create or Edit Projects");
        System.out.println("2. Toggle Project Visibility");
        System.out.println("etc etc");
    }
}
