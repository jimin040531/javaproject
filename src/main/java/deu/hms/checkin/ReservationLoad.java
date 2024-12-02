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
        reservations = new ArrayList<>();
    }

    // 예약 정보를 리스트에 추가하는 메서드
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // 체크인에 필요한 데이터만 반환하는 메서드
    public List<String[]> getCheckInData() {
        List<String[]> checkInDataList = new ArrayList<>();

        for (Reservation reservation : reservations) {
            String[] checkInData = {
                reservation.getUnique(),
                reservation.getName(),
                reservation.getPhoneNum(),
                reservation.getRoomNum(),
                reservation.getFee(),
                reservation.getPaymentMethod()
            };
            checkInDataList.add(checkInData);
        }

        return checkInDataList;
    }
}
