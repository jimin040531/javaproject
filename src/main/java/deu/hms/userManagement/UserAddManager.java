/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.userManagement;

import java.io.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yunhe
 */
public class UserAddManager {
    // 사용자 정보 추가 메소드 (파일 및 JTable)
    public static void addUser(User newUser, DefaultTableModel tableModel) {
        // users.txt 파일에 사용자 정보 추가
        addUserToFile(newUser);
        // JTable에 사용자 정보 추가
        addUserToTable(newUser, tableModel);
    }

    // 파일에 사용자 추가
    private static void addUserToFile(User newUser) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(newUser.getId() + "," + newUser.getPassword() + "," +
                         newUser.getName() + "," + newUser.getRole());
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "사용자 정보를 파일에 저장하는 중 오류가 발생했습니다.");
        }
    }

    // JTable에 사용자 추가
    private static void addUserToTable(User newUser, DefaultTableModel tableModel) {
        tableModel.addRow(new Object[]{newUser.getId(), newUser.getName(),
                                       newUser.getPassword(), newUser.getRole()});
    }
}
