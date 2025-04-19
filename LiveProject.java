import java.util.ArrayList;
import java.util.List;

public class LiveProject extends TemplateProject {

    // DEFAULT ATTRIBUTES //
    private String name;
    private String neighbourhood;
    private String type1;
    private int type1price;
    private String type2;
    private int type2price;
    private String openDate;
    private String closeDate;
    private String managerName;
    private int noOfOfficers;
    private boolean visibility;

    // LIVE ATTRIBUTES //
    private List<Officer> pendingOfficers;
    private List<Officer> approvedOfficers;

    // USER ATTRIBUTES //
    private Applicant applicant;

    public LiveProject(String name, String neighbourhood, String type1, int nType1, double type1price, String type2, 
    int nType2, double type2price, String openDate, String closeDate, String managerName, int noOfOfficers, boolean visibility) {
        super(name, neighbourhood, type1, nType1, type1price, type2, nType2,
     type2price, openDate, closeDate, managerName, noOfOfficers, visibility);
        this.pendingOfficers = new ArrayList<>();
        this.approvedOfficers = new ArrayList<>();
        this.applicant = null;
    }

    public void toggleVisibility() {
        this.visibility = !this.visibility;
    }

    // GETTERS //
    public String getName() {
        return this.name;
    }

    public int getnType1() {
        return super.getNumOfType1();
    }
    public int getnType2() {
        return super.getNumOfType2();
    }
}