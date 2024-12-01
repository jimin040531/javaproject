/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomInfo;

/**
 *
 * @author Jimin
 */
class RoomGrade {
    public static String getRoomGrade(int floor, int roomNumber) {
        if (floor >= 1 && floor <= 3) {
            return "Standard";
        } else if (floor >= 4 && floor <= 7) {
            return "Deluxe";
        } else {
            return "Suite";
        }
    }
}