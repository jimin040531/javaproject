/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package deu.hms.reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.*;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import deu.hms.reservation.ReservationUtils;
import java.io.IOException; 
import java.util.UUID;
import deu.hms.checkin.cardRegist; //카드등록 불러옴


/**
 *
 * @author adsd3
 */
public class Registration extends JFrame {
    
    private reservationFrame reservationFrame;
    private  String cardRegistEered = "카드등록";
    private  String cardNotRegistEered  = "카드미등록";
    private JTable mainTable; // Reservation 테이블과 연결
    private DefaultTableModel tableModel;
    private static int uniqueNumber = 1;
    private int editingRow = -1; // 수정 중인 행의 인덱스 (-1은 수정 아님)
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); //타이머 
    private CardManager cardManager = new CardManager();
    private ReservationStatusScheduler statusScheduler = new ReservationStatusScheduler();
private reservationFrame parentFrame;
        
 

    // 수정 중인 행 인덱스 설정 메서드 수정버튼을 눌렸을때 작동하는 메소드
    public void setEditingRow(int rowIndex) {
    this.editingRow = rowIndex;
    }
      public void setRegistrationData(String name, String address, String phoneNumber, String checkInDate,
                                String checkOutDate, String roomNumber, String guestCount,
                                String paymentMethod, String status, String stayCost) {
         textName.setText(name);
    textAddress.setText(address);
    textPhoneNumber.setText(phoneNumber);
    textCheckInDate.setText(checkInDate);
    textCheckOutDate.setText(checkOutDate);
    textRoomNumber.setText(roomNumber);
    textGuestCount.setText(guestCount);
    Money.setText(stayCost);

        if (paymentMethod.equals("현장결제")) {
        onSitePaymentButton.setSelected(true);
    } else if (paymentMethod.equals("카드결제")) {
        cardRegistButton.setSelected(true);
    }
        
    }
    public Registration(reservationFrame parentFrame) {
    this.parentFrame = parentFrame; // 부모 프레임 저장
    initComponents();
}

    private Registration(JTable table) {
        this.mainTable = table;
        initComponents();
    }

    public void setMainTable(JTable table) {
        this.mainTable = table;
    }

    public JTable getMainTable() {
        return mainTable;
    }

    private String generateUniqueId() {
        return java.util.UUID.randomUUID().toString();
    }

   
       // LocalDateTime targetTime = LocalDateTime.of(checkInDay, LocalTime.of(18, 0));

 
    
private boolean isCardRegistered() {
        // 카드 등록 여부를 확인하는 로직 구현 (예: cardRegistButton.isSelected() 등)
        return cardRegistButton.isSelected();
    }
  


private ReservationData populateReservationData() { 
        String uniqueNumber;
    if (editingRow != -1) {
        uniqueNumber = parentFrame.getMainTable().getValueAt(editingRow, 0).toString(); // 기존 고유번호 유지
    } else {
        uniqueNumber = UUID.randomUUID().toString(); // 새로운 고유번호 생성
    }

    return new ReservationData(
        uniqueNumber,
        textName.getText(),
        textAddress.getText(),
        textPhoneNumber.getText(),
        textCheckInDate.getText(),
        textCheckOutDate.getText(),
        textRoomNumber.getText(),
        textGuestCount.getText(),
        Money.getText(),
        onSitePaymentButton.isSelected() ? "현장결제" : "카드결제",
        "" 
    );
}

//Registration에서 저장버튼 
private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
     DefaultTableModel model = (DefaultTableModel) parentFrame.getMainTable().getModel();
    ReservationData updatedData = populateReservationData();

    try {
        // 선택된 행이 있을 경우 삭제 (눈속임 방식)
        if (editingRow != -1) { 
            model.removeRow(editingRow); // 테이블에서 선택된 행 삭제
            FileManager.deleteFromFile(updatedData.getUniqueNumber(), "Reservation.txt"); // 파일에서도 삭제
        }

        // 새 데이터 추가
        FileManager.saveToFile(updatedData.toCSV());
        TableManager.addRow(model, updatedData);

        JOptionPane.showMessageDialog(this, "데이터가 성공적으로 저장되었습니다!", "성공", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "파일 처리 중 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
    }

    this.dispose(); // 창 닫기
    editingRow = -1; // 수정 상태 초기화
}

