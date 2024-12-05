package deu.hms.utility;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import deu.hms.reservation.Registration;
import deu.hms.reservation.reservationFrame;

/**
 *
 * @author Jimin
 */
public class HotelRoomReservationUI {
    private final JFrame frame;
    private final ReservationManager reservationManager;
    private final JPanel roomPanel;
    private JDateChooser checkInDateChooser;
    private JDateChooser checkOutDateChooser;
    private JComboBox<String> floorSelector;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
    private final reservationFrame parentFrame;

    // 기본 생성자 (parentFrame이 없는 경우)
   

    // parentFrame을 받는 생성자
    public HotelRoomReservationUI(reservationFrame parentFrame) {
     
       this.parentFrame = new reservationFrame(); // 객체 생성
      reservationManager = new hotelReservationManager(10, 10); // 10층, 층당 10개의 객실 초기화
     initializeRoomPricesAndGrades(); // 객실 가격 및 등급 초기화
    registration = new Registration(parentFrame); // 생성한 객체 전달
    frame = new JFrame("호텔 객실 정보");
    roomPanel = new JPanel(new GridLayout(10, 10));
    frame.setLayout(new BorderLayout());
    frame.add(createControlPanel(), BorderLayout.NORTH);
    frame.add(roomPanel, BorderLayout.CENTER);
    frame.setSize(1100, 180); // 전체 프레임 크기 설정
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    }
    // 외부에서 창을 표시할 수 있도록 하는 메서드
    public void showUI() {
        frame.setVisible(true);
    }

