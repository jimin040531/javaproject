/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.checkin;

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
    private final String filePath;

    public ReservationLoad(String filePath) {
        this.filePath = filePath;
    }

    // 예약 정보를 리스트로 반환하는 메서드
    public List<String[]> loadReservations() {
        List<String[]> reservations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] reservationDetails = line.split(",");

                // 인덱스 범위 확인 (데이터가 11개 이상인지 확인)
                if (reservationDetails.length >= 11) {
                    // 필요한 필드만 선택 (예: 고객 이름, 체크인 날짜, 체크아웃 날짜)
                    String[] selectedDetails = {
                        reservationDetails[0],  // 고유 번호
                        reservationDetails[1],  // 이름
                        reservationDetails[3],  // 전화번호
                        reservationDetails[6],  // 방 번호
                        reservationDetails[8],  // 객실 금액
                        reservationDetails[9],  // 결제 수단
                        reservationDetails[10]  // 상태
                    };
                    reservations.add(selectedDetails);
                } else {
                    // 데이터가 부족한 경우 처리 (예: 오류 메시지 출력)
                    System.out.println("예약 데이터가 충분하지 않습니다: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reservations;
    }

}
