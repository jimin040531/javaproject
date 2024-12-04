package deu.hms.checkin;

import deu.hms.reservation.ReservationData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReservationManager {

    private static final String RESERVATION_FILE_PATH = "Reservation.txt"; // 예약 파일 경로
    private static final String CHECK_IN_FILE_PATH = "CheckInData.txt";    // 체크인 파일 경로
    private static final int RESERVATION_FIELD_COUNT = 11;                 // 예약 데이터 필드 수
    private static final int CHECK_IN_FIELD_COUNT = 12;                    // 체크인 데이터 필드 수

    // 모든 예약 데이터를 가져오는 메서드
    public List<ReservationData> getAllReservations() {
        List<String> fileContent = FileHandler.loadFromFile(RESERVATION_FILE_PATH);
        List<ReservationData> reservations = new ArrayList<>();

        for (String line : fileContent) {
            ReservationData reservation = parseReservationData(line);
            if (reservation != null) {
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    // 상태가 "예약 완료"인 예약 데이터만 가져오는 메서드
    public List<ReservationData> getPendingReservations() {
        List<ReservationData> allReservations = getAllReservations();
        List<ReservationData> pendingReservations = new ArrayList<>();

        for (ReservationData reservation : allReservations) {
            if ("예약 완료".equals(reservation.getStatus())) {
                pendingReservations.add(reservation);
            }
        }
        return pendingReservations;
    }

    // 검색 기능 구현
    public List<ReservationData> searchReservations(String searchTerm, String searchType) {
        List<ReservationData> filteredReservations = new ArrayList<>();
        for (ReservationData reservation : getPendingReservations()) {
            if ("고유 번호".equals(searchType) && reservation.getUniqueNumber().contains(searchTerm)) {
                filteredReservations.add(reservation);
            } else if ("성이름".equals(searchType) && reservation.getName().contains(searchTerm)) {
                filteredReservations.add(reservation);
            } else if ("방 번호".equals(searchType) && reservation.getRoomNumber().contains(searchTerm)) {
                filteredReservations.add(reservation);
            }
        }
        return filteredReservations;
    }

    // 체크인 데이터를 가져오는 메서드
    public List<CheckInData> getAllCheckInData() {
        List<String> fileContent = FileHandler.loadFromFile(CHECK_IN_FILE_PATH);
        List<CheckInData> checkInDataList = new ArrayList<>();

        for (String line : fileContent) {
            CheckInData checkInData = parseCheckInData(line);
            if (checkInData != null) {
                checkInDataList.add(checkInData);
            }
        }
        return checkInDataList;
    }

    // 예약 추가 메서드
    public void addReservation(ReservationData reservation) {
        List<ReservationData> reservations = getAllReservations();
        reservations.add(reservation);
        saveReservations(reservations);
    }

    // 체크인 데이터 저장 메서드
    public void saveCheckInData(ReservationData reservation, String requestDetails) {
        CheckInData checkInData = new CheckInData(reservation, requestDetails); // CheckInData 생성
        saveCheckInData(checkInData); // CheckInData를 처리하는 기존 메서드 호출
    }

    // 기존 CheckInData를 받는 saveCheckInData 메서드 유지
    public void saveCheckInData(CheckInData checkInData) {
        FileHandler.appendToFile(CHECK_IN_FILE_PATH, checkInData.toCSV());
    }

    // 특정 예약 상태 업데이트 메서드
    public void updateReservationStatus(String reservationId, String status) {
        List<ReservationData> reservations = getAllReservations();
        for (ReservationData reservation : reservations) {
            if (reservation.getUniqueNumber().equals(reservationId)) {
                reservation.setStatus(status);
                break;
            }
        }
        saveReservations(reservations);
    }
    
    // 특정 예약 ID로 예약 데이터를 검색하는 메서드
    public ReservationData findReservationById(String reservationId) {
        List<ReservationData> reservations = getAllReservations();
        for (ReservationData reservation : reservations) {
            if (reservation.getUniqueNumber().equals(reservationId)) {
                return reservation; // 일치하는 예약 반환
            }
        }
        return null; // 일치하는 예약이 없으면 null 반환
    }

    // 예약 데이터를 파일에 저장하는 메서드
    private void saveReservations(List<ReservationData> reservations) {
        List<String> fileContent = new ArrayList<>();
        for (ReservationData reservation : reservations) {
            fileContent.add(reservation.toCSV());
        }
        FileHandler.writeToFile(RESERVATION_FILE_PATH, fileContent);
    }

    // 체크인 데이터를 파일에 저장하는 메서드
    private void saveCheckInDataList(List<CheckInData> checkInDataList) {
        List<String> fileContent = new ArrayList<>();
        for (CheckInData checkInData : checkInDataList) {
            fileContent.add(checkInData.toCSV());
        }
        FileHandler.writeToFile(CHECK_IN_FILE_PATH, fileContent);
    }

    // ReservationData CSV 변환 메서드
    private ReservationData parseReservationData(String csvLine) {
        String[] fields = csvLine.split(",");
        if (fields.length == RESERVATION_FIELD_COUNT) {
            return new ReservationData(
                fields[0].trim(), fields[1].trim(), fields[2].trim(), fields[3].trim(),
                fields[4].trim(), fields[5].trim(), fields[6].trim(), fields[7].trim(),
                fields[8].trim(), fields[9].trim(), fields[10].trim()
            );
        }
        return null;
    }

    // CheckInData CSV 변환 메서드
    private CheckInData parseCheckInData(String csvLine) {
        String[] fields = csvLine.split(",");
        if (fields.length == CHECK_IN_FIELD_COUNT) {
            String[] reservationFields = Arrays.copyOfRange(fields, 0, RESERVATION_FIELD_COUNT);
            String joinedFields = String.join(",", reservationFields);
            ReservationData reservation = parseReservationData(joinedFields);
            if (reservation != null) {
                return new CheckInData(reservation, fields[RESERVATION_FIELD_COUNT].trim());
            }
        }
        return null;
    }
}
