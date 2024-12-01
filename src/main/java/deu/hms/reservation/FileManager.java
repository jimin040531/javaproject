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
                    fields[10], fields[11]
                );
                dataList.add(data);
            }
        }

        return dataList;
    }

   // FileManager 클래스에 추가 txt파일 행 삭제기능 
public static void deleteFromFile(String uniqueNumber, String filePath) throws IOException {
   File inputFile = new File(filePath); // filePath를 사용
    File tempFile = new File("temp_" + filePath);

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

        String line;
        while ((line = reader.readLine()) != null) {
            // uniqueNumber로 시작하지 않는 행만 파일에 기록
            if (!line.startsWith(uniqueNumber + ",")) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    if (!inputFile.delete()) {
        throw new IOException("원본 파일을 삭제할 수 없습니다.");
    }
    if (!tempFile.renameTo(inputFile)) {
        throw new IOException("임시 파일을 원본 파일로 바꿀 수 없습니다.");
    }
}

//txt파일 수정하는 기능
public static void updateInFile(ReservationData newData, String filePath) throws IOException {
    File inputFile = new File(filePath);
    File tempFile = new File("temp_" + filePath);

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

        String line;
        while ((line = reader.readLine()) != null) {
            // 기존 데이터를 대체
            if (line.startsWith(newData.getUniqueNumber() + ",")) {
                writer.write(newData.toCSV()); // 새 데이터로 대체
            } else {
                writer.write(line); // 기존 데이터 유지
            }
            writer.newLine();
        }
    }

    // 원본 파일 교체
    if (!inputFile.delete()) {
        throw new IOException("원본 파일을 삭제할 수 없습니다.");
    }
    if (!tempFile.renameTo(inputFile)) {
        throw new IOException("임시 파일을 원본 파일로 바꿀 수 없습니다.");
    }
}

}
