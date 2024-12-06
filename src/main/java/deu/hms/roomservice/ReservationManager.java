package deu.hms.roomservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class ReservationManager {
    // 멤버 변수 선언
    private FileHandler fileHandler;
    private DefaultTableModel reservationModel;
    private int selectedRow;
    private String filePath;
    private Calendar calendar;
    
    // 생성자
    public ReservationManager() {
        this.fileHandler = new FileHandler();
        this.filePath = "예약목록.txt";
        this.calendar = Calendar.getInstance();
    }
    
    // Getter/Setter 메소드
    public DefaultTableModel getReservationModel() {
        return reservationModel;
    }
    
    public void setReservationModel(DefaultTableModel model) {
        this.reservationModel = model;
    }
    
    public int getSelectedRow() {
        return selectedRow;
    }
    
    public void setSelectedRow(int row) {
        this.selectedRow = row;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String path) {
        this.filePath = path;
    }
    
    // 예약 생성 메소드
  public void makeReservation(ReservationData reservationData) {
    if (!validateReservationTime(reservationData)) {
        return;
    }
    
    try {
        processReservation(reservationData);
        showSuccessMessage("예약이 성공적으로 생성되었습니다.");
    } catch (Exception e) {
        showErrorMessage("예약 생성 중 오류가 발생했습니다: " + e.getMessage());
    }
}
    
    // 예약 삭제 메소드
    public void deleteReservation(DefaultTableModel model, JTable table) {
        selectedRow = table.getSelectedRow();
        
        if (isValidRowSelection(selectedRow)) {
            try {
                processDeleteReservation(model);
                updateFileAfterDeletion(model);
                showSuccessMessage("예약이 성공적으로 삭제되었습니다.");
            } catch (Exception ex) {
                showErrorMessage("파일 업데이트 중 오류가 발생했습니다.");
            }
        } else {
            showErrorMessage("삭제할 행을 선택해주세요.");
        }
    }
    
    // 내부 검증 메소드들
    private boolean validateReservationTime(ReservationData data) {
        Calendar reservationTime = createReservationCalendar(data);
        if (reservationTime.before(calendar)) {
            showErrorMessage("예약 시간을 확인해주세요.");
            return false;
        }
        return true;
    }
    
    private Calendar createReservationCalendar(ReservationData data) {
        Calendar reservationTime = Calendar.getInstance();
        reservationTime.set(Calendar.YEAR, Integer.parseInt(data.getYear()));
        reservationTime.set(Calendar.MONTH, Integer.parseInt(data.getMonth()) - 1);
        reservationTime.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data.getDay()));
        reservationTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(data.getHour()));
        reservationTime.set(Calendar.MINUTE, Integer.parseInt(data.getMinute()));
        return reservationTime;
    }
    
    private boolean isValidRowSelection(int row) {
        return row >= 0;
    }
    
    private void processDeleteReservation(DefaultTableModel model) {
        int deletedOrderNumber = getOrderNumber(model);
        model.removeRow(selectedRow);
        updateOrderNumbers(model, deletedOrderNumber);
    }
    
    private int getOrderNumber(DefaultTableModel model) {
        return Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
    }
    
    private void updateOrderNumbers(DefaultTableModel model, int deletedOrderNumber) {
        for (int i = 0; i < model.getRowCount(); i++) {
            int currentOrderNumber = Integer.parseInt(model.getValueAt(i, 0).toString());
            if (currentOrderNumber > deletedOrderNumber) {
                model.setValueAt(currentOrderNumber - 1, i, 0);
            }
        }
    }
    
    private void processReservation(ReservationData data) {
    DefaultTableModel orderModel = data.getOrderModel();
    DefaultTableModel reservationModel = data.getReservationModel();
    
    try {
        // 기존 예약 데이터를 임시로 저장
        DefaultTableModel tempModel = new DefaultTableModel(
            new Object[]{"순번", "타입", "날짜", "시간", "호실", "메뉴", "수량", "가격"}, 0
        );
        
        // 기존 데이터 복사
        for (int i = 0; i < reservationModel.getRowCount(); i++) {
            Object[] row = new Object[8];
            for (int j = 0; j < 8; j++) {
                row[j] = reservationModel.getValueAt(i, j);
            }
            tempModel.addRow(row);
        }
        
        // 마지막 순번 가져오기
        int lastOrderNumber = 0;
        if (tempModel.getRowCount() > 0) {
            lastOrderNumber = Integer.parseInt(tempModel.getValueAt(tempModel.getRowCount()-1, 0).toString());
        }
        
        // 새로운 예약 추가
        for (int i = 0; i < orderModel.getRowCount(); i++) {
            Object[] rowData = {
                String.valueOf(lastOrderNumber + i + 1),  // 순차적으로 증가하는 순번
                "룸서비스",
                formatDate(data),
                formatTime(data),
                data.getRoom(),
                orderModel.getValueAt(i, 0).toString(),  // 메뉴
                orderModel.getValueAt(i, 1).toString(),  // 수량
                orderModel.getValueAt(i, 2).toString()   // 가격
            };
            tempModel.addRow(rowData);
        }
        
        // 파일에 저장
        fileHandler.saveReservationToFile(tempModel, "ServiceList.txt");
        
        // 예약 모델 업데이트
        reservationModel.setRowCount(0);
        for (int i = 0; i < tempModel.getRowCount(); i++) {
            Object[] row = new Object[8];
            for (int j = 0; j < 8; j++) {
                row[j] = tempModel.getValueAt(i, j);
            }
            reservationModel.addRow(row);
        }
        
    } catch (Exception e) {
        showErrorMessage("예약 처리 중 오류가 발생했습니다: " + e.getMessage());
    }
}
    