public void transferRegistrationToReservation() {
    DefaultTableModel model = (DefaultTableModel) reservationFrame.getMainTable().getModel();

    // ReservationData 객체 생성
    ReservationData reservationData = populateReservationData();

    // ReservationUtils의 addOrUpdateRow 호출
    ReservationUtils.addOrUpdateRow(model, reservationData);
}



    private void clearFields() {
        textName.setText("");
        textAddress.setText("");
        textPhoneNumber.setText("");
        textCheckInDate.setText("");
        textCheckOutDate.setText("");
        textRoomNumber.setText("");
        textGuestCount.setText("");
    }

    /**
     * Creates new form Registration
     */
    

    public Registration() {
        initComponents();

        //initRadioButtons();

       // configurePaymentButtonState();
        paymentTypeRegistButton.setEnabled(false);

        //
    }

   /* private void initRadioButtons() {
        // paymentButtonGroup은 이미 생성되어 있다고 가정
        // JDialog에 있는 onSitePaymentButton과 cardRegistButton을 ButtonGroup에 추가
        paymentButtonGroup.add(onSitePaymentButton);
        paymentButtonGroup.add(cardRegistButton);
    }*/

   /* private void configurePaymentButtonState() {
        // cardRegistButton을 선택했을 때 paymentTypeRegistButton 활성화
        cardRegistButton.addActionListener(e -> paymentTypeRegistButton.setEnabled(true));

        // onSitePaymentButton을 선택했을 때 paymentTypeRegistButton 비활성화
        onSitePaymentButton.addActionListener(e -> paymentTypeRegistButton.setEnabled(false));
    }*/

    // 다른 컴포넌트 초기화 후에 호출되는 메서드에 작성
    

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        paymentButtonGroup = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        name = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        onSitePaymentButton = new javax.swing.JRadioButton();
        cardRegistButton = new javax.swing.JRadioButton();
        paymentTypeRegistButton = new javax.swing.JButton();
        textName = new java.awt.TextField();
        textCheckOutDate = new java.awt.TextField();
        textAddress = new java.awt.TextField();
        textPhoneNumber = new java.awt.TextField();
        textCheckInDate = new java.awt.TextField();
        textRoomNumber = new java.awt.TextField();
        textGuestCount = new java.awt.TextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        Money = new javax.swing.JTextPane();
        jLabel11 = new javax.swing.JLabel();
        reservationsubmit = new javax.swing.JButton();
        back = new javax.swing.JButton();
        labelReservationStatus = new javax.swing.JLabel();
        labelCardStatus = new javax.swing.JLabel();

        jScrollPane1.setViewportView(jTextPane1);

        name.setText("이름");

        jLabel2.setText("전화번호");

        jLabel3.setText("주소");

        jLabel4.setText("예상 체크인 날짜");

        jLabel5.setText("예상 체크아웃 날짜");

        jLabel6.setText("방번호");

        jLabel7.setText("인원수");

        jLabel9.setText("금액");

        jLabel10.setText("결제수단");

        paymentButtonGroup.add(onSitePaymentButton);
        onSitePaymentButton.setText("현장결제");
        onSitePaymentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSitePaymentButtonActionPerformed(evt);
            }
        });

        paymentButtonGroup.add(cardRegistButton);
        cardRegistButton.setText("카드결제");
        cardRegistButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardRegistButtonActionPerformed(evt);
            }
        });

        paymentTypeRegistButton.setText("등록");
        paymentTypeRegistButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentTypeRegistButtonActionPerformed(evt);
            }
        });

        textName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textNameActionPerformed(evt);
            }
        });

        textAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textAddressActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(Money);

        jLabel11.setText("원");

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

        labelReservationStatus.setText("예약완료! 체크인은 당일 6시입니다 !!");
        labelReservationStatus.setVisible(false);

        labelCardStatus.setText("등록완료");
        labelCardStatus.setVisible(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(78, 78, 78)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textGuestCount, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(name))
                            .addComponent(jLabel5))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textCheckOutDate, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textRoomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textCheckInDate, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textName, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(43, 43, 43)
                        .addComponent(onSitePaymentButton)
                        .addGap(18, 18, 18)
                        .addComponent(cardRegistButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(paymentTypeRegistButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelCardStatus))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelReservationStatus)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(reservationsubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(196, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(textName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(textPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(textCheckOutDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6))
                            .addComponent(textRoomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(textGuestCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(onSitePaymentButton)
                            .addComponent(cardRegistButton)
                            .addComponent(paymentTypeRegistButton)
                            .addComponent(labelCardStatus)))
                    .addComponent(textCheckInDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(back)
                    .addComponent(reservationsubmit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelReservationStatus)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void onSitePaymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSitePaymentButtonActionPerformed
    labelCardStatus.setVisible(false);
    }//GEN-LAST:event_onSitePaymentButtonActionPerformed

    private void cardRegistButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardRegistButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cardRegistButtonActionPerformed

    private void paymentTypeRegistButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentTypeRegistButtonActionPerformed
   cardRegist cardRegistWindow = new cardRegist();
    cardRegistWindow.setVisible(true);
        cardRegistWindow.setLocationRelativeTo(this);  // 부모 컴포넌트를 기준으로 중앙에 배치  

    // 창이 닫힌 후 카드 정보가 등록되었는지 확인
    cardRegistWindow.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            // 파일에서 카드 정보 읽기
            String cardInfo = readCardInfoFromFile();
            if (cardInfo != null) {
                labelCardStatus.setVisible(true);
                labelCardStatus.setText("등록완료");
            } else {
                labelCardStatus.setVisible(false);
            }
        }
    });
}

