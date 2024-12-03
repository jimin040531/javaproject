/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomservice;

import java.util.Calendar;
import javax.swing.JSpinner;

public class TimeManager {

    private Calendar calendar;

    // 현재 날짜/시간으로 초기화하는 메소드
    public void initCurrentDateTime(JSpinner jSpinner1, JSpinner jSpinner2, JSpinner jSpinner3, JSpinner jSpinner4, JSpinner jSpinner5) {
        calendar = Calendar.getInstance();
        
        // 현재 날짜/시간 정보 가져오기
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        
        // 스피너 모델 설정
        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(year, 2000, 3000, 1));
        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(month, 1, 12, 1));
        jSpinner3.setModel(new javax.swing.SpinnerNumberModel(day, 1, 31, 1));
        jSpinner4.setModel(new javax.swing.SpinnerNumberModel(hour, 0, 23, 1));
        jSpinner5.setModel(new javax.swing.SpinnerNumberModel(minute, 0, 59, 1));
        
        // 스피너 에디터 설정
        javax.swing.JSpinner.NumberEditor yearEditor = new javax.swing.JSpinner.NumberEditor(jSpinner1, "#");
        jSpinner1.setEditor(yearEditor);
    } 
}
