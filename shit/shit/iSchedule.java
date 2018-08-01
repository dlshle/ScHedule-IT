package shit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public interface iSchedule {
    public static final long MINUTE = 64000;
    public static final long HOUR = 3600000;
    public static final long DAY = 86400000;
    public static final long WEEK = 604800000;
    public static HashSet<Integer> TIME_PERIOD_IDS = new HashSet<>();
    public static HashSet<Integer> EVENT_IDS = new HashSet<>();
    public boolean addEvent(Event e);
    public boolean addEvents(List<Event> events);
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
    public boolean contaninsEvent(Event e);
    public DaySchedule getDaySchedule(Date date);
    public WeekSchedule getWeekShedule(Date from);
    public MonthSchedule getMonthSchedule(Date month);
    public Event getEventById(int id);
    public List<Event> getEventsByParticipator(Personel participator);
    public List<Event> getEventsByParticipators(Set<Personel> participators);
    public List<Event> getEventsByExatParticipators(Set<Personel> participators);
    public List<Event> getEventsAfter(Date time);
    public List<Event> getEventsByTimePeriod(Date from, Date to);
    public List<Event> getEventsByTitle(String title);
    public boolean removeEventById(int id);
    public boolean removeEventByTitle(String title);
    public boolean removeEvent(Event e);
    public TimePeriod findLongestGap();
    public TimePeriod findLongestConsecutivePeriod();
    
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
    
    public static TimePeriod combineTimePeriods(List<TimePeriod> periods){
        long earliestStarting = 0;
        long latestEnding = 0;
        ArrayList<Event> events = new ArrayList<>();
        for(TimePeriod p:periods){
            if(p.getStarting()<earliestStarting)
                earliestStarting = p.getStarting();
            if(p.getEnding()>latestEnding)
                latestEnding = p.getEnding();
            events.addAll(p.getEvents());
        }
        TimePeriod result = new TimePeriod((int)(Math.random()*1000),earliestStarting,latestEnding);
        result.addEvents(events);
        return result;
    }
            
    public static int acquireEventId(){
        Random r = new Random((int)(Math.random()*123));
        int newId;
        do{
            newId = Math.abs(r.nextInt());
        }while(!EVENT_IDS.add(newId));
        return newId;
    }
    
    public static int checkAndAssignValidEventId(int id){
        if(id<1||EVENT_IDS.contains(id))
            return acquireEventId();
        EVENT_IDS.add(id);
        return id;
    }
}
