public class FlatBooking {
    
    public void viewProject(User user, Project[] projects){
        for(Project project : projects){
            if(project.getVisibility()){
                if(user instanceof Applicant){
                    Applicant applicant = (Applicant) user;
                    int eligibilityStatus = applicant.getEligibilityStatus();

                    if(eligibilityStatus == 1){ //single and above 35
                        if(project.getFlatType1().equals(("2-Room"))){
                            System.out.println("Viewing Project: "+project.getProjName() + ", Type: "+project.getFlatType1());
                        }
                    }
                    else if(eligibilityStatus == 2){
                        System.out.println("Viewing Project: "+project.getProjName()+", Type: "+project.getFlatType1()+", "+project.getFlatType2());
                    }
                    else {
                        System.out.println("You are not eligible for any flat");
                    }
                }

                else if(user instanceof HdbOfficer || user instanceof HdbManager){
                    System.out.println("Viewing Project: "+ project.getProjName()+", Types: "+project.getFlatType1()+", "+project.getFlatType2());
                }
            }
        }
    }
}
