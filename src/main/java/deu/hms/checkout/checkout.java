package deu.hms.checkout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Vector;

public class checkout extends JFrame {

    private JTable reservationListTable;
    private JTextField searchTextField;
    private JTextField roomPriceField;
    private JTextArea feedbackTextArea;
    private JRadioButton onsitePaymentRadio;
    private JRadioButton cardRegistrationRadio;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private ButtonGroup paymentGroup;

    private final String DATA_FILE = "reservation_data.dat"; // 데이터 파일 이름

    public checkout() {//
        setTitle("체크아웃");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 예약자 명단 테이블
        String[] columnNames = {"고유 번호", "이름", "전화 번호", "방 번호", "객실 금액", "결제 수단", "상태", "총 금액"};
        tableModel = new DefaultTableModel(columnNames, 0);
        // 예약자 데이터 로드
        loadReservationData();

        reservationListTable = new JTable(tableModel);

        // 예약자 명단 문구 추가
        JLabel reservationListLabel = new JLabel("예약자 명단", SwingConstants.CENTER);
        reservationListLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        // 검색 패널 코드
        JPanel searchPanel = new JPanel();
        String[] searchOptions = {"성이름", "고유번호", "객실 번호"}; // "객실 번호" 추가
        JComboBox<String> searchComboBox = new JComboBox<>(searchOptions);

        searchTextField = new JTextField(15);
        JButton searchButton = new JButton("검색");
        JButton refreshButton = new JButton("새로고침");
        refreshButton.addActionListener(e -> refreshTable());
        searchButton.addActionListener(e -> performSearch(searchComboBox.getSelectedItem().toString()));

        searchPanel.add(searchComboBox);
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);

        // 수정 버튼
        JButton editButton = new JButton("수정");
        editButton.addActionListener(e -> editReservation());

        // 추가 버튼
        JButton addButton = new JButton("추가");
        addButton.addActionListener(e -> addReservation());

        // FeedBack 패널
        JPanel feedbackPanel = new JPanel(new BorderLayout());
        JLabel feedbackLabel = new JLabel("FeedBack:");
        feedbackTextArea = new JTextArea(3, 20);
        feedbackPanel.add(feedbackLabel, BorderLayout.NORTH);
        feedbackPanel.add(new JScrollPane(feedbackTextArea), BorderLayout.CENTER);

        // 결제 유형 패널
        JPanel paymentPanel = new JPanel();
        paymentGroup = new ButtonGroup();
        onsitePaymentRadio = new JRadioButton("현장 결제");
        cardRegistrationRadio = new JRadioButton("카드 결제");
        paymentGroup.add(onsitePaymentRadio);
        paymentGroup.add(cardRegistrationRadio);

        // 예약자 선택 시 자동 결제 유형 선택
        reservationListTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // 선택이 완료되었을 때만 실행
                updateCheckoutDetails(); // 선택된 예약자의 총 금액 및 결제 수단 표시
            }
        });
        JButton checkoutButton = new JButton("체크아웃");
        checkoutButton.addActionListener(e -> performCheckout());

        roomPriceField = new JTextField(10);
        roomPriceField.setEditable(false);
        paymentPanel.add(onsitePaymentRadio);
        paymentPanel.add(cardRegistrationRadio);
        paymentPanel.add(new JLabel("총 금액:"));
        paymentPanel.add(roomPriceField);
        paymentPanel.add(checkoutButton);

        // 구성
        JScrollPane tableScrollPane = new JScrollPane(reservationListTable);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(editButton);
        buttonPanel.add(addButton);
        bottomPanel.add(buttonPanel, BorderLayout.WEST);
        bottomPanel.add(feedbackPanel, BorderLayout.CENTER);
        bottomPanel.add(paymentPanel, BorderLayout.EAST);

        add(reservationListLabel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // 화면 중앙에 배치
        setVisible(true);

        // 종료 시 데이터 저장
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveReservationData(); // 종료 시 데이터 저장
                System.exit(0);
            }
        });
    }
