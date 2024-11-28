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
    private String roomSelection;
    private String stayCost;
    private String cardStatus;

    // 생성자
    public ReservationData(String name, String address, String phoneNumber, 
                           String checkInDate, String checkOutDate, String roomNumber,
                           String guestCount, String paymentMethod, String roomSelection, 
                           String stayCost, String cardStatus) {
        
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomNumber = roomNumber;
        this.guestCount = guestCount;
        this.paymentMethod = paymentMethod;
        this.roomSelection = roomSelection;
        this.stayCost = stayCost;
        this.cardStatus = cardStatus;
    }

    // Getter 메서드들 추가
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCheckInDate() { return checkInDate; }
    public String getCheckOutDate() { return checkOutDate; }
    public String getRoomNumber() { return roomNumber; }
    public String getGuestCount() { return guestCount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getRoomSelection() { return roomSelection; }
    public String getStayCost() { return stayCost; }
    public String getCardStatus() { return cardStatus; }
}
