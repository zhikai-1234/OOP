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
    private int noOfOfficers;
    private boolean visibility;

    // LIVE ATTRIBUTES //
    private Manager manager;
    private List<Officer> pendingOfficers;
    private List<Officer> approvedOfficers;
    private int nType1;
    private int nType2;


    public LiveProject(String name, String neighbourhood, String type1, int nType1, double type1price, String type2, 
    int nType2, double type2price, String openDate, String closeDate, int noOfOfficers, boolean visibility, Manager manager) {
        super(name, neighbourhood, type1, nType1, type1price, type2, nType2,
     type2price, openDate, closeDate, noOfOfficers, visibility);
        this.manager = manager;
        this.pendingOfficers = new ArrayList<>();
        this.approvedOfficers = new ArrayList<>();
    }
}