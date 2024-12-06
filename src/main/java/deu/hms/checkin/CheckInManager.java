package deu.hms.checkin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CheckInManager {

    private String reservationFilePath = "Reservation.txt"; // 예약 파일 경로
    private String checkInFilePath = "CheckInData.txt"; // 체크인 데이터 파일 경로

    // 예약 데이터를 모두 가져오는 메서드 (필터링된 데이터만 반환)
    public List<CheckInData> getCheckInDataList() {
        List<CheckInData> checkInDataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(reservationFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 예약 정보를 파싱하여 필요한 데이터만 추출
                CheckInData checkInData = parseCheckInData(line);
                if (checkInData != null) {
                    checkInDataList.add(checkInData);
                }
            }
        } catch (IOException e) {
            System.err.println("파일 읽기 오류: " + e.getMessage());
        }
        return checkInDataList;
    }

    // Reservation.txt 한 줄을 읽어서 CheckInData 객체로 변환하는 메서드
    private CheckInData parseCheckInData(String csvLine) {
        String[] fields = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        if (fields.length == 11) { // 고유번호, 이름, 주소, 전화번호, 예상 체크인 날짜, 예상 체크 아웃 날짜, 방 번호, 인원 수 , 금액, 결제 수단, 상태
            String uniqueNumber = fields[0].trim();
            String name = fields[1].trim();
            String phoneNumber = fields[3].trim(); // 전화번호
            String checkInDate = fields[4].trim(); // 체크인 날짜
            String checkOutDate = fields[5].trim(); // 체크아웃 날짜
            String roomNumber = fields[6].trim(); // 방 번호
            String guestCount = fields[7].trim(); // 인원 수
            String stayCost = fields[8].trim(); // 객실 금액
            String paymentMethod = fields[9].trim(); // 결제 수단
            String status = fields[10].trim(); // 상태

            // 요청 사항은 null로 초기화 (필요에 따라 수정 가능)
            String requestDetails = "";
            
            return new CheckInData(uniqueNumber, name, phoneNumber, checkInDate, checkOutDate, roomNumber, guestCount, stayCost, paymentMethod, status, requestDetails);
        }
        return null; // 잘못된 형식의 데이터는 null 반환
    }

    // 체크인 데이터를 요청 사항과 함께 파일에 저장하는 메서드
    public void saveCheckInDataWithRequest(CheckInData checkInData, String requestDetails) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(checkInFilePath, true))) {
            // 요청 사항을 포함하여 CSV 형식으로 저장
            String fullCsvData = checkInData.toCSV();
            writer.write(fullCsvData); // 요청 사항 포함된 데이터 저장
            writer.newLine();
        } catch (IOException e) {
            System.err.println("체크인 데이터와 요청 사항 쓰기 오류: " + e.getMessage());
        }
    }
}






