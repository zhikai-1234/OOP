public class Applicant extends User{
    private String projApplied;
    private String applicationStatus;
    private String[] enquiryList;
    
    public Applicant(String UserID, String age, String maritalStatus, String password) {
        super(UserID, age, maritalStatus, password);
    }

    public void applyForProject(){

    }

    public void viewStatus(){

    }

    public void withdrawApplication(){

    }

    public void bookFlat(){

    }
    
    public void displayJobscope(BTOSystem system) {
        System.out.println("Welcome, Applicant ");
        System.out.println("1. View Projects");
        System.out.println("2. Apply for Project");
        System.out.println("etc etc");
 
    }

}