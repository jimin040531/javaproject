/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.login;

import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author yunhe
 */
public class UserAuthentication {
    private Users users;

    public UserAuthentication() {
        users = new Users();
        users.loadUsersFromFile(); // users.txt 파일에서 사용자 데이터 로드
    }

    // 사용자 인증 메서드
    public boolean authenticate(String userId, String password) {
        User user = users.findUserById(userId);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    // 인증된 사용자의 역할을 반환
    public String getUserRole(String userId) {
        User user = users.findUserById(userId);
        return (user != null) ? user.getRole() : null;
    }

    // 인증된 사용자의 이름을 반환
    public String getUserName(String userId) {
        User user = users.findUserById(userId);
        return (user != null) ? user.getName() : null;
    }
}
