/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.hms.roomInfo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jimin
 */
class room implements Serializable {
    private final Map<LocalDate, Boolean> reservations = new HashMap<>();

    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut) {
        LocalDate date = checkIn;
        while (date.isBefore(checkOut)) {
            if (reservations.getOrDefault(date, false)) {
                return false;
            }
            date = date.plusDays(1);
        }
        return true;
    }

    public boolean reserve(LocalDate checkIn, LocalDate checkOut) {
        if (!isAvailable(checkIn, checkOut)) return false;

        LocalDate date = checkIn;
        while (date.isBefore(checkOut)) {
            reservations.put(date, true);
            date = date.plusDays(1);
        }
        return true;
    }
}
