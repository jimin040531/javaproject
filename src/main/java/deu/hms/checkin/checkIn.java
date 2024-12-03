/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package deu.hms.checkin;

import deu.hms.reservation.Reservation;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jimin
 */
public class CheckIn extends javax.swing.JFrame {

    /**
     * Creates new form CheckIn
     */
    private ReservationLoad reservationLoad;
    
    public CheckIn() {
        this.reservationLoad = new ReservationLoad(); // ReservationLoad 객체 초기화
        initComponents();                // 컴포넌트 초기화
        searchTextField = new javax.swing.JTextField(); // searchTextField 초기화
        setLocationRelativeTo(null);     // 화면 중앙 배치
        initializePlaceholders();
        initRadioButtons();              // 라디오 버튼 초기화
        configurePaymentButtonState();   // 라디오 버튼의 상태 변경 이벤트 연결
        paymentTypeRegistButton.setEnabled(false);
        paymentType.setEnabled(false);

        loadReservationsToTable();       // 테이블에 예약 데이터를 로드하는 메서드 호출
    }
    
    public void searchReservationData() {
        String searchText = searchTextField.getText().trim();
        DefaultTableModel model = (DefaultTableModel) reservationListTable.getModel();
        DefaultTableModel searchModel = new DefaultTableModel(new String[]{
            "고유 번호", "이름", "전화 번호", "방 번호", "객실 금액", "결제 수단", "상태"
        }, 0);

        for (int i = 0; i < model.getRowCount(); i++) {
            String uniqueId = model.getValueAt(i, 0).toString();
            String name = model.getValueAt(i, 1).toString();
            if (uniqueId.contains(searchText) || name.contains(searchText)) {
                searchModel.addRow(new Object[]{
                    model.getValueAt(i, 0),
                    model.getValueAt(i, 1),
                    model.getValueAt(i, 2),
                    model.getValueAt(i, 3),
                    model.getValueAt(i, 4),
                    model.getValueAt(i, 5),
                    model.getValueAt(i, 6)
                });
            }
        }

        reservationListTable.setModel(searchModel);
    }
    
    // 라디오 버튼 초기화 메서드
    private void initRadioButtons() {
        paymentGroup = new javax.swing.ButtonGroup(); // ButtonGroup 초기화
        paymentGroup.add(onSitePaymentButton);
        paymentGroup.add(cardRegistButton);
    }

    // 라디오 버튼 상태 변경 이벤트 설정 메서드
    private void configurePaymentButtonState() {
        cardRegistButton.addActionListener(e -> {
            paymentTypeRegistButton.setEnabled(true);
            paymentType.setEnabled(false);
        });

        onSitePaymentButton.addActionListener(e -> {
            paymentTypeRegistButton.setEnabled(false);
            paymentType.setEnabled(true);
        });
    }

    // 플레이스홀더 초기화 메서드
    private void initializePlaceholders() {
        setTextFieldPlaceholder(reqestTextField, "요청 사항 없을 시  '없음'  입력");
    }
    
    // 텍스트 필드에 플레이스홀더 설정 메서드
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

    // 예약 파일을 읽어오는 메서드
    public void readReservationFile(String filePath) {
        reservationLoad.loadReservationsFromFile(filePath); // ReservationLoad 클래스를 사용해 파일 읽기
        loadReservationsToTable(); // 파일 읽기 후 테이블에 데이터 로드
    }

    // 예약 데이터를 JTable에 로드하는 메서드
    public void loadReservationsToTable() {
        DefaultTableModel model = (DefaultTableModel) reservationListTable.getModel();
        model.setRowCount(0); // 기존 테이블 내용 초기화

        List<Reservation> reservations = reservationLoad.getReservations();
        for (Reservation reservation : reservations) {
            model.addRow(new Object[]{
                reservation.getUnique(),
                reservation.getName(),
                reservation.getPhoneNum(),
                reservation.getRoomNum(),
                reservation.getFee(),
                reservation.getPaymentType(),
                reservation.getPaymentMethod()
            });
        }
    }

