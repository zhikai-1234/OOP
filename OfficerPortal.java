public class OfficerPortal extends ApplicantPortal {
	
	private HdbOfficer officer;
	
	public OfficerPortal(HdbOfficer o) {
		// TODO Auto-generated constructor stub
		super (HdbOfficer o);
		this.officer = o;
	}

	@Override
	public void showOptions() {
		System.out.println("=====APPLICANT OPTIONS=====");
		super.showOptions();
		System.out.println();
		System.out.println("===== OFFICER OPTIONS =====");
		System.out.println("");
		
	}

}
