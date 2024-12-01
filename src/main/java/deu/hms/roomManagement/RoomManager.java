/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomManagement;

/**
 *
 * @author Jimin
 */

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RoomManager {
    private final String FILE_NAME = "room_info.txt";
    private final List<RoomInfo> roomList;

    public RoomManager() {
        roomList = new ArrayList<>();
        loadRoomInfoFromFile();
    }

    public void addRoom(int floor, int roomNumber, int price, String grade) {
        for (RoomInfo room : roomList) {
            if (room.getFloor() == floor && room.getRoomNumber() == roomNumber) {
                JOptionPane.showMessageDialog(null, "해당 객실은 이미 존재합니다.");
                return;
            }
        }
        RoomInfo newRoom = new RoomInfo(floor, roomNumber, price, grade);
        roomList.add(newRoom);
        saveRoomInfoToFile();
    }


    public void updateRoom(int floor, int roomNumber, int newPrice, String newGrade) {
        for (RoomInfo room : roomList) {
            if (room.getFloor() == floor && room.getRoomNumber() == roomNumber) {
                room.setPrice(newPrice);
                room.setGrade(newGrade);
                saveRoomInfoToFile();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "해당 객실을 찾을 수 없습니다.");
    }

    public void deleteRoom(int floor, int roomNumber) {
        Iterator<RoomInfo> iterator = roomList.iterator();
        while (iterator.hasNext()) {
            RoomInfo room = iterator.next();
            if (room.getFloor() == floor && room.getRoomNumber() == roomNumber) {
                iterator.remove();
                saveRoomInfoToFile();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "해당 객실을 찾을 수 없습니다.");
    }

    public List<RoomInfo> getRoomList() {
        return roomList;
    }

    public void loadRoomDataToTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // 기존 데이터 삭제
        for (RoomInfo room : roomList) {
            model.addRow(new Object[]{room.getFloor(), room.getRoomNumber(), room.getPrice(), room.getGrade()});
        }
    }

    // RoomManager 클래스에서 수정
    private void saveRoomInfoToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (RoomInfo room : roomList) {
                // 데이터를 쉽게 파싱할 수 있도록 콤마(,) 구분자로 저장
                writer.write(room.getFloor() + "," + room.getRoomNumber() + "," + room.getPrice() + "," + room.getGrade());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "객실 정보 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadRoomInfoFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) { // 데이터 형식이 맞는지 확인
                    int floor = Integer.parseInt(data[0].trim());
                    int roomNumber = Integer.parseInt(data[1].trim());
                    int price = Integer.parseInt(data[2].trim());
                    String grade = data[3].trim();
                    roomList.add(new RoomInfo(floor, roomNumber, price, grade));
                }
            }
        } catch (IOException e) {
            System.out.println("저장된 객실 정보가 없습니다. 초기화합니다.");
        } catch (NumberFormatException e) {
            System.out.println("파일의 형식이 잘못되었습니다. 데이터 파싱 중 오류 발생: " + e.getMessage());
        }
    }


    public static class RoomInfo {
        private final int floor;
        private final int roomNumber;
        private int price;
        private String grade;

        public RoomInfo(int floor, int roomNumber, int price, String grade) {
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
}