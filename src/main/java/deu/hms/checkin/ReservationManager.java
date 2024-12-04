package deu.hms.checkin;

import deu.hms.reservation.ReservationData;
import java.util.List;

public class ReservationManager {

    private static final String RESERVATION_FILE_PATH = "Reservation.txt";

    // 예약 정보를 파일에서 읽어오는 메서드
    public List<ReservationData> getReservationsFromFile() {
        return FileHandler.loadFromFile(RESERVATION_FILE_PATH);
    }

    // 예약 상태를 업데이트하는 메서드
    public void updateReservationStatus(String reservationId, String updatedStatus) {
        List<ReservationData> reservations = getReservationsFromFile();
        for (ReservationData reservation : reservations) {
            if (reservation.getUniqueNumber().equals(reservationId)) {
                reservation.setStatus(updatedStatus);
            }
        }
        writeReservationsToFile(reservations);
    }

    // 예약을 추가하는 메서드
    public void addReservation(ReservationData reservation) {
        List<ReservationData> reservations = getReservationsFromFile();
        reservations.add(reservation);
        writeReservationsToFile(reservations);
    }

    // 예약 정보를 파일에 쓰는 메서드
    public void writeReservationsToFile(List<ReservationData> reservations) {
        FileHandler.writeToFile(RESERVATION_FILE_PATH, reservations);
    }
    
    
}