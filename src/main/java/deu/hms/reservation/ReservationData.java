/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.reservation;

/**
 *
 * @author adsd3
 */

public class ReservationData {
    private String name;
    private String address;
    private String phoneNumber;
    private String checkInDate;
    private String checkOutDate;
    private String roomNumber;
    private String guestCount;
    private String paymentMethod;
    private String status;
    private String stayCost;

    public ReservationData(String name, String address, String phoneNumber, String checkInDate, String checkOutDate,
                           String roomNumber, String guestCount, String paymentMethod, String status, String stayCost) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomNumber = roomNumber;
        this.guestCount = guestCount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.stayCost = stayCost;
    }

    // Getter and Setter methods for all fields
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCheckInDate() { return checkInDate; }
    public void setCheckInDate(String checkInDate) { this.checkInDate = checkInDate; }

    public String getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(String checkOutDate) { this.checkOutDate = checkOutDate; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getGuestCount() { return guestCount; }
    public void setGuestCount(String guestCount) { this.guestCount = guestCount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getStayCost() { return stayCost; }
    public void setStayCost(String stayCost) { this.stayCost = stayCost; }
}

