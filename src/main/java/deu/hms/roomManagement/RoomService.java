package deu.hms.roomManagement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void addRoom(Room room) {
        try {
            roomRepository.addRoom(room);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("객실 추가 실패: " + e.getMessage());
        }
    }

    public void deleteRoom(int floor, int roomNumber) {
        try {
            roomRepository.deleteRoom(floor, roomNumber);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("객실 삭제 실패: " + e.getMessage());
        }
    }

    public void updateRoom(Room room, int newPrice, String newGrade, int newCapacity) {
        try {
            roomRepository.updateRoom(room, newPrice, newGrade, newCapacity);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("객실 수정 실패: " + e.getMessage());
        }
    }

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }
    
}
