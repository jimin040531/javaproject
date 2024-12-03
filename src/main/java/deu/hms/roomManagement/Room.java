/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomManagement;

/**
 *
 * @author Jimin
 */

public class Room {
    
    // 방의 속성: 층, 방 번호, 가격, 등급, 수용 인원
    private final int floor; // 층 번호 (final -> 방 층수 고정)
    private final int roomNumber; // 방 번호 (final -> 방 번호 고정)
    private int price; // 방 가격
    private String grade; // 방 등급 (스탠다드, 디럭스, 스위트)
    private int capacity; // 방 수용 인원 (사람 수)

    // 방 정보를 초기화하는 생성자
    public Room(int floor, int roomNumber, int price, String grade, int capacity) {
        this.floor = floor; // 층 번호 할당
        this.roomNumber = roomNumber; // 방 번호 할당
        this.price = price; // 방 가격 할당
        this.grade = grade; // 방 등급 할당
        this.capacity = capacity; // 방 수용 인원 할당
    }

    public int getFloor() {
        return floor; // 층 번호 반환
    }

    public int getRoomNumber() {
        return roomNumber; // 방 번호 반환
    }

    public int getPrice() {
        return price; // 방 가격 반환
    }

    public String getGrade() {
        return grade; // 방 등급 반환
    }
    
    public int getCapacity() {
        return capacity; // 방 수용 인원 반환
    }
    
    public void setPrice(int price) {
        this.price = price; // 방의 새 가격 설정
    }
    
    public void setGrade(String grade) {
        this.grade = grade; // 방의 새 등급 설정
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity; // 방의 새 수용 인원 설정
    }
}