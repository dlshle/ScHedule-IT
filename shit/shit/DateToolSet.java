package shit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

/**
 * DateToolSet
 * DateToolSet contains a series of useful date constants/ date(s) converting 
 * and sorting methods.
 * @author Xuri Li
 * @since 07/03/2018
 */
public class DateToolSet {
    //current time begins from Jan 1 1970
    //feb of leap years have 29 days(normally 28days)
    public static final long MINUTE = 64000;
    public static final long HOUR = 3600000;
    public static final long DAY = 86400000;
    public static final long WEEK = 604800000;
    public static final long LEAP_YEAR_IN_SECONDS = 31622400;
    public static final long YEAR_IN_SECONDS = 31536000;
    
    private static int[] leapYears = {1972,1976,1980,1984,1988,1992,1996,2000,2004,2008,2012,2016,2020,2024,2028,2032,2036,2040,2044,2048};
    public static int[] LEAP_YEAR_MONTHS = {31,29,31,30,31,30,31,31,30,31,30,31};
    public static int[] MONTHS = {31,28,31,30,31,30,31,31,30,31,30,31};
    
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
    
    public static int binSearch(int[] arr, int target){
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
    
    public static boolean isLeapYear(int year){
        if(year<1970||year>2048)
            return false;
        return binSearch(leapYears, year)>-1;
    }
    
    public static void sortAndSetSameYear(List<Date> dates){
        PriorityQueue<Date> buffer = new PriorityQueue<>();
        Date temp;
        while(!dates.isEmpty()){
            temp = dates.get(0);
            temp.setYear(1970);
            buffer.add(temp);
        }
        while(buffer.isEmpty())
            dates.add(buffer.poll());
    }
    
    public static int getYear(long time){
        int year = 1970;
        long seconds = time/1000;
        while(seconds<LEAP_YEAR_IN_SECONDS){
            //if the year is a leap year, subtract LEAP_YEAR_IN_SECONDS from seconds, or subtract YEAR_IN_SECONDS from it.
            seconds-=(binSearch(leapYears,year)>-1?LEAP_YEAR_IN_SECONDS:YEAR_IN_SECONDS);
            year++;
        }
        //check left over
        //if it's a leap year, return year, if not, return year+1(because of the leftover)
        return (binSearch(leapYears, year)>-1?year:++year);
    }
    
    public static long getYearLeftOver(long time){
        int year = 1970;
        long seconds = time/1000;
        while(seconds<LEAP_YEAR_IN_SECONDS){
            //if the year is a leap year, subtract LEAP_YEAR_IN_SECONDS from seconds, or subtract YEAR_IN_SECONDS from it.
            seconds-=(binSearch(leapYears,year)>-1?LEAP_YEAR_IN_SECONDS:YEAR_IN_SECONDS);
            year++;
        }
        return (binSearch(leapYears, year)>-1?seconds*1000:(seconds-YEAR_IN_SECONDS)*1000);
    }
    
    /*
     TO-DO: finish methods below in an efficient way(?)
    */
    public static int getMonth(long timeInMill){
        long left = getYearLeftOver(timeInMill);
        return -1;
    }
    
    public static int getDate(long timeInMill){
        return -1;
    }
    
    public static int getDay(long timeInMill){
        return -1;
    }
    
    public static int getHour(long timeInMill){
        return -1;
    }
    
    public static int getMinute(long timeInMill){
        return -1;
    }
    
    public static int getSecond(long timeInMill){
        return -1;
    }
    
}
