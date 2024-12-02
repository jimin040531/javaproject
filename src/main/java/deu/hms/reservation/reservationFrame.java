/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package deu.hms.reservation;

import deu.hms.report.reportFrame;
import deu.hms.roomservice.roomserviceFrame;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.List; 
import java.io.IOException;



/**
 * d
 *
 * @author adsd3
 */
public class reservationFrame extends javax.swing.JDialog {

    private DefaultTableModel tableModel;
    private JTable dataTable;
    private javax.swing.JTable reservationTable;
    private Registration registrationFrame;
private JLabel cardStatusLabel;  // 카드 등록 상태
    private JLabel reservationStatusLabel;  // 예약 상태
    private JLabel autoPaymentTimeLabel;  // 자동 결제 시간
private int editingRow = -1; // 수정 중인 행의 인덱스 (-1은 수정 중이 아님)

    
    
    public JTable getMainTable() {
        return mainTable;
    }

    /**
     * Creates new form reservationFrame
     */
    public DefaultTableModel getReservationTableModel() {
        return tableModel;
    }

    private void openRegistrationFormButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Registration registrationForm = new Registration(this);  // 현재의 reservationFrame 객체를 전달
        registrationForm.setVisible(true);
    }

    public reservationFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        initComponents();
        {

        }
    }//
private void initializeTableFromFile() {
     try {
        TableManager tableManager = new TableManager((DefaultTableModel) mainTable.getModel());

        // 파일에서 데이터를 읽음
        List<String[]> fileData = tableManager.readFile("Reservation.txt");

        // 데이터를 테이블에 로드
        tableManager.loadTableData(fileData);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "파일을 불러오는 중 오류가 발생했습니다: " + e.getMessage());
    }
}
    // 기본 생성자 추가
    public reservationFrame() {
    // 테이블 초기화
    mainTable = new JTable();

    // 테이블 모델 설정
    mainTable.setModel(new DefaultTableModel(
        new Object[][]{},
          new String[]{
        "고유번호", "이름", "주소", "전화번호", "예상 체크인 날짜", "예상 체크아웃 날짜",
        "방번호", "인원수", "결제수단", "금액", "상태"
    }
    ));
    initComponents();

    // 기타 초기화 작업
    initializeTableFromFile();
    setTitle("Reservation Frame");
    setSize(600, 400);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    registrationFrame = new Registration(this); // Registration 인스턴스 초기화
    initializeStatusLabels();
}
    
    private void initializeStatusLabels() {
        cardStatusLabel = new JLabel("카드 미등록");

        reservationStatusLabel = new JLabel("예약 미완료");

        // 패널에 추가하는 코드
        this.add(cardStatusLabel);
        this.add(reservationStatusLabel);
    }
   private void scheduleStatusForRow(int rowIndex) {
    DefaultTableModel model = (DefaultTableModel) mainTable.getModel();
    String checkInDate = (String) model.getValueAt(rowIndex, 4); // 체크인 날짜 가져오기

    ReservationStatusScheduler scheduler = new ReservationStatusScheduler();
    scheduler.scheduleStatusUpdate(checkInDate, rowIndex, model);
}
   
    
    // 클래스의 나머지 내용들...
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        goReservation = new javax.swing.JButton();
        goDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainTable = new javax.swing.JTable();
        goEitFom = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        goReservation.setText("등록");
        goReservation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goReservationActionPerformed(evt);
            }
        });

        goDelete.setText("삭제");
        goDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goDeleteActionPerformed(evt);
            }
        });

        mainTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "고유번호", "이름", "주소", "전화번호", "예상 체크인 날짜", "예상 체크아웃 날짜", "방번호", "인원수", "금액", "결제수단", "상태"
            }
        ));
        jScrollPane1.setViewportView(mainTable);

        goEitFom.setText("수정");
        goEitFom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goEitFomActionPerformed(evt);
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
                        .addComponent(goEitFom, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(goDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(goReservation, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(goDelete)
                    .addComponent(goEitFom))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(goReservation)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void goReservationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goReservationActionPerformed
        // 현재 reservationFrame 숨기기
    this.setVisible(false);
        Registration registrationFrame = new Registration(this);
    registrationFrame.setSize(500, 450);
    registrationFrame.setLocationRelativeTo(this);
    registrationFrame.setTitle("정보등록");
    registrationFrame.setVisible(true);
    registrationFrame.setLocationRelativeTo(this);  // 부모 컴포넌트를 기준으로 중앙에 배치  

    }//GEN-LAST:event_goReservationActionPerformed

    private void goEitFomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goEitFomActionPerformed
     
    int selectedRow = mainTable.getSelectedRow(); // 선택된 행 가져오기
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "수정할 행을 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 수정 중인 행 인덱스 설정
    editingRow = selectedRow;

    // 수정 중인 데이터 가져오기 (null 체크 추가)
    String name = getCellValue(mainTable, selectedRow, 1, "이름 없음");
    String address = getCellValue(mainTable, selectedRow, 2, "주소 없음");
    String phoneNumber = getCellValue(mainTable, selectedRow, 3, "전화번호 없음");
    String checkInDate = getCellValue(mainTable, selectedRow, 4, "0000-00-00");
    String checkOutDate = getCellValue(mainTable, selectedRow, 5, "0000-00-00");
    String roomNumber = getCellValue(mainTable, selectedRow, 6, "0");
    String guestCount = getCellValue(mainTable, selectedRow, 7, "0");
    String stayCost = getCellValue(mainTable, selectedRow, 8, "0");
    String paymentMethod = getCellValue(mainTable, selectedRow, 9, "현장결제");
    String roomSelection = getCellValue(mainTable, selectedRow, 10, "선택 없음");

    // Registration 화면으로 데이터 전달
    registrationFrame.setRegistrationData(name, address, phoneNumber, checkInDate, checkOutDate, 
                                          roomNumber, guestCount, paymentMethod, roomSelection, stayCost);
    registrationFrame.setEditingRow(editingRow); // 수정 행 인덱스 전달
    registrationFrame.setVisible(true); // Registration 화면 표시
    registrationFrame.setSize(500, 450); // 창 크기 설정
    registrationFrame.setLocationRelativeTo(this);  // 부모 컴포넌트를 기준으로 중앙에 배치  
}

