import java.util.List;

public class Project {
    private String ProjName;
    private String neighborhood;
    
    private String flatType1;
    private int numOfUnitsType1;
    private double priceType1;
    
    private String flatType2;
    private int numOfUnitsType2;
    private double priceType2;
    
    private String openDate, closeDate; //save in the CSV file as (yyyy-mm-dd)
    private String managerName;
    private int officerSlots;
    private List<String> officers;
    
    private boolean visibility;
    
    public Project(String ProjName, String neighborhood, String flatType1, int numOfUnitsType1, double priceType1, String flatType2, int numOfUnitsType2, double priceType2, String openDate, String closeDate, String managerName, int officerSlots, List<String> officers) {
    
    	this.ProjName = ProjName;
        this.neighborhood = neighborhood;
        this.flatType1 = flatType1;
        this.numOfUnitsType1 = numOfUnitsType1;
        this.priceType1 = priceType1;
        this.flatType2 = flatType2;
        this.numOfUnitsType2 = numOfUnitsType2;
        this.priceType2 = priceType2;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.managerName = managerName;
        this.officerSlots = officerSlots;
        this.officers = officers;
    }

    public void toggleVisibility(){

    }
    public boolean getVisibility(){
        return this.visibility;
    }
    
    public void viewApplicants(){

    }
    
    public void manageOfficers(){
        
    }
    
    public String getProjName() {
        return ProjName;
    }

    public void setProjName(String projName) {
        this.ProjName = projName;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getFlatType1() {
        return flatType1;
    }

    public void setFlatType1(String flatType1) {
        this.flatType1 = flatType1;
    }

    public int getNumOfUnitsType1() {
        return numOfUnitsType1;
    }

    public void setNumOfUnitsType1(int numOfUnitsType1) {
        this.numOfUnitsType1 = numOfUnitsType1;
    }

    public double getPriceType1() {
        return priceType1;
    }

    public void setPriceType1(double priceType1) {
        this.priceType1 = priceType1;
    }

    public String getFlatType2() {
        return flatType2;
    }

    public void setFlatType2(String flatType2) {
        this.flatType2 = flatType2;
    }

    public int getNumOfUnitsType2() {
        return numOfUnitsType2;
    }

    public void setNumOfUnitsType2(int numOfUnitsType2) {
        this.numOfUnitsType2 = numOfUnitsType2;
    }

    public double getPriceType2() {
        return priceType2;
    }

    public void setPriceType2(double priceType2) {
        this.priceType2 = priceType2;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getOfficerSlots() {
        return officerSlots;
    }

    public void setOfficerSlots(int officerSlots) {
        this.officerSlots = officerSlots;
    }

    public List<String> getOfficers() {
        return officers;
    }

    public void setOfficers(List<String> officers) {
        this.officers = officers;
    }

}
