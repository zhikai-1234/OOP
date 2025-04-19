public class Enquiry {
    public String enquireText;
    public String enquiryResponse;
    public String editChoice;
    public String userID;
    public String projectName;

    public Enquiry(String uID, String project) {
        this.enquireText = "No enquiry";
        this.enquiryResponse = "No response";
        this.userID = uID;
        this.projectName = project;
    }

    // GETTERS //
    public String getEnquiry() {
        return this.enquireText;
    }

    public String getResponse() {
        return this.enquiryResponse;
    }

    public String getUserID() {
        return this.userID;
        
    }public String getProjectName() {
        return this.projectName;
    }

    // SETTERS //
    public void setResponse(String response) {
        this.enquiryResponse = response;
    }
}
