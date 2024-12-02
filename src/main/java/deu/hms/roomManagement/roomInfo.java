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

        public roomInfo(int floor, int roomNumber, int price, String grade) {
            this.floor = floor;
            this.roomNumber = roomNumber;
            this.price = price;
            this.grade = grade;
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

        public void setPrice(int price) {
            this.price = price;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }
}
