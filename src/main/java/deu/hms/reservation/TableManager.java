/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.reservation;
import javax.swing.table.DefaultTableModel;
import java.util.List;
/**
 *
 * @author adsd3
 */
public class TableManager {
    private DefaultTableModel tableModel;

    public TableManager(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    // 테이블에 데이터 추가
    public void addRow(ReservationData data) {
        tableModel.addRow(new Object[]{
            data.getUniqueNumber(),
            data.getName(),
            data.getAddress(),
            data.getPhoneNumber(),
            data.getCheckInDate(),
            data.getCheckOutDate(),
            data.getRoomNumber(),
            data.getGuestCount(),
            data.getStayCost(),
            data.getPaymentMethod(),
            data.getRoomSelection(),
            data.getCardStatus()
        });
    }

    // 파일 데이터를 테이블에 로드
    public void loadTableData(List<String[]> fileData) {
        tableModel.setRowCount(0); // 기존 테이블 데이터 삭제
        for (String[] row : fileData) {
            tableModel.addRow(row);
        }
    }

    // 특정 행 삭제
    public void deleteRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tableModel.getRowCount()) {
            tableModel.removeRow(rowIndex);
        }
    }
}