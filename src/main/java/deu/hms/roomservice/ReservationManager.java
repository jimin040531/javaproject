/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomservice;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReservationManager {
    private FileHandler fileHandler; // FileHandler 인스턴스 추가

    // 생성자에서 FileHandler 초기화
    public ReservationManager() {
        this.fileHandler = new FileHandler();
    }
    
    //예약 삭제 
    public void deleteReservation(DefaultTableModel model, JTable table) {
        int rw = table.getSelectedRow();
        if (rw >= 0) {
           // 삭제할 행의 순번 가져오기
            int deletedOrderNumber = Integer.parseInt(model.getValueAt(rw, 0).toString());
            
            // 선택된 행 삭제
            model.removeRow(rw);
            
            // 삭제된 순번보다 큰 순번들을 1씩 감소
            for (int i = 0; i < model.getRowCount(); i++) {
                int currentOrderNumber = Integer.parseInt(model.getValueAt(i, 0).toString());
                if (currentOrderNumber > deletedOrderNumber) {
                    model.setValueAt(currentOrderNumber - 1, i, 0);
                }
            }
            
            try {
                // 파일 초기화 후 현재 테이블 데이터로 다시 저장
                java.io.FileWriter fw = new java.io.FileWriter("예약목록.txt", false);
                fw.close();
                
                // FileHandler의 메서드 호출
                fileHandler.saveReservationToFile(model, "예약목록.txt");
                
                javax.swing.JOptionPane.showMessageDialog(null, "예약이 성공적으로 삭제되었습니다.", 
                    "삭제 완료", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(null, "파일 업데이트 중 오류가 발생했습니다.", 
                    "오류", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "삭제할 행을 선택해주세요.", "선택 오류", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }
}
