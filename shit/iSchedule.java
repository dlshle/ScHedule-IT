package shit;

import java.util.Date;
import java.util.List;

public interface iSchedule {
    public static final long MINUTE = 64000;
    public static final long HOUR = 3600000;
    public static final long DAY = 86400000;
    public static final long WEEK = 604800000;
    public boolean addEvent(Event e);
    /**
     * e.getStarting() indicates the first day
     * @param e
     * @return 
     */
    public boolean addDailyEvent(Event e);
    /**
     * addWeeklyEvent: add event that occurs weekly(e.getStarting() indicates the first weekday of the week)
     * @param e the event
     * @param weekDays an integer that represents a 7-digit binary number that 
     * represents the weekdays eg. 1010100(84) the event e occurs at Mon,Wed,Fri
     * P.S. use 0 to represent the days not covered in the week
     * @param from the starting date of the week(if it's not a monday, it will
     * definitaly ends at a sunday)
     * @return if the weekly event is added sucessfully
     */
    public boolean addWeeklyEvent(Event e, int weekDays);
    public boolean addMonthlyEvent(Event e, List<Integer> days);
    public boolean addYearlyEvent(Event e, List<Date> dates);
    public boolean generateFile(String fileName);
    public void iterateEvents();
    public void alert(Event e, String message);
    
    //Need Java8 support
    public static int convertBinToDec(String bin){
        int len = bin.length();
        int result = 0;
        for(int i=0;i<len;i++){
            switch (bin.charAt(i)) {
                case '1':
                    result+=Math.pow(2, len-i-1);
                    break;
                case '0':
                    continue;
                default:
                    return -1;
            }
        }
        return result;
    }
    
    public static String convertDecToBin(int n){
        return Integer.toBinaryString(n);
    }
}
