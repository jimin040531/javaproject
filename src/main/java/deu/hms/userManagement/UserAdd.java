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

    private userManagementFrame parent;
    
    // 기본 생성자 추가
    public UserAdd() {
        this(null); // 기본 생성자는 null을 전달
    }
    
    /**
     * Creates new form UserAdd
     */
    public UserAdd(userManagementFrame parent) {
        
        setTitle("사용자 등록");
        
        this.parent = parent;
        initComponents();
        setLocationRelativeTo(null); // 창을 화면 중앙에 배치
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // 창 닫기 동작 설정
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
        pickEmployeeAdd = new javax.swing.JCheckBox();
        pickManagerAdd = new javax.swing.JCheckBox();
        PWLabelAdd = new java.awt.Label();
        inUserPWAdd = new javax.swing.JTextField();
        inUserIDAdd = new javax.swing.JTextField();
        inUserNameAdd = new javax.swing.JTextField();
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

        pickEmployeeAdd.setText("일반 직원");
        pickEmployeeAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pickEmployeeAddActionPerformed(evt);
            }
        });

        pickManagerAdd.setText("관리자");
        pickManagerAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pickManagerAddActionPerformed(evt);
            }
        });

        PWLabelAdd.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        PWLabelAdd.setText("비밀번호");

        inUserPWAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inUserPWAddActionPerformed(evt);
            }
        });

        inUserIDAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inUserIDAddActionPerformed(evt);
            }
        });

        inUserNameAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inUserNameAddActionPerformed(evt);
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
                                        .addComponent(pickManagerAdd)
                                        .addGap(18, 18, 18)
                                        .addComponent(pickEmployeeAdd))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(PWLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(IDLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nameLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(inUserPWAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(inUserIDAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(inUserNameAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(inUserNameAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inUserIDAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IDLabelAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inUserPWAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PWLabelAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pickManagerAdd)
                    .addComponent(pickEmployeeAdd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButtonAdd)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonAddActionPerformed
        // 입력된 사용자 정보 가져오기
            String userName = inUserNameAdd.getText();
            String userId = inUserIDAdd.getText();
            String userPassword = inUserPWAdd.getText();
            String userRole = pickEmployeeAdd.isSelected() ? "employee" : "manager";

            // 유효성 검사
            if (userName.isEmpty() || userId.isEmpty() || userPassword.isEmpty() || (!pickEmployeeAdd.isSelected() && !pickManagerAdd.isSelected())) {
                JOptionPane.showMessageDialog(this, "모든 필드를 올바르게 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // User 객체 생성
            User newUser = new User(userId, userPassword, userName, userRole);

            // UserAddManager를 통해 사용자 추가
            try {
                UserAddManager.addUser(newUser, parent);
                javax.swing.JOptionPane.showMessageDialog(this, "사용자가 성공적으로 추가되었습니다.");
                System.out.println("사용자 추가 성공: " + newUser);

                this.dispose(); // 창 닫기
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "사용자 추가 중 오류가 발생했습니다: " + e.getMessage(), "오류", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_saveButtonAddActionPerformed

    private void pickEmployeeAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pickEmployeeAddActionPerformed
        if (pickEmployeeAdd.isSelected()) {
            pickManagerAdd.setEnabled(false);  // "관리자" 체크박스를 비활성화
        } else {
            pickManagerAdd.setEnabled(true);   // "관리자" 체크박스를 활성화
        }
    }//GEN-LAST:event_pickEmployeeAddActionPerformed

    private void pickManagerAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pickManagerAddActionPerformed
        if (pickManagerAdd.isSelected()) {
            pickEmployeeAdd.setEnabled(false);  // "직원" 체크박스를 비활성화
        } else {
            pickEmployeeAdd.setEnabled(true);   // "직원" 체크박스를 활성화
        }
    }//GEN-LAST:event_pickManagerAddActionPerformed

    private void inUserPWAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inUserPWAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inUserPWAddActionPerformed

    private void inUserIDAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inUserIDAddActionPerformed
        inUserPWAdd.requestFocus();
    }//GEN-LAST:event_inUserIDAddActionPerformed

    private void inUserNameAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inUserNameAddActionPerformed
        inUserIDAdd.requestFocus();
    }//GEN-LAST:event_inUserNameAddActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Label IDLabelAdd;
    private java.awt.Label PWLabelAdd;
    private java.awt.Label UserManagementTitle;
    private javax.swing.JTextField inUserIDAdd;
    private javax.swing.JTextField inUserNameAdd;
    private javax.swing.JTextField inUserPWAdd;
    private java.awt.Label nameLabelAdd;
    private javax.swing.JCheckBox pickEmployeeAdd;
    private javax.swing.JCheckBox pickManagerAdd;
    private javax.swing.JButton saveButtonAdd;
    // End of variables declaration//GEN-END:variables
}