private void addReservationRow(ReservationData data, int orderNumber) {
    // 기존 데이터를 지우고 새로운 데이터만 추가
    data.getReservationModel().setRowCount(0);
    DefaultTableModel orderModel = data.getOrderModel();
    
    // addReservationRow 메소드를 직접 호출하지 않고 여기서 처리
    for(int i = 0; i < orderModel.getRowCount(); i++) {
        String[] rowData = {
            String.valueOf(orderNumber + i),
            "룸서비스",
            formatDate(data),
            formatTime(data),
            data.getRoom(),
            orderModel.getValueAt(i, 0).toString(),  // i번째 행의 메뉴 이름
            orderModel.getValueAt(i, 1).toString(),  // i번째 행의 수량
            orderModel.getValueAt(i, 2).toString()   // i번째 행의 가격
        };
        
        data.getReservationModel().addRow(rowData);
    }
}

    
    private void updateFileAfterDeletion(DefaultTableModel model) throws Exception {
        FileWriter fw = new FileWriter(filePath, false);
        fw.close();
        fileHandler.saveReservationToFile(model, filePath);
    }
    
    private void saveReservationToFile(DefaultTableModel model) {
        fileHandler.saveReservationToFile(model, filePath);
    }
    
    // 유틸리티 메소드들
    private String formatDate(ReservationData data) {
        return String.format("%s-%s-%s", data.getYear(), data.getMonth(), data.getDay());
    }
    
    private String formatTime(ReservationData data) {
        return String.format("%s:%s", data.getHour(), data.getMinute());
    }
    
    private int getNextOrderNumber() {
         int lastNumber = 0;
    try {
        File file = new File("ServiceList.txt");
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String lastLine = null;
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
            if (lastLine != null) {
                String[] data = lastLine.split(",");
                lastNumber = Integer.parseInt(data[0]);
            }
            reader.close();
        }
    } catch (Exception e) {
        showErrorMessage("주문번호 생성 중 오류가 발생했습니다: " + e.getMessage());
    }
    return lastNumber;
};
    
    
    private int getLastOrderNumber() {
        int lastNumber = 0;
        try {
            java.io.File file = new java.io.File(filePath);
            if (file.exists()) {
                java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.FileReader(file));
                String lastLine = null;
                String line;
                while ((line = reader.readLine()) != null) {
                    lastLine = line;
                }
                if (lastLine != null) {
                    String[] data = lastLine.split(",");
                    lastNumber = Integer.parseInt(data[0]);
                }
                reader.close();
            }
        } catch (Exception e) {
            showErrorMessage("주문번호 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
        return lastNumber;
    }
    
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, 
            message, 
            "완료", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, 
            message, 
            "오류", 
            JOptionPane.WARNING_MESSAGE);
    }
}

