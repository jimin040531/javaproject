/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.reservation;

/**
 *
 * @author adsd3
 */
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationUtils {

    // addOrUpdateRow 메서드 분리
    public static int addOrUpdateRow(DefaultTableModel model, ReservationData data) {
        // ReservationData 객체의 각 필드로부터 데이터를 가져옴
        Object[] rowData = {
                data.getUniqueNumber(), data.getName(), data.getAddress(),
                data.getPhoneNumber(), data.getCheckInDate(), data.getCheckOutDate(),
                data.getRoomNumber(), data.getGuestCount(), data.getStayCost(),
                data.getPaymentMethod(), data.getRoomSelection(), data.getCardStatus()
        };

        // 기존의 빈 행 찾기 또는 새로운 행 추가
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0) == null || model.getValueAt(i, 0).toString().trim().isEmpty()) {
                // 빈 행이 있으면 해당 위치에 데이터를 삽입
                for (int j = 0; j < rowData.length; j++) {
                    model.setValueAt(rowData[j], i, j);
                }
                return i; // 수정된 행의 인덱스 반환
            }
        }

        // 빈 행이 없다면 새로운 행 추가
        model.addRow(rowData);
        return model.getRowCount() - 1; // 새로 추가된 행의 인덱스 반환
    }

    // ReservationData 객체 생성 메서드 분리
    public static ReservationData createReservationData(String uniqueNumber, TextField textName, TextField textAddress,
                                                        TextField textPhoneNumber, TextField textCheckInDate, 
                                                        TextField textCheckOutDate, TextField textRoomNumber, 
                                                        TextField textGuestCount, JTextPane moneyField, 
                                                        JRadioButton onSitePaymentButton, JRadioButton thisWeek, 
                                                        JLabel labelCardStatus) {
        return new ReservationData(
                uniqueNumber,
                textName.getText(),
                textAddress.getText(),
                textPhoneNumber.getText(),
                textCheckInDate.getText(),
                textCheckOutDate.getText(),
                textRoomNumber.getText(),
                textGuestCount.getText(),
                onSitePaymentButton.isSelected() ? "현장결제" : "카드결제",
                thisWeek.isSelected() ? "평일" : "주말",
                moneyField.getText(),
                labelCardStatus.isVisible() ? "카드등록" : "카드미등록"
        );
    }

    // 상태 업데이트 예약 메서드 분리
    public static void scheduleStatusUpdateForTest(String checkInDate, int rowIndex, DefaultTableModel model) {
        try {
            LocalDate checkInDay = LocalDate.parse(checkInDate);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime targetTime = LocalDateTime.now().plusSeconds(20);

            long delay = Duration.between(now, targetTime).toMillis();

            if (delay < 0) {
                System.out.println("체크인 날짜가 이미 지났습니다.");
                return;
            }

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.schedule(() -> {
                SwingUtilities.invokeLater(() -> {
                    // 정확한 행 인덱스를 사용하여 상태 업데이트
                    String cardStatus = (String) model.getValueAt(rowIndex, 11);

                    if ("카드등록".equals(cardStatus)) {
                        model.setValueAt("예약확정", rowIndex, 11);
                    } else {
                        model.setValueAt("예약취소", rowIndex, 11);
                    }
                });
                scheduler.shutdown();
            }, delay, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            System.err.println("체크인 날짜 형식이 잘못되었습니다: " + e.getMessage());
        }
    }
}
