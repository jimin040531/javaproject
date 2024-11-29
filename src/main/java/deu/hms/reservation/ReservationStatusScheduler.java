/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.reservation;

/**
 *
 * @author adsd3
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReservationStatusScheduler {

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // 상태 업데이트를 예약하는 메서드
    public void scheduleStatusUpdate(String checkInDate, int rowIndex, DefaultTableModel model) {
        try {
            // 체크인 날짜 파싱
            LocalDate checkInDay = LocalDate.parse(checkInDate);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime targetTime = checkInDay.atTime(18, 0); // 오후 6시 기준

            // 딜레이 계산
            long delay = Duration.between(now, targetTime).toMillis();

            if (delay < 0) {
                System.out.println("체크인 날짜가 이미 지났습니다.");
                return;
            }

            // 타이머 스케줄 설정
            scheduler.schedule(() -> {
                SwingUtilities.invokeLater(() -> {
                    // 모델을 기반으로 상태 업데이트
                    String cardStatus = (String) model.getValueAt(rowIndex, 11);
                    if ("카드등록".equals(cardStatus)) {
                        model.setValueAt("예약확정", rowIndex, 11);
                    } else {
                        model.setValueAt("예약취소", rowIndex, 11);
                    }
                });
            }, delay, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            System.err.println("체크인 날짜 형식이 잘못되었습니다: " + e.getMessage());
        }
    }

    // 서비스 종료 메서드
    public void shutdown() {
        scheduler.shutdown();
    }
}