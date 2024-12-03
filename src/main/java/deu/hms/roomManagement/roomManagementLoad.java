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

public class roomManagementLoad {
    private final String FILE_NAME = "roomInfo.txt";
    private final List<roomInfo> roomList;

    public roomManagementLoad() {
        roomList = new ArrayList<>();
        loadRoomInfoFromFile();
    }

    public void addRoom(int floor, int roomNumber, int price, String grade, int capacity) {
        roomInfo newRoom = new roomInfo(floor, roomNumber, price, grade, capacity);
        roomList.add(newRoom);
        saveRoomInfoToFile();
    }

    public void deleteRoom(int floor, int roomNumber) {
        Iterator<roomInfo> iterator = roomList.iterator();
        while (iterator.hasNext()) {
            roomInfo room = iterator.next();
            if (room.getFloor() == floor && room.getRoomNumber() == roomNumber) {
                iterator.remove();
                saveRoomInfoToFile();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "해당 객실을 찾을 수 없습니다.");
    }

    public List<roomInfo> getRoomList() {
        return roomList;
    }

    public void loadRoomDataToTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // 기존 데이터 삭제
        for (roomInfo room : roomList) {
            model.addRow(new Object[]{room.getFloor(), room.getRoomNumber(), room.getPrice(), room.getGrade(), room.getCapacity()});
        }
    }

    private void saveRoomInfoToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (roomInfo room : roomList) {
                writer.write(room.getFloor() + "," + room.getRoomNumber() + "," + room.getPrice() + "," + room.getGrade() + "," + room.getCapacity());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "객실 정보 저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadRoomInfoFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("객실 정보 파일이 존재하지 않습니다. 새 파일을 생성합니다.");
            try {
                if (file.createNewFile()) {
                    System.out.println("새로운 객실 정보 파일이 생성되었습니다.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "객실 정보 파일 생성 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    int floor = Integer.parseInt(data[0].trim());
                    int roomNumber = Integer.parseInt(data[1].trim());
                    int price = Integer.parseInt(data[2].trim());
                    String grade = data[3].trim();
                    int capacity = Integer.parseInt(data[4].trim());
                    roomList.add(new roomInfo(floor, roomNumber, price, grade, capacity));
                } else {
                    System.out.println("잘못된 데이터 형식: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("파일을 읽는 중 문제가 발생했습니다: " + e.getMessage());
        }
    }
}