/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.checkin;

import deu.hms.reservation.ReservationData;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Jimin
 */
public class ReservationLoad {

    // 테이블에 데이터를 로드하는 메서드
    public static void loadReservationData(DefaultTableModel model) {
        model.setRowCount(0); // 기존 테이블 데이터 초기화

        // Reservation.txt 파일에서 예약자 정보를 불러오기
        List<ReservationData> reservationList = loadFromFile("Reservation.txt");

        // 필요한 정보만 테이블에 추가 (고유 번호, 이름, 전화 번호, 방 번호, 객실 금액, 결제 수단, 상태)
        for (ReservationData reservation : reservationList) {
            Object[] rowData = {
                reservation.getUniqueNumber(), // 고유 번호
                reservation.getName(),         // 이름
                reservation.getPhoneNumber(),  // 전화 번호
                reservation.getRoomNumber(),   // 방 번호
                reservation.getStayCost(),     // 객실 금액
                reservation.getPaymentMethod(),// 결제 수단
                reservation.getStatus()        // 상태
            };
            model.addRow(rowData);
        }
    }

    // 파일에서 예약 데이터를 불러오는 메서드
    public static List<ReservationData> loadFromFile(String fileName) {
        List<ReservationData> reservationList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 11) {
                    ReservationData reservation = new ReservationData(
                        data[0], data[1], data[2], data[3],
                        data[4], data[5], data[6], data[7],
                        data[8], data[9], data[10]
                    );
                    reservationList.add(reservation);
                }
            }
        } catch (IOException e) {
            System.err.println("파일을 불러오는 중 오류 발생: " + e.getMessage());
        }
        return reservationList;
    }
}