    // 객실 정보를 파일에서 불러오는 메서드
    private void loadRoomInfoFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("roomInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int floor = Integer.parseInt(parts[0].trim()) - 1; // 층 번호를 인덱스로 맞추기 위해 1 감소
                    int roomNumber = Integer.parseInt(parts[1].trim()) - 101; // 방 번호를 인덱스로 맞추기 위해 시작 번호를 조정

                    // roomsPerFloor에 따라 방 번호를 조정해야 함
                    if (roomNumber < 0 || roomNumber >= reservationManager.getFloors().get(floor).getRooms().size()) {
                        System.out.println("Invalid room index: " + roomNumber);
                        continue;
                    }

                    int price = Integer.parseInt(parts[2].trim());
                    String grade = parts[3].trim();
                    int capacity = Integer.parseInt(parts[4].trim());

                    // 객실 정보를 ReservationManager에 설정
                    reservationManager.setRoomInfo(floor, roomNumber, price, grade, capacity);
                } 
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "객실 정보를 파일에서 불러오는 중 오류가 발생했습니다.", "불러오기 오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    // 상단 패널 생성 (층 선택, 체크인/체크아웃 날짜 선택 등)
    private JPanel createControlPanel() {
       Registration registration = new Registration(); // registration 객체 초기화

        JPanel panel = new JPanel(new FlowLayout()); // 플로우 레이아웃을 사용한 패널 생성

        // 체크인 및 체크아웃 날짜 선택기를 패널에 추가
        checkInDateChooser = new JDateChooser();
        checkInDateChooser.setPreferredSize(new Dimension(120, 30)); // 크기 설정 (너비 150, 높이 30)
        panel.add(new JLabel("예상 체크인 날짜:"));
        panel.add(checkInDateChooser);

        checkOutDateChooser = new JDateChooser();
        checkOutDateChooser.setPreferredSize(new Dimension(120, 30)); // 크기 설정 (너비 150, 높이 30)
        panel.add(new JLabel("예상 체크아웃 날짜:"));
        panel.add(checkOutDateChooser);

        // 층 선택 콤보박스를 생성하고 패널에 추가
        floorSelector = new JComboBox<>();
        for (int i = 1; i <= 10; i++) floorSelector.addItem("Floor " + i);
        panel.add(new JLabel("층 선택:"));
        panel.add(floorSelector);
            
      //저장버튼
        JButton saveButton = new JButton("저장"); // 버튼 이름 지정
        saveButton.addActionListener(e -> {
         // 체크인 날짜와 체크아웃 날짜 가져오기
    String checkInDate = ((JTextField) checkInDateChooser.getDateEditor().getUiComponent()).getText();
    String checkOutDate = ((JTextField) checkOutDateChooser.getDateEditor().getUiComponent()).getText();


    // Registration에 날짜 전달
    registration.updateDates(checkInDate, checkOutDate);

    // Registration 폼을 다시 보이도록 설정
    registration.setVisible(true);    // 폼 보이기
    registration.toFront();           // 최상단으로 가져오기
    registration.setSize(500, 450);
     frame.setVisible(false);
// 폼 강제 업데이트
});

// 버튼을 패널에 추가
panel.add(saveButton);
        //여기까지가 저장버튼 


        // 뒤로가기 버튼
        JButton backButton = new JButton("이전");
        backButton.addActionListener(e -> {
            frame.setVisible(false);
        });
        panel.add(backButton); // 패널에 뒤로가기 버튼 추가

        // 층 선택 시 객실 상태 업데이트
        floorSelector.addActionListener(e -> {
            LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate());
            LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate());
            if (checkInDate == null || checkOutDate == null) {
                JOptionPane.showMessageDialog(frame, "체크인 및 체크아웃 날짜를 선택해 주세요.", "날짜 미선택 오류", JOptionPane.WARNING_MESSAGE);
            } else {
                updateRoomAvailability(); // 선택된 날짜에 따른 객실 상태 업데이트
            }
        });

        return panel;
    }

    // 객실 예약 가능 상태 업데이트 메서드
    private void updateRoomAvailability() {
        roomPanel.removeAll();

        if (checkInDateChooser.getDate() == null || checkOutDateChooser.getDate() == null) {
            roomPanel.revalidate();
            roomPanel.repaint();
            return;
        }

        final LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate());
        final LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate());

        if (checkOutDate == null || checkOutDate.isBefore(checkInDate)) {
            JOptionPane.showMessageDialog(frame, "체크아웃 날짜는 체크인 날짜보다 나중이어야 합니다.", "날짜 선택 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        final int selectedFloor = floorSelector.getSelectedIndex();
        final HotelFloor currentFloor = reservationManager.getFloor(selectedFloor);
        roomPanel.setLayout(new GridLayout(2, 5));

        for (int roomIndex = 0; roomIndex < currentFloor.getRooms().size(); roomIndex++) {
            final int finalRoomIndex = roomIndex; // roomIndex를 final로 선언하여 사용
            HotelRoom roomObj = currentFloor.getRoom(finalRoomIndex);
            String roomNumber = (selectedFloor + 1) + String.format("%02d", (finalRoomIndex + 1));
            boolean isAvailable = roomObj.isAvailable(checkInDate, checkOutDate);

            int inDay = Integer.parseInt(dateFormat.format(checkInDateChooser.getDate()));
            int outDay = Integer.parseInt(dateFormat.format(checkOutDateChooser.getDate()));

            int roomPrice = roomObj.getPrice();
            String roomGrade = roomObj.getGrade();
            if ((outDay - inDay) > 1) {
                roomPrice += ((outDay - inDay) - 1) * (roomObj.getPrice() / 2);
            }
            final int finalRoomPrice = roomPrice; // roomPrice를 final로 설정
            int roomCapacity = roomObj.getCapacity();

            String buttonText = roomNumber + " / " + finalRoomPrice + "원 / " + roomGrade + " / " + roomCapacity + "명";
            JButton roomButton = new JButton(buttonText);
            roomButton.setPreferredSize(new Dimension(150, 50)); // 버튼 크기 조정 (적당한 크기로 조정)
            roomButton.setBackground(isAvailable ? Color.GREEN : Color.LIGHT_GRAY);

            // 람다 표현식 내 사용 변수는 반드시 final 또는 effectively final이어야 함
            roomButton.addActionListener((ActionEvent e) -> {
                if (isAvailable) {
                    reserveRoom(selectedFloor + 1, finalRoomIndex + 1, checkInDate, checkOutDate, finalRoomPrice);
                } else {
                    JOptionPane.showMessageDialog(frame, "이미 예약된 방입니다.", "예약 불가", JOptionPane.WARNING_MESSAGE);
                }
            });

            roomPanel.add(roomButton);
        }

        roomPanel.revalidate();
        roomPanel.repaint();
    }

    // 특정 층의 특정 방을 예약하는 메서드
    private void reserveRoom(int floor, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate, int totalCost) {
        if (reservationManager.isRoomAvailable(floor - 1, roomNumber - 1, checkInDate, checkOutDate)) {
            if (reservationManager.reserveRoom(floor - 1, roomNumber - 1, checkInDate, checkOutDate)) {
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
            writer.write(uniqueNumber + "," + floor + "," + roomNumber + "," + checkInDateStr + "," + checkOutDateStr + "," + totalCost + "," + status);
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "예약 정보를 저장하는 중 오류가 발생했습니다.", "저장 오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // java.util.Date 객체를 LocalDate로 변환하는 메서드
    private LocalDate getLocalDate(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }
    public void setVisible(boolean visible) {
    if (frame != null) {
        frame.setVisible(visible);
    }
}
}
