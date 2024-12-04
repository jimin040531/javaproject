/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package deu.hms.management;

import deu.hms.roomManagement.RoomService;  // RoomService 임포트
import deu.hms.roomManagement.RoomManagementFrame;  // RoomManagementFrame 임포트
/**
 *
 * @author Jimin
 */
public class managementFrame extends javax.swing.JDialog {

    private Object deu;

    /**
     * Creates new form managementFrame
     * @param parent
     * @param modal
     */
    public managementFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userInfoButton = new javax.swing.JButton();
        roomInfoButton = new javax.swing.JButton();
        restButton = new javax.swing.JButton();
        hotelInfo = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        userInfoButton.setText("계정 관리");
        userInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userInfoButtonActionPerformed(evt);
            }
        });

        roomInfoButton.setText("객실 관리");
        roomInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomInfoButtonActionPerformed(evt);
            }
        });

        restButton.setText("식당 관리");
        restButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restButtonActionPerformed(evt);
            }
        });

        hotelInfo.setFont(new java.awt.Font("맑은 고딕", 1, 24)); // NOI18N
        hotelInfo.setText("호텔 정보");

        backButton.setText("<");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(roomInfoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hotelInfo)
                            .addComponent(restButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(userInfoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backButton)))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(hotelInfo)
                .addGap(30, 30, 30)
                .addComponent(roomInfoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(restButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(userInfoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void userInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userInfoButtonActionPerformed
        deu.hms.userManagement.userManagementFrame usermanagementframe = new deu.hms.userManagement.userManagementFrame();
        usermanagementframe.setLocationRelativeTo(null);
        usermanagementframe.setVisible(true);
        this.dispose();  // 현재 창 닫기
    }//GEN-LAST:event_userInfoButtonActionPerformed

    private void roomInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomInfoButtonActionPerformed
        // RoomService 객체를 생성
        RoomService roomService = new RoomService();  // RoomService 객체 생성

        // RoomManagementFrame을 생성하고 화면 중앙에 띄운다
        RoomManagementFrame roomManagementFrame = new RoomManagementFrame(roomService);

        // RoomManagementFrame을 managementFrame 위에 띄운다
        roomManagementFrame.setLocationRelativeTo(this);  // this는 managementFrame을 의미

        roomManagementFrame.setVisible(true); // RoomManagementFrame을 화면에 표시
    }//GEN-LAST:event_roomInfoButtonActionPerformed

    private void restButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restButtonActionPerformed
        /*deu.hms.restaurantManagement.restaurantFrame restaurnatmanagementFrame = new deu.hms.restaurantManagement.restaurantFrame();
        restaurnatmanagementFrame.setLocationRelativeTo(null);
        restaurnatmanagementFrame.setVIsible(true);
        this.dispose();  // 현재 창 닫기*/
    }//GEN-LAST:event_restButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_backButtonActionPerformed

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
            java.util.logging.Logger.getLogger(managementFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(managementFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(managementFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(managementFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                managementFrame dialog = new managementFrame(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JLabel hotelInfo;
    private javax.swing.JButton restButton;
    private javax.swing.JButton roomInfoButton;
    private javax.swing.JButton userInfoButton;
    // End of variables declaration//GEN-END:variables
}
