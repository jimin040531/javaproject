package deu.hms.checkin;

import deu.hms.reservation.ReservationData;

public class CheckInData {
    private final ReservationData reservationData;
    private final String requestDetails;

    public CheckInData(ReservationData reservationData, String requestDetails) {
        this.reservationData = reservationData;
        this.requestDetails = requestDetails;
    }

    public String toCSV() {
        return reservationData.toCSV() + "," + requestDetails;
    }
}
