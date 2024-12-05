package deu.hms.checkin;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import com.toedter.calendar.JDateChooser; // JCalendar 라이브러리 임포트
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import deu.hms.reservation.Registration;
import deu.hms.reservation.ReservationData;
import deu.hms.reservation.reservationFrame;

public class HotelRoomInfo  {
    final JFrame frame; // 메인 프레임
    private JDateChooser checkInDateChooser, checkOutDateChooser; // 체크인 및 체크아웃 날짜 선택기
    private JComboBox<String> floorSelector; // 층 선택 콤보박스
    private final JPanel roomPanel; // 객실 상태를 표시할 패널
    private final hotelReservationManager reservationManager; // 예약 관리 객체
         private final reservationFrame parentFrame;
    private final Registration registration;


    // 객실 가격과 등급 정보
    private final int[] roomPrices = new int[100]; // 객실 가격 배열
    private final String[] roomGrades = new String[100]; // 객실 등급 배열
    
    // 생성자: GUI 초기화 및 예약 관리 객체 생성
    public HotelRoomInfo () {
                this.parentFrame = new reservationFrame(); // 객체 생성

        reservationManager = new hotelReservationManager(10, 10); // 10층, 층당 10개의 객실 초기화
        initializeRoomPricesAndGrades(); // 객실 가격 및 등급 초기화
        
                registration = new Registration(parentFrame); // 생성한 객체 전달

        frame = new JFrame("호텔 객실 정보");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 150);
        frame.setLayout(new BorderLayout());

        // 화면을 중앙에 배치
        frame.setLocationRelativeTo(null);

        // 상단 패널 추가 (층 선택 및 날짜 선택 기능)
        frame.add(createControlPanel(), BorderLayout.NORTH);

        // 객실 상태를 표시하는 패널 추가
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

