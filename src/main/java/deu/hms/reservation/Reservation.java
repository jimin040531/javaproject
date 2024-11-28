/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.reservation;

/**
 *
 * @author Jinho
 */
public class Reservation {
    private String unique = null;
    private String name = null;
    private String address = null;
    private String phoneNum = null;
    private String exCheckInDate = null;
    private String exCheckOutDate = null;
    private String roomNum = null;
    private String peoples = null;
    private String fee = null;
    private String paymentType = null;
    private String paymentMethod = null;

    public Reservation(String unique, String name, String address, String phoneNum, String exCheckInDate, String exCheckOutDate, String roomNum, String peoples, String fee, String paymentType, String paymentMethod) {
        this.unique = unique;
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.exCheckInDate = exCheckInDate;
        this.exCheckOutDate = exCheckOutDate;
        this.roomNum = roomNum;
        this.peoples = peoples;
        this.fee = fee;
        this.paymentType = paymentType;
        this.paymentMethod = paymentMethod;
    }

    public String getUnique() {
        return unique;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getExCheckInDate() {
        return exCheckInDate;
    }

    public String getExCheckOutDate() {
        return exCheckOutDate;
    }

    public String getRoomNum() {
        return roomNum;
    }
    
    public String getPeoples() {
        return peoples;
    }

    public String getFee() {
        return fee;
    }

    public String getPaymentType() {
        return paymentType;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setExCheckInDate(String exCheckInDate) {
        this.exCheckInDate = exCheckInDate;
    }

    public void setExCheckOutDate(String exCheckOutDate) {
        this.exCheckOutDate = exCheckOutDate;
    }

    public void roomNum(String roomNum) {
        this.roomNum = roomNum;
    }
    
    public void setPeoples(String peoples) {
        this.peoples = peoples;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
