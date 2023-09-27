package com.groot;

import java.util.Calendar;

public class CalendarTest {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.printf("year: %d, month: %d, day: %d", year, month, day);
    }
}
