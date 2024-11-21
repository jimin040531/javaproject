/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.login;

import java.io.*;
import java.util.*;

/**
 *
 * @author yunhe
 */
public class UserAuthentication {
     // users.txt 파일에서 사용자 정보를 읽어 User 객체를 생성하고 인증을 수행하는 메소드
    public static Users authenticateUser(String userId, String password) {
        try {
            File file = new File("users.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] userInfo = line.split(",\\s*");
                String storedId = userInfo[0].trim();
                String storedPassword = userInfo[1].trim();
                String storedName = userInfo[2].trim();
                String storedRole = userInfo[3].trim(); // manager 또는 employee

                // 아이디와 비밀번호 비교
                if (userId.equals(storedId) && password.equals(storedPassword)) {
                    return new Users(storedId, storedPassword, storedName, storedRole);  // 인증된 사용자 객체 반환
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;  // 인증 실패 시 null 반환
    }
}
