/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.userManagement;

import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author yunhe
 */
public class UserModifyManager {
    public static void modifyUser(DefaultTableModel tableModel, int rowIndex, User modifiedUser) {
        tableModel.setValueAt(modifiedUser.getId(), rowIndex, 0);
        tableModel.setValueAt(modifiedUser.getPassword(), rowIndex, 1);
        tableModel.setValueAt(modifiedUser.getName(), rowIndex, 2);
        tableModel.setValueAt(modifiedUser.getRole(), rowIndex, 3);

        List<String[]> users = UserTableManager.loadUsers("users.txt");
        for (String[] user : users) {
            if (user[0].equals(modifiedUser.getId())) {
                user[1] = modifiedUser.getPassword();
                user[2] = modifiedUser.getName();
                user[3] = modifiedUser.getRole();
            }
        }
        UserTableManager.saveUsers(users, "users.txt");
    }
}
