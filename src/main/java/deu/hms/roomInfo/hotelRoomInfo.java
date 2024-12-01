package deu.hms.roomInfo;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;

public class hotelRoomInfo {
    private final JFrame frame;
    private JDateChooser checkInDateChooser, checkOutDateChooser;
    private JComboBox<String> floorSelector;
    private final JPanel roomPanel;
    private final reservationManager reservationManager;
    private final roomPricing roomPricing;
    private final dateValidator dateValidator;
    
    public hotelRoomInfo() {
        reservationManager = new reservationManager(10, 10);
        roomPricing = new roomPricing();
        dateValidator = new dateValidator();

        frame = createFrame("호텔 객실 정보");

        roomPanel = new JPanel(new GridLayout(10, 10));
        frame.add(roomPanel, BorderLayout.CENTER);

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

    private JFrame createFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 200);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.add(createControlPanel(), BorderLayout.NORTH);
        return frame;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        checkInDateChooser = createDateChooser("예상 체크인 날짜:", panel);
        checkOutDateChooser = createDateChooser("예상 체크아웃 날짜:", panel);

        floorSelector = new JComboBox<>();
        for (int i = 1; i <= 10; i++) floorSelector.addItem("Floor " + i);
        panel.add(new JLabel("층 선택:"));
        panel.add(floorSelector);

        floorSelector.addActionListener(e -> updateRoomAvailability());

        JButton roomInfoButton = new JButton("객실 정보 저장");
        roomInfoButton.addActionListener(e -> saveRoomInfoToFile());
        panel.add(roomInfoButton);

        return panel;
    }

    private JDateChooser createDateChooser(String label, JPanel parent) {
        parent.add(new JLabel(label));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        parent.add(dateChooser);

        if (label.equals("예상 체크아웃 날짜:")) {
            dateChooser.addPropertyChangeListener("date", evt -> dateValidator.validateCheckOutDate(checkInDateChooser, checkOutDateChooser, roomPanel));
        }

        return dateChooser;
    }

    private void updateRoomAvailability() {
        roomPanel.removeAll();

        final LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate());
        final LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate());

        if (checkInDate == null || checkOutDate == null) {
            roomPanel.revalidate();
            roomPanel.repaint();
            return;
        }

        final int selectedFloor = floorSelector.getSelectedIndex();
        roomPanel.setLayout(new GridLayout(2, 5, 15, 15));

        for (int room = 0; room < 10; room++) {
            JPanel roomContainer = new JPanel(new BorderLayout(5, 5));
            String roomNumber = (selectedFloor + 1) + String.format("%02d", (room + 1));
            int totalPrice = roomPricing.calculateTotalPrice(selectedFloor + 1, checkInDate, checkOutDate);
            String roomGrade = RoomGrade.getRoomGrade(selectedFloor + 1, room + 1);

            JButton roomButton = new JButton(roomNumber + " / " + totalPrice + " / " + roomGrade);
            roomButton.setPreferredSize(new Dimension(180, 80));

            boolean isAvailable = reservationManager.isRoomAvailable(selectedFloor, room, checkInDate, checkOutDate);
            roomButton.setBackground(isAvailable ? Color.GREEN : Color.LIGHT_GRAY);

            final int currentRoom = room; // effectively final 변수로 만들어 람다식에서 사용 가능하게 함

            roomButton.addActionListener((ActionEvent e) -> {
                if (checkInDateChooser.getDate() == null || checkOutDateChooser.getDate() == null) {
                    JOptionPane.showMessageDialog(frame, "체크인 및 체크아웃 날짜를 선택해 주세요.",
                            "날짜 미선택 오류", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                attemptReservation(selectedFloor, currentRoom, checkInDate, checkOutDate);
            });

            roomContainer.add(roomButton, BorderLayout.CENTER);
            roomPanel.add(roomContainer);
        }

        roomPanel.revalidate();
        roomPanel.repaint();
    }

    private void saveRoomInfoToFile() {
        String[] roomInfoArray = new String[100];
        int index = 0;

        for (Component comp : roomPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                for (Component innerComp : panel.getComponents()) {
                    if (innerComp instanceof JButton) {
                        roomInfoArray[index++] = ((JButton) innerComp).getText();
                    }
                }
            }
        }

        try (FileWriter writer = new FileWriter("roomInfo.txt")) {
            for (String roomInfo : roomInfoArray) {
                if (roomInfo != null) {
                    writer.write(roomInfo + "\n");
                }
            }
            JOptionPane.showMessageDialog(frame, "객실 정보가 저장되었습니다.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "저장 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
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
        return date == null ? null : date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(hotelRoomInfo::new);
    }

    private class ActionListenerImpl implements ActionListener {

        private final int selectedFloor;
        private final int room;
        private final LocalDate checkInDate;
        private final LocalDate checkOutDate;

        public ActionListenerImpl(int selectedFloor, int room, LocalDate checkInDate, LocalDate checkOutDate) {
            this.selectedFloor = selectedFloor;
            this.room = room;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkInDateChooser.getDate() == null || checkOutDateChooser.getDate() == null) {
                JOptionPane.showMessageDialog(frame, "체크인 및 체크아웃 날짜를 선택해 주세요.",
                        "날짜 미선택 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }
            attemptReservation(selectedFloor, room, checkInDate, checkOutDate);
        }
    }
}
