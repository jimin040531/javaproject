/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package deu.hms.roomManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jimin
 */
public class roomManagement extends javax.swing.JFrame {

    /**
     * Creates new form roomManagement
     */
    public roomManagement() {
        initComponents();
        setLocationRelativeTo(null);
        loadTableData();
    }
    
    private void loadTableData() {
        DefaultTableModel model = (DefaultTableModel) roomTable.getModel();
        model.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader("roomInfo.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(",");
                if (rowData.length == 5) { // 층, 호수, 가격, 등급, 인원 순서로 맞는지 확인
                    model.addRow(rowData);
                } else {
                    System.out.println("잘못된 데이터 형식: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("파일을 읽는 중 문제가 발생했습니다.");
        }
    }

    
    private void addRoom() {
        String[] floorOptions = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] gradeOptions = {"Standard", "Deluxe", "Suite"};

        JComboBox<String> floorComboBox = new JComboBox<>(floorOptions);
        JTextField roomField = new JTextField();
        JComboBox<String> gradeComboBox = new JComboBox<>(gradeOptions);
        JTextField capacityField = new JTextField();

        // 층 번호 선택
        int floorResult = JOptionPane.showConfirmDialog(this, floorComboBox, "층 번호를 선택하세요", JOptionPane.OK_CANCEL_OPTION);
        if (floorResult != JOptionPane.OK_OPTION) {
            return;
        }
        String floor = (String) floorComboBox.getSelectedItem();

        // 방 번호 입력
        int roomResult = JOptionPane.showConfirmDialog(this, roomField, "방 번호를 입력하세요", JOptionPane.OK_CANCEL_OPTION);
        if (roomResult != JOptionPane.OK_OPTION) {
            return;
        }
        String roomNumber = roomField.getText().trim();

        // 방 가격 입력
        String price = JOptionPane.showInputDialog(this, "방 가격을 입력하세요:");

        // 방 등급 선택
        int gradeResult = JOptionPane.showConfirmDialog(this, gradeComboBox, "방 등급을 선택하세요", JOptionPane.OK_CANCEL_OPTION);
        if (gradeResult != JOptionPane.OK_OPTION) {
            return;
        }
        String grade = (String) gradeComboBox.getSelectedItem();

        // 인원 수 입력
        int capacityResult = JOptionPane.showConfirmDialog(this, capacityField, "수용 인원을 입력하세요", JOptionPane.OK_CANCEL_OPTION);
        if (capacityResult != JOptionPane.OK_OPTION) {
            return;
        }
        String capacity = capacityField.getText().trim();

        if (price != null && !roomNumber.isEmpty() && !capacity.isEmpty()) {
            DefaultTableModel model = (DefaultTableModel) roomTable.getModel();
            boolean isDuplicate = false;
            for (int i = 0; i < model.getRowCount(); i++) {
                String existingFloor = model.getValueAt(i, 0).toString().trim();
                String existingRoomNumber = model.getValueAt(i, 1).toString().trim();
                if (existingFloor.equals(floor.trim()) && existingRoomNumber.equals(roomNumber)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                JOptionPane.showMessageDialog(this, "이미 존재하는 객실입니다.", "등록 오류", JOptionPane.WARNING_MESSAGE);
            } else {
                model.addRow(new Object[]{floor.trim(), roomNumber, price.trim(), grade.trim(), capacity.trim()});
            }
        }
    }




    private void deleteRoom() {
        int selectedRow = roomTable.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) roomTable.getModel();
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "삭제할 방을 선택해 주세요.", "삭제 오류", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void saveRoomInfoToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("roomInfo.txt"))) {
            DefaultTableModel model = (DefaultTableModel) roomTable.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String floor = model.getValueAt(i, 0).toString();
                String roomNumber = model.getValueAt(i, 1).toString();
                String price = model.getValueAt(i, 2).toString();
                String grade = model.getValueAt(i, 3).toString();
                String capacity = model.getValueAt(i, 4).toString(); // 인원 추가
                bw.write(floor + "," + roomNumber + "," + price + "," + grade + "," + capacity); // 인원 정보도 포함하여 저장
                bw.newLine();
            }
            JOptionPane.showMessageDialog(this, "객실 정보가 저장되었습니다.", "저장 성공", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "객실 정보를 저장하는 중 문제가 발생했습니다.", "저장 오류", JOptionPane.ERROR_MESSAGE);
        }
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roomManagement = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        roomTable = new javax.swing.JTable();
        saveButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        roomManagement.setFont(new java.awt.Font("맑은 고딕", 1, 24)); // NOI18N
        roomManagement.setText("객실 관리");

        roomTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "층", "호수", "가격", "등급", "인원"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        roomTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomTableMouseClicked(evt);
            }
        });
        jScrollPane.setViewportView(roomTable);

        saveButton.setText("저장");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("삭제");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        addButton.setText("등록");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        backButton.setText("<");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(backButton)
                        .addGap(141, 141, 141)
                        .addComponent(roomManagement))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton))
                            .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(roomManagement)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(deleteButton)
                    .addComponent(addButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton)
                .addGap(304, 304, 304))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void roomTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomTableMouseClicked

    }//GEN-LAST:event_roomTableMouseClicked

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        saveRoomInfoToFile();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        deleteRoom();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        addRoom();
    }//GEN-LAST:event_addButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(roomManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(roomManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(roomManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(roomManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new roomManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton backButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JLabel roomManagement;
    private javax.swing.JTable roomTable;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
