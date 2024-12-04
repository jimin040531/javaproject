/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomReservation;

/**
 *
 * @author Jimin
 */
public class CardDetails {
    private String cardNumber;
    private String expirationDate;
    private String password;
    private String cvc;

    // 생성자
    public CardDetails(String cardNum1, String cardNum2, String cardNum3, String cardNum4,
                       String month, String year, String pw, String cvc) {
        if (!validateCardNumber(cardNum1, cardNum2, cardNum3, cardNum4)) {
            throw new IllegalArgumentException("카드 번호는 각 4자리 숫자로 입력해야 합니다.");
        }
        if (!validateExpirationDate(month, year)) {
            throw new IllegalArgumentException("유효기간을 올바르게 입력해야 합니다. (MM/YY 형식)");
        }
        if (!validatePassword(pw)) {
            throw new IllegalArgumentException("비밀번호는 앞 2자리 숫자로 입력해야 합니다.");
        }
        if (!validateCVC(cvc)) {
            throw new IllegalArgumentException("CVC 번호는 3자리 숫자로 입력해야 합니다.");
        }

        this.cardNumber = cardNum1 + "-" + cardNum2 + "-" + cardNum3 + "-" + cardNum4;
        this.expirationDate = month + "/" + year;
        this.password = pw;
        this.cvc = cvc;
    }

    // 카드 정보 반환 메서드
    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getPassword() {
        return password;
    }

    public String getCVC() {
        return cvc;
    }

    // 입력값 검증 메서드들
    private boolean validateCardNumber(String cardNum1, String cardNum2, String cardNum3, String cardNum4) {
        return cardNum1.matches("\\d{4}") && cardNum2.matches("\\d{4}")
                && cardNum3.matches("\\d{4}") && cardNum4.matches("\\d{4}");
    }

    private boolean validateExpirationDate(String month, String year) {
        if (!month.matches("\\d{2}") || !year.matches("\\d{2}")) {
            return false;
        }
        int monthInt = Integer.parseInt(month);
        return monthInt >= 1 && monthInt <= 12;
    }

    private boolean validatePassword(String pw) {
        return pw.matches("\\d{2}");
    }

    private boolean validateCVC(String cvc) {
        return cvc.matches("\\d{3}");
    }
}