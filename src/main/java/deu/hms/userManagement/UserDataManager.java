/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.userManagement;

import java.io.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yunhe
 */
public class UserDataManager {
    private static final String FILE_PATH = "users.txt";  // 사용자 정보 파일 경로

    // 사용자 데이터를 파일에서 읽어오는 메서드
    public List<User> loadUsers() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    users.add(new User(data[0], data[1], data[2], data[3]));
                }
            }
        }
        return users;
    }

    // 사용자 데이터를 파일에 저장하는 메서드
    public void saveUsers(List<User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
        }
    }
}
