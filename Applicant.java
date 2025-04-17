import java.util.Scanner;

public class Applicant extends User{
    private LiveProject projApplied;
    public String applicationStatus;
    private int eligibilityStatus;
    private int appliedFlatType;

    Scanner scan = new Scanner(System.in);

    public Applicant(String name, String userID, int age, String maritalStatus) {
        super(name, userID, age, maritalStatus);
        this.projApplied = null;
        this.applicationStatus = "No Project Applied";
        this.eligibilityStatus = checkEligibility();
        this.appliedFlatType = -1;
    }

    // ELIGIBILITY METHODS //

    public int getEligibilityStatus(){
        return eligibilityStatus;
    }

    private int checkEligibility(){
        int age = this.getAge();
        if(this.getMaritalStatus().equals("Single") && age >= 35){
            return 1;
        }
        else if(this.getMaritalStatus().equals("Married") && age >= 21){
            return 2;
        }
        return 0;
    }
}