// 예약자 데이터 저장
// 선택된 예약자의 결제 수단과 총 금액 표시

    private void saveReservationData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Reservation.txt"))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                writer.write(String.join(", ",
                        tableModel.getValueAt(i, 0).toString(),
                        tableModel.getValueAt(i, 1).toString(),
                        tableModel.getValueAt(i, 2).toString(),
                        tableModel.getValueAt(i, 3).toString(),
                        tableModel.getValueAt(i, 4).toString(),
                        tableModel.getValueAt(i, 5).toString(),
                        tableModel.getValueAt(i, 6).toString()));
                writer.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Reservation.txt 저장 중 오류가 발생했습니다!", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCheckoutDetails() {
        int selectedRow = reservationListTable.getSelectedRow();
        if (selectedRow != -1) {
            // 객실 금액 가져오기
            String basePriceString = tableModel.getValueAt(selectedRow, 4).toString(); // "객실 금액" 열은 인덱스 4
            int basePrice = Integer.parseInt(basePriceString.replace(",", ""));

            // 방 번호 가져오기
            String roomNumber = tableModel.getValueAt(selectedRow, 3).toString(); // "방 번호" 열은 인덱스 3

            // 추가 금액 계산
            int additionalAmount = getAdditionalAmountFromFile(roomNumber);

            // 총 금액 계산
            int totalPrice = basePrice + additionalAmount;
            roomPriceField.setText(String.format("%,d", totalPrice)); // 총 금액 표시

            // 결제 수단 선택
            String paymentMethod = tableModel.getValueAt(selectedRow, 5).toString(); // "결제 수단" 열은 인덱스 5
            if (paymentMethod.equals("현장 결제")) {
                onsitePaymentRadio.setSelected(true);
            } else if (paymentMethod.equals("카드 결제")) { // "카드 등록" -> "카드 결제"
                cardRegistrationRadio.setSelected(true);
            }

// selectPaymentMethod 메서드 수정  
            if (paymentMethod.equals("현장 결제")) {
                onsitePaymentRadio.setSelected(true);
            } else if (paymentMethod.equals("카드 결제")) { // "카드 등록" -> "카드 결제"
                cardRegistrationRadio.setSelected(true);
            }
        } else {
            roomPriceField.setText(""); // 선택된 예약자가 없을 경우 초기화
            paymentGroup.clearSelection(); // 라디오 버튼 초기화
        }
    }

    // 총 금액 계산 메서드
    private void calculateroomPrice() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Reservation.txt"))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String id = tableModel.getValueAt(i, 0).toString();
                String name = tableModel.getValueAt(i, 1).toString();
                String phone = tableModel.getValueAt(i, 2).toString();
                String room = tableModel.getValueAt(i, 3).toString();
                String basePrice = tableModel.getValueAt(i, 4).toString(); // 객실 금액
                String paymentMethod = tableModel.getValueAt(i, 5).toString();
                String status = tableModel.getValueAt(i, 6).toString();

                // 총 금액 계산
                int additionalAmount = getAdditionalAmountFromFile(room);
                int totalPrice = Integer.parseInt(basePrice.replace(",", "")) + additionalAmount;

                writer.write(String.join(", ", id, name, phone, room, basePrice, paymentMethod, status, String.valueOf(totalPrice)));
                writer.newLine();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Reservation.txt 저장 중 오류가 발생했습니다!", "오류", JOptionPane.ERROR_MESSAGE);
        }

    }

    private int getAdditionalAmountFromFile(String roomNumber) {
        int additionalAmount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("ServiceList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 쉼표 양쪽 공백 제거 후 데이터 분리
                String[] data = line.split("\\s*,\\s*");
                if (data.length >= 8 && data[4].trim().equals(roomNumber)) { // 방 번호 일치 확인
                    try {
                        int price = Integer.parseInt(data[data.length - 1].trim()); // 마지막 필드의 가격 파싱
                        additionalAmount += price; // 총 금액에 추가
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "ServiceList.txt의 가격 형식이 잘못되었습니다: " + data[data.length - 1], "오류", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "ServiceList.txt 파일을 찾을 수 없습니다!", "정보", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "ServiceList 파일 읽기 중 오류가 발생했습니다!", "오류", JOptionPane.ERROR_MESSAGE);
        }
        return additionalAmount;
    }

    //ServiceList.txt에서 방번호에 해당하는 추가 금액 가져오기
    
    // 예약자 데이터 로드
    private void loadReservationData1() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            Vector<Vector<Object>> data = (Vector<Vector<Object>>) ois.readObject();
            for (Vector<Object> row : data) {
                tableModel.addRow(row.toArray());
            }
        } catch (IOException | ClassNotFoundException e) {
            // 데이터 파일이 없거나 문제가 있을 경우 무시
        }
    }
     
    // 데이터 로드
    private void loadReservationData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Reservation.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length >= 7) { // 데이터가 충분한지 확인
                    String basePrice = data[4];
                    int additionalAmount = getAdditionalAmountFromFile(data[3]);
                    int totalPrice = Integer.parseInt(basePrice.replace(",", "")) + additionalAmount; // 총 금액 계산

                    tableModel.addRow(new Object[]{
                        data[0], // 고유 번호
                        data[1], // 이름
                        data[2], // 전화 번호
                        data[3], // 방 번호
                        basePrice, // 객실 금액
                        data[5], // 결제 수단
                        data[6], // 상태
                        String.format("%,d", totalPrice) // 총 금액
                    });
                }
            }
            JOptionPane.showMessageDialog(this, "예약자 데이터를 성공적으로 불러왔습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "데이터 파일을 찾을 수 없습니다. 새로 생성됩니다.", "정보", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "예약자 데이터를 불러오는 중 오류가 발생했습니다!", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performSearch(String searchType) {
        String searchInput = searchTextField.getText().trim();
        if (searchInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "검색어를 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 검색 결과를 표시할 새로운 모델 생성
        DefaultTableModel filteredModel = new DefaultTableModel(
                new Object[]{"고유 번호", "이름", "전화 번호", "방 번호", "객실 금액", "결제 수단", "상태", "총 금액"}, 0
        );

        // 검색 조건에 따라 필터링
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String value = "";
            if (searchType.equals("성이름")) {
                value = tableModel.getValueAt(i, 1).toString(); // 이름
            } else if (searchType.equals("고유번호")) {
                value = tableModel.getValueAt(i, 0).toString(); // 고유 번호
            } else if (searchType.equals("객실 번호")) {
                value = tableModel.getValueAt(i, 3).toString(); // 방 번호
            }

            if (value.contains(searchInput)) {
                filteredModel.addRow(new Object[]{
                    tableModel.getValueAt(i, 0),
                    tableModel.getValueAt(i, 1),
                    tableModel.getValueAt(i, 2),
                    tableModel.getValueAt(i, 3),
                    tableModel.getValueAt(i, 4),
                    tableModel.getValueAt(i, 5),
                    tableModel.getValueAt(i, 6),
                    tableModel.getValueAt(i, 7)
                });
            }
        }
        // 테이블 모델을 검색 결과로 설정
        reservationListTable.setModel(filteredModel);
    }

