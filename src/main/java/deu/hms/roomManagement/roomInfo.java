/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomManagement;

/**
 *
 * @author Jimin
 */
public class roomInfo {
    private final int floor;
        private final int roomNumber;
        private int price;
        private String grade;
        private int capacity;

        public roomInfo(int floor, int roomNumber, int price, String grade, int capacity) {
            this.floor = floor;
            this.roomNumber = roomNumber;
            this.price = price;
            this.grade = grade;
            this.capacity = capacity;
        }

        public int getFloor() {
            return floor;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public int getPrice() {
            return price;
        }

        public String getGrade() {
            return grade;
        }
        
        public int getCapacity() {
            return capacity;
        }
        
        public void setPrice(int price) {
            this.price = price;
        }
        
        public void setGrade(String grade) {
            this.grade = grade;
        }
        
        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }
}
