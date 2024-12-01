/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomInfo;
import java.time.LocalDate;
import java.time.DayOfWeek;

/**
 *
 * @author Jimin
 */
class roomPricing {
    private static final int[] FLOOR_BASE_PRICE = {50000, 60000, 70000, 80000, 90000, 100000, 110000, 120000, 130000, 140000};
    private static final int WEEKEND_SURCHARGE = 50000;

    public int calculateTotalPrice(int floor, LocalDate checkInDate, LocalDate checkOutDate) {
        int totalPrice = 0;
        LocalDate currentDate = checkInDate;
        while (!currentDate.isAfter(checkOutDate.minusDays(1))) {
            totalPrice += calculateRoomPrice(floor, currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return totalPrice;
    }

    public int calculateRoomPrice(int floor, LocalDate date) {
        if (floor < 1 || floor > 10) {
            throw new IllegalArgumentException("유효하지 않은 층 번호입니다.");
        }
        int basePrice = FLOOR_BASE_PRICE[floor - 1];
        return isWeekend(date) ? basePrice + WEEKEND_SURCHARGE : basePrice;
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }
}
