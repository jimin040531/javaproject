    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package deu.hms.checkin;

import deu.hms.login.MainScreenEmployees;
import deu.hms.login.MainScreenManager;
import deu.hms.login.UserAuthentication;
import deu.hms.reservation.ReservationData;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CheckInUI extends JFrame {

    private final ReservationManager reservationManager;

    public CheckInUI() {
        reservationManager = new ReservationManager();
        initComponents();
        this.setLocationRelativeTo(null);
    }

    // 예약 정보를 테이블에 로드
    public void loadReservations() {
        List<ReservationData> reservations = reservationManager.getAllReservations();
        updateTable(reservations);
    }
    
    // 예약 테이블 반환 메서드
    public JTable getReservationListTable() {
        return reservationListTable;
    }

    // 테이블 업데이트
    private void updateTable(List<ReservationData> data) {
        DefaultTableModel model = (DefaultTableModel) reservationListTable.getModel();
        model.setRowCount(0); // 기존 데이터 초기화
        for (ReservationData reservation : data) {
            model.addRow(new Object[]{
                reservation.getUniqueNumber(), reservation.getName(), reservation.getPhoneNumber(),
                reservation.getRoomNumber(), reservation.getGuestCount(), reservation.getStayCost(),
                reservation.getPaymentMethod(), reservation.getStatus()
            });
        }
    }

    // 검색 기능
    private void searchReservations() {
        String searchTerm = searchTextField.getText().trim();
        String searchType = (String) searchComboBox.getSelectedItem();
        List<ReservationData> reservations = reservationManager.getAllReservations();
        List<ReservationData> filteredData = new ArrayList<>();

        for (ReservationData reservation : reservations) {
            switch (searchType) {
                case "고유 번호":
                    if (reservation.getUniqueNumber().contains(searchTerm)) {
                        filteredData.add(reservation);
                    }
                    break;
                case "성이름":
                    if (reservation.getName().contains(searchTerm)) {
                        filteredData.add(reservation);
                    }
                    break;
                case "방 번호":
                    if (reservation.getRoomNumber().contains(searchTerm)) {
                        filteredData.add(reservation);
                    }
                    break;
            }
        }

        // 검색 결과를 테이블에 업데이트
        updateTable(filteredData);

        // 검색 결과가 없으면 사용자에게 알림
        if (filteredData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "검색 결과가 없습니다.", "검색 결과", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // 체크인 처리
    private void handleCheckIn() {
        int selectedRow = reservationListTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "체크인할 예약을 선택하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String reservationId = (String) reservationListTable.getValueAt(selectedRow, 0);
        String requestDetails = reqestTextField.getText().trim();
        ReservationData selectedReservation = null;

        // 선택된 예약 정보를 가져오기
        List<ReservationData> reservations = reservationManager.getAllReservations();
        for (ReservationData reservation : reservations) {
            if (reservation.getUniqueNumber().equals(reservationId)) {
                selectedReservation = reservation;
                break;
            }
        }

        if (selectedReservation != null) {
            selectedReservation.setStatus("체크인 완료");
            reservationManager.updateReservationStatus(reservationId, "체크인 완료");
            reservationManager.saveCheckInData(selectedReservation, requestDetails);
            JOptionPane.showMessageDialog(this, "체크인이 완료되었습니다.\n요청 사항: " + requestDetails, "체크인 완료", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "선택된 예약 정보를 찾을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }

        // 테이블 갱신
        loadReservations();

        // 요청 사항 필드 초기화
        reqestTextField.setText("");
    }

    // 손님 등록 처리
    private void registerGuest() {
        GuestRegist guestRegistFrame = new GuestRegist(this, reservationManager);
        guestRegistFrame.setVisible(true);

        // 손님 등록 후 테이블 갱신
        guestRegistFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                loadReservations();
            }
        });
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paymentGroup = new javax.swing.ButtonGroup();
        reqestLabel = new javax.swing.JLabel();
        roomCountTextField = new javax.swing.JTextField();
        searchTextField = new javax.swing.JTextField();
        searchComboBox = new javax.swing.JComboBox<>();
        checkinButton = new javax.swing.JButton();
        checkInLabel = new javax.swing.JLabel();
        ScrollPane = new javax.swing.JScrollPane();
        reservationListTable = new javax.swing.JTable();
        reqestTextField = new javax.swing.JTextField();
        reservationlistLabel = new javax.swing.JLabel();
        roomAmountLabel = new javax.swing.JLabel();
        guestRegistButton = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        reqestLabel.setFont(new java.awt.Font("맑은 고딕", 1, 12)); // NOI18N
        reqestLabel.setText("요청 사항");

        roomCountTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomCountTextFieldActionPerformed(evt);
            }
        });

        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });

        searchComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "고유 번호", "성이름", "방 번호" }));
        searchComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchComboBoxActionPerformed(evt);
            }
        });

        checkinButton.setText("체크인");
        checkinButton.setMaximumSize(new java.awt.Dimension(82, 23));
        checkinButton.setMinimumSize(new java.awt.Dimension(82, 23));
        checkinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkinButtonActionPerformed(evt);
            }
        });

        checkInLabel.setFont(new java.awt.Font("맑은 고딕", 1, 24)); // NOI18N
        checkInLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        checkInLabel.setText("체크인");

        reservationListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "고유 번호", "이름", "전화 번호", "방 번호", "인원수", "객실 금액", "결제 수단", "상태"
            }
        ));
        ScrollPane.setViewportView(reservationListTable);
        if (reservationListTable.getColumnModel().getColumnCount() > 0) {
            reservationListTable.getColumnModel().getColumn(2).setPreferredWidth(120);
            reservationListTable.getColumnModel().getColumn(7).setPreferredWidth(100);
        }

        reqestTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reqestTextFieldActionPerformed(evt);
            }
        });

        reservationlistLabel.setFont(new java.awt.Font("맑은 고딕", 1, 12)); // NOI18N
        reservationlistLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reservationlistLabel.setText("손님 명단");

        roomAmountLabel.setFont(new java.awt.Font("맑은 고딕", 1, 12)); // NOI18N
        roomAmountLabel.setText("객실 금액");

        guestRegistButton.setText("손님 등록");
        guestRegistButton.setActionCommand("등록");
        guestRegistButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guestRegistButtonActionPerformed(evt);
            }
        });

        searchButton.setLabel("검색");
        searchButton.setMaximumSize(new java.awt.Dimension(82, 23));
        searchButton.setMinimumSize(new java.awt.Dimension(82, 23));
        searchButton.setPreferredSize(new java.awt.Dimension(82, 23));
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

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
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkInLabel)
                        .addGap(244, 244, 244))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(searchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)
                            .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                        .addComponent(ScrollPane)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(reqestLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(roomAmountLabel)
                            .addGap(85, 85, 85))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(reservationlistLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(guestRegistButton)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(reqestTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(roomCountTextField)
                                .addComponent(checkinButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkInLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reservationlistLabel)
                    .addComponent(guestRegistButton))
                .addGap(18, 18, 18)
                .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomAmountLabel)
                    .addComponent(reqestLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(roomCountTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(checkinButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(reqestTextField))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        searchButtonActionPerformed(evt); // 검색 버튼과 동일한 동작
    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void searchComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchComboBoxActionPerformed

    private void checkinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkinButtonActionPerformed
        int selectedRow = reservationListTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "체크인할 예약을 선택하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String reservationId = (String) reservationListTable.getValueAt(selectedRow, 0);
        String requestDetails = reqestTextField.getText().trim();

        // 예약 ID로 예약 검색
        ReservationData selectedReservation = reservationManager.findReservationById(reservationId);
        if (selectedReservation == null) {
            JOptionPane.showMessageDialog(this, "선택된 예약을 찾을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 체크인 처리
        selectedReservation.setStatus("체크인 완료");
        reservationManager.updateReservationStatus(reservationId, "체크인 완료");

        CheckInData checkInData = new CheckInData(selectedReservation, requestDetails);
        reservationManager.saveCheckInData(checkInData);

        JOptionPane.showMessageDialog(this, "체크인이 완료되었습니다.\n요청 사항: " + requestDetails, "체크인 완료", JOptionPane.INFORMATION_MESSAGE);

        // 테이블에서 해당 행 제거
        DefaultTableModel model = (DefaultTableModel) reservationListTable.getModel();
        model.removeRow(selectedRow); // 선택된 행 제거

        // 요청 사항 입력 필드 초기화
        reqestTextField.setText("");
        roomCountTextField.setText("");
    }//GEN-LAST:event_checkinButtonActionPerformed

    private void reqestTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reqestTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reqestTextFieldActionPerformed

    private void guestRegistButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guestRegistButtonActionPerformed
        GuestRegist guestRegistFrame = new GuestRegist(this, reservationManager);
        guestRegistFrame.setLocationRelativeTo(this);
        guestRegistFrame.setVisible(true);
    }//GEN-LAST:event_guestRegistButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        searchReservations();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void roomCountTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomCountTextFieldActionPerformed

    }//GEN-LAST:event_roomCountTextFieldActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
    UserAuthentication userAuth = new UserAuthentication();
        String userId = userAuth.getCurrentUserId();
        String userRole = userAuth.getUserRole(userId);

        if ("employee".equalsIgnoreCase(userRole)) {
            MainScreenEmployees mainScreen = new MainScreenEmployees();
            mainScreen.setVisible(true);
        } else if ("manager".equalsIgnoreCase(userRole)) {
            MainScreenManager mainScreen = new MainScreenManager();
            mainScreen.setVisible(true);
        }

        this.dispose(); // 현재 창 닫기
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
            java.util.logging.Logger.getLogger(CheckInUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CheckInUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CheckInUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CheckInUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CheckInUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel checkInLabel;
    private javax.swing.JButton checkinButton;
    private javax.swing.JButton guestRegistButton;
    private javax.swing.ButtonGroup paymentGroup;
    private javax.swing.JLabel reqestLabel;
    private javax.swing.JTextField reqestTextField;
    private javax.swing.JTable reservationListTable;
    private javax.swing.JLabel reservationlistLabel;
    private javax.swing.JLabel roomAmountLabel;
    private javax.swing.JTextField roomCountTextField;
    private javax.swing.JButton searchButton;
    private javax.swing.JComboBox<String> searchComboBox;
    private javax.swing.JTextField searchTextField;
    // End of variables declaration//GEN-END:variables

}
