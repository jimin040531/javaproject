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

/**
 *
 * @author adsd3
 */


public class Registration extends JFrame {
    
    private reservationFrame reservationFrame;
    private  String cardRegistEered = "카드등록";
    private  String cardNotRegistEered  = "카드미등록";
    private static Registration instance; // Singleton 인스턴스
    private JTable mainTable; // Reservation 테이블과 연결
    private DefaultTableModel tableModel;
    private static int uniqueNumber = 1;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1); //타이머 
    private ReservationData reservationData;

    public ReservationData getReservationData() {
        return new ReservationData(
            String.valueOf(uniqueNumber),
            textName.getText(),
            textAddress.getText(),
            textPhoneNumber.getText(),
            textCheckInDate.getText(),
            textCheckOutDate.getText(),
            textRoomNumber.getText(),
            textGuestCount.getText(),
            onSitePaymentButton.isSelected() ? "현장결제" : "카드결제",
            thisWeek.isSelected() ? "평일" : "주말",
            Money.getText(),
            labelCardStatus.isVisible() ? "카드등록" : "카드미등록"
        );
    }
        
    public void setRoomSelection(boolean isWeekday) {
        if (isWeekday) {
            thisWeek.setSelected(true);
        } else {
            weekend.setSelected(true);
        }
    }
public void setRegistrationData(ReservationData data) {
    this.reservationData = data;

    textName.setText(data.getName());
    textAddress.setText(data.getAddress());
    textPhoneNumber.setText(data.getPhoneNumber());
    textCheckInDate.setText(data.getCheckInDate());
    textCheckOutDate.setText(data.getCheckOutDate());
    textRoomNumber.setText(data.getRoomNumber());
    textGuestCount.setText(data.getGuestCount());
    Money.setText(data.getStayCost());

    if (data.getPaymentMethod().equals("현장결제")) {
        onSitePaymentButton.setSelected(true);
    } else {
        cardRegistButton.setSelected(true);
    }

    if (data.getRoomSelection().equals("평일")) {
        thisWeek.setSelected(true);
    } else {
        weekend.setSelected(true);
    }
}

    
    
    public static Registration getInstance(JTable table) {
        if (instance == null) {
            instance = new Registration(table);
        }
        return instance;
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
  private int addOrUpdateRow(DefaultTableModel model) {
    // Registration 클래스의 각 텍스트 필드로부터 데이터를 가져옴
    String name = textName.getText();
    String address = textAddress.getText();
    String phoneNumber = textPhoneNumber.getText();
    String checkInDate = textCheckInDate.getText();
    String checkOutDate = textCheckOutDate.getText();
    String roomNumber = textRoomNumber.getText();
    String count = textGuestCount.getText();
    String stayCost = Money.getText(); // 금액 필드
    String paymentMethod = onSitePaymentButton.isSelected() ? "현장결제" : "카드결제";
    String roomSelection = thisWeek.isSelected() ? "평일" : "주말";
    String cardStatus = labelCardStatus.isVisible() ? "카드등록" : "카드미등록";

    // 새로운 행 데이터를 생성
    Object[] rowData = {uniqueNumber, name, address, phoneNumber, checkInDate, checkOutDate,
            roomNumber, count, stayCost, paymentMethod, roomSelection, cardStatus};

    // 기존의 빈 행 찾기 또는 새로운 행 추가
    for (int i = 0; i < model.getRowCount(); i++) {
        if (model.getValueAt(i, 0) == null || model.getValueAt(i, 0).toString().trim().isEmpty()) {
            // 빈 행이 있으면 해당 위치에 데이터를 삽입
            for (int j = 0; j < rowData.length; j++) {
                model.setValueAt(rowData[j], i, j);
            }
            return i; // 수정된 행의 인덱스 반환
        }
    }

    // 빈 행이 없다면 새로운 행 추가
    model.addRow(rowData);
    return model.getRowCount() - 1; // 새로 추가된 행의 인덱스 반환
}

private static void scheduleStatusUpdateForTest(String checkInDate, int rowIndex, DefaultTableModel model) {
    try {
        LocalDate checkInDay = LocalDate.parse(checkInDate);
        LocalDateTime now = LocalDateTime.now();
        //LocalDateTime targetTime = checkInDay.atTime(18, 0); // 체크인 시간 오후 6시
                LocalDateTime targetTime = LocalDateTime.now().plusSeconds(20);

        long delay = Duration.between(now, targetTime).toMillis();

        if (delay < 0) {
            System.out.println("체크인 날짜가 이미 지났습니다.");
            return;
        }

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            SwingUtilities.invokeLater(() -> {
                // 정확한 행 인덱스를 사용하여 상태 업데이트
                String cardStatus = (String) model.getValueAt(rowIndex, 11);

                if ("카드등록".equals(cardStatus)) {
                    model.setValueAt("예약확정", rowIndex, 11);
                } else {
                    model.setValueAt("예약취소", rowIndex, 11);
                }
            });
            scheduler.shutdown();
        }, delay, TimeUnit.MILLISECONDS);

    } catch (Exception e) {
        System.err.println("체크인 날짜 형식이 잘못되었습니다: " + e.getMessage());
    }
}


