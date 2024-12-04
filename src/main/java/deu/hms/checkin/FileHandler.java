/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.checkin;

import deu.hms.reservation.ReservationData;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jimin
 */
class FileHandler {

    // 파일에 데이터를 추가하는 메서드
    public static void appendToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);
            writer.newLine();
            System.out.println("파일에 데이터 추가 완료: " + content); // 로그 추가
        } catch (IOException e) {
            System.err.println("파일에 데이터를 추가하는 중 오류 발생: " + e.getMessage());
        }
    }

    // 파일에서 예약 정보를 불러오는 메서드
    public static List<ReservationData> loadFromFile(String fileName) {
        List<ReservationData> reservationList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 11) {
                    ReservationData reservation = new ReservationData(
                        data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(),
                        data[4].trim(), data[5].trim(), data[6].trim(), data[7].trim(),
                        data[8].trim(), data[9].trim(), data[10].trim()
                    );
                    reservationList.add(reservation);
                    System.out.println("로드된 예약자: " + reservation.getName()); // 로그 추가
                } else {
                    System.err.println("잘못된 데이터 형식: " + line);
                }
            }
            System.out.println("파일에서 로드된 예약자 수: " + reservationList.size()); // 로그 추가
        } catch (IOException e) {
            System.err.println("파일을 불러오는 중 오류 발생: " + e.getMessage());
        }
        return reservationList;
    }

    // 예약 정보를 파일에 쓰는 메서드
    public static void writeToFile(String fileName, List<ReservationData> reservations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (ReservationData reservation : reservations) {
                writer.write(reservation.toCSV());
                writer.newLine();
                System.out.println("파일에 데이터 저장: " + reservation.getName()); // 로그 추가
            }
        } catch (IOException e) {
            System.err.println("파일 쓰기 중 오류 발생: " + e.getMessage());
        }
    }
}