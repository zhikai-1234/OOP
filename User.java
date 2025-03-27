 public abstract class User { //Make it abstract, so that no one can create a User object,
	                         //so that everyone is either a Applicant, Manager or Officer
	 
    private String UserID, password, age, maritalStatus;
    
    public User(String UserID, String age, String maritalStatus, String password) {
        
    	this.UserID = UserID;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }
    
    public String getUserID() {
        return UserID;
    }

    public String getPassword() {
        return password;
    }

    public String getAge() {
        return age;
    }
    
    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void changePassword(){

    }

    public void viewProj(){

    }
    
    public abstract void displayJobscope();
    
}
