/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomservice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author wjddn
 */
public class FileHandler {
    //객실 목록 파일 불러오기
    public void loadRoomNumbersFromFile(DefaultComboBoxModel<String> model, String filePath) {
    try {
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        
        model.removeAllElements(); // 기존 항목 제거
        
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 7 && !data[6].trim().isEmpty()) { // 7번째 열 확인
                model.addElement(data[6].trim()); // 7번째 열의 정보 추가
            }
        }
        
        br.close();
        fr.close();
        
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, 
            "호실 목록을 불러오는 중 오류가 발생했습니다: " + e.getMessage(),
            "불러오기 오류", 
            JOptionPane.ERROR_MESSAGE);
    }
}
    // 메뉴 목록 파일 불러오기
    public void loadMenuFromFile(DefaultTableModel model, String filePath) {
    try {
        java.io.FileReader fr = new java.io.FileReader(filePath);
        java.io.BufferedReader br = new java.io.BufferedReader(fr);
        
        model.setRowCount(0); // 기존 데이터 초기화
        
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length > 2) {
                // 2열의 정보부터 불러오기
                model.addRow(new Object[]{data[1], data[2]});
            }
        }
        
        br.close();
        fr.close();
            
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, 
            "파일을 불러오는 중 오류가 발생했습니다: " + e.getMessage(),
            "불러오기 오류", 
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
     
     //예약 목록 파일 불러오기
    public void loadReservationFromFile(DefaultTableModel model) {
    try {
        java.io.FileReader fr = new java.io.FileReader("예약목록.txt");
        java.io.BufferedReader br = new java.io.BufferedReader(fr);
        
       
        model.setRowCount(0); // 기존 데이터 초기화
        
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            model.addRow(data);
        }
        
        br.close();
        fr.close();
        
      
            
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "파일을 불러오는 중 오류가 발생했습니다: " + e.getMessage(),
            "불러오기 오류", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
     //예약 목록 파일 저장
    public void saveReservationToFile(DefaultTableModel model,String filePath) {
    try {
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + "/Desktop";
        
        java.io.FileWriter fw = new java.io.FileWriter(filePath, true);
        java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
        
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                bw.write(model.getValueAt(i, j).toString());
                if (j < model.getColumnCount() - 1) {
                    bw.write(",");
                }
            }
            bw.newLine();
        }
        
        bw.close();
        fw.close();
        
        
            
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "파일 저장 중 오류가 발생했습니다: " + e.getMessage(),
            "저장 오류", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}