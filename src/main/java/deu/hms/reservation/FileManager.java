/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.reservation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author adsd3
 */
public class FileManager {

    private static final String FILE_PATH = "Reservation.txt";

    // 파일에 데이터 저장
    public static void saveToFile(String data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(data);
            writer.newLine();
        }
    }

    // 파일에서 데이터를 불러오고 ReservationData 객체 리스트로 변환
    public static List<ReservationData> loadFromFile() throws IOException {
        List<ReservationData> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                ReservationData reservation = new ReservationData(
                    Integer.parseInt(fields[0]),  // 고유번호
                    fields[1],                   // 이름
                    fields[2],                   // 주소
                    fields[3],                   // 전화번호
                    fields[4],                   // 체크인 날짜
                    fields[5],                   // 체크아웃 날짜
                    fields[6],                   // 방번호
                    fields[7],                   // 인원수
                    fields[8],                   // 금액
                    fields[9],                   // 결제수단
                    fields[10],                  // 평일/주말
                    fields[11]                   // 카드 상태
                );
                data.add(reservation);
            }
        }
        return data;
    }

    // 파일에서 데이터를 불러오고 List<String[]> 형태로 반환
    public static List<String[]> readFile(String fileName) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(","));
            }
        }
        return data;
    }
}