    // 콤보 박스 선택에 따라 검색 기준 변경 메서드
    private void comboBoxSelectionChanged() {
        String selectedItem = (String) searchComboBox.getSelectedItem();
        if ("이름".equals(selectedItem)) {
            searchReservationDataByName();
        } else if ("고유 번호".equals(selectedItem)) {
            searchReservationDataById();
        }
    }

    // 이름으로 예약 데이터를 검색하는 메서드
    private void searchReservationDataByName() {
        String searchText = searchTextField.getText().trim();
        DefaultTableModel model = (DefaultTableModel) reservationListTable.getModel();
        DefaultTableModel searchModel = new DefaultTableModel(new String[]{
            "고유 번호", "이름", "전화 번호", "방 번호", "객실 금액", "결제 수단", "상태"
        }, 0);

        for (int i = 0; i < model.getRowCount(); i++) {
            String name = model.getValueAt(i, 1).toString();
            if (name.contains(searchText)) {
                searchModel.addRow(new Object[]{
                    model.getValueAt(i, 0),
                    model.getValueAt(i, 1),
                    model.getValueAt(i, 2),
                    model.getValueAt(i, 3),
                    model.getValueAt(i, 4),
                    model.getValueAt(i, 5),
                    model.getValueAt(i, 6)
                });
            }
        }

        reservationListTable.setModel(searchModel);
    }

