/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomInfo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jimin
 */
class floor implements Serializable {
    private final List<room> rooms;

    public floor(int roomsPerFloor) {
        rooms = new ArrayList<>();
        for (int i = 0; i < roomsPerFloor; i++) {
            rooms.add(new room());
        }
    }

    public boolean isRoomAvailable(int roomIndex, LocalDate checkIn, LocalDate checkOut) {
        return rooms.get(roomIndex).isAvailable(checkIn, checkOut);
    }

    public boolean reserveRoom(int roomIndex, LocalDate checkIn, LocalDate checkOut) {
        return rooms.get(roomIndex).reserve(checkIn, checkOut);
    }
}
