/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.userManagement;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;

/**
 *
 * @author yunhe
 */
public class UserManager {
    private UserDataManager dataManager;

    public UserManager(UserDataManager dataManager) {
        this.dataManager = dataManager;
    }

    // 사용자 정보를 수정하는 메서드
    public void updateUser(User oldUser, User updatedUser) throws IOException {
        List<User> users = dataManager.loadUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(oldUser.getId())) {
                users.set(i, updatedUser);  // 기존 사용자 정보를 수정된 정보로 교체
                break;
            }
        }
        dataManager.saveUsers(users);  // 수정된 사용자 정보를 파일에 저장
    }

    // 사용자 정보를 삭제하는 메서드
    public void deleteUser(User userToDelete) throws IOException {
        List<User> users = dataManager.loadUsers();
        users.removeIf(user -> user.getId().equals(userToDelete.getId()));  // 해당 아이디의 사용자 삭제
        dataManager.saveUsers(users);  // 삭제된 사용자 목록을 파일에 저장
    }

    // 사용자 정보를 추가하는 메서드
    public void addUser(User newUser) throws IOException {
        List<User> users = dataManager.loadUsers();
        users.add(newUser);  // 새 사용자 추가
        dataManager.saveUsers(users);  // 추가된 사용자 목록을 파일에 저장
    }

    // 아이디로 사용자를 찾는 메서드
    public User getUserById(String id) throws IOException {
        List<User> users = dataManager.loadUsers();
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;  // 사용자가 존재하지 않으면 null 반환
    }
}
