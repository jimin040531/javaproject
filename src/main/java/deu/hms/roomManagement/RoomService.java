/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomManagement;

import javax.swing.JOptionPane;

/**
 *
 * @author Jimin
 */
public class RoomService {
    private RoomRepository roomRepository;

    public RoomService() {
        // RoomRepository 초기화
        this.roomRepository = new RoomRepository();
    }

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }   

    public void addRoom(Room room) {
        if (roomRepository.findRoom(room.getFloor(), room.getRoomNumber()) != null) {
            JOptionPane.showMessageDialog(null, "이미 존재하는 객실입니다.", "등록 오류", JOptionPane.WARNING_MESSAGE);
        } else {
            roomRepository.getRoomList().add(room);
            roomRepository.saveRoomInfoToFile();
            JOptionPane.showMessageDialog(null, "객실이 성공적으로 추가되었습니다.", "등록 성공", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void deleteRoom(int floor, int roomNumber) {
        Room room = roomRepository.findRoom(floor, roomNumber);
        if (room != null) {
            roomRepository.getRoomList().remove(room);
            roomRepository.saveRoomInfoToFile();
            JOptionPane.showMessageDialog(null, "객실이 성공적으로 삭제되었습니다.", "삭제 성공", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "해당 객실을 찾을 수 없습니다.", "삭제 오류", JOptionPane.WARNING_MESSAGE);
        }
    }
}