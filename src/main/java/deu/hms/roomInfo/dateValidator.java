/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomInfo;

import javax.swing.*;
import java.time.LocalDate;
import com.toedter.calendar.JDateChooser;

/**
 *
 * @author Jimin
 */
class dateValidator {
    public void validateCheckOutDate(JDateChooser checkInDateChooser, JDateChooser checkOutDateChooser, JPanel roomPanel) {
        LocalDate checkInDate = getLocalDate(checkInDateChooser.getDate());
        LocalDate checkOutDate = getLocalDate(checkOutDateChooser.getDate());

        if (checkInDate != null && checkOutDate != null) {
            if (!checkOutDate.isAfter(checkInDate)) {
                JOptionPane.showMessageDialog(roomPanel,
                        "체크아웃 날짜는 체크인 날짜보다 나중이어야 합니다.",
                        "날짜 선택 오류", JOptionPane.WARNING_MESSAGE);
                checkOutDateChooser.setDate(java.sql.Date.valueOf(checkInDate.plusDays(1)));
            }
        }
    }

    private LocalDate getLocalDate(java.util.Date date) {
        return date == null ? null : date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }
}
