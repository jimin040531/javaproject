package deu.hms.checkin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    // 파일에서 모든 내용을 읽어오는 메서드
    public static List<String> loadFromFile(String fileName) {
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
        } catch (IOException e) {
            System.err.println("파일 읽기 오류: " + fileName + " - " + e.getMessage());
        }
        return fileContent;
    }

    // 파일에 내용을 저장하는 메서드
    public static void writeToFile(String fileName, List<String> content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : content) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("파일 쓰기 오류: " + fileName + " - " + e.getMessage());
        }
    }

    // 파일에 내용을 추가하는 메서드
    public static void appendToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("파일 추가 쓰기 오류: " + fileName + " - " + e.getMessage());
        }
    }
}