private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
    DefaultTableModel model = (DefaultTableModel) reservationFrame.getMainTable().getModel();
    addOrUpdateRow(model); // 공통 메서드 호출


    // 창 숨기기
    this.setVisible(false);
}
public void transferRegistrationToReservation() {
    DefaultTableModel model = (DefaultTableModel) reservationFrame.getMainTable().getModel();
    addOrUpdateRow(model); // 공통 메서드 호출
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
    public Registration(reservationFrame reservationFrame) {
        this.reservationFrame = reservationFrame; // 전달받은 객체를 멤버 변수로 저장
        initComponents(); // UI 초기화

    }

    public Registration() {
        initComponents();

        initRadioButtons();
        this.reservationFrame = reservationFrame.getInstance(); // Singleton 인스턴스 얻기

        initializePlaceholders(); // Placeholder 초기화
        configurePaymentButtonState();
        paymentTypeRegistButton.setEnabled(false);

        //
    }

    private void initRadioButtons() {
        // paymentButtonGroup은 이미 생성되어 있다고 가정
        // JDialog에 있는 onSitePaymentButton과 cardRegistButton을 ButtonGroup에 추가
        paymentButtonGroup.add(onSitePaymentButton);
        paymentButtonGroup.add(cardRegistButton);
    }

    private void configurePaymentButtonState() {
        // cardRegistButton을 선택했을 때 paymentTypeRegistButton 활성화
        cardRegistButton.addActionListener(e -> paymentTypeRegistButton.setEnabled(true));

        // onSitePaymentButton을 선택했을 때 paymentTypeRegistButton 비활성화
        onSitePaymentButton.addActionListener(e -> paymentTypeRegistButton.setEnabled(false));
    }

    // 다른 컴포넌트 초기화 후에 호출되는 메서드에 작성
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


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        cardRegist = new javax.swing.JDialog();
        pwLabel = new javax.swing.JLabel();
        pwTextField = new javax.swing.JTextField();
        cardNumLabel = new javax.swing.JLabel();
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
        paymentButtonGroup = new javax.swing.ButtonGroup();
        weekGroup = new javax.swing.ButtonGroup();
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
        thisWeek = new javax.swing.JRadioButton();
        weekend = new javax.swing.JRadioButton();
        back = new javax.swing.JButton();
        labelReservationStatus = new javax.swing.JLabel();
        labelCardStatus = new javax.swing.JLabel();

        jScrollPane1.setViewportView(jTextPane1);

        pwLabel.setText("비밀번호");

        cardNumLabel.setText("카드번호");

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

        javax.swing.GroupLayout cardRegistLayout = new javax.swing.GroupLayout(cardRegist.getContentPane());
        cardRegist.getContentPane().setLayout(cardRegistLayout);
        cardRegistLayout.setHorizontalGroup(
            cardRegistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardRegistLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cardRegistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(cardRegistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(cardRegistLayout.createSequentialGroup()
                            .addComponent(cancleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(registButton, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(cardRegistLayout.createSequentialGroup()
                            .addGroup(cardRegistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(cardNumLabel)
                                .addComponent(expirationDateLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(cardRegistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(monthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addComponent(cardNumTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(cardRegistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(cardRegistLayout.createSequentialGroup()
                                    .addComponent(Label1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cardNumTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(cardRegistLayout.createSequentialGroup()
                                    .addComponent(slashLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(cardRegistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, cardRegistLayout.createSequentialGroup()
                                    .addComponent(cvcLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cvcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(cardRegistLayout.createSequentialGroup()
                                    .addComponent(Label2)
                                    .addGap(7, 7, 7)
                                    .addComponent(cardNumTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(Label3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cardNumTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(cardRegistLayout.createSequentialGroup()
                        .addComponent(pwLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pwTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cardRegistLayout.setVerticalGroup(
            cardRegistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(cardRegistLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(cardRegistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cardNumTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardNumTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardNumTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardNumTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label1)
                    .addComponent(Label2)
                    .addComponent(Label3)
                    .addComponent(cardNumLabel))
                .addGap(18, 18, 18)
                .addGroup(cardRegistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(expirationDateLabel)
                    .addComponent(monthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(slashLabel)
                    .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvcLabel)
                    .addComponent(cvcTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(cardRegistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pwLabel)
                    .addComponent(pwTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(cardRegistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancleButton)
                    .addComponent(registButton))
                .addContainerGap())
        );

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

        weekGroup.add(thisWeek);
        thisWeek.setText("평일");
        thisWeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thisWeekActionPerformed(evt);
            }
        });

        weekGroup.add(weekend);
        weekend.setText("주말");

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
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(thisWeek)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(weekend))))
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
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(thisWeek)
                                .addComponent(weekend))
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
        cardRegist.setSize(295, 220);  // 다이얼로그 크기 설정
        cardRegist.setLocationRelativeTo(this);  // 부모 컴포넌트를 기준으로 중앙에 배치  
        cardRegist.setTitle("카드 등록");  // 다이얼로그 제목 설정 
        cardRegist.setModal(true);
        cardRegist.setVisible(true);  // 다이얼로그 표시
        cardRegist.toFront();
                         

// 카드 등록 완료 후 라벨 업데이트

    }//GEN-LAST:event_paymentTypeRegistButtonActionPerformed

    private void textNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textNameActionPerformed

    private void registButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registButtonActionPerformed
        // 입력 필드에서 값 가져오기
        String cardNum1 = cardNumTextField1.getText().trim(); // 카드 번호 첫 4자리
        String cardNum2 = cardNumTextField2.getText().trim(); // 카드 번호 두 번째 4자리
        String cardNum3 = cardNumTextField3.getText().trim(); // 카드 번호 세 번째 4자리
        String cardNum4 = cardNumTextField4.getText().trim(); // 카드 번호 네 번째 4자리
        String month = monthTextField.getText().trim(); // 유효기간 월
        String year = yearTextField.getText().trim(); // 유효기간 연도
        String pw = pwTextField.getText().trim(); // 카드 비밀번호 (앞 2자리만 입력받음)
        String cvc = cvcTextField.getText().trim(); // CVC 번호

        // 입력값 검증
        if (cardNum1.isEmpty() || cardNum2.isEmpty() || cardNum3.isEmpty() || cardNum4.isEmpty()
                || month.isEmpty() || year.isEmpty() || pw.isEmpty() || cvc.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(cardRegist, "모든 필드를 입력하세요!", "오류", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!cardNum1.matches("\\d{4}") || !cardNum2.matches("\\d{4}")
                || !cardNum3.matches("\\d{4}") || !cardNum4.matches("\\d{4}")) {
            javax.swing.JOptionPane.showMessageDialog(cardRegist, "카드 번호는 각 4자리 숫자로 입력하세요!", "오류", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!month.matches("\\d{2}") || !year.matches("\\d{2}") || Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12) {
            javax.swing.JOptionPane.showMessageDialog(cardRegist, "유효기간을 올바르게 입력하세요! (MM/YY)", "오류", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!pw.matches("\\d{2}")) { // 비밀번호는 2자리 숫자로 제한
            javax.swing.JOptionPane.showMessageDialog(cardRegist, "비밀번호는 앞 2자리 숫자로 입력하세요!", "오류", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!cvc.matches("\\d{3}")) {
            javax.swing.JOptionPane.showMessageDialog(cardRegist, "CVC 번호는 3자리 숫자로 입력하세요!", "오류", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 카드 정보 합치기
        String fullCardNumber = cardNum1 + "-" + cardNum2 + "-" + cardNum3 + "-" + cardNum4; // 카드 번호를 하나로 합침
        String expirationDate = month + "/" + year; // 유효기간을 MM/YY 형식으로 저장

        // 카드 정보를 저장 (BufferedWriter 사용)
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("card_data.txt", true))) { // 기존 데이터에 추가
            writer.write("카드 번호: " + fullCardNumber + ", 유효기간: " + expirationDate
                    + ", 비밀번호: " + pw + ", CVC: " + cvc);
            writer.newLine();  // 줄바꿈 추가

            javax.swing.JOptionPane.showMessageDialog(cardRegist, "카드 정보가 성공적으로 저장되었습니다!", "성공", javax.swing.JOptionPane.INFORMATION_MESSAGE);

            // 입력 필드 초기화 및 다이얼로그 닫기
            cardNumTextField1.setText("");
            cardNumTextField2.setText("");
            cardNumTextField3.setText("");
            cardNumTextField4.setText("");
            monthTextField.setText("");
            yearTextField.setText("");
            pwTextField.setText("");
            cvcTextField.setText("");
            cardRegist.setVisible(false);
        } catch (java.io.IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(cardRegist, "저장 중 오류가 발생했습니다!", "오류", javax.swing.JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
            labelCardStatus.setVisible(true);
               

    }//GEN-LAST:event_registButtonActionPerformed

    private void cardNumTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cardNumTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cardNumTextField1ActionPerformed

    private void cancleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancleButtonActionPerformed
        cardRegist.setVisible(false);
    }//GEN-LAST:event_cancleButtonActionPerformed

    private void monthTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monthTextFieldActionPerformed

    private void yearTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_yearTextFieldActionPerformed

    private void thisWeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thisWeekActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_thisWeekActionPerformed

    private void textAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textAddressActionPerformed

    private void reservationsubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reservationsubmitActionPerformed
    DefaultTableModel model = (DefaultTableModel) reservationFrame.getMainTable().getModel();

// ReservationData 객체 생성
ReservationData data = ReservationUtils.createReservationData(
    String.valueOf(uniqueNumber),
    textName, textAddress, textPhoneNumber,
    textCheckInDate, textCheckOutDate,
    textRoomNumber, textGuestCount,
    Money, onSitePaymentButton, thisWeek,
    labelCardStatus
);

// addOrUpdateRow를 호출하고 반환된 행 인덱스 사용
int rowIndex = ReservationUtils.addOrUpdateRow(model, data);

// 상태 업데이트 예약
ReservationUtils.scheduleStatusUpdateForTest(data.getCheckInDate(), rowIndex, model);

// 유니크 번호 증가
uniqueNumber++;

// 예약 완료 메시지 표시
labelReservationStatus.setVisible(true);
    }//GEN-LAST:event_reservationsubmitActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        setVisible(false);

        // 이전에 생성된 reservationFrame을 다시 표시
        if (reservationFrame != null) {
            reservationFrame.setVisible(true); // 기존 예약 화면 보이기
        }

    }//GEN-LAST:event_backActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Label1;
    private javax.swing.JLabel Label2;
    private javax.swing.JLabel Label3;
    private javax.swing.JTextPane Money;
    private javax.swing.JButton back;
    private javax.swing.JButton cancleButton;
    private javax.swing.JLabel cardNumLabel;
    private javax.swing.JTextField cardNumTextField1;
    private javax.swing.JTextField cardNumTextField2;
    private javax.swing.JTextField cardNumTextField3;
    private javax.swing.JTextField cardNumTextField4;
    private javax.swing.JDialog cardRegist;
    private javax.swing.JRadioButton cardRegistButton;
    private javax.swing.JLabel cvcLabel;
    private javax.swing.JTextField cvcTextField;
    private javax.swing.JLabel expirationDateLabel;
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
    private javax.swing.JTextField monthTextField;
    private javax.swing.JLabel name;
    private javax.swing.JRadioButton onSitePaymentButton;
    private javax.swing.ButtonGroup paymentButtonGroup;
    private javax.swing.JButton paymentTypeRegistButton;
    private javax.swing.JLabel pwLabel;
    private javax.swing.JTextField pwTextField;
    private javax.swing.JButton registButton;
    private javax.swing.JButton reservationsubmit;
    private javax.swing.JLabel slashLabel;
    private java.awt.TextField textAddress;
    private java.awt.TextField textCheckInDate;
    private java.awt.TextField textCheckOutDate;
    private java.awt.TextField textGuestCount;
    private java.awt.TextField textName;
    private java.awt.TextField textPhoneNumber;
    private java.awt.TextField textRoomNumber;
    private javax.swing.JRadioButton thisWeek;
    private javax.swing.ButtonGroup weekGroup;
    private javax.swing.JRadioButton weekend;
    private javax.swing.JTextField yearTextField;
    // End of variables declaration//GEN-END:variables
}
