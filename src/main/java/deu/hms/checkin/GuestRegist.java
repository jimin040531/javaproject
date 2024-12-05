/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package deu.hms.checkin;

import deu.hms.utility.CardRegistFrame;
import deu.hms.utility.HotelRoomReservationUI;
import java.awt.event.ActionEvent;

import javax.swing.*;
import java.util.UUID;
import javax.swing.table.DefaultTableModel;

public class GuestRegist extends JFrame {

    private final CheckInUI parentUI;  
    private final JTable reservationListTable;  

    public GuestRegist(CheckInUI parentUI, JTable reservationListTable) {
        this.parentUI = parentUI;
        this.reservationListTable = reservationListTable;
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private void saveGuestInformation() {
        String name = nameTextField.getText().trim();
        String phone1 = phoneTextField1.getText().trim();
        String phone2 = phoneTextField2.getText().trim();
        String phone3 = phoneTextField3.getText().trim();
        String phone = phone1 + "-" + phone2 + "-" + phone3;

        String checkInDate = checkInDateTextField.getText().trim(); 
        String checkOutDate = checkOutDateTextField.getText().trim(); 
        String room = roomNumberTextField.getText().trim();
        String cost = stayCostTextField.getText().trim();
        String guestCount = guestCountTextField.getText().trim();
        String paymentMethod = (String) paymentType.getSelectedItem();

        // 입력 필드가 비어 있을 경우 오류 메시지를 출력
        if (name.isEmpty() || phone.isEmpty() || room.isEmpty() || cost.isEmpty() || checkInDate.isEmpty() || checkOutDate.isEmpty() || guestCount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 필드를 올바르게 입력해 주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 중복 데이터 방지: 동일한 방 번호와 날짜로 이미 예약된 경우 추가하지 않음
        DefaultTableModel model = (DefaultTableModel) reservationListTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String existingRoom = (String) model.getValueAt(i, 5);
            String existingCheckInDate = (String) model.getValueAt(i, 3);
            if (existingRoom.equals(room) && existingCheckInDate.equals(checkInDate)) {
                JOptionPane.showMessageDialog(this, "해당 방에 이미 예약이 존재합니다.", "중복 예약", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // 새로운 손님 정보 생성 (고유번호는 UUID로 생성)
        String uniqueNumber = UUID.randomUUID().toString();
        CheckInData newGuest = new CheckInData(uniqueNumber, name, phone, checkInDate, checkOutDate, room, guestCount, cost, paymentMethod, "예약 완료", "");

        // CheckInData를 부모 UI의 테이블에 추가
        model.addRow(new Object[]{
            newGuest.getUniqueNumber(),
            newGuest.getName(),
            newGuest.getPhoneNumber(),
            newGuest.getCheckInDate(),
            newGuest.getCheckOutDate(),
            newGuest.getRoomNumber(),
            newGuest.getGuestCount(),
            newGuest.getStayCost(),
            newGuest.getPaymentMethod(),
            newGuest.getStatus()
        });

        // 손님 등록 완료 메시지
        JOptionPane.showMessageDialog(this, "예약이 성공적으로 추가되었습니다.", "예약 완료", JOptionPane.INFORMATION_MESSAGE);

        // 입력 필드 초기화
        clearInputFields();
    }


    private void clearInputFields() {
        nameTextField.setText("");
        phoneTextField1.setText("010");
        phoneTextField2.setText("");
        phoneTextField3.setText("");
        checkInDateTextField.setText("");
        checkOutDateTextField.setText("");
        roomNumberTextField.setText("");
        stayCostTextField.setText("");
        guestCountTextField.setText("");
        paymentType.setSelectedIndex(0);
    }

    private void handleSave(ActionEvent event) {
        saveGuestInformation();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radiobuttonGroup = new javax.swing.ButtonGroup();
        paymentMethodLabel = new javax.swing.JLabel();
        onSitePaymentButton = new javax.swing.JRadioButton();
        cardRegistButton = new javax.swing.JRadioButton();
        registButton = new javax.swing.JButton();
        reservationsubmit = new javax.swing.JButton();
        back = new javax.swing.JButton();
        labelCardStatus = new javax.swing.JLabel();
        paymentType = new javax.swing.JComboBox<>();
        wonLabel = new javax.swing.JLabel();
        phoneTextField3 = new javax.swing.JTextField();
        phoneTextField2 = new javax.swing.JTextField();
        phoneTextField1 = new javax.swing.JTextField();
        stayCostLabel = new javax.swing.JLabel();
        phoneNumberLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        hoLabel = new javax.swing.JLabel();
        roomNumberTextField = new javax.swing.JTextField();
        roomNumberLabel = new javax.swing.JLabel();
        stayCostTextField = new javax.swing.JTextField();
        checkInDateLabel = new javax.swing.JLabel();
        checkOutDateLabel = new javax.swing.JLabel();
        checkOutDateTextField = new javax.swing.JTextField();
        checkInDateTextField = new javax.swing.JTextField();
        guestCountLabel = new javax.swing.JLabel();
        guestCountTextField = new javax.swing.JTextField();
        meongLabel = new javax.swing.JLabel();
        guestPlusButton = new javax.swing.JButton();
        calendar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        paymentMethodLabel.setText("결제수단");

        radiobuttonGroup.add(onSitePaymentButton);
        onSitePaymentButton.setText("현장결제");
        onSitePaymentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSitePaymentButtonActionPerformed(evt);
            }
        });

        radiobuttonGroup.add(cardRegistButton);
        cardRegistButton.setSelected(true);
        cardRegistButton.setText("카드등록");
        cardRegistButton.setToolTipText("");
        cardRegistButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardRegistButtonActionPerformed(evt);
            }
        });

        registButton.setText("등록");
        registButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registButtonActionPerformed(evt);
            }
        });

        reservationsubmit.setText("저장");
        reservationsubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reservationsubmitActionPerformed(evt);
            }
        });

        back.setText("뒤로");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        labelCardStatus.setText("등록완료");
        labelCardStatus.setVisible(false);

        paymentType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "현금", "카드" }));
        paymentType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentTypeActionPerformed(evt);
            }
        });

        wonLabel.setText("원");

        phoneTextField1.setText("010");

        stayCostLabel.setText("객실 금액");

        phoneNumberLabel.setText("전화 번호");

        nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusLost(evt);
            }
        });

        nameLabel.setText("이름");
        nameLabel.setToolTipText("");

        hoLabel.setText("호");

        roomNumberTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomNumberTextFieldMouseClicked(evt);
            }
        });
        roomNumberTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomNumberTextFieldActionPerformed(evt);
            }
        });

        roomNumberLabel.setText("방 번호");

        stayCostTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stayCostTextFieldMouseClicked(evt);
            }
        });
        stayCostTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stayCostTextFieldActionPerformed(evt);
            }
        });

        checkInDateLabel.setText("예상 체크인 날짜");

        checkOutDateLabel.setText("예상 체크아웃 날짜");

        checkOutDateTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkOutDateTextFieldMouseClicked(evt);
            }
        });
        checkOutDateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkOutDateTextFieldActionPerformed(evt);
            }
        });

        checkInDateTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkInDateTextFieldMouseClicked(evt);
            }
        });
        checkInDateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkInDateTextFieldActionPerformed(evt);
            }
        });

        guestCountLabel.setText("인원수");

        guestCountTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guestCountTextFieldMouseClicked(evt);
            }
        });
        guestCountTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guestCountTextFieldActionPerformed(evt);
            }
        });

        meongLabel.setText("명");

        guestPlusButton.setText("인원 추가");
        guestPlusButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guestPlusButtonActionPerformed(evt);
            }
        });

        calendar.setText("객실 선택");
        calendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calendarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(reservationsubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(checkOutDateLabel)
                                    .addComponent(checkInDateLabel)
                                    .addComponent(roomNumberLabel)
                                    .addComponent(phoneNumberLabel)
                                    .addComponent(nameLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(roomNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(hoLabel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(nameTextField, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(phoneTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(phoneTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phoneTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(stayCostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(wonLabel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(guestCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(meongLabel))
                                    .addComponent(checkOutDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(checkInDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(calendar)))
                                .addGap(132, 132, 132)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(stayCostLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(guestCountLabel)
                                    .addComponent(paymentMethodLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(onSitePaymentButton)
                                .addGap(7, 7, 7)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(paymentType, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cardRegistButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(registButton)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(guestPlusButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labelCardStatus)
                                .addGap(17, 17, 17))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel)
                            .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phoneNumberLabel)
                            .addComponent(phoneTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phoneTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phoneTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkInDateLabel)
                            .addComponent(checkInDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(calendar))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkOutDateLabel)
                            .addComponent(checkOutDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(roomNumberLabel)
                            .addComponent(roomNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hoLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(stayCostLabel)
                            .addComponent(stayCostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wonLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(guestPlusButton)
                            .addComponent(guestCountLabel)
                            .addComponent(guestCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(meongLabel))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelCardStatus)
                        .addGap(6, 6, 6)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(paymentMethodLabel)
                    .addComponent(onSitePaymentButton)
                    .addComponent(paymentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardRegistButton)
                    .addComponent(registButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(back)
                    .addComponent(reservationsubmit))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onSitePaymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSitePaymentButtonActionPerformed

    }//GEN-LAST:event_onSitePaymentButtonActionPerformed

    private void cardRegistButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardRegistButtonActionPerformed
        
    }//GEN-LAST:event_cardRegistButtonActionPerformed

    private void registButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registButtonActionPerformed
        // CardRegistFrame 인스턴스를 생성하여 카드 등록 창을 표시
        CardRegistFrame cardRegistFrame = new CardRegistFrame();
        cardRegistFrame.setVisible(true);
        cardRegistFrame.setLocationRelativeTo(this);  // 현재 창을 기준으로 중앙에 배치
    }//GEN-LAST:event_registButtonActionPerformed

    private void reservationsubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reservationsubmitActionPerformed
        saveGuestInformation();
    }//GEN-LAST:event_reservationsubmitActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        this.dispose(); // 창을 닫음
    }//GEN-LAST:event_backActionPerformed

    private void paymentTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentTypeActionPerformed

    }//GEN-LAST:event_paymentTypeActionPerformed

    private void nameTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusGained

    }//GEN-LAST:event_nameTextFieldFocusGained

    private void nameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusLost

    }//GEN-LAST:event_nameTextFieldFocusLost

    private void roomNumberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomNumberTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roomNumberTextFieldActionPerformed

    private void roomNumberTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_roomNumberTextFieldMouseClicked

    }//GEN-LAST:event_roomNumberTextFieldMouseClicked

    private void stayCostTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stayCostTextFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_stayCostTextFieldMouseClicked

    private void stayCostTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stayCostTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stayCostTextFieldActionPerformed

    private void checkOutDateTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkOutDateTextFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_checkOutDateTextFieldMouseClicked

    private void checkOutDateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkOutDateTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkOutDateTextFieldActionPerformed

    private void checkInDateTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkInDateTextFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_checkInDateTextFieldMouseClicked

    private void checkInDateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkInDateTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkInDateTextFieldActionPerformed

    private void guestCountTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guestCountTextFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_guestCountTextFieldMouseClicked

    private void guestCountTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guestCountTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guestCountTextFieldActionPerformed

    private void guestPlusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guestPlusButtonActionPerformed
        try {
            int currentCost = Integer.parseInt(stayCostTextField.getText().trim());
            currentCost += 20000; // 인원 추가당 비용 증가
            stayCostTextField.setText(String.valueOf(currentCost));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "현재 객실 금액이 올바르지 않습니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_guestPlusButtonActionPerformed

    private void calendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calendarActionPerformed
        SwingUtilities.invokeLater(() -> {  
            new HotelRoomReservationUI(); // HotelRoomInfo 생성자 호출 캘린더
        });
    }//GEN-LAST:event_calendarActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JButton calendar;
    private javax.swing.JRadioButton cardRegistButton;
    private javax.swing.JLabel checkInDateLabel;
    private javax.swing.JTextField checkInDateTextField;
    private javax.swing.JLabel checkOutDateLabel;
    private javax.swing.JTextField checkOutDateTextField;
    private javax.swing.JLabel guestCountLabel;
    private javax.swing.JTextField guestCountTextField;
    private javax.swing.JButton guestPlusButton;
    private javax.swing.JLabel hoLabel;
    private javax.swing.JLabel labelCardStatus;
    private javax.swing.JLabel meongLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JRadioButton onSitePaymentButton;
    private javax.swing.JLabel paymentMethodLabel;
    private javax.swing.JComboBox<String> paymentType;
    private javax.swing.JLabel phoneNumberLabel;
    private javax.swing.JTextField phoneTextField1;
    private javax.swing.JTextField phoneTextField2;
    private javax.swing.JTextField phoneTextField3;
    private javax.swing.ButtonGroup radiobuttonGroup;
    private javax.swing.JButton registButton;
    private javax.swing.JButton reservationsubmit;
    private javax.swing.JLabel roomNumberLabel;
    private javax.swing.JTextField roomNumberTextField;
    private javax.swing.JLabel stayCostLabel;
    private javax.swing.JTextField stayCostTextField;
    private javax.swing.JLabel wonLabel;
    // End of variables declaration//GEN-END:variables
}
