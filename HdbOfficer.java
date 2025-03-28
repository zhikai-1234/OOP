public class HdbOfficer extends User{
    private String[] projHandled;
    private String registrationStatus;
    
    public HdbOfficer(String UserID, String age, String maritalStatus, String password) {
        super(UserID, age, maritalStatus, password);
    }
    
    public void registerForProj(){

    }

    public void viewProjectDetails(){

    }

    public void respondToEnquiries(){

    }

    public void generateReceipt(){

    }
    
    public void displayJobscope(BTOSystem system) {
        System.out.println("Welcome, Officer ");
        System.out.println("1. Apply project");
        System.out.println("2. Manage Project Allocation");
        System.out.println("etc etc");
    }

}
