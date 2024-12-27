    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package deu.hms.checkin;

/**
 *
 * @author Jimin
 */
//나는야 퉁퉁이
import deu.hms.login.MainScreenEmployees;
import deu.hms.login.MainScreenManager;
import deu.hms.login.UserAuthentication;
import deu.hms.utility.HotelRoomReservationUI;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CheckInUI extends JFrame {

    private final CheckInManager reservationManager;

    public CheckInUI() {
        reservationManager = new CheckInManager();
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public void loadReservations(List<CheckInData> checkInDataList) {
        updateTable(checkInDataList);
    }

    private void updateTable(List<CheckInData> data) {
        DefaultTableModel model = (DefaultTableModel) reservationListTable.getModel();

        // 테이블 초기화
        model.setRowCount(0); 

        for (CheckInData checkInData : data) {
            model.addRow(new Object[]{
                checkInData.getUniqueNumber(),
                checkInData.getName(),
                checkInData.getPhoneNumber(),
                checkInData.getCheckInDate(),
                checkInData.getCheckOutDate(),
                checkInData.getRoomNumber(),
                checkInData.getGuestCount(),
                checkInData.getStayCost(),
                checkInData.getPaymentMethod(),
                checkInData.getStatus()
            });
        }
    }

    private void handleCheckIn() {
        int selectedRow = reservationListTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "체크인할 예약을 선택하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String uniqueNumber = (String) reservationListTable.getValueAt(selectedRow, 0);
        String name = (String) reservationListTable.getValueAt(selectedRow, 1);
        String phoneNumber = (String) reservationListTable.getValueAt(selectedRow, 2);
        String checkInDate = (String) reservationListTable.getValueAt(selectedRow, 3);
        String checkOutDate = (String) reservationListTable.getValueAt(selectedRow, 4);
        String roomNumber = (String) reservationListTable.getValueAt(selectedRow, 5);
        String guestCount = (String) reservationListTable.getValueAt(selectedRow, 6);
        String stayCost = (String) reservationListTable.getValueAt(selectedRow, 7);
        String paymentMethod = (String) reservationListTable.getValueAt(selectedRow, 8);
        String status = "체크인 완료";
        String requestDetails = reqestTextField.getText().trim();

        CheckInData checkInData = new CheckInData(uniqueNumber, name, phoneNumber, checkInDate, checkOutDate, roomNumber, guestCount, stayCost, paymentMethod, status, requestDetails);

        reservationManager.saveCheckInDataWithRequest(checkInData, requestDetails);  // 메서드 변경됨

        JOptionPane.showMessageDialog(this, "체크인이 완료되었습니다.\n요청 사항: " + requestDetails, "체크인 완료", JOptionPane.INFORMATION_MESSAGE);

        DefaultTableModel model = (DefaultTableModel) reservationListTable.getModel();
        model.removeRow(selectedRow);

        reqestTextField.setText("");
        roomCountTextField.setText("");
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
        calendar = new javax.swing.JButton();

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
                "고유 번호", "이름", "전화 번호", "체크인 날짜", "체크아웃 날짜", "방 번호", "인원수", "객실 금액", "결제 수단", "상태"
            }
        ));
        ScrollPane.setViewportView(reservationListTable);
        if (reservationListTable.getColumnModel().getColumnCount() > 0) {
            reservationListTable.getColumnModel().getColumn(2).setPreferredWidth(120);
            reservationListTable.getColumnModel().getColumn(3).setPreferredWidth(110);
            reservationListTable.getColumnModel().getColumn(4).setPreferredWidth(120);
            reservationListTable.getColumnModel().getColumn(7).setPreferredWidth(100);
            reservationListTable.getColumnModel().getColumn(8).setPreferredWidth(100);
            reservationListTable.getColumnModel().getColumn(9).setPreferredWidth(100);
        }

        reqestTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reqestTextFieldActionPerformed(evt);
            }
        });

        reservationlistLabel.setFont(new java.awt.Font("맑은 고딕", 1, 12)); // NOI18N
        reservationlistLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reservationlistLabel.setText("고객 명단");

        roomAmountLabel.setFont(new java.awt.Font("맑은 고딕", 1, 12)); // NOI18N
        roomAmountLabel.setText("객실 금액");

        guestRegistButton.setText("고객 등록");
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
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addGap(272, 272, 272)
                        .addComponent(checkInLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 8, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(reqestLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(reservationlistLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(guestRegistButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(calendar))
                            .addComponent(reqestTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(roomAmountLabel))
                            .addComponent(roomCountTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkinButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(checkInLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(searchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reservationlistLabel)
                    .addComponent(guestRegistButton)
                    .addComponent(calendar))
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
        handleCheckIn();
    }//GEN-LAST:event_checkinButtonActionPerformed

    private void reqestTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reqestTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reqestTextFieldActionPerformed

    private void guestRegistButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guestRegistButtonActionPerformed
        // CheckInUI의 인스턴스를 GuestRegist로 전달
        GuestRegist guestRegistFrame = new GuestRegist(this, reservationListTable);
        guestRegistFrame.setVisible(true); // GuestRegist 창을 화면에 표시
    }//GEN-LAST:event_guestRegistButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // 검색어와 검색 기준을 가져오기
        String searchTerm = searchTextField.getText().trim();  // 입력된 검색어
        String searchType = (String) searchComboBox.getSelectedItem();  // 선택된 검색 기준

        // 전체 예약 데이터를 가져옵니다
        List<CheckInData> allReservations = reservationManager.getCheckInDataList();
        List<CheckInData> filteredData = new ArrayList<>();

        // 검색 기준에 맞는 데이터를 필터링합니다
        for (CheckInData checkInData : allReservations) {
            switch (searchType) {
                case "고유 번호":
                    if (checkInData.getUniqueNumber().contains(searchTerm)) {
                        filteredData.add(checkInData);
                    }
                    break;
                case "성이름":
                    if (checkInData.getName().contains(searchTerm)) {
                        filteredData.add(checkInData);
                    }
                    break;
                case "방 번호":
                    if (checkInData.getRoomNumber().contains(searchTerm)) {
                        filteredData.add(checkInData);
                    }
                    break;
            }
        }

        // 검색 결과가 없으면 사용자에게 알림
        if (filteredData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "검색 결과가 없습니다.", "검색 결과", JOptionPane.INFORMATION_MESSAGE);
        }

        // 테이블에 필터링된 데이터를 업데이트합니다
        updateTable(filteredData);
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

    private void calendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calendarActionPerformed
        SwingUtilities.invokeLater(() -> {
            new HotelRoomReservationUI(); // HotelRoomInfo 생성자 호출 캘린더
        });
    }//GEN-LAST:event_calendarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JButton backButton;
    private javax.swing.JButton calendar;
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
