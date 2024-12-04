package deu.hms.checkin;

public class CheckInData {
    private String uniqueNumber;
    private String name;
    private String phoneNumber;
    private String roomNumber;
    private String guestCount;
    private String stayCost;
    private String paymentMethod;
    private String status;
    private String requestDetails;

    public CheckInData(String uniqueNumber, String name, String phoneNumber, String roomNumber, 
                        String guestCount, String stayCost, String paymentMethod, String status, 
                        String requestDetails) {
        this.uniqueNumber = uniqueNumber;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.roomNumber = roomNumber;
        this.guestCount = guestCount;
        this.stayCost = stayCost;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.requestDetails = requestDetails;
    }

    public String toCSV() {
        return uniqueNumber + "," + name + "," + phoneNumber + "," + roomNumber + ","
                + guestCount + "," + stayCost + "," + paymentMethod + "," + status + "," + requestDetails;
    }

    // getter 메서드들
    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getGuestCount() {
        return guestCount;
    }

    public String getStayCost() {
        return stayCost;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public String getRequestDetails() {
        return requestDetails;
    }
}
