/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.userManagement;

import java.io.*;
import java.util.List;

/**
 *
 * @author yunhe
 */
public class UserAddManager {
    public static void addUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(user.getId() + ", " + user.getPassword() + ", " + user.getName() + ", " + user.getRole());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
