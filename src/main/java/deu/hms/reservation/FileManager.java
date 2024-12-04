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
        List<ReservationData> dataList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                ReservationData data = new ReservationData(
                    fields[0], fields[1], fields[2], fields[3], fields[4],
                    fields[5], fields[6], fields[7], fields[8], fields[9],
                    fields[10]
                );
                dataList.add(data);
            }
        }

        return dataList;
    }

    // txt 파일 행 삭제 기능
    public static void deleteFromFile(String uniqueNumber, String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(uniqueNumber + ",")) {
                    lines.add(line);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    // txt 파일 수정 기능
    public static void updateInFile(ReservationData newData, String filePath) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File("temp_" + filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(newData.getUniqueNumber() + ",")) {
                    writer.write(newData.toCSV()); // 수정된 데이터 쓰기
                } else {
                    writer.write(line); // 기존 데이터 유지
                }
                writer.newLine();
            }
        }
    

   