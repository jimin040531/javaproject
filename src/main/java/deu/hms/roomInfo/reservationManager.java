/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomInfo;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.time.LocalDate;

/**
 *
 * @author Jimin
 */
class reservationManager {
    private final List<floor> floors;
    private static final String FILE_NAME = "reservations.dat";

    public reservationManager(int numFloors, int roomsPerFloor) {
        floors = new ArrayList<>();
        for (int i = 0; i < numFloors; i++) {
            floors.add(new floor(roomsPerFloor));
        }
        loadReservations();
    }

    public boolean isRoomAvailable(int floor, int room, LocalDate checkIn, LocalDate checkOut) {
        return floors.get(floor).isRoomAvailable(room, checkIn, checkOut);
    }

    public boolean reserveRoom(int floor, int room, LocalDate checkIn, LocalDate checkOut) {
        boolean success = floors.get(floor).reserveRoom(room, checkIn, checkOut);
        if (success) saveReservations();
        return success;
    }

    private void saveReservations() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(floors);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadReservations() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            List<floor> loadedFloors = (List<floor>) in.readObject();
            for (int i = 0; i < floors.size(); i++) {
                floors.set(i, loadedFloors.get(i));
            }
        } catch (Exception e) {
            System.out.println("No previous reservations found. Starting fresh.");
        }
    }
}
