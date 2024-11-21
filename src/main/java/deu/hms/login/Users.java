/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.login;

/**
 *
 * @author yunhe
 */
public class Users {
    private String id;
    private String password;
    private String name;
    private String role;

    public Users(String id, String password, String name, String role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    // getter 메소드들
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    // 사용자 정보를 문자열로 반환
    @Override
    public String toString() {
        return "User{id='" + id + "', password='" + password + "', name='" + name + "', role='" + role + "'}";
    }
}
