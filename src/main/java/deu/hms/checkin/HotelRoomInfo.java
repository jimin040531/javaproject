/**
 *
 * @author Jimin
 */
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.DayOfWeek;

public class HotelRoomInfo {
    final JFrame frame;
    private JDateChooser checkInDateChooser, checkOutDateChooser;
    private JComboBox<String> floorSelector;
    private final JPanel roomPanel;
    private final ReservationManager reservationManager;

    public HotelRoomInfo() {
        reservationManager = new ReservationManager(10, 10); // 10 floors, 10 rooms per floor

        frame = new JFrame("호텔 객실 정보");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 150);
        frame.setLayout(new BorderLayout());

        // 화면을 중앙에 배치
        frame.setLocationRelativeTo(null);

        // Top panel for controls
        frame.add(createControlPanel(), BorderLayout.NORTH);

        // Room panel for displaying room status
        roomPanel = new JPanel(new GridLayout(10, 10));
        frame.add(roomPanel, BorderLayout.CENTER);

        // JFrame 설정
        frame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE); // 모달 다이얼로그에 의해 차단되지 않도록 설정
        frame.setVisible(true);  // JFrame 표시
        frame.toFront();  // 창을 최상단으로 올리기
        frame.requestFocus();  // 창에 포커스를 줌

        // 창이 포커스를 잃었다가 다시 얻으면 최상단으로 올리기
        frame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                frame.toFront();
                frame.requestFocus();
            }
        });
        
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        checkInDateChooser = createDateChooser("예상 체크인 날짜:", panel);
        checkOutDateChooser = createDateChooser("예상 체크아웃 날짜:", panel);

        floorSelector = new JComboBox<>();
        for (int i = 1; i <= 10; i++) floorSelector.addItem("Floor " + i);
        panel.add(new JLabel("증 선택:"));
        panel.add(floorSelector);
        
        floorSelector.addActionListener(e -> {
            LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate());
            LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate());
            if (checkInDate == null || checkOutDate == null) {
                JOptionPane.showMessageDialog(frame, "체크인 및 체크아웃 날짜를 선택해 주세요.", "날짜 미선택 오류", JOptionPane.WARNING_MESSAGE);
            } else {
                updateRoomAvailability();
            }
        });

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

        // 체크아웃 날짜 선택 시 유효성 검증 이벤트 추가
        if (label.equals("예상 체크아웃 날짜:")) {
            dateChooser.addPropertyChangeListener("date", evt -> {
                validateCheckOutDate();
            });
        }

        return dateChooser;
    }

    private void validateCheckOutDate() {
        LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate());
        LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate());

        if (checkInDate != null && checkOutDate != null) {
            if (checkOutDate.isBefore(checkInDate)) {
                JOptionPane.showMessageDialog(
                    roomPanel,
                    "체크아웃 날짜는 체크인 날짜보다 나중이어야 합니다. 최소 1발 이상 예상 예약 가능합니다.",
                    "날짜 선택 오류",
                    JOptionPane.WARNING_MESSAGE
                );
                checkOutDateChooser.setDate(java.sql.Date.valueOf(checkInDate.plusDays(1))); // 기본값으로 변경
            } else if (checkOutDate.isEqual(checkInDate)) {
                JOptionPane.showMessageDialog(
                    roomPanel,
                    "체크인 날짜와 체크아웃 날짜는 같을 수 없습니다. 최소 1발 이상 예약 가능합니다.",
                    "날짜 선택 오류",
                    JOptionPane.WARNING_MESSAGE
                );
                checkOutDateChooser.setDate(java.sql.Date.valueOf(checkInDate.plusDays(1))); // 기본값으로 변경
            }
        }
    }

    public class RoomPricing {
        private static final int[] FLOOR_BASE_PRICE = {50000, 60000, 70000, 80000, 90000, 100000, 110000, 120000, 130000, 140000};
        private static final int WEEKEND_SURCHARGE = 50000;

        public static int calculateRoomPrice(int floor, LocalDate date) {
            if (floor < 1 || floor > 10) {
                throw new IllegalArgumentException("유효하지 않은 증 번호입니다.");
            }

            int basePrice = FLOOR_BASE_PRICE[floor - 1];
            if (isWeekend(date)) {
                basePrice += WEEKEND_SURCHARGE;
            }
            
            return basePrice;
        }
        
        private static boolean isWeekend(LocalDate date) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            return dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY;
        }
    }

    
    void updateRoomAvailability() {
        roomPanel.removeAll();

        // 체크인 및 체크아웃 날짜가 선택되지 않은 경우 방을 표시하지 않음
        if (checkInDateChooser.getDate() == null || checkOutDateChooser.getDate() == null) {
            roomPanel.revalidate();
            roomPanel.repaint();
            return;
        }

        // 체크인과 체크아웃 날짜를 선택한 경우
        LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate());
        LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate());

        // 기본값 설정: 체크인 날짜가 선택되지 않았다면 오늘 날짜, 체크아웃 날짜는 체크인 단중 남
        if (checkInDate == null) {
            checkInDate = LocalDate.now();
        }
        if (checkOutDate == null || checkOutDate.isBefore(checkInDate)) {
            checkOutDate = checkInDate.plusDays(1);
        }

        int selectedFloor = floorSelector.getSelectedIndex();

        // GridLayout으로 변경하여 위에 5개, 아래에 5개 밍칭
        roomPanel.setLayout(new GridLayout(2, 5)); // 2행, 5열로 설정

        for (int room = 0; room < 10; room++) {
            // 방 번호 생성
            String roomNumber = "Room " + (selectedFloor + 1) + String.format("%02d", (room + 1));

            // 전체 기간 요금 계산
            int totalPrice = 0;
            LocalDate currentDate = checkInDate;
            while (!currentDate.isAfter(checkOutDate.minusDays(1))) {
                totalPrice += RoomPricing.calculateRoomPrice(selectedFloor + 1, currentDate);
                currentDate = currentDate.plusDays(1);
            }

            // 버튼 텍스트에 방 번호와 총 가격 표시
            JButton roomButton = new JButton(roomNumber + " - " + totalPrice + "원");

            // 해당 방이 예약 가능한지 확인
            boolean isAvailable = reservationManager.isRoomAvailable(selectedFloor, room, checkInDate, checkOutDate);
            roomButton.setBackground(isAvailable ? Color.GREEN : Color.LIGHT_GRAY);

            final int currentRoom = room;  // final로 채널 (Effectively final)
            final LocalDate checkIn = checkInDate;  // 추가된 부분
            final LocalDate checkOut = checkOutDate; // 추가된 부분

            // 버튼 클릭 시 예약 시도
            roomButton.addActionListener((ActionEvent e) -> {
                if (checkInDateChooser.getDate() == null || checkOutDateChooser.getDate() == null) {
                    JOptionPane.showMessageDialog(frame, "체크인 및 체크아웃 날짜를 선택해 주세요.",
                                                  "날짜 미선택 오류", JOptionPane.WARNING_MESSAGE);
                    return;  // 날짜가 없으면 예약 진행하지 않음
                }
                attemptReservation(selectedFloor, currentRoom, checkIn, checkOut);  // 람다스타의 상자를 사용
            });

            // 버튼을 roomPanel에 추가
            roomPanel.add(roomButton);
        }

        roomPanel.revalidate();
        roomPanel.repaint();
    }


    private void attemptReservation(int floor, int room, LocalDate checkIn, LocalDate checkOut) {
        // 체크인 및 체크아웃 날짜가 선택되지 않은 경우 경고 메시지 표시
        if (checkIn == null || checkOut == null) {
            JOptionPane.showMessageDialog(frame, "체크인 및 체크아웃 날짜를 선택해 주세요.", 
                                          "날짜 미선택 오류", JOptionPane.WARNING_MESSAGE);
            return;  // 날짜가 없으면 예약 진행하지 않음
        }

        // 예약을 시도
        if (reservationManager.reserveRoom(floor, room, checkIn, checkOut)) {
            JOptionPane.showMessageDialog(frame, "예약 성공 하셨습니다.");
            updateRoomAvailability();
        } else {
            JOptionPane.showMessageDialog(frame, "이미 예약된 방입니다.");
        }
    }

    private LocalDate getLocalDate(java.util.Date date) {
        if (date == null) {
            return null; // null 채널
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
            out.writeObject(floors); // 모든 증의 예약 정보 저장
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
            return false; // 날짜가 null인 경우 예약 부담 처리
        }

        LocalDate date = checkIn;
        while (date.isBefore(checkOut)) {
            // 예약된 날짜를 확인하고, 해당 날짜가 예약된 상태가 아닌한 계속 진행
            if (reservations.containsKey(date) && reservations.get(date)) {
                return false; // 예약된 날짜가 있으면 해당 방은 부가
            }
            date = date.plusDays(1);
        }
        return true;
    }

    public boolean reserve(LocalDate checkIn, LocalDate checkOut) {
        if (!isAvailable(checkIn, checkOut)) return false;

        LocalDate date = checkIn;
        while (date.isBefore(checkOut)) {
            reservations.put(date, true); // 예약을 위한 상태 변경
            date = date.plusDays(1);
        }
        return true;
    }

    public void checkout(LocalDate checkIn, LocalDate checkOut) {
        LocalDate date = checkIn;
        while (date.isBefore(checkOut)) {
            reservations.put(date, false); // 체크아웃 시 예약 해제
            date = date.plusDays(1);
        }
    }
}
