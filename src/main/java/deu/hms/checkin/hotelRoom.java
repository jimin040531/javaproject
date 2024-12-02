/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.checkin;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jimin
 */

class hotelRoom implements Serializable {
    private final Map<LocalDate, Boolean> reservations = new HashMap<>(); // 날짜별 예약 상태를 저장하는 해시맵

    // 방이 주어진 날짜 범위에서 예약 가능한지 확인하는 메서드
    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            return false; // 날짜가 null인 경우 예약 불가 처리
        }

        LocalDate date = checkIn;
        while (date.isBefore(checkOut)) {
            // 예약된 날짜를 확인하고, 해당 날짜가 예약된 상태가 아닌지 확인
            if (reservations.containsKey(date) && reservations.get(date)) {
                return false; // 예약된 날짜가 있으면 해당 방은 예약 불가
            }
            date = date.plusDays(1); // 다음 날짜로 이동
        }
        return true; // 모든 날짜가 예약 가능하면 true 반환
    }

    // 방을 주어진 날짜 범위로 예약하는 메서드
    public boolean reserve(LocalDate checkIn, LocalDate checkOut) {
        if (!isAvailable(checkIn, checkOut)) return false; // 예약 가능 여부 확인

        LocalDate date = checkIn;
        while (date.isBefore(checkOut)) {
            reservations.put(date, true); // 예약을 위한 상태 변경
            date = date.plusDays(1); // 다음 날짜로 이동
        }
        return true; // 예약 성공 시 true 반환
    }

    // 방을 체크아웃 처리하는 메서드
    public void checkout(LocalDate checkIn, LocalDate checkOut) {
        LocalDate date = checkIn;
        while (date.isBefore(checkOut)) {
            reservations.put(date, false); // 체크아웃 시 예약 해제
            date = date.plusDays(1); // 다음 날짜로 이동
        }
    }
}