// 테이블 새로고침
    private void refreshTable() {
        // 기본 테이블 모델로 다시 설정
        reservationListTable.setModel(tableModel);

        // 테이블 데이터 초기화
        tableModel.setRowCount(0);

        // 데이터를 다시 로드하여 기본 테이블 모델에 추가
        loadReservationData();

        // 새로고침 완료 메시지 (옵션)
        JOptionPane.showMessageDialog(this, "예약자 명단이 새로고침되었습니다.", "새로고침 완료", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editReservation() {
        int selectedRow = reservationListTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "수정할 항목을 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 수정 패널 생성
        JPanel editPanel = new JPanel(new GridLayout(7, 2));
        String[] columnNames = {"고유 번호", "이름", "방 번호"};
        JTextField[] fields = new JTextField[columnNames.length];

        // 기존 데이터 가져오기
        String currentId = tableModel.getValueAt(selectedRow, 0).toString();
        String currentName = tableModel.getValueAt(selectedRow, 1).toString();
        String currentPhone = tableModel.getValueAt(selectedRow, 2).toString();
        String currentRoom = tableModel.getValueAt(selectedRow, 3).toString();
        String currentPrice = tableModel.getValueAt(selectedRow, 4).toString();
        String currentPayment = tableModel.getValueAt(selectedRow, 5).toString();

        // 일반 입력 필드 추가
        for (int i = 0; i < columnNames.length; i++) {
            editPanel.add(new JLabel(columnNames[i] + ":"));
            fields[i] = new JTextField();
            fields[i].setText(i == 0 ? currentId : (i == 1 ? currentName : currentRoom));
            editPanel.add(fields[i]);
        }

        // 전화번호 입력 필드 세분화
        editPanel.add(new JLabel("전화 번호:"));
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField phonePart1 = new JTextField(3);
        JTextField phonePart2 = new JTextField(4);
        JTextField phonePart3 = new JTextField(4);

        // 전화번호를 분리하여 세팅
        String[] phoneParts = currentPhone.split("-");
        phonePart1.setText(phoneParts[0]);
        phonePart2.setText(phoneParts[1]);
        phonePart3.setText(phoneParts[2]);

        phonePanel.add(phonePart1);
        phonePanel.add(new JLabel("-"));
        phonePanel.add(phonePart2);
        phonePanel.add(new JLabel("-"));
        phonePanel.add(phonePart3);
        editPanel.add(phonePanel);

        // 객실 금액 입력 필드 추가
        JTextField roomPriceField = new JTextField();
        roomPriceField.setText(currentPrice.replace(",", ""));
        editPanel.add(new JLabel("객실 금액:"));
        editPanel.add(roomPriceField);

        // 결제 수단 선택
        JComboBox<String> paymentComboBox = new JComboBox<>(new String[]{"현장 결제", "카드 결제"});
        paymentComboBox.setSelectedItem(currentPayment);
        editPanel.add(new JLabel("결제 수단:"));
        editPanel.add(paymentComboBox);

        // 다이얼로그 표시
        int result = JOptionPane.showConfirmDialog(this, editPanel, "예약자 수정", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // 수정 데이터 저장
            tableModel.setValueAt(fields[0].getText().trim(), selectedRow, 0); // 고유 번호
            tableModel.setValueAt(fields[1].getText().trim(), selectedRow, 1); // 이름

            // 전화번호 유효성 검사 및 포맷팅
            String phone1 = phonePart1.getText().trim();
            String phone2 = phonePart2.getText().trim();
            String phone3 = phonePart3.getText().trim();
            if (phone1.length() != 3 || phone2.length() != 4 || phone3.length() != 4
                    || !phone1.matches("\\d{3}") || !phone2.matches("\\d{4}") || !phone3.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(this, "유효한 전화번호를 입력해주세요 (예: 010-1234-5678)", "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }
            tableModel.setValueAt(phone1 + "-" + phone2 + "-" + phone3, selectedRow, 2); // 전화 번호

            // 방 번호
            tableModel.setValueAt(fields[2].getText().trim(), selectedRow, 3);

            // 객실 금액 입력 및 유효성 검사
            String roomPrice = roomPriceField.getText().trim();
            if (!roomPrice.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "객실 금액은 숫자로 입력해야 합니다.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }
            tableModel.setValueAt(String.format("%,d", Integer.parseInt(roomPrice)), selectedRow, 4); // 객실 금액

            // 결제 수단 설정
            tableModel.setValueAt(paymentComboBox.getSelectedItem().toString(), selectedRow, 5);
        }
    }

    /*
    private void addReservation() {
        // 예약자 추가 패널 생성
        JPanel addPanel = new JPanel(new GridLayout(7, 2));
        String[] columnNames = {"고유 번호", "이름", "방 번호"};
        JTextField[] fields = new JTextField[columnNames.length];

        // 일반 입력 필드 추가
        for (int i = 0; i < columnNames.length; i++) {
            addPanel.add(new JLabel(columnNames[i] + ":"));
            fields[i] = new JTextField();
            addPanel.add(fields[i]);
        }

        // 전화번호 입력 필드 세분화
        addPanel.add(new JLabel("전화 번호:"));
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField phonePart1 = new JTextField(3);
        JTextField phonePart2 = new JTextField(4);
        JTextField phonePart3 = new JTextField(4);
        phonePanel.add(phonePart1);
        phonePanel.add(new JLabel("-"));
        phonePanel.add(phonePart2);
        phonePanel.add(new JLabel("-"));
        phonePanel.add(phonePart3);
        addPanel.add(phonePanel);

        // 객실 금액 입력 필드 추가
        JTextField roomPriceField = new JTextField();
        addPanel.add(new JLabel("객실 금액:"));
        addPanel.add(roomPriceField);

        // 결제 수단 선택
        JComboBox<String> paymentComboBox = new JComboBox<>(new String[]{"현장 결제", "카드 결제"});
        addPanel.add(new JLabel("결제 수단:"));
        addPanel.add(paymentComboBox);

        // 다이얼로그 표시
        int result = JOptionPane.showConfirmDialog(this, addPanel, "예약자 추가", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String[] newRow = new String[7];

            // 고유 번호 및 이름
            newRow[0] = fields[0].getText().trim(); // 고유 번호
            newRow[1] = fields[1].getText().trim(); // 이름

            // 전화번호 유효성 검사 및 포맷팅
            String phone1 = phonePart1.getText().trim();
            String phone2 = phonePart2.getText().trim();
            String phone3 = phonePart3.getText().trim();
            if (phone1.length() != 3 || phone2.length() != 4 || phone3.length() != 4
                    || !phone1.matches("\\d{3}") || !phone2.matches("\\d{4}") || !phone3.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(this, "유효한 전화번호를 입력해주세요 (예: 010-1234-5678)", "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }
            newRow[2] = phone1 + "-" + phone2 + "-" + phone3; // 전화 번호

            // 방 번호
            newRow[3] = fields[2].getText().trim();

            // 객실 금액 입력 및 유효성 검사
            String roomPrice = roomPriceField.getText().trim();
            if (!roomPrice.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "객실 금액은 숫자로 입력해야 합니다.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // 쉼표 포함한 금액 표시
            newRow[4] = String.format("%,d", Integer.parseInt(roomPrice));

            // 결제 수단 설정
            newRow[5] = paymentComboBox.getSelectedItem().toString();

            // 상태 기본값
            newRow[6] = "-";

            // 테이블에 추가
            tableModel.addRow(newRow);
        }
    }
     */
    private void addReservation() {
        // 예약자 추가 패널 생성
        JPanel addPanel = new JPanel(new GridLayout(7, 2));
        String[] columnNames = {"고유 번호", "이름", "방 번호"};
        JTextField[] fields = new JTextField[columnNames.length];

        // 일반 입력 필드 추가
        for (int i = 0; i < columnNames.length; i++) {
            addPanel.add(new JLabel(columnNames[i] + ":"));
            fields[i] = new JTextField();
            addPanel.add(fields[i]);
        }

        // 전화번호 입력 필드 세분화
        addPanel.add(new JLabel("전화 번호:"));
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField phonePart1 = new JTextField(3);
        JTextField phonePart2 = new JTextField(4);
        JTextField phonePart3 = new JTextField(4);
        phonePanel.add(phonePart1);
        phonePanel.add(new JLabel("-"));
        phonePanel.add(phonePart2);
        phonePanel.add(new JLabel("-"));
        phonePanel.add(phonePart3);
        addPanel.add(phonePanel);

        // 객실 금액 입력 필드 추가
        JTextField roomPriceField = new JTextField();
        addPanel.add(new JLabel("객실 금액:"));
        addPanel.add(roomPriceField);

        // 결제 수단 선택
        JComboBox<String> paymentComboBox = new JComboBox<>(new String[]{"현장 결제", "카드 결제"});
        addPanel.add(new JLabel("결제 수단:"));
        addPanel.add(paymentComboBox);

        // 다이얼로그 표시
        int result = JOptionPane.showConfirmDialog(this, addPanel, "예약자 추가", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String[] newRow = new String[7];

            // 고유 번호 및 이름
            newRow[0] = fields[0].getText().trim(); // 고유 번호
            newRow[1] = fields[1].getText().trim(); // 이름

            // 전화번호 유효성 검사 및 포맷팅
            String phone1 = phonePart1.getText().trim();
            String phone2 = phonePart2.getText().trim();
            String phone3 = phonePart3.getText().trim();
            if (phone1.length() != 3 || phone2.length() != 4 || phone3.length() != 4
                    || !phone1.matches("\\d{3}") || !phone2.matches("\\d{4}") || !phone3.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(this, "유효한 전화번호를 입력해주세요 (예: 010-1234-5678)", "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }
            newRow[2] = phone1 + "-" + phone2 + "-" + phone3; // 전화 번호

            // 방 번호
            newRow[3] = fields[2].getText().trim();

            // 객실 금액 입력 및 유효성 검사
            String roomPrice = roomPriceField.getText().trim();
            if (!roomPrice.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "객실 금액은 숫자로 입력해야 합니다.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }
            newRow[4] = String.format("%,d", Integer.parseInt(roomPrice)); // 쉼표 포함한 금액 표시

            // 결제 수단 설정
            newRow[5] = paymentComboBox.getSelectedItem().toString();

            // 상태 기본값
            newRow[6] = "-";

            // 테이블에 추가
            tableModel.addRow(newRow);

            // 파일에 저장
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Reservation.txt", true))) {
                writer.write(String.join(", ", newRow));
                writer.newLine();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "예약자를 파일에 저장하는 중 오류가 발생했습니다!", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void selectPaymentMethod() {
        int selectedRow = reservationListTable.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }

        String paymentMethod = tableModel.getValueAt(selectedRow, 5).toString();
        if (paymentMethod.equals("현장 결제")) {
            onsitePaymentRadio.setSelected(true);
        } else if (paymentMethod.equals("카드 결제")) {
            cardRegistrationRadio.setSelected(true);
        }

        String paymentAmount = tableModel.getValueAt(selectedRow, 4).toString();
        roomPriceField.setText(paymentAmount);
    }
// 체크아웃 처리

    private void performCheckout() {
        int selectedRow = reservationListTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "체크아웃할 항목을 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (feedbackTextArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "FeedBack을 입력해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!onsitePaymentRadio.isSelected() && !cardRegistrationRadio.isSelected()) {
            JOptionPane.showMessageDialog(this, "결제 유형을 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 체크아웃 시간이 11시를 넘었는지 확인
        java.time.LocalTime now = java.time.LocalTime.now();
        java.time.LocalTime checkoutLimit = java.time.LocalTime.of(11, 0);

        int additionalLateFee = 0; // 초과 요금
        if (now.isAfter(checkoutLimit)) {
            // 객실 요금을 한 번 더 부과
            String basePriceString = tableModel.getValueAt(selectedRow, 4).toString();
            additionalLateFee = Integer.parseInt(basePriceString.replace(",", ""));
        }

        // 선택된 예약자의 정보 가져오기
        String uniqueId = tableModel.getValueAt(selectedRow, 0).toString();
        String name = tableModel.getValueAt(selectedRow, 1).toString();
        String phoneNumber = tableModel.getValueAt(selectedRow, 2).toString();
        String roomNumber = tableModel.getValueAt(selectedRow, 3).toString();
        String basePrice = tableModel.getValueAt(selectedRow, 4).toString();
        String paymentMethod = tableModel.getValueAt(selectedRow, 5).toString();
        String status = "체크아웃 완료";

        // ServiceList.txt에서 추가 금액 계산
        int additionalAmount = getAdditionalAmountFromFile(roomNumber);
        int roomPrice = Integer.parseInt(basePrice.replace(",", "")) + additionalAmount + additionalLateFee;

        // FeedBack.txt에 이름과 FeedBack 저장
        try (BufferedWriter feedbackWriter = new BufferedWriter(new FileWriter("FeedBack.txt", true))) {
            feedbackWriter.write("이름: " + name + ", FeedBack: " + feedbackTextArea.getText());
            feedbackWriter.newLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "FeedBack 저장 중 오류 발생!", "오류", JOptionPane.ERROR_MESSAGE);
        }

        // resultText.txt에 체크아웃 정보 저장 (중복 방지)
        saveTOresultText(uniqueId, name, phoneNumber, roomNumber, roomPrice, paymentMethod, status);

        // 상태 업데이트 (체크아웃 완료)
        tableModel.setValueAt(status, selectedRow, 6);

        // Reservation.txt에 상태 업데이트
        saveReservationData();

        feedbackTextArea.setText("");
        roomPriceField.setText("");
        JOptionPane.showMessageDialog(this, "체크아웃 완료! 추가 요금: " + String.format("%,d원", additionalLateFee), "성공", JOptionPane.INFORMATION_MESSAGE);
    }

// resultText.txt에 정보 저장 (중복 방지)
    private void saveTOresultText(String id, String name, String phone, String room, int roomPrice, String paymentType, String status) {
        try {
            File resultTextFile = new File("resultText.txt");
            boolean entryExists = false;

            // 기존 데이터 확인 및 수정
            if (resultTextFile.exists()) {
                Vector<String> updatedData = new Vector<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(resultTextFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith(id + ", ")) {
                            // 이미 체크아웃한 경우 기존 정보를 유지
                            updatedData.add(line);
                            entryExists = true;
                        } else {
                            updatedData.add(line);
                        }
                    }
                }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultTextFile))) {
                    for (String data : updatedData) {
                        writer.write(data);
                        writer.newLine();
                    }
                    if (!entryExists) {
                        writer.write(String.join(", ", id, name, phone, room,
                                String.format("%,d", roomPrice), paymentType, status));
                        writer.newLine();
                    }
                }
            } else {
                // 파일이 없는 경우 새로 작성
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultTextFile))) {
                    writer.write(String.join(", ", id, name, phone, room,
                            String.format("%,d", roomPrice), paymentType, status));
                    writer.newLine();
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "resultText.txt 저장 중 오류 발생!", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }
}
