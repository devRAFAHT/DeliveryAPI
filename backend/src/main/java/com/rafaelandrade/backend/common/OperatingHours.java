package com.rafaelandrade.backend.common;

import jakarta.persistence.Embeddable;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Embeddable
public class OperatingHours {

    private DayOfWeek dayOfWeek;
    private LocalTime openingTime;
    private LocalTime closingTime;

    public OperatingHours(){
    }

    public OperatingHours(DayOfWeek dayOfWeek, LocalTime openingTime, LocalTime closingTime) {
        this.dayOfWeek = dayOfWeek;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }
}
