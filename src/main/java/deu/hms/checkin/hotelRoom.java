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
    private int capacity; // 객실 수용 인원
    private int price; // 객실 가격
    private String grade; // 객실 등급

    // 생성자: 기본적으로 수용 인원 0으로 초기화
    public hotelRoom() {
        this.capacity = 0;
        this.price = 0;
        this.grade = "Standard"; // 기본 등급을 Standard로 설정
        System.out.println("Initialized room with default values: Capacity=0, Price=0, Grade=Standard");
    }

    // 생성자: 수용 인원, 가격, 등급을 설정할 수 있도록 초기화
    public hotelRoom(int capacity, int price, String grade) {
        this.capacity = capacity;
        this.price = price;
        this.grade = grade;
        System.out.println("Initialized room with values: Capacity=" + capacity + ", Price=" + price + ", Grade=" + grade);
    }

    // 방이 주어진 날짜 범위에서 예약 가능한지 확인하는 메서드
    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            return false; // 날짜가 null인 경우 예약 불가 처리
        }

        LocalDate date = checkIn;
        while (date.isBefore(checkOut)) {
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
        System.out.println("Room reserved from " + checkIn + " to " + checkOut);
        return true; // 예약 성공 시 true 반환
    }

    // 방을 체크아웃 처리하는 메서드
    public void checkout(LocalDate checkIn, LocalDate checkOut) {
        LocalDate date = checkIn;
        while (date.isBefore(checkOut)) {
            reservations.put(date, false); // 체크아웃 시 예약 해제
            date = date.plusDays(1); // 다음 날짜로 이동
        }
        System.out.println("Room checked out from " + checkIn + " to " + checkOut);
    }

    // 수용 인원 설정 메서드
    public void setCapacity(int capacity) {
        this.capacity = capacity;
        System.out.println("Set room capacity: " + capacity);
    }

    // 수용 인원 반환 메서드
    public int getCapacity() {
        return capacity;
    }

    // 가격 설정 메서드
    public void setPrice(int price) {
        this.price = price;
        System.out.println("Set room price: " + price);
    }

    // 가격 반환 메서드
    public int getPrice() {
        return price;
    }

    // 등급 설정 메서드
    public void setGrade(String grade) {
        this.grade = grade;
        System.out.println("Set room grade: " + grade);
    }

    // 등급 반환 메서드
    public String getGrade() {
        return grade;
    }
}