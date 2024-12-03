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
class hotelFloor implements Serializable {
    private final List<HotelRoom> rooms;

    // 생성자: 층당 방의 수를 받아 초기화
    public hotelFloor(int roomsPerFloor) {
        rooms = new ArrayList<>();
        for (int i = 0; i < roomsPerFloor; i++) {
            rooms.add(new HotelRoom()); // 기본 생성자를 통해 초기화
        }
    }

    // 특정 방 가져오기
    public HotelRoom getRoom(int roomIndex) {
        if (roomIndex >= 0 && roomIndex < rooms.size()) {
            return rooms.get(roomIndex);
        } else {
            System.out.println("Invalid room index: " + roomIndex);
            return null; // 유효하지 않은 인덱스일 경우 null 반환
        }
    }

    // 특정 방 예약 가능 여부 확인
    public boolean isRoomAvailable(int roomIndex, LocalDate checkIn, LocalDate checkOut) {
        HotelRoom room = getRoom(roomIndex);
        if (room != null) {
            return room.isAvailable(checkIn, checkOut);
        }
        return false;
    }

    // 특정 방 예약 처리
    public boolean reserveRoom(int roomIndex, LocalDate checkIn, LocalDate checkOut) {
        HotelRoom room = getRoom(roomIndex);
        if (room != null) {
            return room.reserve(checkIn, checkOut);
        }
        return false;
    }

    // 특정 방의 가격, 등급, 수용 인원을 설정하는 메서드
    public void setRoomInfo(int roomIndex, int price, String grade, int capacity) {
        HotelRoom room = getRoom(roomIndex);
        if (room != null) {
            room.setPrice(price);
            room.setGrade(grade);
            room.setCapacity(capacity);
        } else {
            System.out.println("Cannot set room info. Invalid room index: " + roomIndex);
        }
    }

    // 방의 리스트를 반환하는 메서드 추가
    public List<HotelRoom> getRooms() {
        return rooms;
    }
}
