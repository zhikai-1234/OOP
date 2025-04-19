public class ManagerPortal {
	private static Map<Applicant, BookingRequest> bookingsPendingApproval = new HashMap<>();
	public void showOptions() {
		// TODO Auto-generated method stub
		
	}

	public static void submitBookingRequest(Applicant a, LiveProject p, int flatType) {
		int NumUnit;
		if(flatType ==1){
			NumUnits = p.getnType1();
		}
		else {
			NumUnits = p.getnType2();
		}

		if(NumUnits == 0) { {
			System.out.println("ERROR: No units available for this type of flat.");
			return;
		}

		BookingRequest bookingRequest = new BookingRequest(a, p, flatType);
		bookingsPendingApproval.put(a, bookingRequest);
		a.setApplicationStatus("Pending Booking Approval");
		System.out.println("Booking request submitted successfully.");
	}

}
