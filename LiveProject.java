
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

    // USER ATTRIBUTES //
    private Applicant applicant;

    public LiveProject(String name, String neighbourhood, String type1, int nType1, double type1price, String type2, 
    int nType2, double type2price, String openDate, String closeDate, String managerName, int noOfOfficers, boolean visibility, Applicant a) {
        super(name, neighbourhood, type1, nType1, type1price, type2, nType2,
     type2price, openDate, closeDate, managerName, noOfOfficers, visibility);
        this.applicant = a;
    }

    public void toggleVisibility() {
        this.visibility = !this.visibility;
    }

    // GETTERS //
    public Applicant getApplicant() {
        return this.applicant;
    }
}