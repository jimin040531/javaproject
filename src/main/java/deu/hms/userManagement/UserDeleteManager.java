/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.userManagement;

import java.io.*;
import java.util.*;

/**
 *
 * @author yunhe
 */
public class UserDeleteManager {
    private static final String FILE_PATH = "users.txt";
    private UserDataManager dataManager;  // 외부에서 전달된 UserDataManager 객체를 저장

    // 생성자에서 전달된 UserDataManager 객체를 그대로 사용
    public UserDeleteManager(UserDataManager dataManager) {
        this.dataManager = dataManager;  // 전달받은 dataManager 사용
    }

    // 사용자 삭제
    public void deleteUser(String userId) throws IOException {
        List<User> users = dataManager.loadUsers();  // UserDataManager에서 사용자 로드
        users.removeIf(user -> user.getId().equals(userId));  // 아이디로 사용자 삭제
        dataManager.saveUsers(users);  // 파일에 변경사항 저장
    }
}
