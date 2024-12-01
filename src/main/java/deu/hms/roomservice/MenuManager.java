/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomservice;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author wjddn
 */
public class MenuManager {
    private DefaultTableModel menuModel;

    
    public MenuManager(DefaultTableModel model) {
        this.menuModel = model;
    }
    
    public void loadMenuFromFile() {
        try {
            java.io.FileReader fr = new java.io.FileReader("메뉴목록.txt");
            java.io.BufferedReader br = new java.io.BufferedReader(fr);
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                menuModel.addRow(data);
            }
            
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    