// 셀 값 가져오기 메서드
private String getCellValue(javax.swing.JTable table, int row, int column, String defaultValue) {
    Object value = table.getValueAt(row, column);
    return value != null ? value.toString() : defaultValue;

   
    }//GEN-LAST:event_goEitFomActionPerformed

    private void goDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goDeleteActionPerformed
     
 int selectedRow = mainTable.getSelectedRow();

    // 선택된 행이 있는지 확인
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "삭제할 행을 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // 고유 번호를 가져옴 (첫 번째 열)
    String uniqueNumber = mainTable.getValueAt(selectedRow, 0).toString();

    // 파일에서 해당 행 삭제
    try {
        FileManager.deleteFromFile(uniqueNumber, "Reservation.txt");
        JOptionPane.showMessageDialog(this, "선택한 행이 삭제되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "파일에서 데이터를 삭제하는 중 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 테이블에서 해당 행 삭제
    DefaultTableModel model = (DefaultTableModel) mainTable.getModel();
    model.removeRow(selectedRow);
    }//GEN-LAST:event_goDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(roomserviceFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(roomserviceFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(roomserviceFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(roomserviceFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
          LocalDateTime now = LocalDateTime.now();
    String testCheckInDate = now.toLocalDate().toString(); // 오늘 날짜를 체크인 날짜로 설정
    int testRowIndex = 0; // 테스트할 테이블 행 인덱스


    // 테이블 모델 생성 및 데이터 삽입
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                reservationFrame frame = new reservationFrame();
                
            // 테이블 초기화 (파일 로드)
            frame.initializeTableFromFile();

            frame.setVisible(true); // 메인 화면 표시
               frame.setSize(850, 250);
            }
        });
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton goDelete;
    private javax.swing.JButton goEitFom;
    private javax.swing.JButton goReservation;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable mainTable;
    // End of variables declaration//GEN-END:variables
}
