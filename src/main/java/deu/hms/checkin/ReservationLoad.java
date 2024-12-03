/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.checkin;

import deu.hms.reservation.Reservation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jimin
 */
public class ReservationLoad {

    private List<Reservation> reservations;

    public ReservationLoad() {
        this.reservations = new ArrayList<>();
    }

    // Reservation.txt 파일을 읽어와 예약 정보를 로드하는 메서드
    public void loadReservationsFromFile(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Reservation reservation = parseReservation(line);
                if (reservation != null) {
                    reservations.add(reservation);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading reservation file: " + e.getMessage());
        }
    }

    // 예약 정보를 파싱하여 Reservation 객체로 변환하는 메서드
    private Reservation parseReservation(String line) {
        String[] parts = line.split(",");
        if (parts.length != 11) {
            System.err.println("Invalid reservation data: " + line);
            return null;
        }

        try {
            return new Reservation(
                parts[0], // 고유 번호
                parts[1], // 이름
                parts[2], // 주소
                parts[3], // 전화번호
                parts[4], // 체크인 날짜
                parts[5], // 체크아웃 날짜
                parts[6], // 방 번호
                parts[7], // 결제 수단 개수
                parts[8], // 객실 금액
                parts[9], // 결제 수단
                parts[10] // 상태
            );
        } catch (Exception e) {
            System.err.println("Error parsing reservation data: " + e.getMessage());
            return null;
        }
    }

    // 체크인에 필요한 데이터만 반환하는 메서드
    public List<String[]> getCheckInData() {
        List<String[]> checkInDataList = new ArrayList<>();

        for (Reservation reservation : reservations) {
            checkInDataList.add(new String[]{
                reservation.getUnique(),
                reservation.getName(),
                reservation.getPhoneNum(),
                reservation.getRoomNum(),
                reservation.getFee(),
                reservation.getPaymentType(),
                reservation.getPaymentMethod() // 예약 상태 추가
            });
        }

        return checkInDataList;
    }

    // 예약 목록을 반환하는 메서드
    public List<Reservation> getReservations() {
        return reservations;
    }
}