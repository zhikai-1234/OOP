import java.util.ArrayList;
import java.util.List;

public class TemplateProject {

    private String name;
    private String neighbourhood;
    private String type1;
    private int nType1;
    private double type1price;
    private String type2;
    private int nType2;
    private double type2price;
    private String openDate;
    private String closeDate;
    private String managerName;
    private int nOfficers;
    private boolean visibility;
    private List<Enquiry> enquiries;

    public TemplateProject(String name, String neighbourhood, String type1, int nType1, double type1price, String type2, int nType2,
     double type2price, String openDate, String closeDate, String managerName, int nOfficers, boolean visibility) {
        this.name = name;
        this.neighbourhood = neighbourhood;
        this.type1 = type1;
        this.type1price = type1price;
        this.type2 = type2;
        this.type2price = type2price;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.managerName = managerName;
        this.nOfficers = nOfficers;
        this.visibility = visibility;
        this.enquiries = new ArrayList<>();
    }

    // GETTERS //

    public String getName() {
        return this.name;
    }

    public String getNeighbourhood() {
        return this.neighbourhood;
    }

    public String getType1() {
        return this.type1;
    }

    public int getNumOfType1() {
        return this.nType1;
    }

    public double getType1Price() {
        return this.type1price;
    }

    public String getType2() {
        return this.type2;
    }

    public int getNumOfType2() {
        return this.nType2;
    }

    public double getType2Price() {
        return this.type2price;
    }

    public String getOpenDate() {
        return this.openDate;
    }

    public String getCloseDate() {
        return this.closeDate;
    }

    public String getManagerName() {
        return this.managerName;
    }

    public int getNumOfficers() {
        return this.nOfficers;
    }

    public boolean getVisibility() {
        return this.visibility;
    }

    public List<Enquiry> getEnquiries() {
        return this.enquiries;
    }

    // SETTERS (KEEP TRACK OF NUMBER OF FLATS ONLY) //
    public void setNumOfType1(int n) {
        this.nType1 = n;
    }

    public void setNumOfType2(int n) {
        this.nType2 = n;
    }

    public void addEnquiry(Enquiry e) {
        this.enquiries.add(e);
    }

    // DISPLAY //
    public void displayProjectDetails() {
    System.out.println("---------------------------------------");
    System.out.printf("Project Name: %s\n", this.getName());
    System.out.printf("Neighborhood: %s\n", this.getNeighbourhood());
    System.out.println();
    System.out.printf("Flat Type 1: %s\n", this.getType1());
    System.out.printf("Units: %d\n", this.getNumOfType1());
    System.out.printf("Price: $%.2f\n", this.getType1Price());
    System.out.println();
    System.out.printf("Flat Type 2: %s\n", this.getType2());
    System.out.printf("Units: %d\n", this.getNumOfType2());
    System.out.printf("Price: $%.2f\n", this.getType2Price());
    System.out.println();
    System.out.println("Application Period: " + this.getOpenDate() + " to " + this.getCloseDate());
    System.out.printf("Manager in charge: %s\n" + this.getManagerName());
    }
}