/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package deu.hms.checkin;

import deu.hms.reservation.ReservationData;
import deu.hms.reservation.reservationFrame;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jimin
 */
public class checkIn extends javax.swing.JFrame {

    /**
     * Creates new form checkIn
     */
    
    public checkIn() {
        initComponents();                // 컴포넌트 초기화
        setLocationRelativeTo(null);     // 화면 중앙 배치
        initializePlaceholders();
    }

    public checkIn(Frame frame, boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void loadTableData() {
    // 테이블 모델 가져오기
        DefaultTableModel model = (DefaultTableModel) reservationListTable.getModel();
        model.setRowCount(0); // 기존 테이블 데이터 초기화

        // 파일에서 예약자 정보를 읽어오기
        List<ReservationData> reservationList = loadFromFile("Reservation.txt");

        // 필요한 데이터만 테이블에 추가
        for (ReservationData reservation : reservationList) {
            Object[] rowData = {
                reservation.getUniqueNumber(), // 고유 번호
                reservation.getName(),         // 이름
                reservation.getPhoneNumber(),  // 전화 번호
                reservation.getRoomNumber(),   // 방 번호
                reservation.getStayCost(),     // 객실 금액
                reservation.getPaymentMethod(),// 결제 수단
                reservation.getStatus()        // 상태
            };
            model.addRow(rowData);
        }
    }

    // 파일에서 예약 데이터를 불러오는 메서드
    public static List<ReservationData> loadFromFile(String fileName) {
        List<ReservationData> reservationList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // 파일의 데이터가 정확한지 확인
                if (data.length >= 11) {
                    try {
                        // 올바른 순서로 데이터를 매핑하여 ReservationData 객체 생성
                        String uniqueNumber = data[0].trim();
                        String name = data[1].trim();
                        String phoneNumber = data[2].trim();
                        String address = data[3].trim();
                        String checkInDate = data[4].trim();
                        String checkOutDate = data[5].trim();
                        String roomNumber = data[6].trim();
                        String guestCount = data[7].trim();
                        String stayCost = data[8].trim();
                        String paymentMethod = data[9].trim();
                        String status = data[10].trim();

                        ReservationData reservation = new ReservationData(
                            uniqueNumber, name, phoneNumber, address, 
                            checkInDate, checkOutDate, roomNumber,
                            guestCount, stayCost, paymentMethod, status
                        );

                        reservationList.add(reservation);
                    } catch (Exception e) {
                        System.err.println("데이터 매핑 중 오류 발생: " + e.getMessage());
                    }
                } else {
                    System.err.println("필드 개수가 충분하지 않음: " + data.length);
                }
            }
        } catch (IOException e) {
            System.err.println("파일을 불러오는 중 오류 발생: " + e.getMessage());
        }
        return reservationList;
    }

    private void initializePlaceholders() {
        setTextFieldPlaceholder(reqestTextField, "요청 사항 없을 시  '없음'  입력");
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
        checkInTextField = new javax.swing.JTextField();
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
        serchButton = new javax.swing.JButton();
        roomInfoButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        reqestLabel.setFont(new java.awt.Font("맑은 고딕", 1, 12)); // NOI18N
        reqestLabel.setText("요청 사항");

        checkInTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkInTextFieldActionPerformed(evt);
            }
        });

        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });

        searchComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "성이름", "고유 번호" }));
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
                "고유 번호", "이름", "전화 번호", "방 번호", "객실 금액", "결제 수단", "상태"
            }
        ));
        ScrollPane.setViewportView(reservationListTable);

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

        serchButton.setLabel("검색");
        serchButton.setMaximumSize(new java.awt.Dimension(82, 23));
        serchButton.setMinimumSize(new java.awt.Dimension(82, 23));
        serchButton.setPreferredSize(new java.awt.Dimension(82, 23));
        serchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serchButtonActionPerformed(evt);
            }
        });

        roomInfoButton.setText("객실 정보");
        roomInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomInfoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(serchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addComponent(ScrollPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(reservationlistLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(guestRegistButton))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(reqestTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(reqestLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(roomInfoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(checkInTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(roomAmountLabel, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGap(12, 12, 12)
                            .addComponent(checkinButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkInLabel)
                .addGap(261, 261, 261))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(checkInLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reservationlistLabel)
                    .addComponent(guestRegistButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomAmountLabel)
                    .addComponent(reqestLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(reqestTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(checkInTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roomInfoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(checkinButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void searchComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchComboBoxActionPerformed

    private void checkinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkinButtonActionPerformed
        // 예약자 명단 테이블에서 선택된 행의 정보를 가져옴
        int selectedRow = reservationListTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "체크인할 행을 선택해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 테이블에서 선택된 예약자 정보를 가져옴
        String uniqueNumber = reservationListTable.getValueAt(selectedRow, 0).toString();
        String name = reservationListTable.getValueAt(selectedRow, 1).toString();
        String phoneNumber = reservationListTable.getValueAt(selectedRow, 2).toString();
        String roomNumber = reservationListTable.getValueAt(selectedRow, 3).toString();
        String stayCost = reservationListTable.getValueAt(selectedRow, 4).toString();
        String paymentMethod = reservationListTable.getValueAt(selectedRow, 5).toString();
        String status = "체크인 완료";  // 체크인 상태로 변경

        // 요청 사항 가져오기
        String requestDetails = reqestTextField.getText().trim();
        if (requestDetails.isEmpty() || requestDetails.equals("요청 사항 없을 시  '없음'  입력")) {
            requestDetails = "없음"; // 요청 사항이 없을 경우 기본값 설정
        }

        // 체크인 정보를 파일에 저장
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("CheckedInRequests.txt", true))) {
            writer.write("고유 번호: " + uniqueNumber);
            writer.write(", 이름: " + name);
            writer.write(", 전화번호: " + phoneNumber);
            writer.write(", 방 번호: " + roomNumber);
            writer.write(", 객실 금액: " + stayCost);
            writer.write(", 결제 수단: " + paymentMethod);
            writer.write(", 상태: " + status);
            writer.write(", 요청 사항: " + requestDetails);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "체크인 되었습니다.", "체크인 완료", JOptionPane.INFORMATION_MESSAGE);

            // 요청 사항 입력 필드 초기화
            reqestTextField.setText("요청 사항 없을 시  '없음'  입력");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "저장 중 오류가 발생했습니다!", "오류", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        // 테이블에서 상태 업데이트
        DefaultTableModel model = (DefaultTableModel) reservationListTable.getModel();
        model.setValueAt("체크인 완료", selectedRow, 6);
    }//GEN-LAST:event_checkinButtonActionPerformed

    private void reqestTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reqestTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reqestTextFieldActionPerformed

    private void guestRegistButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guestRegistButtonActionPerformed
        
    }//GEN-LAST:event_guestRegistButtonActionPerformed

    private void serchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serchButtonActionPerformed
        String searchTerm = searchTextField.getText().trim();
        String searchType = (String) searchComboBox.getSelectedItem();

        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "검색어를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Reservation.txt 파일에서 데이터를 읽어와 필터링
        List<ReservationData> reservationList = ReservationLoad.loadFromFile("Reservation.txt");
        List<ReservationData> filteredData = new ArrayList<>();

        for (ReservationData reservation : reservationList) {
            if ("성이름".equals(searchType) && reservation.getName().contains(searchTerm)) {
                filteredData.add(reservation);
            } else if ("고유 번호".equals(searchType) && reservation.getUniqueNumber().equals(searchTerm)) {
                filteredData.add(reservation);
            }
        }

        // 검색된 결과를 테이블에 로드
        DefaultTableModel model = (DefaultTableModel) reservationListTable.getModel();
        model.setRowCount(0); // 기존 테이블 데이터 초기화

        if (!filteredData.isEmpty()) {
            for (ReservationData reservation : filteredData) {
                Object[] rowData = {
                    reservation.getUniqueNumber(), // 고유 번호
                    reservation.getName(),         // 이름
                    reservation.getPhoneNumber(),  // 전화 번호
                    reservation.getRoomNumber(),   // 방 번호
                    reservation.getStayCost(),     // 객실 금액
                    reservation.getPaymentMethod(),// 결제 수단
                    reservation.getStatus()        // 상태
                };
                model.addRow(rowData);
            }

            // 첫 번째 검색 결과의 객실 금액을 checkInTextField에 설정
            ReservationData firstReservation = filteredData.get(0);
            checkInTextField.setText(firstReservation.getStayCost());
        } else {
            JOptionPane.showMessageDialog(this, "검색 결과가 없습니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_serchButtonActionPerformed

    private void roomInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomInfoButtonActionPerformed
        // hotelRoomInfo 인스턴스를 생성하여 새로운 창을 엽니다.
        SwingUtilities.invokeLater(() -> {
            HotelRoomInfo  hotelRoomInfo = new HotelRoomInfo (); // hotelRoomInfo 창을 띄웁니다.
        });
    }//GEN-LAST:event_roomInfoButtonActionPerformed

    private void checkInTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkInTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkInTextFieldActionPerformed

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
            java.util.logging.Logger.getLogger(checkIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(checkIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(checkIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(checkIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new checkIn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JLabel checkInLabel;
    private javax.swing.JTextField checkInTextField;
    private javax.swing.JButton checkinButton;
    private javax.swing.JButton guestRegistButton;
    private javax.swing.ButtonGroup paymentGroup;
    private javax.swing.JLabel reqestLabel;
    private javax.swing.JTextField reqestTextField;
    private javax.swing.JTable reservationListTable;
    private javax.swing.JLabel reservationlistLabel;
    private javax.swing.JLabel roomAmountLabel;
    private javax.swing.JButton roomInfoButton;
    private javax.swing.JComboBox<String> searchComboBox;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JButton serchButton;
    // End of variables declaration//GEN-END:variables

}
