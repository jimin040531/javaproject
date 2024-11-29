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
public class ReservationUtils {

    public static int addOrUpdateRow(DefaultTableModel model, int uniqueNumber, 
                                     String name, String address, String phoneNumber, 
                                     String checkInDate, String checkOutDate, 
                                     String roomNumber, String guestCount, 
                                     String stayCost, String paymentMethod, 
                                     String roomSelection, String cardStatus) {
        // 새로운 행 데이터를 생성
        Object[] rowData = {
            uniqueNumber, name, address, phoneNumber, checkInDate, checkOutDate,
            roomNumber, guestCount, stayCost, paymentMethod, roomSelection, cardStatus
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
}