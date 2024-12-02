/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.userManagement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author yunhe
 */
public class UserAddManager {
     public static void addUser(User user, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(String.join(",", user.getId(), user.getPassword(), user.getName(), user.getRole()));
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }
}
