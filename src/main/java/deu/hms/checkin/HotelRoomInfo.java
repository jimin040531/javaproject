package deu.hms.checkin;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import com.toedter.calendar.JDateChooser; // JCalendar 라이브러리 임포트
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class HotelRoomInfo {

    final JFrame frame;
    private JDateChooser checkInDateChooser, checkOutDateChooser;
    private JComboBox<String> floorSelector;
    private final JPanel roomPanel;
    private final RoomReservationManager reservationManager;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
    private int inDay = 0;
    private int outDay = 0;

    // 생성자: GUI 초기화 및 예약 관리 객체 생성
    public HotelRoomInfo() {
        reservationManager = new RoomReservationManager(10, 10); // 10층, 층당 10개의 객실 초기화
        initializeRoomPricesAndGrades(); // 객실 가격 및 등급 초기화

        frame = new JFrame("호텔 객실 정보");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1100, 150);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        frame.add(createControlPanel(), BorderLayout.NORTH);
        roomPanel = new JPanel(new GridLayout(10, 10));
        frame.add(roomPanel, BorderLayout.CENTER);

        frame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        frame.setVisible(true);
        frame.toFront();
        frame.requestFocus();

        frame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                frame.toFront();
                frame.requestFocus();
            }
        });
    }

    // 객실 가격과 등급, 수용 인원 초기화 메서드
    private void initializeRoomPricesAndGrades() {
        try (BufferedReader reader = new BufferedReader(new FileReader("roomInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) { // 층, 객실 번호, 가격, 등급, 수용 인원
                    int floor = Integer.parseInt(parts[0].trim()) - 1;
                    int roomNumber = Integer.parseInt(parts[1].trim()) % 100 - 1;
                    int price = Integer.parseInt(parts[2].trim());
                    String grade = parts[3].trim();
                    int capacity = Integer.parseInt(parts[4].trim());

                    if (floor >= 0 && floor < reservationManager.getFloors().size()) {
                        hotelFloor currentFloor = reservationManager.getFloor(floor);
                        if (roomNumber >= 0 && roomNumber < currentFloor.getRooms().size()) {
                            currentFloor.setRoomInfo(roomNumber, price, grade, capacity);
                        } else {
                            System.out.println("Invalid room number: " + roomNumber);
                        }
                    } else {
                        System.out.println("Invalid floor number: " + floor);
                    }
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "객실 정보를 파일에서 불러오는 중 오류가 발생했습니다.", "불러오기 오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // 특정 층의 특정 방을 예약하는 메서드
    public void reserveRoom(int floor, int roomNumber) {
        LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate());
        LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate());

        if (checkInDate == null || checkOutDate == null) {
            JOptionPane.showMessageDialog(frame, "체크인 및 체크아웃 날짜를 선택해 주세요.", "날짜 미선택 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate currentDate = LocalDate.now();
        if (checkInDate.isBefore(currentDate)) {
            JOptionPane.showMessageDialog(frame, "지난 날짜로는 예약할 수 없습니다.", "예약 불가", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (reservationManager.isRoomAvailable(floor - 1, roomNumber - 1, checkInDate, checkOutDate)) {
            if (reservationManager.reserveRoom(floor - 1, roomNumber - 1, checkInDate, checkOutDate)) {
                long days = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
                int roomPrice = reservationManager.getFloor(floor - 1).getRoom(roomNumber - 1).getPrice();
                int totalCost = roomPrice;
                if (days > 1) {
                    totalCost += (outDay - inDay) * (roomPrice / 2); // 1박 추가 시 기본 요금의 반값 추가
                }

                JOptionPane.showMessageDialog(frame, "객실 예약이 완료되었습니다. 총 요금: " + totalCost + "원", "예약 성공", JOptionPane.INFORMATION_MESSAGE);
                saveReservationToFile(floor, roomNumber, checkInDate, checkOutDate, totalCost);
                updateRoomAvailability();
            } else {
                JOptionPane.showMessageDialog(frame, "이미 예약된 방입니다.", "예약 불가", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "잘못된 객실 번호이거나 예약 불가 상태입니다.", "예약 불가", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 예약 정보를 파일에 저장하는 메서드
    private void saveReservationToFile(int floor, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate, int totalCost) {
        String uniqueNumber = UUID.randomUUID().toString(); // 고유 번호 생성
        String status = "예약 완료"; // 상태 설정
        String checkInDateStr = checkInDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String checkOutDateStr = checkOutDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Reservation.txt", true))) {
            writer.write(uniqueNumber + "," + (floor + 1) + "," + (roomNumber + 1) + "," + checkInDateStr + "," + checkOutDateStr + "," + totalCost + "," + status);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "예약 정보를 저장하는 중 오류가 발생했습니다.", "저장 오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // 상단 패널 생성
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        checkInDateChooser = createDateChooser("예상 체크인 날짜:", panel);
        checkOutDateChooser = createDateChooser("예상 체크아웃 날짜:", panel);

        floorSelector = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            floorSelector.addItem("Floor " + i);
        }
        panel.add(new JLabel("층 선택:"));
        panel.add(floorSelector);

        floorSelector.addActionListener(e -> updateRoomAvailability());

        return panel;
    }

    // 날짜 선택기 생성 메서드
    private JDateChooser createDateChooser(String label, JPanel parent) {
        parent.add(new JLabel(label));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        parent.add(dateChooser);
        dateChooser.addPropertyChangeListener("date", evt -> {
            if (label.equals("예상 체크아웃 날짜:")) {
                validateCheckOutDate();
            }
        });
        return dateChooser;
    }

    // 체크아웃 날짜 유효성 검사 메서드
    private void validateCheckOutDate() {
        LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate());
        LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate());

        if (checkInDate != null && checkOutDate != null && (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate))) {
            JOptionPane.showMessageDialog(frame, "체크아웃 날짜는 체크인 날짜보다 나중이어야 합니다.", "날짜 선택 오류", JOptionPane.WARNING_MESSAGE);
            checkOutDateChooser.setDate(java.sql.Date.valueOf(checkInDate.plusDays(1)));
        }
    }

    // 객실 예약 가능 상태 업데이트 메서드
    void updateRoomAvailability() {
        roomPanel.removeAll();

        if (checkInDateChooser.getDate() == null || checkOutDateChooser.getDate() == null) {
            roomPanel.revalidate();
            roomPanel.repaint();
            return;
        }

        LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate());
        LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate());

        if (checkOutDate == null || checkOutDate.isBefore(checkInDate)) {
            checkOutDate = checkInDate.plusDays(1);
        }

        int selectedFloor = floorSelector.getSelectedIndex();

        roomPanel.setLayout(new GridLayout(2, 5));

        hotelFloor currentFloor = reservationManager.getFloor(selectedFloor);

        for (int room = 0; room < currentFloor.getRooms().size(); room++) {
            HotelRoom roomObj = currentFloor.getRoom(room);
            String roomNumber = (selectedFloor + 1) + String.format("%02d", (room + 1));
            boolean isAvailable = roomObj.isAvailable(checkInDate, checkOutDate);
            
            inDay = Integer.valueOf(dateFormat.format(checkInDateChooser.getDate()));
            outDay = Integer.valueOf(dateFormat.format(checkOutDateChooser.getDate()));

            int roomPrice = roomObj.getPrice();
            String roomGrade = roomObj.getGrade();
            if((outDay - inDay) > 1){
                roomPrice += ((outDay - inDay) - 1) * (roomObj.getPrice() / 2);
            }
            int roomCapacity = roomObj.getCapacity();

            String buttonText = roomNumber + " / " + roomPrice + "원 / " + roomGrade + " / " + roomCapacity + "명";
            JButton roomButton = new JButton(buttonText);
            roomButton.setBackground(isAvailable ? Color.GREEN : Color.LIGHT_GRAY);

            final int currentFloorIndex = selectedFloor;
            final int currentRoomIndex = room;

            roomButton.addActionListener((ActionEvent e) -> {
                if (isAvailable) {
                    reserveRoom(currentFloorIndex + 1, currentRoomIndex + 1);
                } else {
                    JOptionPane.showMessageDialog(frame, "이미 예약된 방입니다.", "예약 불가", JOptionPane.WARNING_MESSAGE);
                }
            });

            roomPanel.add(roomButton);
        }

        roomPanel.revalidate();
        roomPanel.repaint();
    }

    // java.util.Date 객체를 LocalDate로 변환하는 메서드
    private LocalDate getLocalDate(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    // 메인 메서드: 프로그램 실행 진입점
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelRoomInfo::new);
    }
}