// 파일에서 카드 정보를 읽는 메서드
private String readCardInfoFromFile() {
    try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("card_data.txt"))) {
        return reader.readLine(); // 첫 번째 줄 읽기
    } catch (java.io.IOException ex) {
        return null; // 읽기 실패 시 null 반환
    }

// 카드 등록 완료 후 라벨 업데이트

    }//GEN-LAST:event_paymentTypeRegistButtonActionPerformed

    private void textNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textNameActionPerformed

    private void textAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textAddressActionPerformed

    private void reservationsubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reservationsubmitActionPerformed
    
        DefaultTableModel model = (DefaultTableModel) parentFrame.getMainTable().getModel();
    ReservationData updatedData = populateReservationData();

    try {
        if (editingRow != -1) {
            // 기존 데이터 수정
            TableManager.updateRow(model, editingRow, updatedData);
            FileManager.updateInFile(updatedData, "Reservation.txt");
        } else {
            // 새 데이터 추가
            TableManager.addRow(model, updatedData);
            FileManager.saveToFile(updatedData.toCSV());
        }

        JOptionPane.showMessageDialog(this, "저장 완료!", "성공", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "저장 실패: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
    }

    editingRow = -1; // 수정 상태 초기화
    
    
    
    }//GEN-LAST:event_reservationsubmitActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
      editingRow = -1; // 수정 상태 초기화
       this.setVisible(false);

    // 이전에 생성된 reservationFrame이 null인지 확인
    if (reservationFrame == null) {
        reservationFrame = new reservationFrame(); // 새로운 reservationFrame 생성
        reservationFrame.setSize(850, 250);
        reservationFrame.setLocationRelativeTo(null);
        reservationFrame.setLocationRelativeTo(this);  // 부모 컴포넌트를 기준으로 중앙에 배치  

    }
    }//GEN-LAST:event_backActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane Money;
    private javax.swing.JButton back;
    private javax.swing.JRadioButton cardRegistButton;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel labelCardStatus;
    private javax.swing.JLabel labelReservationStatus;
    private javax.swing.JLabel name;
    private javax.swing.JRadioButton onSitePaymentButton;
    private javax.swing.ButtonGroup paymentButtonGroup;
    private javax.swing.JButton paymentTypeRegistButton;
    private javax.swing.JButton reservationsubmit;
    private java.awt.TextField textAddress;
    private java.awt.TextField textCheckInDate;
    private java.awt.TextField textCheckOutDate;
    private java.awt.TextField textGuestCount;
    private java.awt.TextField textName;
    private java.awt.TextField textPhoneNumber;
    private java.awt.TextField textRoomNumber;
    // End of variables declaration//GEN-END:variables
}
