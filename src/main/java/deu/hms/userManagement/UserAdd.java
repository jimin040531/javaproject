/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package deu.hms.userManagement;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author yunhe
 */
public class UserAdd extends javax.swing.JFrame {

    private UserManagementFrame parent;
    
    // 기본 생성자 추가
    public UserAdd() {
        this(null); // 기본 생성자는 null을 전달
    }
    
    /**
     * Creates new form UserAdd
     */
    public UserAdd(UserManagementFrame parent) {
        this.parent = parent;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        saveButtonAdd = new javax.swing.JButton();
        PickEmployeeAdd = new javax.swing.JCheckBox();
        PickManagerAdd = new javax.swing.JCheckBox();
        PWLabelAdd = new java.awt.Label();
        InUserPWAdd = new javax.swing.JTextField();
        InUserIDAdd = new javax.swing.JTextField();
        InUserNameAdd = new javax.swing.JTextField();
        nameLabelAdd = new java.awt.Label();
        IDLabelAdd = new java.awt.Label();
        UserManagementTitle = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        saveButtonAdd.setText("저장");
        saveButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonAddActionPerformed(evt);
            }
        });

        PickEmployeeAdd.setText("일반 직원");
        PickEmployeeAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PickEmployeeAddActionPerformed(evt);
            }
        });

        PickManagerAdd.setText("관리자");
        PickManagerAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PickManagerAddActionPerformed(evt);
            }
        });

        PWLabelAdd.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        PWLabelAdd.setText("비밀번호");

        InUserPWAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InUserPWAddActionPerformed(evt);
            }
        });

        InUserIDAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InUserIDAddActionPerformed(evt);
            }
        });

        InUserNameAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InUserNameAddActionPerformed(evt);
            }
        });

        nameLabelAdd.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        nameLabelAdd.setText("이름");

        IDLabelAdd.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        IDLabelAdd.setText("아이디");

        UserManagementTitle.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        UserManagementTitle.setText("사용자 등록");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(PickManagerAdd)
                                        .addGap(18, 18, 18)
                                        .addComponent(PickEmployeeAdd))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(PWLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(IDLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nameLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(InUserPWAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(InUserIDAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(InUserNameAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(UserManagementTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 23, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(UserManagementTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(InUserNameAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(InUserIDAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IDLabelAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(InUserPWAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PWLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PickManagerAdd)
                    .addComponent(PickEmployeeAdd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButtonAdd)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonAddActionPerformed
        String name = InUserNameAdd.getText();
        String id = InUserIDAdd.getText();
        String password = InUserPWAdd.getText();
        String role = PickEmployeeAdd.isSelected() ? "Employee" : "Manager";

        UserAddManager.addUser(new User(id, password, name, role));
    
    }//GEN-LAST:event_saveButtonAddActionPerformed

    private void PickEmployeeAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PickEmployeeAddActionPerformed
        if (PickEmployeeAdd.isSelected()) {
            PickManagerAdd.setEnabled(false);  // "관리자" 체크박스를 비활성화
        } else {
            PickManagerAdd.setEnabled(true);   // "관리자" 체크박스를 활성화
        }
    }//GEN-LAST:event_PickEmployeeAddActionPerformed

    private void PickManagerAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PickManagerAddActionPerformed
        if (PickManagerAdd.isSelected()) {
            PickEmployeeAdd.setEnabled(false);  // "직원" 체크박스를 비활성화
        } else {
            PickEmployeeAdd.setEnabled(true);   // "직원" 체크박스를 활성화
        }
    }//GEN-LAST:event_PickManagerAddActionPerformed

    private void InUserPWAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InUserPWAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InUserPWAddActionPerformed

    private void InUserIDAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InUserIDAddActionPerformed
        InUserPWAdd.requestFocus();
    }//GEN-LAST:event_InUserIDAddActionPerformed

    private void InUserNameAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InUserNameAddActionPerformed
        InUserIDAdd.requestFocus();
    }//GEN-LAST:event_InUserNameAddActionPerformed

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
            java.util.logging.Logger.getLogger(UserAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserAdd().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Label IDLabelAdd;
    private javax.swing.JTextField InUserIDAdd;
    private javax.swing.JTextField InUserNameAdd;
    private javax.swing.JTextField InUserPWAdd;
    private java.awt.Label PWLabelAdd;
    private javax.swing.JCheckBox PickEmployeeAdd;
    private javax.swing.JCheckBox PickManagerAdd;
    private java.awt.Label UserManagementTitle;
    private java.awt.Label nameLabelAdd;
    private javax.swing.JButton saveButtonAdd;
    // End of variables declaration//GEN-END:variables
}
