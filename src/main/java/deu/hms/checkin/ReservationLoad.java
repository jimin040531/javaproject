/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.checkin;

import deu.hms.reservation.Reservation;
import javax.swing.JTable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jimin
 */
public class ReservationLoad {
    
    ArrayList<Reservation> reservation = new ArrayList<>();

    // 생성자 - JTable을 인수로 받아서 예약 파일을 읽어옵니다.
    public ReservationLoad(JTable table) {
        readReservationFile();
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for(int i=0; i<reservation.size(); i++) {
            model.addRow(new Object[]{
                reservation.get(i).getUnique(), reservation.get(i).getName(), reservation.get(i).getPhoneNum(), 
                reservation.get(i).getRoomNum(), reservation.get(i).getFee(),
                reservation.get(i).getPaymentType(), reservation.get(i).getPaymentMethod()
            });
        }
    }

    // 예약 파일을 읽어서 JTable에 데이터를 추가하는 메서드
    private void readReservationFile() {
        
        String paths = System.getProperty("user.dir");
        File reservationFile = new File(paths + "/ReservationLoad.txt");
        
        String line;

        // 파일을 읽어들이는 BufferedReader 사용
        try (BufferedReader br = new BufferedReader(new FileReader(reservationFile))) {
            while ((line = br.readLine()) != null) {
                // 각 줄을 구분자를 이용해 나누고 JTable 모델에 추가합니다.
                String[] data = line.split("\t");
                reservation.add(new Reservation(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9], data[10]));
            }
        } catch (IOException e) {
            // 파일 읽기 중 오류가 발생했을 때 스택 트레이스를 출력합니다.
            e.printStackTrace();
        }
    }
}