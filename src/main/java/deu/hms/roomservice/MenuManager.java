/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomservice;


import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MenuManager {
    // 메뉴 추가
    public void addMenuToOrder(JTable menuTable, JTable orderTable, JLabel total) {
        DefaultTableModel menuModel = (DefaultTableModel) menuTable.getModel();
        DefaultTableModel orderModel = (DefaultTableModel) orderTable.getModel();
        
        int selectedRow = menuTable.getSelectedRow();
        
        if (selectedRow != -1) {
            String menuName = menuModel.getValueAt(selectedRow, 0).toString();
            String priceStr = menuModel.getValueAt(selectedRow, 1).toString();
            int price = Integer.parseInt(priceStr);
                  
            boolean found = false;
            
            // 이미 존재하는 메뉴인지 확인
            for (int i = 0; i < orderModel.getRowCount(); i++) {
                if (orderModel.getValueAt(i, 0).equals(menuName)) {
                    // 기존 수량과 가격 가져오기
                    int quantity = Integer.parseInt(orderModel.getValueAt(i, 1).toString());
                    
                    // 수량 증가
                    quantity++;
                    
                    // 새로운 가격 계산
                    int newPrice = price * quantity;
                    
                    // 테이블 업데이트
                    orderModel.setValueAt(quantity, i, 1);
                    orderModel.setValueAt(newPrice, i, 2);
                    
                    found = true;
                    break;
                }
            }
            
            // 새로운 메뉴라면 추가
            if (!found) {
                orderModel.addRow(new Object[]{menuName, 1, price});
            }
            updateTotal(orderModel, total); // 수정된 부분
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "메뉴를 선택해주세요.");
        }   
    }
    
    // 총 금액 계산
    public void updateTotal(DefaultTableModel model, JLabel total) { 
        int sum = 0;
    
    for (int i = 0; i < model.getRowCount(); i++) {
        sum += (int) model.getValueAt(i, 2);
    }
    
        total.setText(String.valueOf(sum) + "원");  // setText() 사용
    }
}
  
 
