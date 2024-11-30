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

    // 파일에서 데이터 불러오기
    public static List<String[]> loadFromFile() throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(","));
            }
        }
        return data;
    }

    // 파일 내용 전체 초기화 (Optional)
    public static void clearFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // 파일을 비웁니다.
        }
    }
}