    // 객실 가격과 등급 초기화 메서드
    private void initializeRoomPricesAndGrades() {
        try (BufferedReader reader = new BufferedReader(new FileReader("roomInfo.txt"))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null && index < 100) {
                String[] parts = line.split(",");
                if (parts.length == 4) { // 층, 객실 번호, 가격, 등급
                    int floor = Integer.parseInt(parts[0].trim());
                    int roomNumber = Integer.parseInt(parts[1].trim());
                    int price = Integer.parseInt(parts[2].trim());
                    String grade = parts[3].trim();

                    // 배열 인덱스를 계산하여 저장
                    int roomIndex = (floor - 1) * 10 + (roomNumber % 100 - 1);
                    if (roomIndex >= 0 && roomIndex < 100) {
                        roomPrices[roomIndex] = price;
                        roomGrades[roomIndex] = grade;
                        index++;
                    } else {
                        System.out.println("Invalid room index: " + roomIndex);
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
        LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate()); // 체크인 날짜 가져오기
        LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate()); // 체크아웃 날짜 가져오기

        if (checkInDate == null || checkOutDate == null) { // 체크인 또는 체크아웃 날짜가 선택되지 않았을 경우
            JOptionPane.showMessageDialog(frame, "체크인 및 체크아웃 날짜를 선택해 주세요.", "날짜 미선택 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 현재 날짜보다 이전 날짜로 체크인하려고 할 경우 예약 불가 처리
        LocalDate currentDate = LocalDate.now();
        if (checkInDate.isBefore(currentDate)) {
            JOptionPane.showMessageDialog(frame, "지난 날짜로는 예약할 수 없습니다.", "예약 불가", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int roomIndex = (floor - 1) * 10 + (roomNumber % 100 - 1); // 객실 인덱스 계산
        if (roomIndex < 0 || roomIndex >= roomPrices.length) { // 유효하지 않은 객실 번호일 경우
            JOptionPane.showMessageDialog(frame, "잘못된 객실 번호입니다.", "예약 불가", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 예약 가능 여부 확인 후 예약 처리
        if (reservationManager.isRoomAvailable(floor - 1, roomNumber - 1, checkInDate, checkOutDate)) {
            if (reservationManager.reserveRoom(floor - 1, roomNumber - 1, checkInDate, checkOutDate)) {
                JOptionPane.showMessageDialog(frame, "객실 예약이 완료되었습니다.", "예약 성공", JOptionPane.INFORMATION_MESSAGE);
                updateRoomAvailability(); // 예약 후 객실 상태 업데이트
            } else {
                JOptionPane.showMessageDialog(frame, "이미 예약된 방입니다.", "예약 불가", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "잘못된 객실 번호이거나 예약 불가 상태입니다.", "예약 불가", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 상단 패널 생성 (층 선택, 체크인/체크아웃 날짜 선택 등)
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout()); // 플로우 레이아웃을 사용한 패널 생성

        // 체크인 및 체크아웃 날짜 선택기를 패널에 추가
        checkInDateChooser = createDateChooser("예상 체크인 날짜:", panel);
        checkOutDateChooser = createDateChooser("예상 체크아웃 날짜:", panel);

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
    
    registration.setVisible(true);    // 폼 보이기
    registration.toFront();           // 최상단으로 가져오기
    registration.setSize(500, 450);
     frame.setVisible(false); 

});
panel.add(backButton); // 패널에 뒤로가기 버튼 추가
         //여기까지가 뒤로가기




        panel.add(saveButton); // 패널에 버튼 추가
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

    // 날짜 선택기 생성 메서드 수정 (오류 수정)
    private JDateChooser createDateChooser(String label, JPanel parent) {
        parent.add(new JLabel(label)); // 레이블 추가
        JDateChooser dateChooser = new JDateChooser(); // 날짜 선택기 생성
        dateChooser.setDateFormatString("yyyy-MM-dd"); // 날짜 형식 설정
        parent.add(dateChooser); // 날짜 선택기 패널에 추가

        // 체크아웃 날짜가 변경될 때 유효성 검사
        dateChooser.addPropertyChangeListener("date", evt -> {
            if (label.equals("예상 체크아웃 날짜:")) {
                validateCheckOutDate(); // 체크아웃 날짜 유효성 검사 호출
            }
        });

        return dateChooser;
    }

    // 체크아웃 날짜 유효성 검사 메서드 수정 (오류 수정)
    private void validateCheckOutDate() {
        LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate()); // 체크인 날짜 가져오기
        LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate()); // 체크아웃 날짜 가져오기

        // 체크아웃 날짜가 체크인 날짜보다 빠르거나 같으면 경고 메시지 출력 및 날짜 조정
        if (checkInDate != null && checkOutDate != null) {
            if (checkOutDate.isBefore(checkInDate)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "체크아웃 날짜는 체크인 날짜보다 나중이어야 합니다. 최소 1박 이상 예약 가능합니다.",
                    "날짜 선택 오류",
                    JOptionPane.WARNING_MESSAGE
                );
                checkOutDateChooser.setDate(java.sql.Date.valueOf(checkInDate.plusDays(1))); // 기본값으로 변경
            } else if (checkOutDate.isEqual(checkInDate)) {
                JOptionPane.showMessageDialog(
                    frame,
                    "체크인 날짜와 체크아웃 날짜는 같을 수 없습니다. 최소 1박 이상 예약 가능합니다.",
                    "날짜 선택 오류",
                    JOptionPane.WARNING_MESSAGE
                );
                checkOutDateChooser.setDate(java.sql.Date.valueOf(checkInDate.plusDays(1))); // 기본값으로 변경
            }
        }
    }

   // 객실 예약 가능 상태 업데이트 메서드
    void updateRoomAvailability() {
        roomPanel.removeAll(); // 기존의 모든 객실 버튼 제거

        // 체크인 및 체크아웃 날짜가 선택되지 않았을 경우 업데이트 중지
        if (checkInDateChooser.getDate() == null || checkOutDateChooser.getDate() == null) {
            roomPanel.revalidate();
            roomPanel.repaint();
            return;
        }

        LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate()); // 체크인 날짜 가져오기
        LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate()); // 체크아웃 날짜 가져오기

        // 체크아웃 날짜가 체크인 날짜보다 빠르다면 체크아웃 날짜를 조정
        if (checkInDate == null) {
            checkInDate = LocalDate.now();
        }
        if (checkOutDate == null || checkOutDate.isBefore(checkInDate)) {
            checkOutDate = checkInDate.plusDays(1);
        }

        int selectedFloor = floorSelector.getSelectedIndex(); // 선택된 층 가져오기

        roomPanel.setLayout(new GridLayout(2, 5)); // 객실 버튼을 2행 5열로 배치

        final int selectedFloorFinal = selectedFloor;
        final LocalDate checkInDateFinal = checkInDate;
        final LocalDate checkOutDateFinal = checkOutDate;

        // 각 객실의 예약 가능 여부를 확인하고 버튼 생성
        for (int room = 0; room < 10; room++) {
            String roomNumber = (selectedFloorFinal + 1) + String.format("%02d", (room + 1)); // 객실 번호 생성
            int roomIndex = selectedFloorFinal * 10 + room; // 객실 인덱스 계산
            boolean isAvailable = reservationManager.isRoomAvailable(selectedFloorFinal, room, checkInDateFinal, checkOutDateFinal); // 예약 가능 여부 확인

            String roomGrade = roomGrades[roomIndex]; // 객실 등급 가져오기
            int roomPrice = roomPrices[roomIndex]; // 객실 가격 가져오기
            JButton roomButton = new JButton(roomNumber + " / " + roomPrice + "원 / " + roomGrade); // 객실 정보 표시 버튼 생성
            roomButton.setBackground(isAvailable ? Color.GREEN : Color.LIGHT_GRAY); // 예약 가능 여부에 따라 버튼 색상 설정

            final int currentRoom = room;
            roomButton.addActionListener((ActionEvent e) -> {
                if (checkInDateChooser.getDate() == null || checkOutDateChooser.getDate() == null) {
                    JOptionPane.showMessageDialog(frame, "체크인 및 체크아웃 날짜를 선택해 주세요.",
                                                  "날짜 미선택 오류", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                attemptReservation(selectedFloorFinal + 1, currentRoom + 1, checkInDateFinal, checkOutDateFinal);
            });

            roomPanel.add(roomButton);
        }

        roomPanel.revalidate();
        roomPanel.repaint();
    }

    // 예약 시도 메서드 수정 (객체지향적 구조 반영)
    private void attemptReservation(int floor, int room, LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            JOptionPane.showMessageDialog(frame, "체크인 및 체크아웃 날짜를 선택해 주세요.", 
                                          "날짜 미선택 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (reservationManager.reserveRoom(floor - 1, room - 1, checkIn, checkOut)) {
            JOptionPane.showMessageDialog(frame, "예약 성공 하셨습니다.");
            updateRoomAvailability();
        } else {
            JOptionPane.showMessageDialog(frame, "이미 예약된 방입니다.");
        }
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
        SwingUtilities.invokeLater(HotelRoomInfo ::new);
    }
}
