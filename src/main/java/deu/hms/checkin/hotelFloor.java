/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.checkin;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jimin
 */
class HotelFloor implements Serializable {
    private final List<HotelRoom> rooms; // 층에 있는 방들의 리스트를 저장하는 변수

    // 생성자: 층당 방의 수를 받아 초기화
    public HotelFloor(int roomsPerFloor) {
        rooms = new ArrayList<>();
        for (int i = 0; i < roomsPerFloor; i++) {
            rooms.add(new HotelRoom()); // 방을 리스트에 추가
        }
    }

    // 특정 방의 인덱스를 통해 방 객체를 반환하는 메서드
    public HotelRoom getRoom(int roomIndex) {
        return rooms.get(roomIndex);
    }

    // 특정 방이 주어진 날짜 범위에서 예약 가능한지 확인하는 메서드
    public boolean isRoomAvailable(int roomIndex, LocalDate checkIn, LocalDate checkOut) {
        return rooms.get(roomIndex).isAvailable(checkIn, checkOut);
    }

    // 특정 방을 주어진 날짜 범위로 예약하는 메서드
    public boolean reserveRoom(int roomIndex, LocalDate checkIn, LocalDate checkOut) {
        return rooms.get(roomIndex).reserve(checkIn, checkOut);
    }
}
