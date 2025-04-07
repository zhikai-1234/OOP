
        return eligibilityStatus;
    }

    private int checkEligibility(){
        int age = Integer.parseInt(this.getAge());
        if(this.getMaritalStatus().equals("Single") && age >= 35){
            return 1;