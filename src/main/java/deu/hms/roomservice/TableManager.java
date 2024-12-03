/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomservice;


import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

public class TableManager {
    private MenuManager menuManager;
    
     public TableManager() {
        this.menuManager = new MenuManager();  // MenuManager 초기화
    }
     
    // 테이블 복사 메소드를 public으로 변경
    public void copyTableData(DefaultTableModel sourceModel, DefaultTableModel targetModel) {
        // sourceModel의 데이터를 targetModel로 복사
        for (int i = 0; i < sourceModel.getRowCount(); i++) {
            Object[] rowData = new Object[sourceModel.getColumnCount()];
            for (int j = 0; j < sourceModel.getColumnCount(); j++) {
                rowData[j] = sourceModel.getValueAt(i, j);
            }
            targetModel.addRow(rowData);
        }
        // sourceModel의 기존 데이터 삭제
        sourceModel.setRowCount(0);
    }
    
    // 테이블 초기화
    public void reset(DefaultTableModel model, JLabel total) {
        model.setRowCount(0); // 테이블의 모든 행 삭제
        
        menuManager.updateTotal(model,total);
     
    }
}

