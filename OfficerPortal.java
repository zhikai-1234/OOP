import java.util.HashMap;
import java.util.Map;

public class OfficerPortal extends ApplicantPortal {

	private Applicant applicant;
	private Officer officer;
    private ApplicationHandler appHandler;
	private static Map<Applicant, BookingRequest> bookingsPendingApproval = new HashMap<>();
    private boolean asApplicant;

	public OfficerPortal(Applicant applicant, ApplicationHandler appHandler, Officer o) {
		// TODO Auto-generated constructor stub
		super (applicant);
		this.officer = o;
        this.asApplicant = false; // set default intention to apply as applicant as false
	}

	@Override
	public void showOptions() {
		System.out.println("=====APPLICANT OPTIONS=====");
		super.showOptions();
		System.out.println();
		System.out.println("===== OFFICER OPTIONS =====");
		System.out.println("");
		
	}

	public static void submitBookingRequest(Applicant a, TemplateProject p, int flatType) {
		if(a.hasBookedFlat() || !"Approved".equals(a.getApplicationStatus())) {
            System.out.println("Applicant not eligible for booking");
            return;
        }
		bookingsPendingApproval.put(a, new BookingRequest(a, p, flatType));
        a.setApplicationStatus("Pending Booking Approval");
	}

	public void approveBooking(Applicant a) {
        BookingRequest request = bookingsPendingApproval.get(a);
        if(request == null) return;

        TemplateProject p = request.getTemplateProject();
        int flatType = request.getFlatType();

        // Convert TemplateProject to LiveProject
        LiveProject liveProject = new LiveProject(
            p.getName(),
            p.getNeighbourhood(),
            p.getType1(),
            p.getNumOfType1(),
            p.getType1Price(),
            p.getType2(),
            p.getNumOfType2(),
            p.getType2Price(),
            p.getOpenDate(),
            p.getCloseDate(),
            p.getManagerName(),
            p.getNumOfficers(),
            p.getVisibility()
        );

        // Update unit availability
        if(flatType == 1 && liveProject.getnType1() > 0) {
            p.setNumOfType1(liveProject.getnType1() - 1); // keep a record of remaining available flats in the template project
        } else if(flatType == 2 && liveProject.getnType2() > 0) {
            p.setNumOfType2(liveProject.getnType2() - 1); // keep a record of remaining available flats in the template project
        } else {
            System.out.println("No units available");
            return;
        }

        // Update applicant records
        a.setProjApplied(liveProject);
        a.setApplicationStatus("Flat Booked");
        a.setBookedFlat(true);
        
        // Update project manager
        ProjectManager pm = new ProjectManager();
        pm.addLiveProject(liveProject);
        
        bookingsPendingApproval.remove(a);
    }

}

