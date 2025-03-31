 public abstract class User { //Make it abstract, so that no one can create a User object,
	                         //so that everyone is either a Applicant, Manager or Officer
	 
    private String name, UserID, password, age, maritalStatus;
    
    public User(String name, String UserID, String age, String maritalStatus, String password) {
        
    	this.name = name;
    	this.UserID = UserID;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }
    
    public String getName() {
        return name;
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
    
    public abstract void displayJobscope(BTOSystem system);
    
}
