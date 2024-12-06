/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package deu.hms.checkin;

import javax.swing.JOptionPane;
import deu.hms.reservation.Registration;

/**
 *
 * @author Jimin
 */
public class cardRegist extends javax.swing.JFrame {
private Registration registration; // Registration 객체 참조

    
    
    /**
     * Creates new form cardRegist
     */
    
 
    public cardRegist() {
        initComponents();
        initializePlaceholders();
    }
    
    private void initializePlaceholders() {
        setTextFieldPlaceholder(cardNumTextField1, "****");
        setTextFieldPlaceholder(cardNumTextField2, "****");
        setTextFieldPlaceholder(cardNumTextField3, "****");
        setTextFieldPlaceholder(cardNumTextField4, "****");
        setTextFieldPlaceholder(monthTextField, "MM");
        setTextFieldPlaceholder(yearTextField, "YY");
        setTextFieldPlaceholder(pwTextField, "비밀번호 앞 2자리");
        setTextFieldPlaceholder(cvcTextField, "***");
    }
    
    private void setTextFieldPlaceholder(javax.swing.JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(java.awt.Color.GRAY);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText(""); // 기본 텍스트 제거
                    textField.setForeground(java.awt.Color.BLACK); // 글자색 검정
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().trim().isEmpty()) {
                    textField.setText(placeholder); // 기본 텍스트 복원
                    textField.setForeground(java.awt.Color.GRAY); // 글자색 회색
                }
            }
        });
    }
    public cardRegist(Registration registration) {
    this.registration = registration; // Registration 객체 저장
    initComponents(); // NetBeans GUI에서 자동 생성된 초기화 코드
    initializePlaceholders(); // 기존 초기화 코드
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        registButton = new javax.swing.JButton();
        cardNumTextField1 = new javax.swing.JTextField();
        Label1 = new javax.swing.JLabel();
        cardNumTextField2 = new javax.swing.JTextField();
        Label2 = new javax.swing.JLabel();
        cardNumTextField3 = new javax.swing.JTextField();
        Label3 = new javax.swing.JLabel();
        cardNumTextField4 = new javax.swing.JTextField();
        cancleButton = new javax.swing.JButton();
        expirationDateLabel = new javax.swing.JLabel();
        slashLabel = new javax.swing.JLabel();
        monthTextField = new javax.swing.JTextField();
        yearTextField = new javax.swing.JTextField();
        cvcLabel = new javax.swing.JLabel();
        cvcTextField = new javax.swing.JTextField();
        pwLabel = new javax.swing.JLabel();
        pwTextField = new javax.swing.JTextField();
        cardNumLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        registButton.setText("등록");
        registButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registButtonActionPerformed(evt);
            }
        });

        cardNumTextField1.setToolTipText("");
        cardNumTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardNumTextField1ActionPerformed(evt);
            }
        });

        Label1.setText("-");

        cardNumTextField2.setToolTipText("");

        Label2.setText("-");

        Label3.setText("-");

        cancleButton.setText("취소");
        cancleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancleButtonActionPerformed(evt);
            }
        });

        expirationDateLabel.setText("유효기간");

        slashLabel.setText("/");

        monthTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthTextFieldActionPerformed(evt);
            }
        });

        yearTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearTextFieldActionPerformed(evt);
            }
        });

        cvcLabel.setText("CVC");

        pwLabel.setText("비밀번호");

        cardNumLabel.setText("카드번호");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(cancleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(registButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(cardNumLabel)
                                .addComponent(expirationDateLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(monthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addComponent(cardNumTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(Label1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cardNumTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(slashLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(cvcLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cvcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(Label2)
                                    .addGap(7, 7, 7)
                                    .addComponent(cardNumTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(Label3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cardNumTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pwLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pwTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cardNumTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardNumTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardNumTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardNumTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label1)
                    .addComponent(Label2)
                    .addComponent(Label3)
                    .addComponent(cardNumLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(expirationDateLabel)
                    .addComponent(monthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slashLabel)
                    .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvcLabel)
                    .addComponent(cvcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pwLabel)
                    .addComponent(pwTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancleButton)
                    .addComponent(registButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registButtonActionPerformed
    // 입력 필드에서 값 가져오기
   
        String cardNum1 = cardNumTextField1.getText().trim();
        String cardNum2 = cardNumTextField2.getText().trim();
        String cardNum3 = cardNumTextField3.getText().trim();
        String cardNum4 = cardNumTextField4.getText().trim();
        String month = monthTextField.getText().trim();
        String year = yearTextField.getText().trim();
        String pw = pwTextField.getText().trim();
        String cvc = cvcTextField.getText().trim();

        try {
            // cardRegistLoad 객체 생성
            cardRegistLoad cardInfoObj = new cardRegistLoad(cardNum1, cardNum2, cardNum3, cardNum4, month, year, pw, cvc);
             System.out.println("카드 정보 저장 테스트 성공: " + cardNum1 + "-" + cardNum2 + "-" + cardNum3 + "-" + cardNum4);

            // 카드 정보 저장
            cardInfoObj.saveCardInformation();
             if (registration != null) {
            registration.showCardRegistrationStatus();
        }
            JOptionPane.showMessageDialog(this, "카드 정보가 성공적으로 저장되었습니다!", "성공", JOptionPane.INFORMATION_MESSAGE);
            //    labelCardStatus.setVisible(true);

            // 입력 필드 초기화 및 프레임 닫기
            cardNumTextField1.setText("");
            cardNumTextField2.setText("");
            cardNumTextField3.setText("");
            cardNumTextField4.setText("");
            monthTextField.setText("");
            yearTextField.setText("");
            pwTextField.setText("");
            cvcTextField.setText("");
            this.setVisible(false);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
       
    }//GEN-LAST:event_registButtonActionPerformed

    private void cardNumTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardNumTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cardNumTextField1ActionPerformed

    private void cancleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancleButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancleButtonActionPerformed

    private void monthTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthTextFieldActionPerformed

    private void yearTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearTextFieldActionPerformed

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
            java.util.logging.Logger.getLogger(cardRegist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cardRegist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cardRegist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cardRegist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new cardRegist().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Label1;
    private javax.swing.JLabel Label2;
    private javax.swing.JLabel Label3;
    private javax.swing.JButton cancleButton;
    private javax.swing.JLabel cardNumLabel;
    private javax.swing.JTextField cardNumTextField1;
    private javax.swing.JTextField cardNumTextField2;
    private javax.swing.JTextField cardNumTextField3;
    private javax.swing.JTextField cardNumTextField4;
    private javax.swing.JLabel cvcLabel;
    private javax.swing.JTextField cvcTextField;
    private javax.swing.JLabel expirationDateLabel;
    private javax.swing.JTextField monthTextField;
    private javax.swing.JLabel pwLabel;
    private javax.swing.JTextField pwTextField;
    private javax.swing.JButton registButton;
    private javax.swing.JLabel slashLabel;
    private javax.swing.JTextField yearTextField;
    // End of variables declaration//GEN-END:variables
}
