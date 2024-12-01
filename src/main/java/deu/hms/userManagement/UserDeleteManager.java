/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.userManagement;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunhe
 */
public class UserDeleteManager {
    public static void deleteUser(DefaultTableModel tableModel, int rowIndex) {
        String userId = tableModel.getValueAt(rowIndex, 0).toString();
        tableModel.removeRow(rowIndex);

        List<String[]> users = UserTableManager.loadUsers("users.txt");
        users.removeIf(user -> user[0].equals(userId));
        UserTableManager.saveUsers(users, "users.txt");
    }
}
