/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.checkin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

import com.toedter.calendar.JDateChooser; // JCalendar 라이브러리 임포트
import java.awt.event.ActionEvent;

public class HotelRoomInfo {
    private final JFrame frame;
    private JDateChooser checkInDateChooser, checkOutDateChooser;
    private JComboBox<String> floorSelector;
    private final JPanel roomPanel;
    private final ReservationManager reservationManager;

    public HotelRoomInfo() {
        reservationManager = new ReservationManager(10, 10); // 10 floors, 10 rooms per floor

        frame = new JFrame("호텔 객실 정보");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // 화면을 중앙에 띄우기
        frame.setLocationRelativeTo(null);

        // Top panel for controls
        frame.add(createControlPanel(), BorderLayout.NORTH);

        // Room panel for displaying room status
        roomPanel = new JPanel(new GridLayout(10, 10));
        frame.add(roomPanel, BorderLayout.CENTER);

        updateRoomAvailability(); // Initial room update
        frame.setVisible(true);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        checkInDateChooser = createDateChooser("예상 체크인 날짜:", panel);
        checkOutDateChooser = createDateChooser("예상 체크아웃 날짜:", panel);

        floorSelector = new JComboBox<>();
        for (int i = 1; i <= 10; i++) floorSelector.addItem("Floor " + i);
        panel.add(new JLabel("층 선택:"));
        panel.add(floorSelector);

        JButton roomInfoButton = new JButton("객실 정보 저장");
        roomInfoButton.addActionListener(e -> updateRoomAvailability());
        panel.add(roomInfoButton);

        return panel;
    }

    private JDateChooser createDateChooser(String label, JPanel parent) {
        parent.add(new JLabel(label));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        parent.add(dateChooser);
        return dateChooser;
    }

    private void updateRoomAvailability() {
        roomPanel.removeAll();

        // 체크인과 체크아웃 날짜가 null인 경우 기본 날짜를 설정
        LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate());
        LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate());

        // 만약 날짜가 null일 경우 오늘 날짜로 기본 설정
        if (checkInDate == null) {
            checkInDate = LocalDate.now();
        }
        if (checkOutDate == null) {
            checkOutDate = checkInDate.plusDays(1); // 기본 체크아웃을 체크인 날짜의 다음 날로 설정
        }

        int selectedFloor = floorSelector.getSelectedIndex();

        for (int room = 0; room < 10; room++) {
            JButton roomButton = new JButton("Room " + (selectedFloor + 1) + "-" + (room + 1));
            boolean isAvailable = reservationManager.isRoomAvailable(selectedFloor, room, checkInDate, checkOutDate);
            roomButton.setBackground(isAvailable ? Color.GREEN : Color.LIGHT_GRAY);

            final int currentRoom = room;  // final로 처리 (Effectively final)
            final LocalDate checkIn = checkInDate;  // 추가된 부분
            final LocalDate checkOut = checkOutDate; // 추가된 부분

            roomButton.addActionListener((ActionEvent e) -> {
                attemptReservation(selectedFloor, currentRoom, checkIn, checkOut);  // 람다식에서 캡처된 변수 사용
            });
            roomPanel.add(roomButton);
        }

        roomPanel.revalidate();
        roomPanel.repaint();
    }


    private void attemptReservation(int floor, int room, LocalDate checkIn, LocalDate checkOut) {
        if (reservationManager.reserveRoom(floor, room, checkIn, checkOut)) {
            JOptionPane.showMessageDialog(frame, "예약 성공 하셨습니다.");
            updateRoomAvailability();
        } else {
            JOptionPane.showMessageDialog(frame, "이미 예약된 방입니다.");
        }
    }

    private LocalDate getLocalDate(java.util.Date date) {
        if (date == null) {
            return null; // null 처리
        }
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelRoomInfo::new);
    }
}

// Reservation Manager
class ReservationManager {
    private final List<Floor> floors;
    private static final String FILE_NAME = "reservations.dat";

    public ReservationManager(int numFloors, int roomsPerFloor) {
        floors = new ArrayList<>();
        for (int i = 0; i < numFloors; i++) {
            floors.add(new Floor(roomsPerFloor));
        }
        loadReservations(); // 프로그램 시작 시 예약 정보 로드
    }

    public boolean isRoomAvailable(int floor, int room, LocalDate checkIn, LocalDate checkOut) {
        return floors.get(floor).isRoomAvailable(room, checkIn, checkOut);
    }

    public boolean reserveRoom(int floor, int room, LocalDate checkIn, LocalDate checkOut) {
        boolean success = floors.get(floor).reserveRoom(room, checkIn, checkOut);
        if (success) saveReservations(); // 예약 성공 시 저장
        return success;
    }

    public boolean checkoutRoom(int floor, int room, LocalDate checkIn, LocalDate checkOut) {
        floors.get(floor).getRoom(room).checkout(checkIn, checkOut); // 방의 체크아웃 처리
        saveReservations(); // 체크아웃 후 예약 정보 저장
        return true;
    }

    // 파일로 예약 정보 저장
    public void saveReservations() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(floors); // 모든 층의 예약 정보 저장
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 파일에서 예약 정보 로드
    @SuppressWarnings("unchecked")
    public final void loadReservations() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            List<Floor> loadedFloors = (List<Floor>) in.readObject();
            for (int i = 0; i < floors.size(); i++) {
                floors.set(i, loadedFloors.get(i));
            }
        } catch (Exception e) {
            System.out.println("No previous reservations found. Starting fresh.");
        }
    }
}

// Floor class
class Floor implements Serializable {
    private final List<Room> rooms;

    public Floor(int roomsPerFloor) {
        rooms = new ArrayList<>();
        for (int i = 0; i < roomsPerFloor; i++) {
            rooms.add(new Room());
        }
    }

    public Room getRoom(int roomIndex) {
        return rooms.get(roomIndex);
    }

    public boolean isRoomAvailable(int roomIndex, LocalDate checkIn, LocalDate checkOut) {
        return rooms.get(roomIndex).isAvailable(checkIn, checkOut);
    }

    public boolean reserveRoom(int roomIndex, LocalDate checkIn, LocalDate checkOut) {
        return rooms.get(roomIndex).reserve(checkIn, checkOut);
    }
}

class Room implements Serializable {
    private final Map<LocalDate, Boolean> reservations = new HashMap<>();

    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            return false; // 날짜가 null인 경우 예약 불가능 처리
        }

        LocalDate date = checkIn;
        while (!date.isAfter(checkOut)) {
            // 예약된 날짜를 확인하고, 해당 날짜가 예약된 상태가 아니라면 계속 진행
            if (reservations.containsKey(date) && reservations.get(date)) {
                return false; // 예약된 날짜가 있으면 해당 방은 불가
            }
            date = date.plusDays(1);
        }
        return true;
    }

    public boolean reserve(LocalDate checkIn, LocalDate checkOut) {
        if (!isAvailable(checkIn, checkOut)) return false;

        LocalDate date = checkIn;
        while (!date.isAfter(checkOut)) {
            reservations.put(date, true); // 예약을 위한 상태 변경
            date = date.plusDays(1);
        }
        return true;
    }

    public void checkout(LocalDate checkIn, LocalDate checkOut) {
        LocalDate date = checkIn;
        while (!date.isAfter(checkOut)) {
            reservations.put(date, false); // 체크아웃 시 예약 해제
            date = date.plusDays(1);
        }
    }
}