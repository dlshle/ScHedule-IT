/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shit;

/**
 *
 * @author Xuri
 */
public class SDate {
    
    private long time;
    private int year;
    private int month;
    private int date;
    private int hour;
    private int minute;
    private int second;
    
    //current time begins from Jan 1 1970
    //feb of leap years have 29 days(normally 28days)
    public static final long MINUTE = 64000;
    public static final long HOUR = 3600000;
    public static final long DAY = 86400000;
    public static final long WEEK = 604800000;
    public static final long LEAP_YEAR_IN_SECONDS = 31622400;
    public static final long YEAR_IN_SECONDS = 31536000;
    
    private static int[] leapYears = {1972,1976,1980,1984,1988,1992,1996,2000,2004,2008,2012,2016,2020,2024,2028,2032,2036,2040,2044,2048};
    
    public SDate(long time){
        this.time = time;
        Long leftOverSeconds = time/1000;
        this.year = getYear(leftOverSeconds);
    }
    
    public int binSearch(int[] arr, int target){
        if(arr.length==0)
            return -1;
        int left = 0;
        int right = arr.length;
        int index;
        while(left<=right){
            index = (left+right)/2;
            if(arr[index]<target){
                left=index+1;
            } else if(arr[index]>target){
                right=index;
            } else {
                return index;
            }
        }
        return -1;
    }
    
    public int getYear(long seconds){
        int year = 1970;
        while(seconds<LEAP_YEAR_IN_SECONDS){
            //if the year is a leap year, subtract LEAP_YEAR_IN_SECONDS from seconds, or subtract YEAR_IN_SECONDS from it.
            seconds-=(binSearch(leapYears,year)>-1?LEAP_YEAR_IN_SECONDS:YEAR_IN_SECONDS);
            year++;
        }
        //check left over
        //if it's a leap year, return year, if not, return year+1(because of the leftover)
        return (binSearch(leapYears, year)>-1?year:++year);
    }
    
    public long getYearLeftOver(long time){
        int year = 1970;
        long seconds = time/1000;
        while(seconds<LEAP_YEAR_IN_SECONDS){
            //if the year is a leap year, subtract LEAP_YEAR_IN_SECONDS from seconds, or subtract YEAR_IN_SECONDS from it.
            seconds-=(binSearch(leapYears,year)>-1?LEAP_YEAR_IN_SECONDS:YEAR_IN_SECONDS);
            year++;
        }
        return (binSearch(leapYears, year)>-1?seconds*1000:(seconds-YEAR_IN_SECONDS)*1000);
    }
    
    public int getMonth(long time){
        long left = getYearLeftOver(time);
        return -1;
    }
    
    public int getDate(long time){
        return -1;
    }
    
    public int getDay(long time){
        return -1;
    }
    
    public int getHour(long time){
        return -1;
    }
    
    public int getMinute(long time){
        return -1;
    }
    
    public int getSecond(long time){
        return -1;
    }
    
}
