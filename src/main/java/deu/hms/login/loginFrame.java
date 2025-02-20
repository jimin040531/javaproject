/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package deu.hms.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Jimin
 */
public class loginFrame extends javax.swing.JDialog {

    /**
     * Creates new form loginFrame
     */
     private UserAuthentication auth;
     
    public loginFrame(UserAuthentication auth) {
        super(new JFrame(), true);
        this.auth = auth;
        initComponents();
        initializeFieldBehaviors();
        setTitle("로그인");
        
        this.setLocationRelativeTo(null);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        passwordField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        idField = new java.awt.TextField();
        mainTitleLabel = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        passwordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordFieldActionPerformed(evt);
            }
        });

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        idField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idFieldActionPerformed(evt);
            }
        });

        mainTitleLabel.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        mainTitleLabel.setText("호텔 관리자 시스템");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(passwordField)
                                    .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(loginButton))
                            .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(mainTitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 82, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(mainTitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(115, 115, 115))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // ID와 비밀번호 가져오기
        String userId = idField.getText();
        String password = new String(passwordField.getPassword());

        // 로그인 인증 시도
        boolean authenticated = auth.authenticate(userId, password);

        // 로그인 성공 여부에 따라 분기
        if (authenticated) {
            // 인증된 사용자 이름 가져오기
            String userName = auth.getUserName(userId);
            String userRole = auth.getUserRole(userId);  // 사용자의 역할 가져오기

            // 사용자 역할에 따라 메시지 구성
            String roleMessage;
            if ("manager".equalsIgnoreCase(userRole)) {
                roleMessage = "현재 권한은 관리자입니다.";
            } else if ("employee".equalsIgnoreCase(userRole)) {
                roleMessage = "현재 권한은 일반 직원입니다.";
            } else {
                roleMessage = "정의되지 않은 권한입니다.";
            }

            // 로그인 성공 메시지 표시
            JOptionPane.showMessageDialog(this, 
                "Welcome, " + userName + "\n" + roleMessage, 
                "Login Success", 
                JOptionPane.INFORMATION_MESSAGE);

            // 사용자의 역할에 따라 화면 열기
            if ("manager".equalsIgnoreCase(userRole)) {
                // 사용자가 'manager'일 경우 MainScreenManager 창 열기
                MainScreenManager managerScreen = new MainScreenManager(auth);
                managerScreen.setLocationRelativeTo(null);
                managerScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                managerScreen.setVisible(true);
            } else if ("employee".equalsIgnoreCase(userRole)) {
                // 사용자가 'employee'일 경우 MainScreenEmployee 창 열기
                MainScreenEmployees employeeScreen = new MainScreenEmployees(auth);
                employeeScreen.setLocationRelativeTo(null);
                employeeScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                employeeScreen.setVisible(true);
            } else {
                // 정의되지 않은 역할에 대한 처리 (예외 처리)
                JOptionPane.showMessageDialog(this, "Undefined User Role", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // 로그인 창 닫기
            System.out.println("Disposing loginFrame...");
            this.dispose();
        } else {
            // 로그인 실패 메시지 표시
            JOptionPane.showMessageDialog(this, "Login Failed", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_loginButtonActionPerformed

    private void idFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idFieldActionPerformed
        passwordField.requestFocus();
    }//GEN-LAST:event_idFieldActionPerformed
   
    private void passwordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordFieldActionPerformed
        loginButton.doClick();
    }//GEN-LAST:event_passwordFieldActionPerformed
   
    
    /**
     * 필드 동작 초기화 메서드
     */
    private void initializeFieldBehaviors() {
        addIDFieldBehavior();         // IDField의 기본 메시지와 입력 동작 설정
        addPasswordFieldBehavior();   // PasswordField의 클릭 시 초기화 및 입력 동작 설정
    }

    /**
     * IDField의 기본 메시지 설정 및 동작 관리
     */
    private void addIDFieldBehavior() {
        String placeholderText = "ID";
        Color placeholderColor = Color.GRAY;
        Color inputColor = Color.BLACK;

        idField.setText(placeholderText);
        idField.setForeground(placeholderColor);

        idField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (idField.getText().equals(placeholderText)) {
                    idField.setText("");
                    idField.setForeground(inputColor);
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (idField.getText().isEmpty()) {
                    idField.setText(placeholderText);
                    idField.setForeground(placeholderColor);
                }
            }
        });
    }

    /**
     * PasswordField의 기본 상태 설정 및 동작 관리
     */
    private void addPasswordFieldBehavior() {
        String placeholderText = "*******";
        Color placeholderColor = Color.GRAY;
        Color inputColor = Color.BLACK;

        passwordField.setText(placeholderText);
        passwordField.setForeground(placeholderColor);
        passwordField.setEchoChar((char) 0);  // 기본 메시지를 보여주기 위해 echo char 비활성화

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (new String(passwordField.getPassword()).equals(placeholderText)) {
                    passwordField.setText("");
                    passwordField.setForeground(inputColor);
                    passwordField.setEchoChar('*');  // 비밀번호 입력 시 echo char 활성화
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText(placeholderText);
                    passwordField.setForeground(placeholderColor);
                    passwordField.setEchoChar((char) 0);  // 기본 메시지를 보여주기 위해 echo char 비활성화
                }
            }
        });
    }
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
            java.util.logging.Logger.getLogger(loginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(loginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        UserAuthentication auth = new UserAuthentication(); 
        /* Create and display the dialog */  
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                // UserAuthentication 객체를 인자로 전달하여 loginFrame 생성
                loginFrame dialog = new loginFrame(auth);  // UserAuthentication 전달
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        dialog.dispose();
                    }
                });
                dialog.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        });
    }

    
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private java.awt.TextField idField;
    private javax.swing.JButton loginButton;
    private java.awt.Label mainTitleLabel;
    private javax.swing.JPasswordField passwordField;
    // End of variables declaration//GEN-END:variables
}