    // 고유 번호로 예약 데이터를 검색하는 메서드
    private void searchReservationDataById() {
        String searchText = searchTextField.getText().trim();
        DefaultTableModel model = (DefaultTableModel) reservationListTable.getModel();
        DefaultTableModel searchModel = new DefaultTableModel(new String[]{
            "고유 번호", "이름", "전화 번호", "방 번호", "객실 금액", "결제 수단", "상태"
        }, 0);

        for (int i = 0; i < model.getRowCount(); i++) {
            String uniqueId = model.getValueAt(i, 0).toString();
            if (uniqueId.contains(searchText)) {
                searchModel.addRow(new Object[]{
                    model.getValueAt(i, 0),
                    model.getValueAt(i, 1),
                    model.getValueAt(i, 2),
                    model.getValueAt(i, 3),
                    model.getValueAt(i, 4),
                    model.getValueAt(i, 5),
                    model.getValueAt(i, 6)
                });
            }
        }

        reservationListTable.setModel(searchModel);
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
        cardRegistButton = new javax.swing.JRadioButton();
        reqestLabel = new javax.swing.JLabel();
        paymentTypeRegistButton = new javax.swing.JButton();
        checkInTextField = new javax.swing.JTextField();
        searchTextField = new javax.swing.JTextField();
        searchComboBox = new javax.swing.JComboBox<>();
        checkinButton = new javax.swing.JButton();
        checkInLabel = new javax.swing.JLabel();
        ScrollPane = new javax.swing.JScrollPane();
        reservationListTable = new javax.swing.JTable();
        reqestTextField = new javax.swing.JTextField();
        reservationlistLabel = new javax.swing.JLabel();
        paymentTypeLabel = new javax.swing.JLabel();
        onSitePaymentButton = new javax.swing.JRadioButton();
        roomAmountLabel = new javax.swing.JLabel();
        guestRegistButton = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        roomInfoButton = new javax.swing.JButton();
        paymentType = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        paymentGroup.add(cardRegistButton);
        cardRegistButton.setText("카드 등록");
        cardRegistButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cardRegistButtonActionPerformed(evt);
            }
        });

        reqestLabel.setText("요청 사항");

        paymentTypeRegistButton.setText("등록");
        paymentTypeRegistButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentTypeRegistButtonActionPerformed(evt);
            }
        });

        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });

        searchComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "이름", "고유 번호" }));
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

        checkInLabel.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
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

        reservationlistLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reservationlistLabel.setText("예약자 명단");

        paymentTypeLabel.setText("결제 유형");

        paymentGroup.add(onSitePaymentButton);
        onSitePaymentButton.setText("현장 결제");
        onSitePaymentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSitePaymentButtonActionPerformed(evt);
            }
        });

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

        roomInfoButton.setText("객실 정보");
        roomInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomInfoButtonActionPerformed(evt);
            }
        });

        paymentType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "현금", "카드" }));
        paymentType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentTypeActionPerformed(evt);
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
                        .addGap(248, 248, 248)
                        .addComponent(checkInLabel))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(reqestLabel)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(searchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(reqestTextField)
                            .addComponent(reservationlistLabel)
                            .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(guestRegistButton, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(paymentTypeLabel)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(onSitePaymentButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(paymentType, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cardRegistButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(paymentTypeRegistButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(26, 26, 26)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(checkInTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(roomAmountLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(roomInfoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(checkinButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkInLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(reservationlistLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guestRegistButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reqestLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reqestTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(paymentTypeLabel)
                    .addComponent(roomAmountLabel)
                    .addComponent(roomInfoButton))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onSitePaymentButton)
                    .addComponent(paymentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardRegistButton)
                    .addComponent(paymentTypeRegistButton)
                    .addComponent(checkInTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkinButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cardRegistButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardRegistButtonActionPerformed

    }//GEN-LAST:event_cardRegistButtonActionPerformed

    private void paymentTypeRegistButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentTypeRegistButtonActionPerformed
    // CardRegist 프레임 생성 및 표시
    CardRegist cardRegistFrame = new CardRegist();  // CardRegist 클래스의 객체 생성
    cardRegistFrame.setLocationRelativeTo(this);    // 현재 프레임을 기준으로 중앙에 배치
    cardRegistFrame.setVisible(true);               // 프레임 표시
    cardRegistFrame.toFront();                      // 프레임을 최상위로 이동
    }//GEN-LAST:event_paymentTypeRegistButtonActionPerformed

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed

    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void searchComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchComboBoxActionPerformed

    }//GEN-LAST:event_searchComboBoxActionPerformed

    private void checkinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkinButtonActionPerformed
        // 요청 사항을 파일에 저장 (BufferedWriter 사용)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("requests_data.txt", true))) {
            writer.write("고객 요청 사항: " + reqestTextField);  // 요청 사항을 파일에 기록
            writer.newLine();       // 줄바꿈 추가
            JOptionPane.showMessageDialog(null, "체크인 되었습니다.");

            // 요청 사항 입력 필드 초기화
            reqestTextField.setText("");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "저장 중 오류가 발생했습니다!", "오류", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_checkinButtonActionPerformed

    private void reqestTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reqestTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reqestTextFieldActionPerformed

    private void onSitePaymentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSitePaymentButtonActionPerformed

    }//GEN-LAST:event_onSitePaymentButtonActionPerformed

    private void guestRegistButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guestRegistButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_guestRegistButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        comboBoxSelectionChanged();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void roomInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomInfoButtonActionPerformed
        // HotelRoomInfo 인스턴스를 생성하여 새로운 창을 엽니다.
        SwingUtilities.invokeLater(() -> {
            HotelRoomInfo hotelRoomInfo = new HotelRoomInfo(); // HotelRoomInfo 창을 띄웁니다.
        });
    }//GEN-LAST:event_roomInfoButtonActionPerformed

    private void paymentTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paymentTypeActionPerformed

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
            java.util.logging.Logger.getLogger(CheckIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CheckIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CheckIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CheckIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CheckIn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JRadioButton cardRegistButton;
    private javax.swing.JLabel checkInLabel;
    private javax.swing.JTextField checkInTextField;
    private javax.swing.JButton checkinButton;
    private javax.swing.JButton guestRegistButton;
    private javax.swing.JRadioButton onSitePaymentButton;
    private javax.swing.ButtonGroup paymentGroup;
    private javax.swing.JComboBox<String> paymentType;
    private javax.swing.JLabel paymentTypeLabel;
    private javax.swing.JButton paymentTypeRegistButton;
    private javax.swing.JLabel reqestLabel;
    private javax.swing.JTextField reqestTextField;
    private javax.swing.JTable reservationListTable;
    private javax.swing.JLabel reservationlistLabel;
    private javax.swing.JLabel roomAmountLabel;
    private javax.swing.JButton roomInfoButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JComboBox<String> searchComboBox;
    private javax.swing.JTextField searchTextField;
    // End of variables declaration//GEN-END:variables


}
