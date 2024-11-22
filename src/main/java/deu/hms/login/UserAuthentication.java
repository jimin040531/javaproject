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
        this.users = new Users();  // Users 객체 초기화
        this.users.loadUsersFromFile();  // 파일에서 사용자 목록 불러오기
    }

    // 사용자 인증 메서드
    public boolean authenticate(String userId, String password) {
        // 사용자 정보를 찾기
        User user = users.findUserById(userId);
        if (user != null) {
            // 사용자 ID가 존재하면 비밀번호 확인
            return user.getPassword().equals(password);
        }
        return false;  // 사용자 ID가 없으면 인증 실패
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
