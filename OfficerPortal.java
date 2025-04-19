import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OfficerPortal extends ApplicantPortal {
	
	private HdbOfficer officer;
	private static Map<Applicant, BookingRequest> bookingsPendingApproval = new HashMap<>();

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

	public static void submitBookingRequest(Applicant a, LiveProject p, int flatType) {
		if(a.hasBookedFlat() || !"Approved".equals(a.getApplicationStatus())) {
            System.out.println("Applicant not eligible for booking");
            return;
        }
		bookingsPendingApproval.put(a, new BookingRequest(a, p, flatType));
        a.setApplicationStatus("Pending Booking Approval");
	}

	public void approveBooking(Applicant a) {
        BookingRequest request = pendingBookings.get(applicant);
        if(request == null) return;

        TemplateProject p = request.getTemplateProject();
        int flatType = request.getFlatType();

        // Convert TemplateProject to LiveProject
        LiveProject liveProject = new LiveProject(
            p.getName(),
            p.getNeighbourhood(),
            p.getType1(),
            p.getnType1(),
            p.getType1Price(),
            p.getType2(),
            p.getnType2(),
            p.getType2Price(),
            p.getOpenDate(),
            p.getCloseDate(),
            p.getManagerName(),
            p.getNoOfOfficers(),
            p.getVisibility(),
            new Manager(p.getManagerName())
        );

        // Update unit availability
        if(flatType == 1 && liveProject.getnType1() > 0) {
            liveProject.setnType1(liveProject.getnType1() - 1);
        } else if(flatType == 2 && liveProject.getnType2() > 0) {
            liveProject.setnType2(liveProject.getnType2() - 1);
        } else {
            System.out.println("No units available");
            return;
        }

        // Update applicant records
        applicant.setProjApplied(liveProject);
        applicant.setApplicationStatus("Flat Booked");
        applicant.setHasBookedFlat(true);
        
        // Update project manager
        ProjectManager pm = new ProjectManager();
        pm.addLiveProject(liveProject);
        
        pendingBookings.remove(applicant);
    }

}

